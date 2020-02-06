package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.SurgeryTypeDTO;
import team57.project.model.*;
import team57.project.repository.SurgeryRepository;
import team57.project.repository.SurgeryTypeRepository;
import team57.project.service.SurgeryTypeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SurgeryTypeServiceImpl implements SurgeryTypeService {

    @Autowired
    private SurgeryTypeRepository surgeryTypeRepository;
    @Autowired
    private SurgeryRepository surgeryRepository;


    @Override
    public SurgeryType findOne(Long id) {
        return surgeryTypeRepository.findById(id).orElse(null);
    }

    @Override
    public boolean addSurgeryType(Clinic clinic, SurgeryTypeDTO surgeryTypeDTO) {
        for(SurgeryType st : clinic.getSurgeryTypes()){
            if(st.getName().equals(surgeryTypeDTO.getName()) && !st.isRemoved()){
                return false;
            }
        }
        SurgeryType surgeryType = new SurgeryType(surgeryTypeDTO.getName(),surgeryTypeDTO.getDescription(),surgeryTypeDTO.getPrice(),surgeryTypeDTO.getDiscount());
        surgeryType.getClinics().add(clinic);
        surgeryTypeRepository.save(surgeryType);
        return true;
    }

    @Override
    public String updateSurgeryType(SurgeryType surgeryType, SurgeryTypeDTO surgeryTypeDTO, Clinic clinic) {
        for(SurgeryType st : clinic.getSurgeryTypes()){
            if(st.getName().equals(surgeryTypeDTO.getName()) && !st.isRemoved() && st.getId() != surgeryType.getId()){
                return "Surgery type with that name already exists in the clinic.";
            }
        }

        List<Surgery> s = surgeryRepository.findSurgeryWithType(clinic.getId(),surgeryType.getId(), LocalDate.now(), LocalTime.now());

        if(s.size()!=0){
            return "This surgery type can't be updated because the surgery of this type is happening now or is arranged in the future.";
        }

        surgeryType.setName(surgeryTypeDTO.getName());
        surgeryType.setDescription(surgeryTypeDTO.getDescription());
        surgeryType.setPrice(surgeryTypeDTO.getPrice());
        surgeryType.setDiscount(surgeryType.getDiscount());
        surgeryTypeRepository.save(surgeryType);
        return null;
    }

    @Override
    public boolean removeSurgeryType(SurgeryType surgeryType, Clinic clinic) {

        //ovde ide kod za proveru da li je neka operacija ovog tipa zakazana za buducnost

        surgeryType.setRemoved(true);
        surgeryTypeRepository.save(surgeryType);
        return true;
    }
}
