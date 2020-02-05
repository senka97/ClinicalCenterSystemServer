package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.IncomeDate;
import team57.project.dto.MERoomRequest;
import team57.project.dto.MedicalExamRequest;
import team57.project.dto.RoomME;
import team57.project.model.*;
import team57.project.repository.ClinicRepository;
import team57.project.repository.MedicalExamRepository;
import team57.project.repository.TermDoctorRepository;
import team57.project.repository.TermRoomRepository;
import team57.project.service.EmailService;
import team57.project.service.MedicalExamService;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public void reserveRoom(MERoomRequest meRoomRequest) {

        //zakljuca se ovaj termin sobe da ga niko drugi ne moze zauzeti
        TermRoom tr = termRoomRepository.findTermRoom(meRoomRequest.getRoomME().getDate(),meRoomRequest.getRoomME().getStartTime(),meRoomRequest.getRoomME().getId());
        tr.setFree(false);
        termRoomRepository.save(tr);
        System.out.println("Dodje dovde1");
        //ako se promenio doktor ili termin, mora se osloboditi prethodni i zauzeti novi termin kod doktora
        if(meRoomRequest.getExamStart().getIdDoctor() != meRoomRequest.getExamEnd().getIdDoctor() ||
                !meRoomRequest.getExamStart().getDate().equals(meRoomRequest.getExamEnd().getDate()) ||
        !meRoomRequest.getExamStart().getStartTime().equals(meRoomRequest.getExamEnd().getStartTime())){
            TermDoctor tdOld = termDoctorRepository.findTermDoctor(meRoomRequest.getExamStart().getDate(),meRoomRequest.getExamStart().getStartTime(),meRoomRequest.getExamStart().getIdDoctor());
            TermDoctor tdNew = termDoctorRepository.findTermDoctor(meRoomRequest.getExamEnd().getDate(),meRoomRequest.getExamEnd().getStartTime(),meRoomRequest.getExamEnd().getIdDoctor());
            tdOld.setFree(true);
            tdNew.setFree(false);
            termDoctorRepository.save(tdOld);
            termDoctorRepository.save(tdNew);

        }
        System.out.println("Dodje dovde1");

        Room room = roomService.findOne(meRoomRequest.getRoomME().getId());
        MedicalExam me = (MedicalExam) medicalExamRepository.findById(meRoomRequest.getExamStart().getId()).orElse(null);
        me.setExamRoom(room);
        medicalExamRepository.save(me); //medical exam ima optimisticko zakljucavanje i ako je neko
        //u medjuvremenu dodelio neku drugu sobu puci ce
    }


}
