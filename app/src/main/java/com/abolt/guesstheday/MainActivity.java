package com.abolt.guesstheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private TextView highscoretext;
    private int highscore;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final int Request_code_quiz = 1;
    public static final String KEY_HIGHSCORE = "keyHighscore";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        highscoretext = findViewById(R.id.text_score);

        Intent i =getIntent();
        int get_score = i.getIntExtra("high_score",0);
        if(get_score > highscore)
        {
            update_highscore(get_score);
        }
        loadHighscore();
        Button b_st_quiz = findViewById(R.id.button_start_quiz);
        b_st_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_q();
            }
        });
        Button b_rules = findViewById(R.id.button_rules);
        b_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_rules();
            }
        });
    }

    private void start_q() {
        Intent intent = new Intent(this, Quiz_Activity.class);
        startActivity(intent);
    }
        private void open_rules()
    {
        Intent intent = new Intent(this, Rules_Activity.class);
        startActivity(intent);
    }
    private void loadHighscore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        highscoretext.setText("Highscore: " + highscore);
    }
    private void update_highscore(int new_high){
        highscore = new_high;
        highscoretext.setText("Highscore: " + highscore);
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }
}
