package QuizApp.View;

import QuizApp.Model.Answer;
import QuizApp.Model.Task;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;



public class TasksView {

    private static final Color CORRECT_TASK_COLOR = Color.GREEN;
    private static final Color INCORRECT_TASK_COLOR = Color.RED;

    private static final Color CURRENT_TASK_BORDER_COLOR = Color.CYAN;
    private static final Border DEFAULT_BORDER = UIManager.getBorder("Button.border");
    private static final Border CURRENT_TASK_BORDER = new LineBorder(CURRENT_TASK_BORDER_COLOR, 3);

    private static final Color SELECTED_ANSWER_COLOR = CURRENT_TASK_BORDER_COLOR;
    private static final Color UNSELECTED_ANSWER_COLOR = Color.WHITE;

    private static final Color CORRECT_SELECTED_ANSWER_COLOR = CORRECT_TASK_COLOR;
    private static final Color INCORRECT_SELECTED_ANSWER_COLOR = INCORRECT_TASK_COLOR;
    private static final Color CORRECT_UNSELECTED_ANSWER_COLOR = Color.ORANGE;
    private static final Color INCORRECT_UNSELECTED_ANSWER_COLOR = UNSELECTED_ANSWER_COLOR;

    private JPanel tasksPanel;
    private JPanel answersPanel;
    private JPanel taskListPanel;
    private JTextArea questionArea;
    private JButton nextButton;
    private JButton prevButton;
    private JButton finishButton;
    private JButton musicButton;

    private List<JButton> taskList;

    private View view;

    private TasksViewStates state;

    public TasksView(int tasksNumber) {
        createTaskList(tasksNumber);
        addListeners();
        setState(TasksViewStates.RUN);
    }

    void setView(View view) {
        this.view = view;
    }

    JPanel getTasksPanel() {
        return tasksPanel;
    }

    private void setState(TasksViewStates state) {

        if (state.equals(this.state)) {
            return;
        }

        this.state = state;

        switch (state) {
            case RUN:
                finishButton.setText("Finish");
                removeActionListeners(finishButton);
                finishButton.addActionListener(event -> finishCallback());
                discolorTaskList();
                break;
            case RESULTS:
                finishButton.setText("To menu");
                removeActionListeners(finishButton);
                finishButton.addActionListener(event -> toMenuCallback());
                view.getController().checkTasks();
                break;
        }
    }

    private void removeActionListeners(JButton button) {

        for (var listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
    }

    void update(Task task) {
        updateQuestion(task.getQuestion());
        updateAnswers(task.getAnswers());
    }

    // Question:

    private void updateQuestion(String question) {
        questionArea.setText(question);
    }

    // Answers:

    private void updateAnswers(List<Answer> answers) {

        answersPanel.removeAll();
        answersPanel.setLayout(new GridLayout(answers.size(), 1));

        int answerIndex = 0;

        for (var answer : answers) {
            addAnswerButton(answer, answerIndex);
            ++answerIndex;
        }
    }

    private void addAnswerButton(Answer answer, int answerIndex) {

        var button = new JButton();

        button.setIcon(answer.getIcon());
        button.setText(answer.getText());
        button.setBackground(getAnswerButtonColor(answer));

        switch (state) {
            case RUN:
                button.setEnabled(true);
                button.addActionListener(event -> answerCallback(answerIndex));
                break;
            case RESULTS:
                button.setEnabled(false);
                break;
        }

        answersPanel.add(button);
    }

    private Color getAnswerButtonColor(Answer answer) {

        Color color = null;

        switch (state) {

            case RUN: {
                color = answer.isSelected() ? SELECTED_ANSWER_COLOR : UNSELECTED_ANSWER_COLOR;
                break;
            }
            case RESULTS: {
                if (answer.isCorrectlySelected()) {
                    color = answer.isSelected() ? CORRECT_SELECTED_ANSWER_COLOR : INCORRECT_UNSELECTED_ANSWER_COLOR;
                } else {
                    color = answer.isSelected() ? INCORRECT_SELECTED_ANSWER_COLOR : CORRECT_UNSELECTED_ANSWER_COLOR;
                }
                break;
            }
        }

        return color;
    }

    // TaskList:

    public void markTask(int taskIndex, boolean isCorrect) {

        var color = isCorrect ? CORRECT_TASK_COLOR : INCORRECT_TASK_COLOR;

        taskList.get(taskIndex).setBackground(color);
    }

    public void setTaskBorder(int index) {
        for (var task : taskList) {
            task.setBorder(DEFAULT_BORDER);
        }
        taskList.get(index).setBorder(CURRENT_TASK_BORDER);
    }

    private void createTaskList(int tasksNumber) {

        taskList = new ArrayList<>();

        taskListPanel.removeAll();
        taskListPanel.setLayout(new GridLayout(tasksNumber, 1));

        for (int taskIndex = 0; taskIndex < tasksNumber; ++taskIndex) {
            addTaskButton(taskIndex);
        }
    }

    private void addTaskButton(int taskIndex) {

        var button = new JButton(Integer.toString(taskIndex + 1));

        button.addActionListener(event -> taskIndexCallback(taskIndex));

        taskListPanel.add(button);
        taskList.add(button);
    }

    private void discolorTaskList() {

        for (var button : taskList) {
            button.setBackground(null);
        }
    }

    // Callbacks:

    private void addListeners() {
        prevButton.addActionListener(event -> prevCallback());
        nextButton.addActionListener(event -> nextCallback());
        musicButton.addActionListener(event -> musicCallback());
    }

    private void prevCallback() {
        view.getController().prevTask();
    }

    private void nextCallback() {
        view.getController().nextTask();
    }

    private void finishCallback() {

        setState(TasksViewStates.RESULTS);

        var message = "You score: " + view.getController().getScore() + "%";
        showMessageDialog(view, message, "Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void toMenuCallback() {
        view.getController().updateStartView();
        setState(TasksViewStates.RUN);
    }

    private void answerCallback(int answerIndex) {
        view.getController().selectAnswer(answerIndex);
    }

    private void taskIndexCallback(int taskIndex) {
        view.getController().setCurrentTask(taskIndex);
    }

    private void musicCallback() {
        var isMusicRunning = view.getController().isMusicRunning();
        view.getController().setMusic(!isMusicRunning);
    }
}
