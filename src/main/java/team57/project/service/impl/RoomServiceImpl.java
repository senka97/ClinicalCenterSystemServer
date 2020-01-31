package team57.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team57.project.dto.AvailableRoomRequest;
import team57.project.dto.RoomDTO;
import team57.project.dto.RoomFA;
import team57.project.model.Clinic;
import team57.project.model.Room;
import team57.project.model.RoomReservationTime;
import team57.project.repository.RoomRepository;
import team57.project.service.ClinicService;
import team57.project.service.RoomService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ClinicService clinicService;

    public String updateRoom(Room room, RoomDTO roomDTO, Long idClinic){

        /*Set<RoomReservationTime> roomReservationTimes = room.getRoomReservationTimes();
        for(RoomReservationTime rrt: roomReservationTimes) {
            if (rrt.getStartDateTime().isAfter(LocalDateTime.now())) {
                return "The room can't be updated because it is reserved " +
                        "for the upcoming exam or surgery.";
            } else {
                if (rrt.getEndDateTime().isAfter(LocalDateTime.now())) {
                    return "The room can't be updated because it is currently used for exam or surgery";
                }
            }
        }*/

        List<Room> scheduledRooms = roomRepository.findScheduledRooms(room.getId(), LocalDate.now(), LocalTime.now());
        if(scheduledRooms.size() != 0){
            return "The room can't be updated because it is reserved " +
                    "for the upcoming exam or surgery.";
        }


                Clinic clinic = clinicService.findOne(idClinic);
                Set<Room> rooms = clinic.getRooms();
                for(Room r: rooms){
                    boolean exists = false;
                    String msg = "";
                    if(r.getName().equals(roomDTO.getName()) && r.getId() != room.getId() && !r.isRemoved()){
                        exists = true;
                        msg += "The room can't be updated because this name already exists in the clinic. ";
                    }
                    if(r.getNumber().equals(roomDTO.getNumber()) && r.getId() != room.getId() && !r.isRemoved()){
                        exists = true;
                        msg += "The room can't be updated because this number already exists in the clinic. ";
                    }
                    if(exists){
                        return msg;
                    }
                }
                room.setName(roomDTO.getName());
                room.setNumber(roomDTO.getNumber());
                room.setRoomType(roomDTO.getRoomType());
                roomRepository.save(room);


        return null;
    }

    public boolean removeRoom(Room room){

        /*Set<RoomReservationTime> roomReservationTimes = room.getRoomReservationTimes();
        for(RoomReservationTime rrt: roomReservationTimes){
            if(rrt.getStartDateTime().isAfter(LocalDateTime.now())){
                return false;
            }else{
                if(rrt.getEndDateTime().isAfter(LocalDateTime.now())){
                    return false;
                }

            }
        }*/
        List<Room> scheduledRooms = roomRepository.findScheduledRooms(room.getId(), LocalDate.now(), LocalTime.now());
        if(scheduledRooms.size() != 0){
            return false;
        }

        room.setRemoved(true);
        roomRepository.save(room);
        return true;
    }

    public Room findOne(Long id){

        return (Room) roomRepository.findById(id).orElse(null);
    }

    @Override
    public List<RoomFA> findAvailableRooms(Clinic clinic, AvailableRoomRequest arq) {
        List<RoomFA> roomsFA = new ArrayList<RoomFA>();
        List<Room> rooms = new ArrayList<Room>();
        rooms = roomRepository.getAvailableRooms(clinic.getId(),arq.getDate(),arq.getTime());
        for(Room room: rooms){
            roomsFA.add(new RoomFA(room));
        }
        return roomsFA;
    }


}
