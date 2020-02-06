package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import team57.project.dto.IncomeDate;
import team57.project.dto.MERoomRequest;
import team57.project.dto.MedicalExamRequest;
import team57.project.dto.RoomME;
import team57.project.model.*;
import team57.project.repository.*;
import team57.project.service.EmailService;
import team57.project.service.MedicalExamService;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class MedicalExamServiceImpl implements MedicalExamService {
    @Autowired
    private MedicalExamRepository medicalExamRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private TermRoomRepository termRoomRepository;
    @Autowired
    private TermDoctorRepository termDoctorRepository;
    @Autowired
    private RoomServiceImpl roomService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DoctorServiceImpl doctorService;

    @Override
    public List<MedicalExam> findAll() {
        return this.medicalExamRepository.findAll();
    }

    @Override
    public void save(MedicalExam m){
         this.medicalExamRepository.save(m);
    }


    @Override
    public List<MedicalExam> findByPatientId(Long patientId) {
        return this.medicalExamRepository.findMedicalExamByPatient(patientId);
    }

    @Override
    public List<MedicalExam> findDoctorsExams(Long doctorId)
    {
        return this.medicalExamRepository.findDoctorsExams(doctorId);
    }

    @Override
    public List<MedicalExam> findDoctorPatientExams(Long doctorId,Long patientId)
    {
        return this.medicalExamRepository.findDoctorPatientExams(doctorId,patientId);
    }


    @Override
    public double getNumExamRequests(Clinic clinic) {

        return medicalExamRepository.getNumExamRequests(clinic.getId());
    }

    @Override
    public List<MedicalExamRequest> findExamRequests(Clinic clinic) {

        List<MedicalExam> examRequests = medicalExamRepository.findExamRequests(clinic.getId());
        List<MedicalExamRequest> examRequestsDTO = new ArrayList<MedicalExamRequest>();

        for(MedicalExam me : examRequests){
            examRequestsDTO.add(new MedicalExamRequest(me));
        }

        return examRequestsDTO;
    }

    @Override
    public MedicalExam findOne(Long id) {
        return medicalExamRepository.findById(id).orElse(null);
    }

    @Override
    public List<RoomME> getAvailableRooms(MedicalExam me) {
        List<RoomME> availableRooms = new ArrayList<RoomME>();
        List<Room> rooms = medicalExamRepository.findAvailableRooms(me.getClinic().getId(),me.getDate(),me.getStartTime(),me.getEndTime());

        for(Room room: rooms){
            availableRooms.add(new RoomME(room,me));
        }

        return availableRooms;

    }

    @Override
    @Transactional
    public void reserveRoom(MERoomRequest meRoomRequest) throws MessagingException {
        //podaci o tome da li se datum ili doktor promenio se nalaze u meRoomRequest.getExamEnd
        //podatak da li se termin promenio se nalazi u meRoomRequest.getRoomME

        //zakljuca se ovaj termin sobe da ga niko drugi ne moze zauzeti
        TermRoom tr = termRoomRepository.findTermRoom(meRoomRequest.getRoomME().getDate(),meRoomRequest.getRoomME().getStartTime(),meRoomRequest.getRoomME().getId());
        tr.setFree(false);
        termRoomRepository.save(tr);
        //ako je doktor ostao isti ali se datum ili vreme promenilo, onda slobadjam stari i zauzimam novi termin kod doktora
        if(meRoomRequest.getExamStart().getDoctor().getId() == meRoomRequest.getExamEnd().getDoctor().getId()){
            if(!meRoomRequest.getExamStart().getDate().equals(meRoomRequest.getExamEnd().getDate())||
                    !meRoomRequest.getExamStart().getStartTime().equals(meRoomRequest.getRoomME().getStartTime())){
                TermDoctor tdOld = termDoctorRepository.findTermDoctor(meRoomRequest.getExamStart().getDate(),meRoomRequest.getExamStart().getStartTime(),meRoomRequest.getExamStart().getDoctor().getId());
                TermDoctor tdNew = termDoctorRepository.findTermDoctor(meRoomRequest.getExamEnd().getDate(),meRoomRequest.getRoomME().getStartTime(),meRoomRequest.getExamEnd().getDoctor().getId());
                tdOld.setFree(true);
                tdNew.setFree(false);
                termDoctorRepository.save(tdOld);
                termDoctorRepository.save(tdNew);
            }
        }
        //ukoliko su se samo doktori promenili a vreme je ostalo isto, onda oslobadjam termin kod jednog, a zauzimam kod drugog, i postavljam drugog doktora
        if(meRoomRequest.getExamStart().getDate().equals(meRoomRequest.getExamEnd().getDate())||
                meRoomRequest.getExamStart().getStartTime().equals(meRoomRequest.getRoomME().getStartTime())){
            if(meRoomRequest.getExamStart().getDoctor().getId() != meRoomRequest.getExamEnd().getDoctor().getId()) {
                TermDoctor tdOld = termDoctorRepository.findTermDoctor(meRoomRequest.getExamStart().getDate(), meRoomRequest.getExamStart().getStartTime(), meRoomRequest.getExamStart().getDoctor().getId());
                TermDoctor tdNew = termDoctorRepository.findTermDoctor(meRoomRequest.getExamStart().getDate(), meRoomRequest.getExamStart().getStartTime(), meRoomRequest.getExamEnd().getDoctor().getId());
                tdOld.setFree(true);
                tdNew.setFree(false);
                termDoctorRepository.save(tdOld);
                termDoctorRepository.save(tdNew);

            }
        }
        //ukoliko se promenio i doktor i datum i vreme
        if(meRoomRequest.getExamStart().getDoctor().getId() != meRoomRequest.getExamEnd().getDoctor().getId()){
           if(!meRoomRequest.getExamStart().getDate().equals(meRoomRequest.getExamEnd().getDate()) ||
                   !meRoomRequest.getExamStart().getStartTime().equals(meRoomRequest.getRoomME().getStartTime())){
               TermDoctor tdOld = termDoctorRepository.findTermDoctor(meRoomRequest.getExamStart().getDate(), meRoomRequest.getExamStart().getStartTime(), meRoomRequest.getExamStart().getDoctor().getId());
               TermDoctor tdNew = termDoctorRepository.findTermDoctor(meRoomRequest.getExamEnd().getDate(), meRoomRequest.getRoomME().getStartTime(), meRoomRequest.getExamEnd().getDoctor().getId());
               tdOld.setFree(true);
               tdNew.setFree(false);
               termDoctorRepository.save(tdOld);
               termDoctorRepository.save(tdNew);

            }
        }

        Room room = roomService.findOne(meRoomRequest.getRoomME().getId());
        MedicalExam me = (MedicalExam) medicalExamRepository.findById(meRoomRequest.getExamStart().getId()).orElse(null);
        me.setExamRoom(room);
        me.setStatusME("APPROVED");
        me.setDate(meRoomRequest.getExamEnd().getDate());
        me.setStartTime(meRoomRequest.getRoomME().getStartTime());
        me.setEndTime(meRoomRequest.getRoomME().getEndTime());
        Doctor d = doctorService.findOne(meRoomRequest.getExamEnd().getDoctor().getId());
        me.setDoctor(d);
        medicalExamRepository.save(me); //medical exam ima optimisticko zakljucavanje i ako je neko
        //u medjuvremenu dodelio neku drugu sobu puci ce

        emailService.sendNotificationForReservation(meRoomRequest.getExamEnd(),meRoomRequest.getExamStart(),meRoomRequest.getRoomME(),me.getPatient().getEmail());
    }

    @Override
    @Transactional
    public void rejectExam(MedicalExam me) throws MessagingException, InterruptedException {

        Doctor d = me.getDoctor(); //moram da oslobodim zauzeti termin za doktora, sobu nisam zauzela
        LocalDate date = me.getDate();
        LocalTime startTime = me.getStartTime();
        TermDoctor td = termDoctorRepository.findTermDoctor(date,startTime,d.getId());
        td.setFree(true);
        termDoctorRepository.save(td);
        me.setStatusME("REJECTED");
        medicalExamRepository.save(me);
        emailService.sendRejectExam(me);

    }

    @Override
    @Transactional
    public void acceptExamPatient(MedicalExam me) {
        me.setStatusME("ACCEPTED");
        medicalExamRepository.save(me);
    }

    @Override
    @Transactional
    public void rejectExamPatient(MedicalExam me) {
        Doctor d = me.getDoctor(); //moram da oslobodim zauzeti termin za doktora, sobu nisam zauzela
        LocalDate date = me.getDate();
        LocalTime startTime = me.getStartTime();
        TermDoctor td = termDoctorRepository.findTermDoctor(date,startTime,d.getId());
        td.setFree(true);
        termDoctorRepository.save(td);
        me.setStatusME("REJECTED");
        medicalExamRepository.save(me);
    }

    @Override
    @Scheduled(cron = "${rooms.cron}")
    public void systemReservingRooms() throws MessagingException, InterruptedException {

            List<Clinic> clinics = clinicRepository.findAll();
            for(Clinic clinic: clinics) {
                List<MedicalExam> medicalExams = medicalExamRepository.findExamRequests(clinic.getId()); //pronadjem sve preglede kojima nije dodeljena soba
                List<Room> rooms = roomRepository.findAllInClinic(clinic.getId()); //pronadjem sve sobe u klinici
                for (MedicalExam me : medicalExams) {
                    LocalDate tempDate = me.getDate();
                    boolean foundRoom = false;
                    while (!foundRoom) {
                        for (Room room : rooms) {
                            List<TermRoom> termsRooms = termRoomRepository.findFreeTermsJustDate(room.getId(), me.getDate()); //za svaku sobu uzmem slobodne termine i dodelim prvi
                            termsRooms.sort(Comparator.comparing(TermRoom::getStartTime));
                            if (termsRooms.size() != 0) {
                                TermRoom tr = termsRooms.get(0); // uzmem prvi termin
                                me.setExamRoom(room);
                                me.setStatusME("APPROVED");
                                me.setDate(tr.getDateTerm());
                                me.setStartTime(tr.getStartTime());
                                me.setEndTime(tr.getEndTime());
                                medicalExamRepository.save(me);
                                tr.setFree(false);
                                termRoomRepository.save(tr);
                                foundRoom = true;
                                emailService.sendPatientRoom(me);
                                emailService.sendDoctorRoom(me);
                                break;
                            }
                        }
                        if (!foundRoom) {
                            tempDate = tempDate.plusDays(1);
                        }
                    }
                }

            }
    }


}
