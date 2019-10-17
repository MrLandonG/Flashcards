package com.example.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Collections;

public class Flashcard extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private TextView text_view_character;
    private TextView text_view_answer;
    private TextView text_view_progress;
    private ViewFlipper viewFlipper;
    private int position;

    private DatabaseAccess databaseAccess;
    private ArrayList<Vocabulary> vocab = new ArrayList<>();
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        text_view_character = findViewById(R.id.text_view_character);
        text_view_answer = findViewById(R.id.text_view_answer);
        viewFlipper = findViewById(R.id.view_flipper);

        databaseAccess = DatabaseAccess.getInstance(Flashcard.this);
        databaseAccess.open();
        getCards();
    }

    // Retrieves the flashcards of the chosen deck
    private void getCards() {
        String flashcard_deck = getIntent().getStringExtra("flashcard_deck");
        boolean isRandom = getIntent().getBooleanExtra("checkbox_randomize", false);
        boolean isDigraph = getIntent().getBooleanExtra("checkbox_digraph", false);
        boolean isDiacritic = getIntent().getBooleanExtra("checkbox_diacritic", false);
        boolean isDigraphs_with_diacritics = getIntent().getBooleanExtra("checkbox_digraphs_with_diacritics", false);

        vocab = databaseAccess.getCards(flashcard_deck,
                isDigraph,
                isDiacritic,
                isDigraphs_with_diacritics);

        // Randomizes the flashcards if randomize checkbox is checked
        if (isRandom) {
            Collections.shuffle(vocab);
        }

        gestureDetector = new GestureDetector(this, this);

        // Displays the first character, answer, and progress
        text_view_character.setText(vocab.get(position).getCharacter());
        text_view_answer.setText(vocab.get(position).getEnglish());
        text_view_progress = findViewById(R.id.text_view_progress);
        text_view_progress.setText(getString(R.string.string_progress, position + 1, vocab.size()));

    }


    @Override
    public boolean onDown(MotionEvent e) {
        Log.v("Gesture:", "On Down");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.v("Gesture:", "On Show Press");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.v("Gesture:", "On Single Tap Up");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.v("Gesture:", "On Scroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.v("Gesture:", "On Long Press");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.v("Gesture:", "On Fling");

        float differenceX = e2.getX() - e1.getX();
        float differenceY = e2.getY() - e1.getY();

        // Checks for the greater movement between X and Y axis
        if (Math.abs(differenceX) > Math.abs(differenceY)) {

            // Right or left
            if (Math.abs(differenceX) > 100 && Math.abs(velocityX) > 100) {
                if (differenceX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }

            }

        } else {
            // Up or down
            if (Math.abs(differenceY) > 100 && Math.abs(velocityY) > 100) {
                if (differenceY > 100) {
                    onSwipeDown();
                } else {
                    onSwipeUp();
                }
            }
        }

        return false;

    }

    private void onSwipeRight() {
        Log.v("Gesture:", "On Swipe Right");
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
        viewFlipper.showNext();
        text_view_answer.setText(vocab.get(position).getEnglish());
    }

    private void onSwipeLeft() {
        Log.v("Gesture:", "On Swipe Left");
        viewFlipper.setInAnimation(this, R.anim.slide_in_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_left);
        viewFlipper.showPrevious();
        text_view_character.setText(vocab.get(position).getCharacter());
    }

    private void onSwipeDown() {
        Log.v("Gesture:", "On Swipe Down");

        if (position > 0) {
            position--;
            text_view_character.setText(vocab.get(position).getCharacter());
            text_view_answer.setText(vocab.get(position).getEnglish());
            text_view_progress.setText(getString(R.string.string_progress, position + 1, vocab.size()));
            viewFlipper.setDisplayedChild(0);
        }

    }

    private void onSwipeUp() {
        Log.v("Gesture:", "On Swipe Up");

        position++;

        if (position != vocab.size()) {
            text_view_character.setText(vocab.get(position).getCharacter());
            text_view_answer.setText(vocab.get(position).getEnglish());
            text_view_progress.setText(getString(R.string.string_progress, position + 1, vocab.size()));
            viewFlipper.setDisplayedChild(0);

        }

        else {
            // Safely exit flashcards
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
