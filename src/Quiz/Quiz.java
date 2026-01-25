package Quiz;

import Cards.Card;
import Cards.CardCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    String quizname;
    HashMap<Integer, Question> questions = new HashMap<>();
    int index = 0;
    public Quiz(String name){
        this.quizname = name;
    }
    public void createQuestion(String text, List<String> answers, int correct){
        questions.put(index, new Question(quizname, text, answers, correct));
        index++;
    }
    public void deleteQuiz(int i){
        questions.remove(i);
    }
    public void makeCard(Question question, CardCollection cards) {
        cards.add(new Card(question.getText(), question.getAnswers()));
    }
    public void start(String name){
        if (quizname.equals(name)){
            Scanner sc = new Scanner(System.in);
            for (int i = 0; i < questions.size(); i++){
                Question question = questions.get(i + 1);
                System.out.println(i + 1 + " " + question.getText());
                System.out.println(question.getAnswers());
                if (sc.nextInt() != (question.getCorrect() + 1)){
                    System.out.println("НЕПРАВИЛЬНО\n правильный ответ: " + question.getCorrect() + 1);
                }
            }
        }
    }
}
