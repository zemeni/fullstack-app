package com.example.fullstackapp.service;

import com.example.fullstackapp.model.Room;
import com.example.fullstackapp.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoomServiceTest {

    List<Room> rooms =  new ArrayList<>();

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;


    @BeforeEach
    void beforeEach(){

    }

    @Test
    void shouldReturnAllRooms(){
        rooms = List.of(
                new Room(1,"sydney"),
                new Room(2,"Melbourne"),
                new Room(3,"Perth")
        );
        when(roomRepository.findAll()).thenReturn(rooms);
        List<Room> result = roomService.findAll();
        assertIterableEquals(rooms, result,"must be equal");
//        assertArrayEquals(rooms.toArray(),roomService.findAll().toArray(),"must be equal");
        verify(roomRepository, times(1)).findAll();
    }
}
