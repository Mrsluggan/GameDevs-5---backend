package com.gamedevs5.gamedevs5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import com.gamedevs5.gamedevs5.models.User;
import com.gamedevs5.gamedevs5.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserPointsAndWinTest {


    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddWinToUser() throws Exception {

        String userId = "1";
        User user = new User(userId, "testname", "testpassword", 0, 1);

        when(mongoOperations.findOne(any(Query.class), eq(User.class))).thenReturn(user);

        when(userService.addWin(userId)).thenReturn(user);

        assertEquals(2, user.getTotalWins());
        
    }
    
}
