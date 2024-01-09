package com.example.fullstackapp.controller;

import com.example.fullstackapp.model.Room;
import com.example.fullstackapp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
class RoomController {

    private final RoomService roomService;

    @Autowired
    RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> findAll(){
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public Room findRoomById(@PathVariable int id) {
        return roomService.findRoomById(id);
    }

    @PostMapping
    public Room addRoom(@RequestBody Room room) {
        return roomService.addRoom(room);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@RequestBody Room room, @PathVariable int id){
       return roomService.updateRoom(room, id);
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable int id){
        return roomService.deleteRoom(id);
    }
}
