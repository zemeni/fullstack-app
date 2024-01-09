package com.example.fullstackapp.service;

import com.example.fullstackapp.model.Room;
import com.example.fullstackapp.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room addRoom(Room room){
        return roomRepository.save(room);
    }

    public Room findRoomById(int id) {
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent())
            return room.get();
        else throw new IllegalArgumentException("room with id "+ id +" not found");
    }

    public Room updateRoom(Room room, int id) {
        Room existingRoom = roomRepository.findById(id).orElse(null);
        existingRoom.setLocation(room.getLocation());
        return roomRepository.save(existingRoom);
    }

    public String deleteRoom(int id) {
        roomRepository.deleteById(id);
        return "product removed "+id;
    }
}
