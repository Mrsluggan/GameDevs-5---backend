package com.gamedevs5.gamedevs5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamedevs5.gamedevs5.models.Word;
import com.gamedevs5.gamedevs5.repositories.WordRepository;

@Service
public class WordService {

    @Autowired
    WordRepository wordRepository;

    public Word saveWord(Word word) {

        String wordString = word.getWord().toLowerCase();
        word.setWord(wordString);

        Word wordExists = wordRepository.findByWord(word.getWord());

        if (wordExists != null) {
            System.out.println("Ordet finns redan");
            throw new IllegalArgumentException("Ordet finns redan");
        }
        if (word.getWord().length() < 1) {
            System.out.println("Du glömde skriva in ett ord");
            throw new IllegalArgumentException("Du glömde skriva in ett ord");
        }
        else {
            return wordRepository.save(word);
        }
    }

    public Object getWords() {
        return wordRepository.findAll();
    }

    public String getRandomWord() {
       Word randomWord = wordRepository.findAll().get((int) (Math.random() * wordRepository.count()));
       return randomWord.getWord();
    }
    
}
