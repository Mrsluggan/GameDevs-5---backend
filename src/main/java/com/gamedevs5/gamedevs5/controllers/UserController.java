package com.gamedevs5.gamedevs5.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamedevs5.gamedevs5.models.User;
import com.gamedevs5.gamedevs5.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpriationMs;

    @GetMapping("/get-users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/get-top-users")
    public ResponseEntity<List<User>> getTopWinsUsers() {
        List<User> topUsers = userService.getTopWinsUsers(5);
        return ResponseEntity.ok(topUsers);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {

        try {
            User addedUser = userService.addUser(user);
            return ResponseEntity.ok(addedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Användarnamnet du angav är upptaget.");
        }

    }

    @PostMapping("/login-user")
    public ResponseEntity<?> login(@RequestBody User user) {
        User dbUser = userService.getUserByUsername(user.getUsername());

        if (dbUser != null) {
            String encodedPassword = dbUser.getPassword();
            String incomingPassword = user.getPassword();

            if (passwordEncoder.matches(incomingPassword, encodedPassword)) {
                @SuppressWarnings("deprecation")
                String token = Jwts.builder()
                        .setSubject(dbUser.getUsername())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + jwtExpriationMs))
                        .signWith(SignatureAlgorithm.HS512, jwtSecret)
                        .compact();
                return ResponseEntity.ok(token);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Fel användarnamn eller lösenord.");
    }

    @PostMapping("/add-win/{userId}")
    public ResponseEntity<?> addWin(@PathVariable String userId) {
        try {
            User updatedUser = userService.addWin(userId);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kunde inte lägga til vinst");
        }

    }

    @PostMapping("/pointsRewarded")
    public ResponseEntity<String> addPoints(@RequestParam String winnerId, @RequestParam String painterId) {
        try {
            userService.addPoints(winnerId, painterId);
            return ResponseEntity.ok("points added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Kunde inte lägga till poäng");
        }

    }


}
