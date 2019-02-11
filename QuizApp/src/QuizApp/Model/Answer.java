package QuizApp.Model;

import javax.swing.*;



public class Answer {

    private String text;
    private ImageIcon icon;
    private boolean isCorrect;
    private boolean isSelected;

    public Answer(String text, String iconPath) {
        this.text = text;
        this.icon = new ImageIcon(iconPath);
        this.isCorrect = false;
        this.isSelected = false;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getText() {
        return text;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isCorrectlySelected() {
        return isCorrect == isSelected;
    }
}
