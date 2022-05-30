package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

import static android.view.View.*;


public class MainActivity extends AppCompatActivity {

    Button onTap;
    Button button;
    Button button1;
    Button button2;
    Button button3;
    ArrayList<String> questions;
    ArrayList<String> answersRight;
    ArrayList<String>[] options;
    TextView question;
    int id;
    String ourID;
    String buttonText;
    TextView result;
    int i;
    int k;
    TextView score;
    TextView timer;
    int num;
    int din;
    CountDownTimer count;
    TextView startGame;
    TextView textView;
    int check;
    public void replay(View view)
    {
        playAgain();
        startGame.setVisibility(INVISIBLE);

    }
    public void restart()
    {
        button.setVisibility(INVISIBLE);
        button1.setVisibility(INVISIBLE);
        button2.setVisibility(INVISIBLE);
        button3.setVisibility(INVISIBLE);
        timer.setVisibility(INVISIBLE);
        score.setVisibility(INVISIBLE);
        result.setVisibility(INVISIBLE);
        question.setVisibility(INVISIBLE);
        startGame.setVisibility(VISIBLE);
        textView.setVisibility(VISIBLE);
        startGame.setText("PLAY AGAIN");
        textView.setText("Your Score: "+num + "/" + din);
    }

    public void playAgain(){

        num=0;
        din=1;
        i=0;
        k=0;
        check=1;
        button.setVisibility(VISIBLE);
        button1.setVisibility(VISIBLE);
        button2.setVisibility(VISIBLE);
        button3.setVisibility(VISIBLE);
        timer.setVisibility(VISIBLE);
        score.setVisibility(VISIBLE);
        result.setVisibility(VISIBLE);
        question.setVisibility(VISIBLE);
        textView.setVisibility(INVISIBLE);
        score.setText("0" +"/"+ "1");
        result.setText("");
        questions.add("What is the next prime number after 7?");
        questions.add("The perimeter of a circle is also known as what?");
        questions.add("65 – 43 = ?");
        questions.add("What does the square root of 144 equal?");
        questions.add("38 + 23 = ?");
        questions.add("What comes after a million, billion and trillion?");
        questions.add("321 - 123 = ?");
        questions.add("52 divided by 4 equals what?");
        questions.add(" 87 + 56 = ?");
        questions.add("How many sides does a nonagon have?");
        questions.add("7 * 9 = ?");
        questions.add("What is the next number in the Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ?");
        questions.add("9 * 9 = ?");
        questions.add("48 divided by 4");
        questions.add(" In statistics, the middle value of an ordered set of values is called what?");
        questions.add("52 - 43 =?");
        questions.add("What does 3 squared equal?");
        questions.add("12 * 8 = ?");
        questions.add(" 5 to the power of 0 equals what?");
        questions.add("3.5 + 2.5 = ?");
        answersRight.add("11");
        answersRight.add("Circumference");
        answersRight.add("22");
        answersRight.add("12");
        answersRight.add("61");
        answersRight.add("Quadrillion");
        answersRight.add("198");
        answersRight.add("13");
        answersRight.add("None");
        answersRight.add("9");
        answersRight.add("63");
        answersRight.add("55");
        answersRight.add("81");
        answersRight.add("12");
        answersRight.add("Median");
        answersRight.add("9");
        answersRight.add("9");
        answersRight.add("96");
        answersRight.add("1");
        answersRight.add("6");

        options[0].add("9");
        options[0].add("11"); //true
        options[0].add("13");
        options[0].add("10");
        options[1].add("Circumference");   //true
        options[1].add("Radius");
        options[1].add("Diameter");
        options[1].add("None");
        options[2].add("18");
        options[2].add("27");
        options[2].add("22");   //true
        options[2].add("23");
        options[3].add("14");
        options[3].add("8");
        options[3].add("17");
        options[3].add("12");   //true
        options[4].add("61");    //true
        options[4].add("58");
        options[4].add("64");
        options[4].add("49");
        options[5].add("Sextillion");
        options[5].add("Nonillion");
        options[5].add("Quadrillion");   //true
        options[5].add("Quintillion");
        options[6].add("187");
        options[6].add("198");    //true
        options[6].add("156");
        options[6].add("194");
        options[7].add("12");
        options[7].add("13");   //true
        options[7].add("10");
        options[7].add("19");
        options[8].add("153");
        options[8].add("147");
        options[8].add("138");
        options[8].add("None"); //true
        options[9].add("8");
        options[9].add("11");
        options[9].add("13");
        options[9].add("9");    //true
        options[10].add("68");
        options[10].add("53");
        options[10].add("63");      //true
        options[10].add("67");
        options[11].add("52");
        options[11].add("48");
        options[11].add("55");      //true
        options[11].add("51");
        options[12].add("81");       //true
        options[12].add("99");
        options[12].add("83");
        options[12].add("96");
        options[13].add("14");
        options[13].add("17");
        options[13].add("12");      //true
        options[13].add("13");
        options[14].add("Mean");
        options[14].add("Mode");
        options[14].add("Median");      //true
        options[14].add("None");
        options[15].add("7");
        options[15].add("13");
        options[15].add("8");
        options[15].add("9");   //true
        options[16].add("9");   //true
        options[16].add("6");
        options[16].add("2");
        options[16].add("1");
        options[17].add("69");
        options[17].add("88");
        options[17].add("96");  //true
        options[17].add("76");
        options[18].add("0");
        options[18].add("5");
        options[18].add("1");  //true
        options[18].add("6");
        options[19].add("7");
        options[19].add("6");  //true
        options[19].add("5.8");
        options[19].add("6.5");

        question.setText(String.valueOf(questions.get(0)));
        button.setText(String.valueOf(options[0].get(0)));
        button1.setText(String.valueOf(options[0].get(1)));
        button2.setText(String.valueOf(options[0].get(2)));
        button3.setText(String.valueOf(options[0].get(3)));

        count = new CountDownTimer(60000+100,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText((int) (millisUntilFinished/1000)+"s");

            }

            @Override
            public void onFinish() {
                din--;
                restart();
            }
        }.start();

    }


    public void ButtonTapped(View view) {

        if(i<19) {
            id = view.getId();
            onTap = findViewById(id);
            buttonText = (String) onTap.getText();
            result = findViewById(R.id.result);

            if (buttonText.equals(answersRight.get(k))) {

                result.setTextColor(Color.parseColor("#008000"));
                result.setText("CORRECT!");
                score.setText(++num + "/" + ++din);
            } else {
                result.setTextColor(Color.parseColor("#FF0000"));
                result.setText("WRONG!");
                score.setText(num + "/" + ++din);
            }
            k++;
            i++;
            question.setText(String.valueOf(questions.get(i)));
            button.setText(String.valueOf(options[i].get(0)));
            button1.setText(String.valueOf(options[i].get(1)));
            button2.setText(String.valueOf(options[i].get(2)));
            button3.setText(String.valueOf(options[i].get(3)));
        }
            else
            {
                question.setText(String.valueOf(questions.get(i-1)));
                button.setText(String.valueOf(options[i-1].get(0)));
                button1.setText(String.valueOf(options[i-1].get(1)));
                button2.setText(String.valueOf(options[i-1].get(2)));
                button3.setText(String.valueOf(options[i-1].get(3)));
                if (buttonText.equals(answersRight.get(k-1))) {
                    result.setTextColor(Color.parseColor("#008000"));
                    score.setText(++num +"/"+ din);
                    count.cancel();
                    restart();
                } else {
                    result.setTextColor(Color.parseColor("#FF0000"));
                    score.setText(num +"/"+ din);
                    count.cancel();
                    restart();
                }

            }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        startGame = findViewById(R.id.playAgain);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button2);
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);
        questions = new ArrayList<>();
        answersRight = new ArrayList<>();
        options = new ArrayList[20];
        for (int i = 0; i < 20; i++) {
            options[i] = new ArrayList<String>();
        }
        result = findViewById(R.id.result);
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        timer  = findViewById(R.id.timer);
        button.setVisibility(INVISIBLE);
        button1.setVisibility(INVISIBLE);
        button2.setVisibility(INVISIBLE);
        button3.setVisibility(INVISIBLE);
        timer.setVisibility(INVISIBLE);
        score.setVisibility(INVISIBLE);
        result.setVisibility(INVISIBLE);
        question.setVisibility(INVISIBLE);
        textView.setVisibility(INVISIBLE);
        startGame.setVisibility(VISIBLE);

    }
}
