package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.trivia.data.Score;
import com.example.trivia.data.QuestionData;
import com.example.trivia.data.QuestionListAsyncResponse;
import com.example.trivia.model.QuestionBank;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String STATE = "com.example.trivia.STATE";
    private static final String QUESTION_NO = "com.example.trivia.QUESTION_NO";
    Score scoreData;
    SharedPreferences saveState;
    List<QuestionBank> questionList;
    TextView questionTextView;
    TextView questionNumberTextView;
    TextView highScoreTextView;
    TextView yourScoreTextView;
    Button trueButton;
    Button falseButton;
    ImageButton prevButton;
    ImageButton nextButton;
    int counter=0;
    int lastQuestion=-1;
    int highScore;
    String score="";
    String newScore="";
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTextView=findViewById(R.id.question_textView);
        questionNumberTextView=findViewById(R.id.no_of_question_textView);
        highScoreTextView=findViewById(R.id.high_score_textView);
        yourScoreTextView=findViewById(R.id.your_score_textView);
        trueButton=findViewById(R.id.true_button);
        falseButton=findViewById(R.id.false_button);
        prevButton=findViewById(R.id.prev_button);
        nextButton=findViewById(R.id.next_button);
        cardView=findViewById(R.id.cardView);
        scoreData = new Score(this);
        saveState = getSharedPreferences(STATE,MODE_PRIVATE);
        counter=saveState.getInt(QUESTION_NO,0);
        highScore=scoreData.getHighScore(0);
        score= getResources().getString(R.string.high_score) + " " + highScore;
        newScore = getResources().getString(R.string.your_score)+" "+scoreData.getCurrentScore();
        highScoreTextView.setText(score);
        yourScoreTextView.setText(newScore);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        questionList = new QuestionData().getQuestion(new QuestionListAsyncResponse() {
            @Override
            public void onFinished(ArrayList<QuestionBank> questionBankArrayList) {
                questionTextView.setText(questionBankArrayList.get(counter).getQuestion());
                updateQuestionNo();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.true_button:
                checkAnswer(true);
                setQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                setQuestion();
                break;
            case R.id.prev_button:
                movePrev();
                break;
            case R.id.next_button:
                moveNext();
                break;
            default:
                break;
        }
    }

    private void setQuestion(){
        questionTextView.setText(questionList.get(counter).getQuestion());
        updateQuestionNo();
    }

    private void checkAnswer(Boolean answer){
        if (questionList.get(counter).getAnswer()==answer){
            fadeAnimation();
            if (counter>=lastQuestion){
                updateCurrentScore();
            }
        }else {
            shakeAnimation();
        }
    }

    private void updateCurrentScore() {
        scoreData.setCurrentScore(scoreData.getCurrentScore()+1);
        newScore = getResources().getString(R.string.your_score)+" "+scoreData.getCurrentScore();
        yourScoreTextView.setText(newScore);

        if (scoreData.getCurrentScore()>scoreData.getHighScore(-1)) {
            updateHighScore();
        }
    }


    private void updateHighScore() {
        highScore=scoreData.getCurrentScore();
        score= getResources().getString(R.string.high_score) + " " + highScore;
        highScoreTextView.setText(score);
    }

    private void updateQuestionNo(){
        String questionNumber = (counter+1) + "/" + questionList.size();
        questionNumberTextView.setText(questionNumber);
        if (counter>lastQuestion){
            lastQuestion=counter;
        }
    }

    private void fadeAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);
                moveNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation(){
        Animation shake= AnimationUtils.loadAnimation(this,R.anim.shake_animation);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setBackgroundColor(Color.WHITE);
                moveNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void moveNext(){
        if (counter<questionList.size()) {
            counter++;
            setQuestion();
        }
    }

    private void movePrev(){
        if (counter>0){
            counter--;
            setQuestion();
        }
    }

    @Override
    protected void onPause() {
        scoreData.setHighScore(highScore);
        SharedPreferences.Editor editor = saveState.edit();
        editor.putInt(QUESTION_NO,counter);
        editor.apply();
        super.onPause();

    }
}