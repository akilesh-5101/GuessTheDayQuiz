package com.abolt.guesstheday;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.text.Html;
import android.text.method.LinkMovementMethod;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Math.abs;


public class Quiz_Activity extends AppCompatActivity {
    //variables and object
    public static final String Extra_score = "extrascore";
    private static final long Count_down_in_millis= 40000;

    public int finish;
    public int q_no;
    public int correct_option;
    public int correct_year;
    public int correct_month;
    public int correct_date;
    public int score;

    private TextView t_time;

    private RadioGroup rb_grp;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button confirm;
    private ColorStateList textColorDefaultRb;
    private ColorStateList TextColorDefaultCd;// to change color of radio button depending on true or false
    private long time_left_in_millis;
    private CountDownTimer count_down_timer;
    private boolean question_given;
    private TextView link;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent i = getIntent();
        // assignment
        finish = 0;
        t_time = findViewById(R.id.text_countdown);
        link = findViewById(R.id.reference);
        rb_grp = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        rb4 = findViewById(R.id.radio_button4);
        confirm = findViewById(R.id.next_q);

        TextColorDefaultCd = t_time.getTextColors();
        score = 0;
        question_given = false;
        q_no = 1;
        link = findViewById(R.id.reference);

        link.setMovementMethod(LinkMovementMethod.getInstance());
        //initial setting
        setQuestion();
        setOption();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link.setVisibility(View.INVISIBLE);
                if(finish == 1)
                {
                    finish_quiz();
                }
                else {
                    confirm.setText("Confirm");
                    rb1.setTextColor(Color.BLACK);
                    rb2.setTextColor(Color.BLACK);
                    rb3.setTextColor(Color.BLACK);
                    rb4.setTextColor(Color.BLACK);

                    if (question_given) {
                        if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                            checkAnswer();
                        } else {
                            Toast.makeText(Quiz_Activity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        setQuestion();
                        setOption();
                    }
                }


                }

        });

    }


    public void setQuestion()
    {

        int i = 0; // for loop
        int cor = 0; // to give correct option number
        int temp = 0; // just for fun
       // to change question number
        TextView q = findViewById(R.id.q_no);

        String s = "Question: " + q_no;
        q.setText(s);
        q_no++;
        rb_grp.clearCheck();


        // the date:
        String month = ret_month();
        correct_year = ret_year();
        correct_date = ret_date(correct_year,correct_month - 3);
        // to get option
        Random r = new Random();
        correct_option = abs(r.nextInt(4));

        // to change question box
        TextView t = findViewById(R.id.question_box);
        String question = "What is the day : " + correct_date + "." + month + "." + correct_year ;
        t.setText(question);
        question_given = true;

        //set timer
        time_left_in_millis = Count_down_in_millis;
        startCountDown();
    }

    private String ret_month()
    {
        // month
        String[] months = {"March","April","May","June","July","August"
                            ,"September","October","November","December","January","February"};
        Random rand = new Random();
        int x = abs(rand.nextInt(12));
        correct_month = x+3;
        return months[x];
    }
    private static int ret_year()
    {
        // year
        Random rand1 = new Random();
        int year = abs(rand1.nextInt(400) + 1800);
        return year;
    }
    private static int ret_date(int y, int m)
    {
        //date
        Random rand2 = new Random();
        int date = 0;
        int r = leap_y(y);
        if (m%2 == 0)
        {
            date = 1 + abs(rand2.nextInt(31));
        }
        else if(m == 11 && r == 1)
        {
            date = 1 + rand2.nextInt(29);
        }
        else if(m == 11 && r == 0)
        {
            date = 1 + rand2.nextInt(28);
        }
        else
        {
            date = 1 + rand2.nextInt(30);
        }
        return date;
    }

    private static int leap_y(int y)
    {
        if(y % 4 == 0)
        {
            if( y % 100 == 0)
            {
                if (y % 400 == 0)
                    return 1;
                else
                    return 0;
            }
            else
                return 1;
        }
        else
            return 0;
    }

    public void setOption() {
        int day = 0;
        int i = 0,a,b,c;
        int j = 0;Random r = new Random();
        String[] days = {"Saturday","Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        int[] num = new int[6];

        if(correct_month >= 13) {
            correct_year -= 1;
        }
        int l_2 = correct_year % 100;
        int f_2 = correct_year / 100;
        int f = correct_date + (int) (13 * (correct_month + 1) / 5) + l_2 + (int) (l_2 / 4) + (int) (f_2 / 4) + (5 * f_2);// Zeller's congruence to find the day of the week

        //to calculate correct day
        day = f%7;
        for (i = 0; i < 7; i++) {
            if (i != day) {
                num[j] = i;
                j++;
            }
        }
        a = num[r.nextInt(6)];j=0;
        for (i = 0; i < 7; i++) {
            if (i != day && i!=a) {
                num[j] = i;
                j++;
            }
        }
        b = num[r.nextInt(5)];j=0;
        for (i = 0; i < 7; i++) {
            if (i != day && i!=a && i!=b) {
                num[j] = i;
                j++;
            }
        }
        c = num[r.nextInt(4)];j=0;
        switch(correct_option) {
            case 0:
                rb1.setText(days[day]);
                rb2.setText(days[a]);
                rb3.setText(days[b]);
                rb4.setText(days[c]);
                break;
            case 1:
                rb2.setText(days[day]);
                rb3.setText(days[a]);
                rb4.setText(days[b]);
                rb1.setText(days[c]);
                break;
            case 2:
                rb3.setText(days[day]);
                rb4.setText(days[a]);
                rb2.setText(days[b]);
                rb1.setText(days[c]);
                break;
            case 3:
                rb4.setText(days[day]);
                rb1.setText(days[a]);
                rb3.setText(days[b]);
                rb2.setText(days[c]);
                break;
        }
    }

    private void checkAnswer()
    {
        question_given = false;
        count_down_timer.cancel();

        RadioButton rbselected = findViewById(rb_grp.getCheckedRadioButtonId());
        int answer_num = rb_grp.indexOfChild(rbselected);
        if(answer_num == correct_option){
            score+=3;
            confirm.setText("Next");
        }
        else{
            confirm.setText("View Score");finish = 1;link.setVisibility(View.VISIBLE);
        }
        showSolution();
    }
    private void showSolution()
    {
        switch(correct_option)
        {
            case 0: rb1.setTextColor(Color.GREEN);break;
            case 1: rb2.setTextColor(Color.GREEN);break;
            case 2: rb3.setTextColor(Color.GREEN);break;
            case 3: rb4.setTextColor(Color.GREEN);break;
        }

    }
    private void startCountDown(){
        count_down_timer = new CountDownTimer(time_left_in_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_millis = millisUntilFinished;
                updateCountdowntext();
            }

            @Override
            public void onFinish() {
                time_left_in_millis = 0;
                updateCountdowntext();
                checkAnswer();
            }
        }.start();
    }
    private void updateCountdowntext(){
        int min = (int) (time_left_in_millis/1000) / 60;
        int sec = (int) (time_left_in_millis/1000) % 60;

        String time_formatted = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        t_time.setText(time_formatted);

        if(time_left_in_millis < 10000){
            t_time.setTextColor(Color.RED);
        }
        else {
            t_time.setTextColor(Color.BLACK);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(count_down_timer!=null){
            count_down_timer.cancel();
        }
    }
    private void finish_quiz(){
        Intent in = new Intent(Quiz_Activity.this, Quiz_end.class);
        String sc = Integer.toString(score);
        in.putExtra("Score",sc);
        startActivity(in);
        finish();
    }
}