package com.example.javaquizsystem.Quiz;

import java.util.List;

public class Question {
    private final String name;
    private int correct;
    private String text = "";
    private List<String> answers;

    public Question(String name, String text, List<String> answers, int correct){
        this.text = text;
        this.answers = answers;
        this.correct = correct;
        this.name = name;
    }
    public String getText(){
        return text;
    }
    public String getName(){return name;}
    public int getCorrect(){
        return correct;
    }
    public String getCorrectAnswer(){return answers.get(getCorrect());}

    public String getAnswers() {
        StringBuilder sb = new StringBuilder();
        for (String ans: answers){
            sb.append(ans).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
