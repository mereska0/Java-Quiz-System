package com.example.javaquizsystem.Quiz;

import com.example.javaquizsystem.Cards.Card;
import com.example.javaquizsystem.Cards.CardCollection;

import java.util.HashMap;
import java.util.List;

public class Quiz {
    String quizname;
    HashMap<Integer, Question> questions = new HashMap<>();
    int index = 0;

    public Quiz(String name) {
        this.quizname = name;
    }

    public void createQuestion(String text, List<String> answers, int correct) {
        questions.put(index, new Question(quizname, text, answers, correct));
        index++;
    }

    public void deleteQuiz(int i) {
        questions.remove(i);
    }

    public void makeCard(Question question, CardCollection cards) {
        cards.add(new Card(question.getText(), question.getAnswers()));
    }

    public HashMap<Integer, Question> getQuestions(){
        return questions;
    }
    public String getName(){return quizname;}
}
