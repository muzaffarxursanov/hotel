package com.example.hotel.controller;

import com.example.hotel.model.Hotel;
import com.example.hotel.model.dto.HotelDto;
import com.example.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/get")
    public Page<Hotel> allHotels(@RequestParam int page){
        Pageable pageable= PageRequest.of(page, 10);
        return hotelRepository.findAll(pageable);
    }

    @GetMapping("/get/{id}")
    public String getHotel(@PathVariable Long id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty())
            return "hotel is not found";
        return optionalHotel.get().toString();
    }

    @PostMapping("/add")
    public String addHotel(@RequestBody HotelDto hotelDto){
        Hotel hotel=new Hotel();
        hotel.setName(hotelDto.getName());
        hotelRepository.save(hotel);
        return "hotel saved";
    }

    @DeleteMapping("/delete/{id}}")
    public String deleteHotel(@PathVariable Long id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty())
            return "hotel not found";
        hotelRepository.deleteById(id);
        return "hotel is deleted";
    }

    @PutMapping("/update/{id}")
    public String updateHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty())
            return "hotel not found";
        Hotel hotel = optionalHotel.get();
        hotel.setName(hotelDto.getName());
        hotelRepository.save(hotel);
        return "hotel updated";
    }
}
