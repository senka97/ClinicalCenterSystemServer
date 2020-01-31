package team57.project.service;

public interface TermDoctorService {

    boolean existTermsInDB();
    void createFreeTerms();
    void creatFreeTermForTheNextWeek();
}
