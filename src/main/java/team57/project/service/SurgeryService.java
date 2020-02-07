package team57.project.service;

import team57.project.dto.RoomME;
import team57.project.dto.RoomTerm;
import team57.project.dto.SurgeryRequest;
import team57.project.model.Clinic;
import team57.project.model.Surgery;

import javax.mail.MessagingException;
import java.util.List;

public interface SurgeryService {
    List<Surgery> findAll();

    List<Surgery> findByPatientId(Long patientId);
    List<Surgery> findDoctorsSurgeries(Long doctorID);
    void save(Surgery s);
    double getNumSurgeryRequests(Clinic clinic);
    List<SurgeryRequest> findSurgeryRequests(Clinic clinic);
    Surgery findOne(Long id);
    List<RoomTerm> getAvailableRoomsTerms(Surgery s);
    void rejectSurgery(Surgery me) throws MessagingException, InterruptedException;
    void reserve(Surgery s, RoomTerm rt);
    void systemReservingRooms() throws MessagingException, InterruptedException;
}
