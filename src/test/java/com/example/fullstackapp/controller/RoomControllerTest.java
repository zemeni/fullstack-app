package com.example.fullstackapp.controller;

import com.example.fullstackapp.model.Room;
import com.example.fullstackapp.service.RoomService;
import com.example.fullstackapp.service.RoomServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class RoomControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;
    List<Room> rooms =  new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomServiceMock;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void beforeEach(){
        rooms = List.of(
                new Room(1,"sydney"),
                new Room(2, "Melbourne"),
                new Room(3,"Perth")
        );
    }

    @DisplayName("should show all rooms")
    @Test
    void shouldShowAllRooms() throws Exception {
        when(roomServiceMock.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(rooms)));

       /*
            This is for Spring MVC
            ModelAndView modelAndView = mvcResult.getModelAndView();
            ModelAndViewAssert.assertViewName(modelAndView, "index");
        */
    }
}
