package team57.project.service;

import team57.project.dto.SurgeryTypeDTO;
import team57.project.model.Clinic;
import team57.project.model.SurgeryType;

public interface SurgeryTypeService {

    SurgeryType findOne(Long id);
    boolean addSurgeryType(Clinic clinic, SurgeryTypeDTO surgeryTypeDTO);
    String updateSurgeryType(SurgeryType surgeryType, SurgeryTypeDTO surgeryTypeDTO, Clinic clinic);
    boolean removeSurgeryType(SurgeryType surgeryType, Clinic clinic);
}
