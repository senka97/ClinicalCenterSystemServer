package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.IncomeDate;
import team57.project.dto.MedicalExamDTO;
import team57.project.dto.MedicalExamWKDTO;
import team57.project.model.Clinic;
import team57.project.model.MedicalExam;
import team57.project.service.ClinicService;
import team57.project.service.MedicalExamService;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/medicalExams")
@CrossOrigin("http://localhost:4200")
public class MedicalExamController {
    @Autowired
    private MedicalExamService medicalExamService;
    @Autowired
    private ClinicService clinicService;

    @RequestMapping(value = "/getMedicalExam/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_PATIENT')")
    public List<MedicalExamDTO> getMedicalExam(@PathVariable("id") Long id) {
        List<MedicalExam> medicalExams = this.medicalExamService.findByPatientId(id);
        List<MedicalExamDTO> examsDTO = new ArrayList<MedicalExamDTO>();
        System.out.println(medicalExams);
        for(MedicalExam exam : medicalExams){
            if(exam.getExamRoom() != null){
                MedicalExamDTO dto = new MedicalExamDTO();
                dto.setDate(exam.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
                dto.setDoctor(exam.getDoctor().getName());
                dto.setEndTime(exam.getEndTime());
                dto.setStartTime(exam.getStartTime());
                dto.setExamType(exam.getExamType().getName());
                examsDTO.add(dto);
            }

        }
        return examsDTO;
    }

    @RequestMapping(value = "/getDoctorsExams/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_NURSE') or hasRole('ROLE_PATIENT')")
    public List<MedicalExamWKDTO> getDoctorsExams(@PathVariable("id") Long id) {
        List<MedicalExam> medicalExams = this.medicalExamService.findDoctorsExams(id);
        List<MedicalExamWKDTO> examsDTO = new ArrayList<MedicalExamWKDTO>();

        for(MedicalExam exam : medicalExams){
            MedicalExamWKDTO dto = new MedicalExamWKDTO(exam);
            examsDTO.add(dto);
        }
        return examsDTO;
    }

    @PostMapping(value="/getIncome/{id}")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN')")
    public ResponseEntity<?> getIncome(@PathVariable("id") Long id, @RequestBody IncomeDate incomeDate){

        if(incomeDate.getStartDate() == null || incomeDate.getEndDate() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date and end date are mandatory.");
        }

        try{
            Clinic clinic = clinicService.findOne(id);
            double income = medicalExamService.getIncome(clinic,incomeDate);
            return new ResponseEntity(income,HttpStatus.OK);

        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
