package com.example.fullstackapp.integration;

import com.example.fullstackapp.model.Room;
import com.example.fullstackapp.repository.RoomTestRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomIntegrationTest {

    private final StringBuffer baseUrl = new StringBuffer("http://localhost");

    @LocalServerPort
    private int port;


    @Autowired
    private RoomTestRepository roomTestRepository;

    private static RestTemplate restTemplate;

    @BeforeAll
    public static void setup(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void init(){
        baseUrl.append(":").append(port).append("/api/rooms");
    }

    @Test
    public void testAddRoom(){
        Room room = new Room("sydney");
        Room response = restTemplate.postForObject(baseUrl.toString(), room, Room.class);
        assertEquals("sydney", response.getLocation());
        assertEquals(1, roomTestRepository.findAll().size());
    }

    @Test
    @Sql(statements = "insert into room(location) values('sydney')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from room where location='sydney'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindAll(){
        List<Room> rooms = restTemplate.getForObject(baseUrl.toString(), List.class);
        assertEquals(1, rooms.size());
        assertEquals(1, roomTestRepository.findAll().size());
    }

    @Test
    @Sql(statements = "insert into room(location) values('sydney')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from room where location='sydney'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindProductById(){
        Room room = restTemplate.getForObject(baseUrl+"/{id}", Room.class, 1);
        assertEquals("sydney", room.getLocation());
        assertAll(
                () -> assertNotNull(room),
                () -> assertEquals("sydney", room.getLocation())
        );
    }

    @Test
    @Sql(statements = "insert into room(Id,location) values(1, 'sydney')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "delete from room where Id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateProduct(){
        Room room = new Room("Melbourne");
        restTemplate.put(baseUrl+"/{id}", room, 1);
        Room room1 = roomTestRepository.findById(1).get();
        assertAll(
                () -> assertNotNull(room1),
                () -> assertEquals("Melbourne", room1.getLocation())
        );
    }

    @Test
    @Sql(statements = "insert into room(Id,location) values(1, 'sydney')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteProduct(){
        Room room = roomTestRepository.findById(1).get();
        assertEquals("sydney", room.getLocation());
        restTemplate.delete(baseUrl+"/{id}",1);
        Room room1 = roomTestRepository.findById(1).orElse(null);
        assertNull(room1);
    }
}
