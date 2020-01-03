package team57.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.DiagnosisDTO;
import team57.project.model.Diagnose;
import team57.project.repository.DiagnosisRepository;

import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public List<Diagnose> findAll() { return diagnosisRepository.findAll(); }

    public Diagnose saveDiagnosis(Diagnose d) { return  diagnosisRepository.save(d); }

    public Diagnose findByCode(String code) {
        return diagnosisRepository.findByCode(code);
    }

    public Diagnose findOne(Long id){ return diagnosisRepository.findById(id).orElseGet(null);}

    public Diagnose updateDiagnosis(Diagnose existDiagnosis, DiagnosisDTO diagnosis)
    {
        if(existDiagnosis.getCode().equals(diagnosis.getCode()))//ako code nije menjan
        {
            existDiagnosis.setDescription(diagnosis.getDescription());
            return diagnosisRepository.save(existDiagnosis);
        }
        else
        {
            Diagnose diag = findByCode(diagnosis.getCode());
            if(diag == null) // nova se ne poklapa ni sa jednom postojecom
            {
                existDiagnosis.setCode(diagnosis.getCode());
                existDiagnosis.setDescription(diagnosis.getDescription());
                return diagnosisRepository.save(existDiagnosis);
            }
            else return null;

        }


    }
}
