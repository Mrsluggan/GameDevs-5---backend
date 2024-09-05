package com.gamedevs5.gamedevs5;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.gamedevs5.gamedevs5.models.User;
import com.gamedevs5.gamedevs5.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void testLoginSuccess() throws Exception {

        User encodedUser = new User("1", "testname", "encodedpassword", 0, 0);

        when(userService.getUserByUsername("testname")).thenReturn(encodedUser);
        when(passwordEncoder.matches("testpassword", "encodedpassword")).thenReturn(true);

        mockMvc.perform(post("/login-user")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"testname\",\"password\":\"testpassword\"}"))
            .andExpect(status().isOk());
    }

    @Test
    public void testPassword() throws Exception {

        User encodedUser = new User("1", "testname", "password", 0, 0);

        when(userService.getUserByUsername("testname")).thenReturn(encodedUser);
        when(passwordEncoder.matches("wrongpassword", "encodedpassword")).thenReturn(false);

        mockMvc.perform(post("/login-user")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"testname\",\"password\":\"wrongpassword\"}"))
            .andExpect(status().isUnauthorized());
    }
}
