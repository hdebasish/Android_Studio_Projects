package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NO_OF_QUESTION = 10 ;
    TextView questionTextView;
    Button option1Button;
    Button option2Button;
    Button option3Button;
    Button option4Button;
    int counter=0;

    Questions[] questions = new Questions[]{
            new Questions(R.string.Question1,
                    R.string.Question1_OptionA,
                    R.string.Question1_OptionB,
                    R.string.Question1_OptionC,
                    R.string.Question1_OptionD,
                    R.string.Answer1),
            new Questions(R.string.Question2,
                    R.string.Question2_OptionA,
                    R.string.Question2_OptionB,
                    R.string.Question2_OptionC,
                    R.string.Question2_OptionD,
                    R.string.Answer2),
            new Questions(R.string.Question3,
                    R.string.Question3_OptionA,
                    R.string.Question3_OptionB,
                    R.string.Question3_OptionC,
                    R.string.Question3_OptionD,
                    R.string.Answer3),
            new Questions(R.string.Question4,
                    R.string.Question4_OptionA,
                    R.string.Question4_OptionB,
                    R.string.Question4_OptionC,
                    R.string.Question4_OptionD,
                    R.string.Answer4),
            new Questions(R.string.Question5,
                    R.string.Question5_OptionA,
                    R.string.Question5_OptionB,
                    R.string.Question5_OptionC,
                    R.string.Question5_OptionD,
                    R.string.Answer5),
            new Questions(R.string.Question6,
                    R.string.Question6_OptionA,
                    R.string.Question6_OptionB,
                    R.string.Question6_OptionC,
                    R.string.Question6_OptionD,
                    R.string.Answer6),
            new Questions(R.string.Question7,
                    R.string.Question7_OptionA,
                    R.string.Question7_OptionB,
                    R.string.Question7_OptionC,
                    R.string.Question7_OptionD,
                    R.string.Answer7),
            new Questions(R.string.Question8,
                    R.string.Question8_OptionA,
                    R.string.Question8_OptionB,
                    R.string.Question8_OptionC,
                    R.string.Question8_OptionD,
                    R.string.Answer8),
            new Questions(R.string.Question9,
                    R.string.Question9_OptionA,
                    R.string.Question9_OptionB,
                    R.string.Question9_OptionC,
                    R.string.Question9_OptionD,
                    R.string.Answer9),
            new Questions(R.string.Question10,
                    R.string.Question10_OptionA,
                    R.string.Question10_OptionB,
                    R.string.Question10_OptionC,
                    R.string.Question10_OptionD,
                    R.string.Answer10)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTextView=findViewById(R.id.question_textView);
        option1Button=findViewById(R.id.option1_button);
        option2Button=findViewById(R.id.option2_button);
        option3Button=findViewById(R.id.option3_button);
        option4Button=findViewById(R.id.option4_button);
        setQuestion();
        option1Button.setOnClickListener(this);
        option2Button.setOnClickListener(this);
        option3Button.setOnClickListener(this);
        option4Button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String userChoice="";
        switch (v.getId()){
            case R.id.option1_button:
                userChoice = option1Button.getText().toString();
                Log.i("User Choice",userChoice);
                checkAnswer(userChoice);
                break;
            case R.id.option2_button:
                userChoice = option2Button.getText().toString();
                checkAnswer(userChoice);
                break;
            case R.id.option3_button:
                userChoice = option3Button.getText().toString();
                checkAnswer(userChoice);
                break;
            case R.id.option4_button:
                userChoice = option4Button.getText().toString();
                checkAnswer(userChoice);
            default:
                break;
        }
    }
    private void setQuestion(){
        questionTextView.setText(questions[counter].getQuestion());
        option1Button.setText(questions[counter].getOptionA());
        option2Button.setText(questions[counter].getOptionB());
        option3Button.setText(questions[counter].getOptionC());
        option4Button.setText(questions[counter].getOptionD());
    }

    private void checkAnswer(String userChoice){
        String answer = getResources().getString(questions[counter].getAnswer());
        Log.i("Answer",answer);
        if (userChoice.equals(answer)){
            Toast.makeText(this, "Answer is correct!", Toast.LENGTH_SHORT).show();
            incrementCounter();
            setQuestion();

        }else {
            Toast.makeText(this, "Answer is incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    private void incrementCounter() {
        counter++;
        if (counter==NO_OF_QUESTION){
            counter=0;
        }

    }
}