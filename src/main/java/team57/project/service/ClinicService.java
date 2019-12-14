package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.ClinicDTO;
import team57.project.model.Clinic;
import team57.project.repository.ClinicRepository;

import java.util.List;

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

    public Clinic findByName(String name) {return clinicRepository.findByName(name);}

    public  Clinic saveClinic(Clinic clinic){
        return clinicRepository.save(clinic);
    }
}
