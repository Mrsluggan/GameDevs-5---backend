package com.gamedevs5.gamedevs5;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamedevs5.gamedevs5.models.User;
import com.gamedevs5.gamedevs5.models.Gameroom.GameRoom;
import com.gamedevs5.gamedevs5.services.GameRoomService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameRoomTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameRoomService gameRoomService;

    @Test
    public void testCreateGameRoom() throws Exception {

        GameRoom gameRoom = new GameRoom();

        when(gameRoomService.createGameRoom(gameRoom)).thenReturn(gameRoom);

        String gameRoomJson = objectMapper.writeValueAsString(gameRoom);

        mockMvc.perform(post("/api/gameroom/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(gameRoomJson))
        .andExpect(status().isOk());
    }



 }

    

