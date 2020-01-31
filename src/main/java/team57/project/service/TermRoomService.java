package team57.project.service;

public interface TermRoomService {

    boolean existTermsInDB();
    void createFreeTerms();
    void creatFreeTermForTheNextWeek();
}
