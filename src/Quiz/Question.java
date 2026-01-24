package Quiz;

import java.util.List;

public class Question {
    private static int correct;
    private static String text = "";
    private static List<String> answers;
    public Question(String text, List<String> answers, int correct){
        Question.text = text;
        Question.answers = answers;
        Question.correct = correct;
    }
    public static String getText(){
        return text;
    }
    public static String getCorrect(){
        return answers.get(correct);
    }
}
