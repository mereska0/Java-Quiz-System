package Quiz;

import java.util.HashMap;
import java.util.Scanner;

public class QuizLearning {
    public static void start(Quiz quiz){
        HashMap<Integer, Question> questions = quiz.getQuestions();
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println(i + 1 + " " + question.getText());
            System.out.println(question.getAnswers());
            if (sc.nextInt() != (question.getCorrect() + 1)) {
                System.out.println("НЕПРАВИЛЬНО\n правильный ответ: " +  (question.getCorrect() + 1));
            }else {
                System.out.println("ПРАВИЛЬНО");
            }
        }
    }
}
