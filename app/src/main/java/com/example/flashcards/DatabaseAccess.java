package com.example.flashcards;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    // Constructor
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    // Returns singleton instance of DatabaseAccess
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    // Open database connection
    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public ArrayList<Vocabulary> getCards(String flashcard_deck, boolean isDigraph, boolean isDiacritic, boolean isDigraphs_with_diacritics) {

        String deck = "SELECT * FROM " + flashcard_deck;
        String digraph= "", diacritic = "", digraphs_with_diacritics = "";

        if (isDigraph) {
            digraph = " UNION SELECT * FROM Digraphs_" + flashcard_deck;
        }

        if (isDiacritic) {
            diacritic = " UNION SELECT * FROM Diacritics_" + flashcard_deck;
        }

        if (isDigraphs_with_diacritics) {
            digraphs_with_diacritics = " UNION SELECT * FROM Digraphs_with_diacritics_" + flashcard_deck;
        }

        String query = deck + digraph + diacritic + digraphs_with_diacritics;

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Vocabulary> words = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Vocabulary vocab = new Vocabulary(cursor.getString(cursor.getColumnIndex("Character")),
                        cursor.getString(cursor.getColumnIndex("English")),
                        cursor.getString(cursor.getColumnIndex("Category")));

                words.add(vocab);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return words;

    }

}
