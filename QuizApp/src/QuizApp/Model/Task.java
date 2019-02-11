package QuizApp.Model;

import java.util.*;



public class Task {

    private String question;
    private List<Answer> answers;

    public Task(String question, List<Answer> answers, int correctAnswerIndex) {
        this.question = question;
        this.answers = answers;
        answers.get(correctAnswerIndex).setCorrect(true);
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswerSelected(int selectedAnswerIndex, boolean isSelected) {
        answers.get(selectedAnswerIndex).setSelected(isSelected);
    }

    public void unselectAnswers() {

        for (var answer : answers) {
            answer.setSelected(false);
        }
    }

    public boolean isCorrect() {

        for (var answer : answers) {
            if (!answer.isCorrectlySelected()) {
                return false;
            }
        }
        return true;
    }
}
