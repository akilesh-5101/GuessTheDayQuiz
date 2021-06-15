package com.abolt.guesstheday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Rules_Activity extends AppCompatActivity {

    private Button go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        go = findViewById(R.id.go_back);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Rules_Activity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}