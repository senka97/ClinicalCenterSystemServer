package team57.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team57.project.dto.AvailableRoomRequest;
import team57.project.dto.RoomDTO;
import team57.project.dto.RoomFA;
import team57.project.model.Clinic;
import team57.project.model.Room;
import team57.project.service.ClinicService;
import team57.project.service.impl.RoomServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = "api/rooms")
@CrossOrigin("http://localhost:4200")
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;
    @Autowired
    private ClinicService clinicService;

    @GetMapping(value="/getRoom/{id}", produces="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getRoom(@PathVariable("id") Long id)
    {
        try {
            Room room = roomService.findOne(id);
            if(room.isRemoved()) {
                return ResponseEntity.status(HttpStatus.GONE).build();
            }

            RoomDTO roomDTO = new RoomDTO(room.getId(),room.getName(),room.getNumber(),room.getRoomType(), room.isRemoved());
            return new ResponseEntity(roomDTO, HttpStatus.OK);

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value="/getRooms/{idClinic}", produces="application/json")
    @PreAuthorize("hasRole('ROLE_CLINIC_ADMIN') or hasRole('ROLE_PATIENT')")
    public ResponseEntity<?> getRooms(@PathVariable("idClinic") Long idClinic)
    {
        try {
            Clinic clinic = clinicService.findOne(idClinic);
            List<RoomDTO> roomsDTO = new ArrayList<RoomDTO>();
            for(Room room : clinic.getRooms()) {
                if (!room.isRemoved()) {
                    roomsDTO.add(new RoomDTO(room));
                }
            }

            roomsDTO.sort(Comparator.comparing(RoomDTO::getRoomType));

            return new ResponseEntity(roomsDTO, HttpStatus.OK);

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value="/updateRoom/{id}/{id_clinic}", consumes="application/json")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @PathVariable("id_clinic") Long idClinic, @RequestBody RoomDTO roomDTO)
    {
        if(roomDTO.getName().equals("") || roomDTO.getName() == null || roomDTO.getName().equals("") ||
        roomDTO.getNumber() == null || roomDTO.getRoomType().equals("") || roomDTO.getRoomType() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name, number and room type are mandatory.");
        }

        try {
            Room room = roomService.findOne(id);
            if(room.isRemoved()){
                return ResponseEntity.status(HttpStatus.GONE).build();
            }
            String msg = roomService.updateRoom(room, roomDTO, idClinic);
            if(msg == null){
                return new ResponseEntity(HttpStatus.OK);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
            }

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value="/removeRoom/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> removeRoom(@PathVariable("id") Long id)
    {

        try {
            Room room = roomService.findOne(id);
            if(room.isRemoved()){
                return ResponseEntity.status(HttpStatus.GONE).build();
            }
            if(roomService.removeRoom(room)){
                return new ResponseEntity(HttpStatus.OK);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The room can't be removed because it is reserved" +
                        " for the upcoming exam or surgery.");
            }

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="/getAvailableRooms/{id}")
    @PreAuthorize("hasRole('CLINIC_ADMIN')")
    public ResponseEntity<?> getAvailableRooms(@PathVariable("id") Long id, @RequestBody AvailableRoomRequest arq)
    {
        if(arq.getDate() == null || arq.getTime() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date and time are mandatory.");
        }
        if(arq.getDate().getDayOfWeek().getValue() == 6 || arq.getDate().getDayOfWeek().getValue() == 7){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't reserve a room at the weekend.");
        }
        try {
            Clinic clinic = clinicService.findOne(id);
            List<RoomFA> availableRooms = roomService.findAvailableRooms(clinic,arq);
            return new ResponseEntity(availableRooms,HttpStatus.OK);

        } catch(NullPointerException e){

            return ResponseEntity.notFound().build();
        }
    }
}
