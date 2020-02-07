package team57.project.service;

import team57.project.model.TermDoctor;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TermDoctorService {

    boolean existTermsInDB();
    void createFreeTerms();
    void creatFreeTermForTheNextWeek();
    TermDoctor findByDateTime(LocalDate date, LocalTime time,Long id);
    TermDoctor save(TermDoctor td);
}
