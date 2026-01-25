package Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Quiz quiz = new Quiz("test");
        quiz.createQuestion("это тестовый квиз с одним вопросом, напиши 1/2/3 чтобы выбрать ответ",
                List.of("1", "2", "3"), 1);

        QuizCollection collection = new QuizCollection();
        collection.add(quiz);
        Scanner sc = new Scanner(System.in);
        System.out.println("Введи start чтобы начать квиз");
        String message = sc.next().trim();
        while (!message.equals("start")){
            System.out.println("Введи start чтобы начать квиз");
            message = sc.next().trim();
        }
        System.out.println("чтобы создать свой квиз, напиши /create");
        System.out.println("чтобы посмотреть уже созданные напиши /load");
        message = sc.next().trim();
        while (!message.equals("/create") && !message.equals("/load")){
            System.out.println("чтобы создать свой квиз, напиши /create");
            System.out.println("чтобы посмотреть уже созданные напиши /load");
            message = sc.next().trim();
        }
        if (message.equals("/create")){
            System.out.println("введи название для квиза(без пробелов)");
            message = sc.next().trim();
            Quiz newQuiz = new Quiz(message);
            while (!message.equals("n")) {
                sc.nextLine();
                System.out.println("введи вопрос для своего квиза");
                String text = sc.nextLine().trim();
                System.out.println("введи количество вопросов в квизе");
                int num = sc.nextInt();
                List<String> questions = new ArrayList<>();
                for (int i = 0; i < num; i++) {
                    System.out.println("введи " + (i + 1) + " ответ");
                    questions.add(sc.next().trim());
                }
                System.out.println("введи номер правильного ответа");
                int correct = sc.nextInt() - 1;
                newQuiz.createQuestion(text, questions, correct);
                System.out.println("создать еще один вопрос?(y/n)");
                message = sc.next().trim();
            }
            collection.add(newQuiz);
            System.out.println("Квиз '" + newQuiz.getName() + "' создан!");
        }else{
            System.out.println("квизы:");
            System.out.println(collection.getCollectionString());
        }
        sc.nextLine();
        System.out.println("введи /start <название> чтобы запустить квиз");
        message = sc.nextLine().trim();
        if (message.contains("/start ")) {
            for (int i = 0; i < collection.getCollection().size(); i++) {
                Quiz startQuiz = (Quiz) collection.getCollection().get(i);
                if (message.split(" ")[1].equals(startQuiz.getName())) {
                    QuizLearning.start(startQuiz);
                }
            }
        }


        //TODO QuizLearn + check logic
    }
}
