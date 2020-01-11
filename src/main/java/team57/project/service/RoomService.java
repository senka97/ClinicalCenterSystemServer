package team57.project.service;

import team57.project.dto.RoomDTO;
import team57.project.model.Room;

public interface RoomService {

    String updateRoom(Room room, RoomDTO roomDTO, Long idClinic);
    boolean removeRoom(Room room);
    Room findOne(Long id);

}
