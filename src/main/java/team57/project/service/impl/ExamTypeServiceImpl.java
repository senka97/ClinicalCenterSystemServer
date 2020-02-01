package team57.project.service.impl;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team57.project.dto.ExamTypeDTO;
import team57.project.model.Clinic;
import team57.project.model.ExamType;
import team57.project.model.FastAppointment;
import team57.project.repository.ExamTypeRepository;
import team57.project.service.ExamTypeService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamTypeServiceImpl implements ExamTypeService {

    @Autowired
    private ExamTypeRepository examTypeRepository;


    @Override
    public ExamType findOne(Long id) {
        return examTypeRepository.findById(id).orElse(null);
    }

    @Override
    public boolean addExamType(Clinic clinic, ExamTypeDTO examTypeDTO) {

        for(ExamType et : clinic.getExamTypes()){
            if(et.getName().equals(examTypeDTO.getName()) && !et.isRemoved()){
                    return false;
            }
        }
        ExamType examType = new ExamType(examTypeDTO.getName(),examTypeDTO.getDescription(),examTypeDTO.getPrice(),examTypeDTO.getDiscount());
        examType.getClinics().add(clinic); //da bi se ovo moglo pravilno sacuvati potrebno je da examType bude vlasnik veze; potrebno da bude cascade ALL
        // kada examType nije bio vlasnik veze onda ovo nije radilo, nego se examType morao ubaciti u kliniku i sacuvati
        examTypeRepository.save(examType);
        return true;
    }


    @Override
    public String updateExamType(ExamType examType, ExamTypeDTO examTypeDTO, Clinic clinic) {

        for(ExamType et : clinic.getExamTypes()){
            if(et.getName().equals(examTypeDTO.getName()) && !et.isRemoved() && et.getId() != examType.getId()){
                return "Exam type with that name already exists in the clinic.";
            }
        }
        for(FastAppointment fa : clinic.getFastAppointments()) {
            if (fa.getExamType().getName().equals(examType.getName())) {
                if(fa.getDateTime().isAfter(LocalDateTime.now()) || (fa.getDateTime().isBefore(LocalDateTime.now()) && fa.getDateTime().plusMinutes(fa.getDuration()).isAfter(LocalDateTime.now()))){
                    //if (!fa.isDone()) {
                    return "This exam type can't be updated because the exam of this type is happening now or is arranged in the future.";
                }
            }
        }
        examType.setName(examTypeDTO.getName());
        examType.setDescription(examTypeDTO.getDescription());
        examType.setPrice(examTypeDTO.getPrice());
        examType.setDiscount(examType.getDiscount());
        examTypeRepository.save(examType);
        return null;
    }

    @Override
    public boolean removeExamType(ExamType examType, Clinic clinic) {

            for(FastAppointment fa : clinic.getFastAppointments()) {
                if (fa.getExamType().getName().equals(examType.getName())) {
                    if (fa.getDateTime().isAfter(LocalDateTime.now()) || (fa.getDateTime().isBefore(LocalDateTime.now()) && fa.getDateTime().plusMinutes(fa.getDuration()).isAfter(LocalDateTime.now()))) {
                        //if(!fa.isDone())
                        return false;
                    }
                }
            }
            examType.setRemoved(true);
            examTypeRepository.save(examType);
            return true;
    }

    @Override
    public List<ExamType> findAll() {
        return this.examTypeRepository.findAll();
    }
}
