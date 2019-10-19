package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    private String flashcard_deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView card_hiragana = findViewById(R.id.card_hiragana);
        card_hiragana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard_deck = "Hiragana";
                openSettings(flashcard_deck);
            }
        });

        CardView card_katakana = findViewById(R.id.card_katakana);
        card_katakana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard_deck = "Katakana";
                openSettings(flashcard_deck);
            }
        });

        CardView card_vocabulary = findViewById(R.id.card_vocabulary);
        card_vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Vocabulary card pressed!", Toast.LENGTH_SHORT).show();
            }
        });

        CardView card_kanji = findViewById(R.id.card_kanji);
        card_kanji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Kanji card pressed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Allows the user to add additional digraph and diacritic cards and randomize the cards
    private void openSettings(final String flashcard_deck) {

        String[] additional_settings = new String[]{"Randomize", "Digraph", "Diacritic", "Digraphs with diacritics"};
        final boolean[] checked_settings = new boolean[]{false, true, true, true};

        new MaterialAlertDialogBuilder(MainActivity.this)
                .setTitle("Additional options")
                .setMultiChoiceItems(additional_settings, checked_settings, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checked_settings[which] = isChecked;
                    }
                })

                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Flashcard.class);
                        intent.putExtra("flashcard_deck", flashcard_deck);
                        intent.putExtra("checked_settings", checked_settings);
                        startActivity(intent);
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })

                .show();

    }

}
