package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import team57.project.model.Room;
import team57.project.model.TermRoom;
import team57.project.repository.RoomRepository;
import team57.project.repository.TermRoomRepository;
import team57.project.service.TermRoomService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TermRoomServiceImpl implements TermRoomService {

    @Autowired
    private TermRoomRepository termRoomRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public TermRoom save(TermRoom tr)
    {
        return termRoomRepository.save(tr);
    }

    @Override
    public boolean existTermsInDB() {
        List<TermRoom> terms = termRoomRepository.findAll();
        if(terms.size() == 0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void createFreeTerms() {
        if(this.existTermsInDB()){
            System.out.println("Vec su kreirani.");
        }else{
            System.out.println("Kreiranje termina za sobe pocinje.");
            List<Room> rooms = this.roomRepository.findAll();
            LocalDate nowDate = LocalDate.now();
            for(Room room: rooms){
                int today = nowDate.getDayOfWeek().getValue(); //redni broj dana u nedelji
                LocalDate temp = LocalDate.now();
                temp = temp.plusDays(1); //termini se prave od sutra pa do kraja sledece nedelje
                for(int i=0;i<12-today;i++){
                    int n = temp.getDayOfWeek().getValue();
                    if(n == 6 || n == 7){ //ako je dan subota ili nedelja nema termina
                        System.out.println("Subota ili nedelja, ne kreiraju se termini.");
                    }else{
                        LocalTime startTime = LocalTime.of(6,0);
                        LocalTime endTime = LocalTime.of(22,0);
                        while(startTime.isBefore(endTime)){
                            TermRoom term = new TermRoom(temp,startTime,startTime.plusHours(1),true,room);
                            termRoomRepository.save(term);
                            startTime = startTime.plusHours(1);
                        }
                    }
                    temp = temp.plusDays(1);
                }
            }
            System.out.println("Kreiranje termina za sobe zavrsilo.");
        }

    }

    @Override
    @Scheduled(cron = "${terms.cron}")
    public void creatFreeTermForTheNextWeek() {
        List<Room> rooms = roomRepository.findAll();
        for(Room room: rooms){
            LocalDate temp = LocalDate.now();
            temp = temp.plusWeeks(1); //termini se prave od ponedeljka do petka sledece nedelje
            for(int i=0;i<5;i++){
                LocalTime startTime = LocalTime.of(6,0);
                LocalTime endTime = LocalTime.of(22,0);
                while(startTime.isBefore(endTime)) {
                    TermRoom term = new TermRoom(temp, startTime, startTime.plusHours(1), true, room);
                    termRoomRepository.save(term);
                    startTime = startTime.plusHours(1);
                }
                temp = temp.plusDays(1);
            }
        }

    }

    @Override
    public TermRoom findByDateTime(LocalDate date, LocalTime time,Long id)
    {
        return termRoomRepository.findByDateTime(date,time, id);
    }
}
