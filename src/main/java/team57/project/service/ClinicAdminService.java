package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.ClinicAdmin;
import team57.project.repository.ClinicAdminRepository;

@Service
public class ClinicAdminService {

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;

    public ClinicAdmin saveClinicAdmin(ClinicAdmin clinicAdmin) {
        return clinicAdminRepository.save(clinicAdmin);
    }

    public ClinicAdmin findOne(Long id) { return clinicAdminRepository.findById(id).orElseGet(null); }

    public void remove(Long id) { clinicAdminRepository.deleteById(id); }
}
