package team57.project.service;

import com.sun.java.swing.plaf.motif.MotifEditorPaneUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.model.Diagnose;
import team57.project.model.MedicalReport;
import team57.project.repository.MedicalReportRepository;

@Service
public class MedicalReportService {

    @Autowired
    private MedicalReportRepository medicalReportRepository;

    public MedicalReport findOne(Long id){
        return medicalReportRepository.findById(id).orElseGet(null);
    }

    public MedicalReport save(MedicalReport report) {
        return medicalReportRepository.save(report);
    }

    public void addDiagnosis(Diagnose diagnosis, MedicalReport report) {
        if (report != null) {
            report.getDiagnoses().add(diagnosis);
            this.medicalReportRepository.save(report);
        }
    }

    public void deleteDiagnosisFromReport(Diagnose diagnosis, MedicalReport report)
    {
        if(report!=null)
        {
            report.getDiagnoses().remove(diagnosis);
            this.medicalReportRepository.save(report);
        }
    }
}
