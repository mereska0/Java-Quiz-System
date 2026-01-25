package Quiz;

import java.util.List;

public class Question {
    private int correct;
    private String text = "";
    private List<String> answers;

    public Question(String text, List<String> answers, int correct){
        this.text = text;
        this.answers = answers;
        this.correct = correct;
    }
    public String getText(){
        return text;
    }
    public String getCorrect(){
        return answers.get(correct);
    }
}
