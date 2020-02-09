package team57.project.service;

import team57.project.dto.*;
import team57.project.model.Clinic;
import team57.project.model.Room;

import java.util.List;

public interface RoomService {

    String updateRoom(Room room, RoomDTO roomDTO, Long idClinic);
    boolean removeRoom(Room room);
    Room findOne(Long id);
    List<RoomFA> findAvailableRooms(Clinic clinic, AvailableRoomRequest arq);
    List<RoomME> findRoomsFreeTerms(Clinic clinic, FreeTermsRequest ftr);
    List<TermRoomDTO>getReservedRoomTerms(Room room);

}
