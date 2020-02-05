package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.*;
import team57.project.model.*;
import team57.project.repository.ClinicRepository;
import team57.project.repository.PatientRepository;
import team57.project.repository.RoomRepository;
import team57.project.repository.TermRoomRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private TermRoomRepository termRoomRepository;
    @Autowired
    private RoomRepository roomRepository;

    public Clinic findOne(Long id) {
        return clinicRepository.findById(id).orElseGet(null);
    }

    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    public void updateClinic(Clinic existClinic, ClinicDTO clinicDTO) {
        existClinic.setName(clinicDTO.getName());
        existClinic.setDescription(clinicDTO.getDescription());
        existClinic.setAddress(clinicDTO.getAddress());
        clinicRepository.save(existClinic);
    }

    public boolean clinicNameExists(String name, Long id) {

        List<Clinic> allClinics = this.findAll();
        for (Clinic c : allClinics) {
            if (c.getName().equals(name) && c.getId() != id) {
                return true;
            }
        }
        return false;
    }

    public void addNewRoom(Clinic clinic, RoomDTO roomDTO) {

        Room room = new Room(roomDTO.getName(), roomDTO.getNumber(), roomDTO.getRoomType(), false);
        clinic.getRooms().add(room);
        clinicRepository.save(clinic);
        Room r = roomRepository.findByNameAndNumber(roomDTO.getName(),roomDTO.getNumber());

        LocalDate nowDate = LocalDate.now();
        int today = nowDate.getDayOfWeek().getValue(); //redni broj dana u nedelji
        LocalDate temp = LocalDate.now();
        temp = temp.plusDays(1); //termini se prave od sutra pa do kraja sledece nedelje
        for(int i=0;i<12-today;i++){
            int n = temp.getDayOfWeek().getValue();
            if(n == 6 || n == 7){ //ako je dan subota ili nedelja nema termina
                System.out.println("Subota ili nedelja, ne kreiraju se termini.");
            }else{
                LocalTime startTime = LocalTime.of(6,0);
                LocalTime endTime = LocalTime.of(22,0);
                while(startTime.isBefore(endTime)){
                    TermRoom term = new TermRoom(temp,startTime,startTime.plusHours(1),true,r);
                    termRoomRepository.save(term);
                    startTime = startTime.plusHours(1);
                }
            }
            temp = temp.plusDays(1);
        }
    }



    public Clinic findByName(String name) {
        return clinicRepository.findByName(name);
    }

    public Clinic saveClinic(Clinic clinic) {
        return clinicRepository.save(clinic);

    }

    //Transakcija
    public Clinic rateClinic(Long clinicId, RateDTO rate) {
        try {

            System.out.println(rate);
            Patient p = this.patientRepository.findById(rate.getPatient_id()).orElse(null);
            System.out.println(p);
            Clinic c = this.clinicRepository.findById(clinicId).orElse(null);

            System.out.println("Rate: " + rate + "Before: " + c.getRating() * c.getNumberOfReviews());
            Double rated = c.getRating() * c.getNumberOfReviews() + rate.getRate();
            System.out.println("After: " + rated);
            c.setNumberOfReviews(c.getNumberOfReviews() + 1);
            rated = rated / c.getNumberOfReviews();
            c.setRating(rated);
            System.out.println("Final: " + c.getRating() + "  " + c.getNumberOfReviews());
            this.clinicRepository.save(c);
            p.getClinics().add(c);
            System.out.println(p);
            this.patientRepository.save(p);
            return c;
        } catch (NullPointerException e) {
            return null;
        }

    }

    public List<ClinicDTO> findFreeClinics(AvailableDoctorRequest adr) {

        System.out.println(adr);
        List<ClinicDTO> clinicDTOS = new ArrayList<>();
        List<Clinic> clinics = new ArrayList<>();

        //doctors that have 1 or more free terms
        clinics = this.clinicRepository.getFreeClinics(adr.getIdExamType(),adr.getDate());

        for (Clinic c : clinics) {
            for ( Doctor doctor : c.getDoctors()){
                boolean isAbsent = isDoctorAbsent(adr, doctor);
                if (!isAbsent) {
                    boolean add = true;
                    for(ClinicDTO dto : clinicDTOS){
                        if(dto.getId().equals(c.getId())){
                            add = false;
                        }
                    }
                    if(add)
                        clinicDTOS.add(new ClinicDTO(c.getId(),c.getName(),c.getAddress(),c.getDescription(),c.getRating(),c.getNumberOfReviews()));

                }
            }

        }
        System.out.println(clinicDTOS);

        return clinicDTOS;
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
