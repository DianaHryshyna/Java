package QuizApp.Model;

import java.io.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;



public class ModelReader {

    private static final String MODEL_PATH = "tasks/tasks.json";
    private static final String ANSWERS_IMAGES_FOLDER_PATH = "tasks/answersImages/";

    public static List<Task> read() throws IOException, ParseException {

        var fileReader = new FileReader(MODEL_PATH);
        var parser = new JSONParser();

        Object obj = parser.parse(fileReader);

        var jObj = (JSONObject) obj;

        return readTasks(jObj);
    }

    private static List<Task> readTasks(JSONObject jObj) {

        var tasks = new ArrayList<Task>();

        var jTasks = (JSONArray) jObj.get("tasks");

        for (var taskObj : jTasks) {
            var task = readTask(taskObj);
            tasks.add(task);
        }

        return tasks;
    }

    private static Task readTask(Object taskObj) {

        var taskMap = (Map) taskObj;

        var question = (String) taskMap.get("question");
        var jAnswers = (JSONArray) taskMap.get("answers");
        var answers = readAnswers(jAnswers);
        var correctAnswerIndex = (int)(long) taskMap.get("correct");

        return new Task(question, answers, correctAnswerIndex - 1);
    }

    private static List<Answer> readAnswers(JSONArray jAnswers) {

        var answers = new ArrayList<Answer>();

        for (var answerObj : jAnswers) {
            var answer = readAnswer(answerObj);
            answers.add(answer);
        }

        return answers;
    }

    private static Answer readAnswer(Object answerObj) {

        var answerMap = (Map) answerObj;

        var text = (String) answerMap.get("text");
        var imageName = (String) answerMap.get("image");

        return new Answer(text, ANSWERS_IMAGES_FOLDER_PATH + imageName);
    }
}
