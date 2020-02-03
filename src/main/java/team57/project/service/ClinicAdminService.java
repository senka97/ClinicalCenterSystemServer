package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import team57.project.model.Clinic;
import team57.project.model.ClinicAdmin;
import team57.project.repository.ClinicAdminRepository;

import java.util.Set;

@Service
public class ClinicAdminService {

    @Autowired
    private ClinicAdminRepository clinicAdminRepository;



    public ClinicAdmin saveClinicAdmin(ClinicAdmin clinicAdmin) {
        return clinicAdminRepository.save(clinicAdmin);
    }

    public ClinicAdmin findOne(Long id) { return clinicAdminRepository.findById(id).orElseGet(null); }

    public void remove(Long id) { clinicAdminRepository.deleteById(id); }

    public Clinic findMyClinic(){

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        ClinicAdmin clinicAdmin = clinicAdminRepository.findByEmail(email);
        return clinicAdmin.getClinic();

    }

    public Set<ClinicAdmin> findClinicAdmins(Long clinicId){
        return this.clinicAdminRepository.getClinicAdmins(clinicId);

    }
}
