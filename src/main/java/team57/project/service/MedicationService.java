package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.MedicationDTO;
import team57.project.model.Medication;
import team57.project.repository.MedicationRepository;

import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public List<Medication> findAll() { return medicationRepository.findAll(); }

    public Medication saveMedication(Medication m) {return medicationRepository.save(m);}

    public Medication findByCode(String code) { return medicationRepository.findByCode(code); }

    public Medication findOne(Long id){ return medicationRepository.findById(id).orElseGet(null);}

    public Medication updateMedication(Medication existMedication, MedicationDTO medication)
    {
        if(existMedication.getCode().equals(medication.getCode()))//ako code nije menjan
        {
            existMedication.setDescription(medication.getDescription());
            return medicationRepository.save(existMedication);
        }
        else
        {
            Medication diag = findByCode(medication.getCode());
            if(diag == null) // nova se ne poklapa ni sa jednom postojecom
            {
                existMedication.setCode(medication.getCode());
                existMedication.setDescription(medication.getDescription());
                return medicationRepository.save(existMedication);
            }
            else return null;

        }


    }
}
