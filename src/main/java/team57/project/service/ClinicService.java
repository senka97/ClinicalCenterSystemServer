package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.ClinicDTO;
import team57.project.dto.RoomDTO;
import team57.project.model.Clinic;
import team57.project.model.Room;
import team57.project.repository.ClinicRepository;

import java.util.List;
import java.util.Set;

@Service
public class ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public Clinic findOne(Long id) {
        return clinicRepository.findById(id).orElseGet(null);
    }

    public List<Clinic> findAll() { return clinicRepository.findAll(); }

    public void updateClinic(Clinic existClinic, ClinicDTO clinicDTO){
        existClinic.setName(clinicDTO.getName());
        existClinic.setDescription(clinicDTO.getDescription());
        existClinic.setAddress(clinicDTO.getAddress());
        clinicRepository.save(existClinic);
    }

    public Set<Room> findRooms(Long id){

        Clinic clinic = this.findOne(id);
        return clinic.getRooms();
    }

    public void addNewRoom(Clinic clinic, RoomDTO roomDTO) {

        Room room = new Room(roomDTO.getName(), roomDTO.getNumber(), roomDTO.getRoomType(), false);
        clinic.getRooms().add(room);
        clinicRepository.save(clinic);
    }

    public Clinic findByName(String name) {return clinicRepository.findByName(name);}

    public  Clinic saveClinic(Clinic clinic){
        return clinicRepository.save(clinic);

    }
}
