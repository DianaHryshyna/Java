package QuizApp;

import QuizApp.Controller.Controller;
import QuizApp.Model.ModelReader;
import QuizApp.Model.Task;
import QuizApp.View.StartView;
import QuizApp.View.TasksView;
import QuizApp.View.View;

import java.io.*;
import java.util.List;

import org.json.simple.parser.*;



public class QuizApp {

    public void run() {

        try {

            var model = createModel();
            var view = createView(model.size());
            new Controller(view, model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View createView(int modelSize) {

        var startView = new StartView();
        var tasksView = new TasksView(modelSize);

        return new View(startView, tasksView);
    }

    private List<Task> createModel() throws IOException, ParseException {
        return ModelReader.read();
    }
}
