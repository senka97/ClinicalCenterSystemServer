package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.ExamTypeDTO;
import team57.project.dto.ExamTypeReg;
import team57.project.dto.SurgeryTypeReg;
import team57.project.dto.TypeRegDoctor;
import team57.project.model.Clinic;
import team57.project.model.ExamType;
import team57.project.model.SurgeryType;
import team57.project.service.ClinicService;
import team57.project.service.impl.ExamTypeServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "api/examTypes")
@CrossOrigin("http://localhost:4200")
public class ExamTypeController {

       @Autowired
       private ExamTypeServiceImpl examTypeService;
       @Autowired
       private ClinicService clinicService;

       @GetMapping(value="/getExamType/{id}", produces="application/json")
       @PreAuthorize("hasRole('CLINIC_ADMIN')")
       public ResponseEntity<?> getExamType(@PathVariable("id") Long id)
       {
              try {
                     ExamType examType = examTypeService.findOne(id);
                     if(examType.isRemoved()) {
                            return ResponseEntity.status(HttpStatus.GONE).build();
                     }

                     ExamTypeDTO examTypeDTO = new ExamTypeDTO(examType.getId(),examType.getName(),examType.getDescription(),examType.getPrice(), examType.getDiscount());
                     return new ResponseEntity(examTypeDTO, HttpStatus.OK);

              } catch(NullPointerException e){

                     return ResponseEntity.notFound().build();
              }
       }

       @GetMapping(value="/getExamTypes/{idClinic}", produces="application/json")
       @PreAuthorize("hasRole('CLINIC_ADMIN')")
       public ResponseEntity<?> getExamTypes(@PathVariable("idClinic") Long idClinic)
       {
              try {
                     Clinic clinic = clinicService.findOne(idClinic);
                     Set<ExamType> examTypes = clinic.getExamTypes();
                     List<ExamTypeDTO> examTypesDTO = new ArrayList<>();
                     for(ExamType examType: examTypes){
                            if(!examType.isRemoved()){
                                   examTypesDTO.add(new ExamTypeDTO(examType));
                            }
                     }

                     return new ResponseEntity(examTypesDTO, HttpStatus.OK);

              } catch(NullPointerException e){

                     return ResponseEntity.notFound().build();
              }
       }

       @GetMapping(value="/getTypesForReg/{idClinic}", produces="application/json")
       @PreAuthorize("hasRole('CLINIC_ADMIN')")
       public ResponseEntity<?> getTypesForReg(@PathVariable("idClinic") Long idClinic){

              try{
                     Clinic clinic = clinicService.findOne(idClinic);
                     TypeRegDoctor typeRegDoctor = new TypeRegDoctor();
                     for(ExamType et: clinic.getExamTypes()){
                            if(!et.isRemoved()){
                                   typeRegDoctor.getExamTypeRegs().add(new ExamTypeReg(et));
                            }
                     }
                     for(SurgeryType st: clinic.getSurgeryTypes()){
                            if(!st.isRemoved()){
                                   typeRegDoctor.getSurgeryTypeRegs().add(new SurgeryTypeReg(st));
                            }
                     }

                     return new ResponseEntity(typeRegDoctor,HttpStatus.OK);
              }catch(NullPointerException e){

                     return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
              }
       }

       @PostMapping(value="/addExamType/{idClinic}", consumes="application/json")
       @PreAuthorize("hasRole('CLINIC_ADMIN')")
       public ResponseEntity<?> addExamType(@PathVariable("idClinic") Long idClinic, @RequestBody ExamTypeDTO examTypeDTO)
       {
              if(examTypeDTO.getName() == null || examTypeDTO.getName().equals("") || examTypeDTO.getDescription() == null || examTypeDTO.getDescription().equals("")){
                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name and description are mandatory.");
              }
              try {
                     Clinic clinic = clinicService.findOne(idClinic);

                     if(examTypeService.addExamType(clinic,examTypeDTO)){
                            return new ResponseEntity(HttpStatus.OK);

                     }else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exam type with that name already exists in the clinic.");
                     }

              } catch(NullPointerException e){

                     return ResponseEntity.notFound().build();
              }
       }

       @PutMapping(value="/updateExamType/{idClinic}/{id}", consumes="application/json")
       @PreAuthorize("hasRole('CLINIC_ADMIN')")
       public ResponseEntity<?> updateExamType(@PathVariable("idClinic") Long idClinic, @PathVariable("id") Long id, @RequestBody ExamTypeDTO examTypeDTO)
       {
              if(examTypeDTO.getName() == null || examTypeDTO.getName().equals("") || examTypeDTO.getDescription() == null || examTypeDTO.getDescription().equals("")){
                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name and description are mandatory.");
              }
              try {
                     Clinic clinic = clinicService.findOne(idClinic);
                     ExamType examType = examTypeService.findOne(id);
                     if(examType.isRemoved()){
                            return ResponseEntity.status(HttpStatus.GONE).build();
                     }

                     String msg = examTypeService.updateExamType(examType,examTypeDTO,clinic);
                     if(msg == null){
                            return new ResponseEntity(HttpStatus.OK);

                     }else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
                     }

              } catch(NullPointerException e){

                     return ResponseEntity.notFound().build();
              }
       }

       @DeleteMapping(value="/removeExamType/{idClinic}/{id}", consumes="application/json")
       @PreAuthorize("hasRole('CLINIC_ADMIN')")
       public ResponseEntity<?> removeExamType(@PathVariable("id") Long id, @PathVariable("idClinic") Long idClinic)
       {
              try {
                     ExamType examType = examTypeService.findOne(id);
                     Clinic clinic = clinicService.findOne(idClinic);
                     if(examType.isRemoved()){
                            return ResponseEntity.status(HttpStatus.GONE).build();
                     }

                     if(examTypeService.removeExamType(examType, clinic)){
                            return new ResponseEntity(HttpStatus.OK);

                     }else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This exam type can't be removed because the exam of this type is happening now or is arranged in the future.");
                     }

              } catch(NullPointerException e){

                     return ResponseEntity.notFound().build();
              }
       }
}
