package team57.project.service;

import team57.project.model.TermRoom;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TermRoomService {

    boolean existTermsInDB();
    void createFreeTerms();
    void creatFreeTermForTheNextWeek();
    TermRoom findByDateTime(LocalDate date, LocalTime time, Long id);
    TermRoom save(TermRoom tr);
}
