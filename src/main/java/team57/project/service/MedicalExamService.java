package team57.project.service;

import team57.project.dto.IncomeDate;
import team57.project.dto.MERoomRequest;
import team57.project.dto.MedicalExamRequest;
import team57.project.dto.RoomME;
import team57.project.model.Clinic;
import team57.project.model.MedicalExam;

import javax.mail.MessagingException;
import java.util.List;

public interface MedicalExamService {
    List<MedicalExam> findAll();
    List<MedicalExam> findByPatientId(Long patientId);
    double getNumExamRequests(Clinic clinic);
    List<MedicalExamRequest> findExamRequests(Clinic clinic);
    MedicalExam findOne(Long id);
    List<RoomME> getAvailableRooms(MedicalExam me);
    void reserveRoom(MERoomRequest meRoomRequest) throws MessagingException;
    void rejectExam(MedicalExam me) throws MessagingException, InterruptedException;
    void acceptExamPatient(MedicalExam me);
    void rejectExamPatient(MedicalExam me);
    void systemReservingRooms() throws MessagingException, InterruptedException;

}
