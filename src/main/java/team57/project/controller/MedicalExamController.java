package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.MedicalExamDTO;
import team57.project.model.MedicalExam;
import team57.project.model.Patient;
import team57.project.service.MedicalExamService;
import team57.project.service.PatientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/medicalExams")
@CrossOrigin("http://localhost:4200")
public class MedicalExamController {
    @Autowired
    private MedicalExamService medicalExamService;
    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/getMedicalExam/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_PATIENT')")
    public List<MedicalExamDTO> getMedicalExam(@PathVariable("id") Long id) {
        Patient p = this.patientService.findOne(id);

        System.out.println("TEEEEEEEEEEEESTT");
        System.out.println("TEEEEEEEEEEEESTT");
        System.out.println("TEEEEEEEEEEEESTT");
        System.out.println(p.getName());
      //  System.out.println(p.getMedicalExams().toString());
        //System.out.println(this.medicalExamService.findByPatientId(id));
        List<MedicalExam> medicalExams = this.medicalExamService.findAll();
        List<MedicalExamDTO> examsDTO = new ArrayList<MedicalExamDTO>();
        for(MedicalExam exam : medicalExams){
            MedicalExamDTO dto = new MedicalExamDTO();
            dto.setDate(exam.getDate().toLocalDate().toString());

            dto.setDoctor(exam.getDoctor().getName());
            dto.setEndTime(exam.getEndTime());
            dto.setExamRoom(exam.getExamRoom().getName());
            dto.setStartTime(exam.getStartTime());
            dto.setExamType(exam.getExamType().getName());
            examsDTO.add(dto);
        }
        System.out.println(examsDTO);
        return examsDTO;
    }
}
