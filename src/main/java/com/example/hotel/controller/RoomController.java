package com.example.hotel.controller;

import com.example.hotel.model.Hotel;
import com.example.hotel.model.Room;
import com.example.hotel.model.dto.RoomDto;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/get")
    public Page<Room> allRooms(@RequestParam int page){
        Pageable pageable= PageRequest.of(page, 10);
        return roomRepository.findAll(pageable);
    }

    @GetMapping("/get/{id}")
    public Page<Room> allRoomsHotel(@PathVariable Long id, @RequestParam int page){
        Pageable pageable=PageRequest.of(page, 10);
        return roomRepository.findAllByHotel_Id(id, pageable);
    }

    @GetMapping("/getRoom/{id}")
    public String getRoom(@PathVariable Long id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty())
            return "room is not found";
        return optionalRoom.get().toString();
    }

    @PostMapping("/add")
    public String addRoom(@RequestBody RoomDto roomDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotel_id());
        if (optionalHotel.isEmpty())
            return "hotel not found";
        Hotel hotel = optionalHotel.get();
        Room room=new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        room.setHotel(hotel);
        roomRepository.save(room);
        return "room saved";
    }

    @DeleteMapping("/delete/{id}}")
    public String deleteRoom(@PathVariable Long id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty())
            return "room not found";
        roomRepository.deleteById(id);
        return "room is deleted";
    }

    @PutMapping("/update/{id}")
    public String updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty())
            return "room not found";
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotel_id());
        if (optionalHotel.isEmpty())
            return "hotel not found";
        Hotel hotel = optionalHotel.get();
        Room room = optionalRoom.get();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        room.setHotel(hotel);
        roomRepository.save(room);
        return "room updated";
    }
}
