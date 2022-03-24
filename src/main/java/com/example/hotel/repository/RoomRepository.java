package com.example.hotel.repository;

import com.example.hotel.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findAllByHotel_Id (long hotel_id, Pageable pageable);
}
