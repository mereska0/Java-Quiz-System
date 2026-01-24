package Quiz;

import Cards.Card;
import Cards.CardCollection;

import java.util.HashMap;
import java.util.List;

public class Quiz {//пока что тока это
    static HashMap<Integer, Question> questions = new HashMap<>();
    static int index = 0;
    public static void createQuiz(String text, List<String> answers, int correct){
        questions.put(index, new Question(text, answers, correct));
        index++;
    }
    public static void deleteQuiz(int i){
        questions.remove(i);
    }
    public static void makeCard(Question question){
        CardCollection.add(new Card(question.getText(), question.getCorrect()));
    }


}
