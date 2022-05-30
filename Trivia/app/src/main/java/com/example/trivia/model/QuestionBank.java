package com.example.trivia.model;

public class QuestionBank {
    private String question;
    private Boolean answer;

    public QuestionBank(){}

    public QuestionBank(String question, Boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "question='" + question + '\'' +
                ", answer=" + answer +
                '}';
    }
}
