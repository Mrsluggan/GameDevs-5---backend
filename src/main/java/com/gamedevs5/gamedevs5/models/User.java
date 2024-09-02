package com.gamedevs5.gamedevs5.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {
    @Id
    public String userId;
    public String username;
}
