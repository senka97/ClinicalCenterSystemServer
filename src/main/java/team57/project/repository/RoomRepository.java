package team57.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team57.project.model.Room;


public interface RoomRepository extends JpaRepository<Room, Long> {
}
