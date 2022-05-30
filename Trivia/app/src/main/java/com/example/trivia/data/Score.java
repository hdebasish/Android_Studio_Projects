package com.example.trivia.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Score {

    private static final String SCORE = "com.example.trivia.data.SCORE";
    private final SharedPreferences score;
    private int currentScore=0;

    public Score(Context context) {
        this.score = context.getSharedPreferences(SCORE,Context.MODE_PRIVATE);
    }

    public void setHighScore(int scoreValue) {
        SharedPreferences.Editor editor = score.edit();
        editor.putInt(SCORE,scoreValue);
        editor.apply();
    }

    public int getHighScore(int defaultValue){
        return score.getInt(SCORE,defaultValue);
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
}
