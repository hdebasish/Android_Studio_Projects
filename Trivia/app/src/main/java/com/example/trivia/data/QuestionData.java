package com.example.trivia.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.QuestionBank;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionData {
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    ArrayList<QuestionBank> questionBankArrayList = new ArrayList<>();

    public List<QuestionBank> getQuestion(final QuestionListAsyncResponse callback){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        QuestionBank questionBank = new QuestionBank();
                        questionBank.setQuestion(response.getJSONArray(i).getString(0));
                        questionBank.setAnswer(response.getJSONArray(i).getBoolean(1));
                        questionBankArrayList.add(questionBank);
                       // Log.i("JSON response", questionBank.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (callback!=null){ callback.onFinished(questionBankArrayList); }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionBankArrayList;
    }
}
