package Quiz;

import Cards.Card;
import Cards.CardCollection;

import java.util.HashMap;
import java.util.List;

public class Quiz {//пока что тока это
    HashMap<Integer, Question> questions = new HashMap<>();
    int index = 0;
    public void createQuiz(String text, List<String> answers, int correct){
        questions.put(index, new Question(text, answers, correct));
        index++;
    }
    public void deleteQuiz(int i){
        questions.remove(i);
    }
    public void makeCard(Question question, CardCollection cards) {
        cards.add(new Card(question.getText(), question.getCorrect()));
    }


}
