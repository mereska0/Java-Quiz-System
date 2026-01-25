package Quiz;

import java.util.ArrayList;

public class QuizCollection {
    ArrayList<String> collection = new ArrayList<>();
    public void add(String name){
        collection.add(name);
    }
    public String getCollection(){
        StringBuilder sb = new StringBuilder();
        for (String quiz: collection){
            sb.append(quiz).append("\n");
        }
        return sb.toString().trim();
    }
}
