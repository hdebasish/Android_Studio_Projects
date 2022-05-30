package com.example.trivia.data;

import com.example.trivia.model.QuestionBank;

import java.util.ArrayList;

public interface QuestionListAsyncResponse {
    void onFinished(ArrayList<QuestionBank> questionBankArrayList);
}
