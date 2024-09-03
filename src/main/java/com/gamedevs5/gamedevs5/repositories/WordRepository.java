package com.gamedevs5.gamedevs5.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gamedevs5.gamedevs5.models.Word;

public interface WordRepository extends MongoRepository<Word, String> {
    
    Word findByWord(String word);
}
