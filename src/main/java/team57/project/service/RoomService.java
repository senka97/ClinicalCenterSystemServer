package team57.project.service;

import team57.project.dto.AvailableRoomRequest;
import team57.project.dto.RoomDTO;
import team57.project.dto.RoomFA;
import team57.project.model.Clinic;
import team57.project.model.Room;

import java.util.List;

public interface RoomService {

    String updateRoom(Room room, RoomDTO roomDTO, Long idClinic);
    boolean removeRoom(Room room);
    Room findOne(Long id);
    List<RoomFA> findAvailableRooms(Clinic clinic, AvailableRoomRequest arq);

}
