package QuizApp.View;

import QuizApp.Controller.Controller;
import QuizApp.Model.Task;

import javax.swing.*;



public class View extends JFrame {

    private static final String WINDOW_TITLE = "Quiz";

    private Controller controller;

    private StartView startView;
    private TasksView tasksView;

    public View(StartView startView, TasksView tasksView) {
        this.startView = startView;
        this.tasksView = tasksView;

        startView.setView(this);
        tasksView.setView(this);

        setFrameParams();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    Controller getController() {
        return controller;
    }

    public TasksView getTasksView() {
        return tasksView;
    }

    public void update(ViewTypes viewType, Task task) {

        switch (viewType) {

            case START:
                setContentPane(startView.getStartPanel());
                setVisible(true);
                break;

            case TASKS:
                tasksView.update(task);
                setContentPane(tasksView.getTasksPanel());
                break;
        }
    }

    private void setFrameParams() {
        setTitle(WINDOW_TITLE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
