package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team57.project.dto.DoctorDTO;
import team57.project.dto.DoctorRating;
import team57.project.dto.DoctorSearch;
import team57.project.dto.RateDTO;
import team57.project.model.*;
import team57.project.repository.DoctorRepository;
import team57.project.repository.PatientRepository;
import team57.project.service.DoctorService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
//@Transactional(readOnly = true)
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityServiceImpl authService;


    @Override
    public Doctor findOne(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public Doctor findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }


    @Override
    public String doctorExists(String email, String serialNumber) {
        String msg = "";
        boolean exists = false;
        Doctor doctor = doctorRepository.findByEmail(email);
        if(doctor != null){
            msg += "Doctor with that email aready exists in the clinic. ";
            exists = true;
        }
        Doctor doctor1 = doctorRepository.findBySerialNumber(serialNumber);
        if(doctor1 != null){
            msg += "Doctor with that serial number alreay exists in the clinic.";
            exists = true;
        }

        if(exists){
            return msg;
        }
        return null;
    }

    @Override
    public void addDoctor(DoctorDTO doctorDTO, Clinic clinic) {

        Doctor doctor = new Doctor(doctorDTO.getName(),doctorDTO.getSurname(),doctorDTO.getEmail(),passwordEncoder.encode(doctorDTO.getPassword()),
                doctorDTO.getAddress(), doctorDTO.getCity(), doctorDTO.getCountry(), doctorDTO.getPhoneNumber(), doctorDTO.getSerialNumber(),
                doctorDTO.getWorkingHoursStart(), doctorDTO.getWorkingHoursEnd());
        doctor.setClinic(clinic);
        for (Long id : doctorDTO.getExamTypesId()) {
            for (ExamType et : clinic.getExamTypes()) {
                if (et.getId() == id) {
                    doctor.getExamTypes().add(et);
                }
            }
        }
        for (Long id : doctorDTO.getSurgeryTypesId()) {
            for (SurgeryType st : clinic.getSurgeryTypes()) {
                if (st.getId() == id) {
                    doctor.getSurgeryTypes().add(st);
                }
            }
        }
        List<Authority> auth = authService.findByname("ROLE_DOCTOR");
        doctor.setAuthorities(auth);
        doctor.setEnabled(true);
        doctorRepository.save(doctor);
    }

    @Override
    public boolean removeDoctor(Doctor doctor) {

        for(FastAppointment fa: doctor.getFastAppointments()){
            if(fa.getDateTime().isAfter(LocalDateTime.now()) || (fa.getDateTime().isBefore(LocalDateTime.now()) &&
                    fa.getDateTime().plusMinutes(fa.getDuration()).isAfter(LocalDateTime.now()))){
                return false;
            }
        }

        //ovde jos ide kod za proveru da li se nalazi u zakazanim pregledima ili operacijama

        doctor.setRemoved(true);
        doctorRepository.save(doctor);
        return true;
    }

    @Override
    public List<DoctorSearch> searchForDoctors(DoctorSearch doctorSearch, Long clinicId) {

        List<DoctorSearch> doctorsFound = new ArrayList<DoctorSearch>();
        List<Doctor> doctors = doctorRepository.findDoctors(clinicId);
        for(Doctor doctor : doctors){
            if(doctor.isRemoved())
                continue;
            boolean nameCorrect = true;
            boolean surnameCorrect = true;
            boolean serialNumberCorrect = true;
            if(!doctorSearch.getName().equals("") && doctorSearch.getName() != null){
                if(doctor.getName().toLowerCase().contains(doctorSearch.getName().toLowerCase())){
                    nameCorrect = true;
                }else{
                    nameCorrect = false;
                }
            }
            if(!doctorSearch.getSurname().equals("") && doctorSearch.getSurname() != null){
                if(doctor.getSurname().toLowerCase().contains(doctorSearch.getSurname().toLowerCase())){
                    surnameCorrect = true;
                }else{
                    surnameCorrect = false;
                }
            }
            if(!doctorSearch.getSerialNumber().equals("") && doctorSearch.getSerialNumber() != null){

                if(doctor.getSerialNumber().startsWith(doctorSearch.getSerialNumber())){
                    serialNumberCorrect = true;
                }else{
                    serialNumberCorrect = false;
                }
            }
            if(nameCorrect && surnameCorrect && serialNumberCorrect){
                doctorsFound.add(new DoctorSearch(doctor.getId(),doctor.getName(), doctor.getSurname(),doctor.getSerialNumber()));
            }

        }

        return doctorsFound;

    }

    @Override
    public List<DoctorSearch> getAllDoctors(Long idClinic) {
        List<DoctorSearch> doctorsAll = new ArrayList<DoctorSearch>();
        List<Doctor> doctors = new ArrayList<Doctor>();
        doctors = doctorRepository.findDoctors(idClinic);
        for(Doctor doctor: doctors){
            if(!doctor.isRemoved()){
                doctorsAll.add(new DoctorSearch(doctor.getId(),doctor.getName(),doctor.getSurname(),doctor.getSerialNumber()));
            }
        }
        return doctorsAll;
    }

    @Override
    public List<DoctorRating> getAllDoctorsRating(Long idClinic) {
        List<DoctorRating> doctorsAll = new ArrayList<DoctorRating>();
        List<Doctor> doctors = new ArrayList<Doctor>();
        doctors = doctorRepository.findDoctors(idClinic);
        for(Doctor doctor: doctors){
            if(!doctor.isRemoved()){
                doctorsAll.add(new DoctorRating(doctor));
            }
        }
        return doctorsAll;
    }


    @Override
    public Doctor save(Doctor d) {
        return this.doctorRepository.save(d);
    }

    @Override
    //  @Transactional()
    public Doctor rateDoctor(Long doctorId, RateDTO rate) {
        try {
            System.out.println("Test");

            System.out.println(rate);
            Patient p = this.patientRepository.findById(rate.getPatient_id()).orElse(null);
            System.out.println(p);
            Doctor d = this.doctorRepository.findById(doctorId).orElse(null);

            System.out.println("Rate: " + rate);
            System.out.println("Before: " + d.getRating() * d.getNumberOfReviews());
            Double rated = d.getRating() * d.getNumberOfReviews() + rate.getRate();
            System.out.println("After: " + rated);
            d.setNumberOfReviews(d.getNumberOfReviews() + 1);
            rated = rated / d.getNumberOfReviews();
            d.setRating(rated);
            System.out.println("Final: " + d.getRating() + "  " + d.getNumberOfReviews());
            this.doctorRepository.save(d);
            p.getDoctors().add(d);
            System.out.println(p);
            this.patientRepository.save(p);
            return d;
        } catch (NullPointerException e) {
            return null;
        }

    }


}
