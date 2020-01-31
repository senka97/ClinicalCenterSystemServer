package team57.project.service.impl;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import team57.project.model.Doctor;
import team57.project.model.TermDoctor;
import team57.project.repository.DoctorRepository;
import team57.project.repository.TermDoctorRepository;
import team57.project.service.TermDoctorService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TermDoctorServiceImpl implements TermDoctorService {
    @Autowired
    private TermDoctorRepository termDoctorRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public boolean existTermsInDB() {
        List<TermDoctor> terms = termDoctorRepository.findAll();
        if(terms.size() == 0){
            return false;
        }
        return true;
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void createFreeTerms() {
        if(this.existTermsInDB()){
            System.out.println("Vec su kreirani");
        }else{
            System.out.println("Kreiranje termina za doktore pocinje.");
            List<Doctor> doctors = doctorRepository.findAll();
            LocalDate nowDate = LocalDate.now();
            for(Doctor doctor: doctors){
                    int today = nowDate.getDayOfWeek().getValue(); //redni broj dana u nedelji
                    LocalDate temp = LocalDate.now();
                    temp = temp.plusDays(1); //termini se prave od sutra pa do kraja sledece nedelje
                    for(int i=0;i<12-today;i++){
                        int n = temp.getDayOfWeek().getValue();
                        if(n == 6 || n == 7){ //ako je dan subota ili nedelja nema termina
                            System.out.println("Subota ili nedelja, ne kreiraju se termini.");
                        }else{
                            LocalTime wokingHoursStart = doctor.getWorkingHoursStart();
                            while(wokingHoursStart.isBefore(doctor.getWorkingHoursEnd())){
                                TermDoctor term = new TermDoctor(temp,wokingHoursStart,wokingHoursStart.plusHours(1),true,doctor);
                                termDoctorRepository.save(term);
                                wokingHoursStart = wokingHoursStart.plusHours(1);
                            }
                        }
                        temp = temp.plusDays(1);
                    }
            }
            System.out.println("Kreiranje termina za doktore zavrsilo.");

        }
    }

    @Override
    @Scheduled(cron = "${terms.cron}")
    public void creatFreeTermForTheNextWeek() {
        //ova funckija pocinje da se izvrsava u ponoc u nedelju a to je vec ponedeljak
        List<Doctor> doctors = doctorRepository.findAll();
        for(Doctor doctor: doctors){
            LocalDate temp = LocalDate.now();
            temp = temp.plusWeeks(1); //termini se prave od ponedeljka do petka sledece nedelje
            for(int i=0;i<5;i++){
                    LocalTime wokingHoursStart = doctor.getWorkingHoursStart();
                    while(wokingHoursStart.isBefore(doctor.getWorkingHoursEnd())) {
                        TermDoctor term = new TermDoctor(temp, wokingHoursStart, wokingHoursStart.plusHours(1), true, doctor);
                        termDoctorRepository.save(term);
                        wokingHoursStart = wokingHoursStart.plusHours(1);
                    }
                temp = temp.plusDays(1);
            }
        }
    }


}
