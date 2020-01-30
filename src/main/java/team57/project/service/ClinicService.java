package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.ClinicDTO;
import team57.project.dto.RateDTO;
import team57.project.dto.RoomDTO;
import team57.project.model.Clinic;
import team57.project.model.Patient;
import team57.project.model.Room;
import team57.project.repository.ClinicRepository;
import team57.project.repository.PatientRepository;

import java.util.List;
import java.util.Set;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private PatientRepository patientRepository;

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
}
