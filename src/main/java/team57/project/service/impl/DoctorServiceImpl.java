package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team57.project.dto.*;
import team57.project.model.*;
import team57.project.repository.DoctorRepository;
import team57.project.repository.PatientRepository;
import team57.project.repository.TermDoctorRepository;
import team57.project.service.DoctorService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
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
    @Autowired
    private TermDoctorRepository termDoctorRepository;


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

        Doctor d = doctorRepository.findByEmail(doctorDTO.getEmail());
        LocalDate nowDate = LocalDate.now();
        int today = nowDate.getDayOfWeek().getValue(); //redni broj dana u nedelji
        LocalDate temp = LocalDate.now();
        temp = temp.plusDays(1); //termini se prave od sutra pa do kraja sledece nedelje
        for(int i=0;i<12-today;i++){
            int n = temp.getDayOfWeek().getValue();
            if(n == 6 || n == 7){ //ako je dan subota ili nedelja nema termina
                System.out.println("Subota ili nedelja, ne kreiraju se termini.");
            }else{
                LocalTime wokingHoursStart = d.getWorkingHoursStart();
                while(wokingHoursStart.isBefore(d.getWorkingHoursEnd())){
                    TermDoctor term = new TermDoctor(temp,wokingHoursStart,wokingHoursStart.plusHours(1),true,d);
                    termDoctorRepository.save(term);
                    wokingHoursStart = wokingHoursStart.plusHours(1);
                }
            }
            temp = temp.plusDays(1);
        }
    }


    @Override
    public boolean removeDoctor(Doctor doctor) {

        /*for(FastAppointment fa: doctor.getFastAppointments()){
            if(fa.getDateFA().isAfter(LocalDate.now())){ //ako je u buducnosti odmah vrati false
                return false;
            }else if(fa.getDateFA().equals(LocalDate.now())){ //ako je danas proveri vreme
                if((fa.getTimeFA().plusHours(1).isAfter(LocalTime.now()))){ //dovoljno je proveriti samo da li je kraj pregleda posle sadasnjeg trenutka
                    return false;
                }
            }
        }*/

        List<TermDoctor> scheduledTerms = doctorRepository.findScheduledTerms(doctor.getId(),LocalDate.now(),LocalTime.now());

        if(scheduledTerms.size()!=0){
            return false;
        }

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
    @Transactional
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



    @Override
    @Scheduled(cron = "${doctor.cron}")
    public void probaZaScheduling() {
        //System.out.println("sada");
    }

    @Override
    public List<DoctorFA> findAvailableDoctors(Clinic clinic, AvailableDoctorRequest adr) {
        List<DoctorFA> doctorsFA = new ArrayList<DoctorFA>();
        List<Doctor> doctors = new ArrayList<Doctor>();

        doctors = doctorRepository.getAvailableDoctors(clinic.getId(),adr.getIdExamType(),adr.getDate(),adr.getTime());
        for(Doctor doctor: doctors){
            boolean isAbsent = isDoctorAbsent(adr, doctor);
            if (!isAbsent) {
                doctorsFA.add(new DoctorFA(doctor));
            }
        }

        return doctorsFA;

    }


    @Override
    public List<DoctorRating> findFreeDoctors(Clinic clinic, AvailableDoctorRequest adr) {

        List<DoctorRating> doctorsRating = new ArrayList<>();
        List<Doctor> doctors = new ArrayList<>();

        //doctors that have 1 or more free terms
        doctors = this.doctorRepository.getFreeDoctors(clinic.getId(), adr.getIdExamType(), adr.getDate());
        for (Doctor doctor : doctors) {

            boolean isAbsent = isDoctorAbsent(adr, doctor);
            if (!isAbsent) {
                doctorsRating.add(new DoctorRating(doctor));
            }
        }


        return doctorsRating;
    }

    @Override
    public List<AppointmentDTO> findFreeTerms(Long doctorId, AvailableDoctorRequest adr) {
        List<AppointmentDTO> appointments = new ArrayList<>();
        List<TermDoctor> terms = this.termDoctorRepository.getFreeTerms(doctorId,adr.getIdExamType(),adr.getDate());
        for(TermDoctor term : terms){
            appointments.add(new AppointmentDTO(term));
        }

        return appointments;
    }

    @Override
    public List<DoctorFA> searchForDoctorsExamTypes(Clinic clinic, ExamType examType) {

        List<Doctor> doctors = doctorRepository.searchDoctorsExamType(clinic.getId(),examType.getId());
        List<DoctorFA> doctorsFA = new ArrayList<DoctorFA>();
        for(Doctor d: doctors){
            doctorsFA.add(new DoctorFA(d));
        }

        return doctorsFA;
    }


    private boolean isDoctorAbsent(AvailableDoctorRequest adr, Doctor doctor) {
        boolean isAbsent = false;
        for (Absence a : doctor.getAbsences()) {
            if (a.getStatusOfAbsence().equals("APPROVED")) {
                if (a.getStartDate().minusDays(1).isBefore(adr.getDate()) && a.getEndDate().plusDays(1).isAfter(adr.getDate())) {
                    isAbsent = true;
                }
            }
        }
        return isAbsent;
    }


}
