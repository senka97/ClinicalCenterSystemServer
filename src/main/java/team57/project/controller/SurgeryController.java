package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.SurgeryDTO;
import team57.project.dto.SurgeryWKDTO;
import team57.project.model.Surgery;
import team57.project.service.SurgeryService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/surgery")
@CrossOrigin("http://localhost:4200")
public class SurgeryController {
    @Autowired
    private SurgeryService surgeryService;


    @RequestMapping(value = "/getSurgeries/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_PATIENT')")
    public List<SurgeryDTO> getSurgeries(@PathVariable("id") Long id) {
        List<Surgery> surgeries = this.surgeryService.findByPatientId(id);
        List<SurgeryDTO> surgeryDTO = new ArrayList<SurgeryDTO>();
        for (Surgery surgery : surgeries) {
            SurgeryDTO dto = new SurgeryDTO();
            dto.setDate(surgery.getDate().toString());
            dto.setStartTime(surgery.getStartTime());
            dto.setEndTime(surgery.getEndTime());

            dto.setSurgeryType(surgery.getSurgeryType().getName());
            surgeryDTO.add(dto);
        }
        return surgeryDTO;
    }

    @RequestMapping(value = "/getDoctorsSurgeries/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    public List<SurgeryWKDTO> getDoctorsSurgeries(@PathVariable("id") Long id) {
        List<Surgery> surgeries = this.surgeryService.findDoctorsSurgeries(id);
        List<SurgeryWKDTO> surgeryDTO = new ArrayList<SurgeryWKDTO>();
        for (Surgery surgery : surgeries) {
            SurgeryWKDTO dto = new SurgeryWKDTO(surgery);
            surgeryDTO.add(dto);
        }
        return surgeryDTO;
    }
}
