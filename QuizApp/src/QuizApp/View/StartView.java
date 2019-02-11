package QuizApp.View;

import javax.swing.*;


public class StartView {

    private static final String BACKGROUND_IMAGE_PATH = "resources/images/background.jpg";

    private JPanel startPanel;
    private JButton startButton;

    private View view;

    public StartView() {
        addListeners();
    }

    void setView(View view) {
        this.view = view;
    }

    JPanel getStartPanel() {
        return startPanel;
    }

    private void addListeners() {
        startButton.addActionListener(event -> startCallback());
    }

    private void startCallback() {
        view.getController().unselectAnswers();
        view.getController().updateTasksView();
    }

    // IntelliJ method.
    private void createUIComponents() {
        var background = new ImageIcon(BACKGROUND_IMAGE_PATH).getImage();
        startPanel = new ImagePanel(background);
    }
}
