package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.PriceTag;
import team57.project.dto.SurgeryTypeDTO;
import team57.project.model.Clinic;
import team57.project.model.ExamType;
import team57.project.model.SurgeryType;
import team57.project.service.ClinicService;
import team57.project.service.impl.SurgeryTypeServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "api/surgeryTypes")
@CrossOrigin("http://localhost:4200")
public class SurgeryTypeController {

    @Autowired
    private SurgeryTypeServiceImpl surgeryTypeService;
    @Autowired
    private ClinicService clinicService;

    @GetMapping(value="/getSurgeryType/{id}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getSurgeryType(@PathVariable("id") Long id)
    {
        try {
            SurgeryType surgeryType = surgeryTypeService.findOne(id);
            if(surgeryType.isRemoved()) {
                return ResponseEntity.status(HttpStatus.GONE).build();
            }

            SurgeryTypeDTO surgeryTypeDTO = new SurgeryTypeDTO(surgeryType.getId(),surgeryType.getName(),surgeryType.getDescription(),surgeryType.getPrice(), surgeryType.getDiscount(),surgeryType.getDuration());
            return new ResponseEntity(surgeryTypeDTO, HttpStatus.OK);

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/getSurgeryTypes/{idClinic}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getSurgeryTypes(@PathVariable("idClinic") Long idClinic)
    {
        try {
            Clinic clinic = clinicService.findOne(idClinic);
            Set<SurgeryType> surgeryTypes = clinic.getSurgeryTypes();
            List<SurgeryTypeDTO> surgeryTypesDTO = new ArrayList<>();
            for(SurgeryType surgeryType: surgeryTypes){
                if(!surgeryType.isRemoved()){
                    surgeryTypesDTO.add(new SurgeryTypeDTO(surgeryType));
                }
            }

            return new ResponseEntity(surgeryTypesDTO, HttpStatus.OK);

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="/addSurgeryType/{idClinic}", consumes="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> addSurgeryType(@PathVariable("idClinic") Long idClinic, @RequestBody SurgeryTypeDTO surgeryTypeDTO)
    {
        if(surgeryTypeDTO.getName() == null || surgeryTypeDTO.getName().equals("") || surgeryTypeDTO.getDescription() == null || surgeryTypeDTO.getDescription().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name and description are mandatory.");
        }
        try {
            Clinic clinic = clinicService.findOne(idClinic);

            if(surgeryTypeService.addSurgeryType(clinic,surgeryTypeDTO)){
                return new ResponseEntity(HttpStatus.OK);

            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Surgery type with that name already exists in the clinic.");
            }

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/updateSurgeryType/{idClinic}/{id}", consumes="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> updateSurgeryType(@PathVariable("idClinic") Long idClinic, @PathVariable("id") Long id, @RequestBody SurgeryTypeDTO surgeryTypeDTO)
    {
        if(surgeryTypeDTO.getName() == null || surgeryTypeDTO.getName().equals("") || surgeryTypeDTO.getDescription() == null || surgeryTypeDTO.getDescription().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name and description are mandatory.");
        }
        try {
            Clinic clinic = clinicService.findOne(idClinic);
            SurgeryType surgeryType = surgeryTypeService.findOne(id);
            if(surgeryType.isRemoved()){
                return ResponseEntity.status(HttpStatus.GONE).build();
            }

            String msg = surgeryTypeService.updateSurgeryType(surgeryType,surgeryTypeDTO,clinic);
            if(msg == null){
                return new ResponseEntity(HttpStatus.OK);

            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            }

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value="/removeSurgeryType/{idClinic}/{id}", consumes="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> removeSurgeryType(@PathVariable("id") Long id, @PathVariable("idClinic") Long idClinic)
    {
        try {
            SurgeryType surgeryType = surgeryTypeService.findOne(id);
            Clinic clinic = clinicService.findOne(idClinic);
            if(surgeryType.isRemoved()){
                return ResponseEntity.status(HttpStatus.GONE).build();
            }

            if(surgeryTypeService.removeSurgeryType(surgeryType, clinic)){
                return new ResponseEntity(HttpStatus.OK);

            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This surgery type can't be removed because the surgery of this type is happening now or is arranged in the future.");
            }

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/getSurgeryPrice/{idClinic}", produces="application/json")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> getExamPrice(@PathVariable("idClinic") Long idClinic){

        try{
            Clinic clinic = this.clinicService.findOne(idClinic);
            List<PriceTag> surgeryPrice = new ArrayList<PriceTag>();
            for(SurgeryType st: clinic.getSurgeryTypes()){
                if(!st.isRemoved()){
                    surgeryPrice.add(new PriceTag(st.getName(),st.getPrice(),st.getDiscount()));
                }
            }
            return new ResponseEntity(surgeryPrice, HttpStatus.OK);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
