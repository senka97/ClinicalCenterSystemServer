package team57.project.service;


import team57.project.dto.DoctorDTO;
import team57.project.dto.DoctorSearch;
import team57.project.dto.RateDTO;
import team57.project.model.Clinic;
import team57.project.model.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor findOne(Long id);
    Doctor findByEmail(String email);
    String doctorExists(String email, String serialNumber);
    void addDoctor(DoctorDTO doctorDTO, Clinic clinic);
    boolean removeDoctor(Doctor doctor);
    List<DoctorSearch> searchForDoctors(DoctorSearch doctorSearch, Long clinicId);
    List<DoctorSearch> getAllDoctors(Long idClinic);

    Doctor save(Doctor d);

    Doctor rateDoctor(Long doctorId, RateDTO rate);
}
