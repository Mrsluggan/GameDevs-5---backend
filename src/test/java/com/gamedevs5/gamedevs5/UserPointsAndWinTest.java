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

    @Test
    public void testAddPointsToUsers() throws Exception {

        String username1 = "testname";
        User winner = new User(username1, "testname", "testpassword", 0, 1);

        String username2 = "testname2";
        User painter = new User(username2, "testname2", "testpassword2", 0, 1);

        when(mongoOperations.findOne(any(Query.class), eq(User.class)))
                .thenAnswer(invocation -> {
                    Query query = invocation.getArgument(0);
                    if (query.getQueryObject().get("username").equals(username1)) {
                        return winner;
                    } else if (query.getQueryObject().get("username").equals(username2)) {
                        return painter;
                    }
                    return null;
                });

        userService.addPoints(username1, 3);
        userService.addPoints(username2, 1);

        assertEquals(3, winner.getCurrentPoints());
        assertEquals(1, painter.getCurrentPoints());

    }

}
