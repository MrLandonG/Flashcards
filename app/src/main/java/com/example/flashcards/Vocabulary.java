package com.example.flashcards;


public class Vocabulary {

    private String character;
    private String english;
    private String category;

    public Vocabulary(String word, String answer, String type) {
        character = word;
        english = answer;
        category = type;
    }

    public String getCharacter() {
        return character;
    }

    public String getEnglish() {
        return english;
    }

    public String getCatergory() {
        return category;
    }

}
