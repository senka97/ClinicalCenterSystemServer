package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.IncomeDate;
import team57.project.model.*;
import team57.project.repository.ClinicRepository;
import team57.project.repository.MedicalExamRepository;
import team57.project.service.MedicalExamService;

import java.util.List;
@Service
public class MedicalExamServiceImpl implements MedicalExamService {
    @Autowired
    private MedicalExamRepository medicalExamRepository;
    @Autowired
    private ClinicRepository clinicRepository;

    @Override
    public List<MedicalExam> findAll() {
        return this.medicalExamRepository.findAll();
    }

    @Override
    public List<MedicalExam> findByPatientId(Long patientId) {
        return this.medicalExamRepository.findMedicalExamByPatient(patientId);
    }

    @Override
    public double getIncome(Clinic clinic, IncomeDate incomeDate) {

        List<FastAppointment> fa = clinicRepository.findFAIncome(clinic.getId(),incomeDate.getStartDate(),incomeDate.getEndDate());
        List<MedicalExam> me = clinicRepository.findMEIncome(clinic.getId(),incomeDate.getStartDate(),incomeDate.getEndDate());
        List<Surgery> s = clinicRepository.findSIncome(clinic.getId(),incomeDate.getStartDate(),incomeDate.getEndDate());

        double income = 0;
        for(FastAppointment f:fa){
            income += f.getPrice() - f.getPrice()*(f.getDiscount()/100);
        }
        for(MedicalExam m:me){
            income += m.getExamType().getPrice() - m.getExamType().getPrice()*(m.getExamType().getDiscount()/100);
        }
        for(Surgery d:s){
            income += d.getSurgeryType().getPrice() - d.getSurgeryType().getPrice()*(d.getSurgeryType().getDiscount()/100);
        }
        return income;
    }


}
