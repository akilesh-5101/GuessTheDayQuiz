package com.abolt.guesstheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Quiz_end extends AppCompatActivity {
    private Button start_again;
    private TextView score;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_end);

        score = findViewById(R.id.score_x);
        message = findViewById(R.id.comment);
        start_again = findViewById(R.id.start_ag);
        Intent in = getIntent();
        String scor = in.getStringExtra("Score");
        int score_q = Integer.parseInt(scor);
        String s = "Score: " + score_q;
        if(score_q >= 60)
        {
            message.setText(" Great Job");
        }
        else if (score_q >= 30){
            message.setText("Good Job");
        }
        else if(score_q >= 15){
            message.setText("Nice Job");
        }
        else{
            message.setText("Try Again for better results");
        }
        score.setText(s);
        start_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s = new Intent(Quiz_end.this, MainActivity.class);
                s.putExtra("high_score", score_q );
                startActivity(s);
            }
        });
    }
}