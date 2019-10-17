package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    private String flashcard_deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_select_hiragana = findViewById(R.id.button_hiragana);
        Button button_select_katakana = findViewById(R.id.button_katakana);

        // User clicks hiragana button
        button_select_hiragana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard_deck = "Hiragana";
                openSettings(flashcard_deck);
            }
        });

        // User clicks katakana button
        button_select_katakana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcard_deck = "Katakana";
                openSettings(flashcard_deck);
            }
        });
    }

    // Checks with the user if they would like to review the selected flashcards
    public void openSettings(final String flashcard_deck) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Inflater custom view
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_settings, null);

        builder.setView(view)
                .setTitle("Additional options")
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Flashcard.class);
                        intent.putExtra("flashcard_deck", flashcard_deck);

                        CheckBox checkbox_randomize = view.findViewById(R.id.checkbox_randomize);
                        CheckBox checkbox_digraph = view.findViewById(R.id.checkbox_digraph);
                        CheckBox checkbox_diacritic = view.findViewById(R.id.checkbox_diacritic);
                        CheckBox checkbox_digraphs_with_diacritics = view.findViewById(R.id.checkbox_digraphs_with_diacritics);

                        intent.putExtra("checkbox_randomize", checkbox_randomize.isChecked());
                        intent.putExtra("checkbox_digraph", checkbox_digraph.isChecked());
                        intent.putExtra("checkbox_diacritic", checkbox_diacritic.isChecked());
                        intent.putExtra("checkbox_digraphs_with_diacritics", checkbox_digraphs_with_diacritics.isChecked());

                        startActivity(intent);

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }

}
