package com.gamedevs5.gamedevs5.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamedevs5.gamedevs5.models.User;

@Service
public class UserService {
    private final MongoOperations mongoOperations;
    private PasswordEncoder passwordEncoder;

    public UserService(MongoOperations mongoOperations, PasswordEncoder passwordEncoder) {
        this.mongoOperations = mongoOperations;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return mongoOperations.findAll(User.class);
    }

    public User getUserByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoOperations.findOne(query, User.class);
    }

    public List<User> getTopWinsUsers(int length) {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "totalWins"));
        query.limit(length);
        return mongoOperations.find(query, User.class);
    }

    public User addUser(User user) {

        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        User dbUser = mongoOperations.findOne(query, User.class);

        if (dbUser != null) {
            throw new RuntimeException("Användarnamnet du angav är upptaget.");
        }
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return mongoOperations.insert(user);
    }

    public User addWin(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        User user = mongoOperations.findOne(query, User.class);

        if (user != null) {
            user.setTotalWins(user.getTotalWins() + 1);
            mongoOperations.save(user);
            return user;
        } else {
            throw new RuntimeException("Användaren " + userId + " finns inte.");
        }
    }
}
