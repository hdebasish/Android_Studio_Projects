package com.example.quizapp;

public class Questions {
    private int question;
    private int optionA;
    private int optionB;
    private int optionC;
    private int optionD;
    private int answer;

    public Questions(int question, int optionA, int optionB, int optionC, int optionD, int answer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public int getOptionA() {
        return optionA;
    }

    public void setOptionA(int optionA) {
        this.optionA = optionA;
    }

    public int getOptionB() {
        return optionB;
    }

    public void setOptionB(int optionB) {
        this.optionB = optionB;
    }

    public int getOptionC() {
        return optionC;
    }

    public void setOptionC(int optionC) {
        this.optionC = optionC;
    }

    public int getOptionD() {
        return optionD;
    }

    public void setOptionD(int optionD) {
        this.optionD = optionD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
