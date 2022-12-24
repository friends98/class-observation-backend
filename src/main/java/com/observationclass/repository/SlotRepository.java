package com.observationclass.repository;

import com.observationclass.entity.Slot;
import com.observationclass.model.response.DropdownListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot,Integer> {
    @Query(value="SELECT s.id as value, s.slot_range as name FROM slot s",nativeQuery = true)
    List<DropdownListResponse> slotDropdownList();
}
