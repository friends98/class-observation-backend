package com.observationclass.repository;

import com.observationclass.entity.Room;
import com.observationclass.model.response.DropdownListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query(value = "SELECT r.id as value,r.room_name as name  FROM room r", nativeQuery = true)
    List<DropdownListResponse> findAllByCampusId(Integer campusId);
}
