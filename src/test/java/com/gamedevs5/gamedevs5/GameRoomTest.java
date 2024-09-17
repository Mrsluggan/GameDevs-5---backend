package com.gamedevs5.gamedevs5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamedevs5.gamedevs5.models.Gameroom.GameRoom;
import com.gamedevs5.gamedevs5.services.GameRoomService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameRoomTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private GameRoomService gameRoomService;

    @MockBean
    private SimpMessagingTemplate messagingTemplate;

    @Test
    public void testCreateGameRoomSuccess() throws Exception {
        GameRoom newRoom = new GameRoom();
        newRoom.setGameRoomName("testRoom");

        when(gameRoomService.createGameRoom(any(GameRoom.class))).thenReturn(newRoom);

        mockMvc.perform(post("/api/gameroom/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newRoom)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameRoomName").value("testRoom"));

        verify(gameRoomService).createGameRoom(any(GameRoom.class));
        verify(messagingTemplate).convertAndSend(eq("/topic/gamerooms"), eq(newRoom));
    }
    
    @Test
    public void testCreateGameRoomWithEmptyName() throws Exception {
        GameRoom newRoom = new GameRoom();
        newRoom.setGameRoomName("");

        when(gameRoomService.createGameRoom(any(GameRoom.class)))
                .thenThrow(new RuntimeException("Du måste ange ett namn på rummet"));


                mockMvc.perform(post("/api/gameroom/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newRoom)))
                .andExpect(status().isBadRequest());

        verify(gameRoomService).createGameRoom(any(GameRoom.class));
        verifyNoInteractions(messagingTemplate);
    }
        
    }
