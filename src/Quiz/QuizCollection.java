package Quiz;

import java.util.ArrayList;

public class QuizCollection {
    ArrayList<Quiz> collection = new ArrayList<>();
    public void add(Quiz quiz){
        collection.add(quiz);
    }
    public ArrayList getCollection(){
        return collection;
    }
    public String getCollectionString(){
        StringBuilder sb = new StringBuilder();
        for (Quiz quiz: collection){
            sb.append(quiz.getName()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
