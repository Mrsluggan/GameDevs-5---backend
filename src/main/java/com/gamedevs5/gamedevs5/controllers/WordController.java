package com.gamedevs5.gamedevs5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gamedevs5.gamedevs5.models.Word;
import com.gamedevs5.gamedevs5.services.WordService;

@RestController
@CrossOrigin(origins = "*")
public class WordController {

    @Autowired
    WordService wordService;

    @PostMapping("/createWord")
    public Word createWord(@RequestBody Word word) {
        return wordService.saveWord(word);
    }

    @GetMapping("/getWords")
    public Object getWords() {
        return wordService.getWords();
    }

    @GetMapping("getRandomWord")
    public String getRandomWord() {
        return wordService.getRandomWord();
    }

}
