package QuizApp.Controller;

import QuizApp.Model.Task;
import QuizApp.View.View;
import QuizApp.View.ViewTypes;

import javax.sound.sampled.*;
import java.io.*;
import java.util.*;



public class Controller {

    private static final String BACKGROUND_MUSIC_PATH = "resources/audio/";

    private View view;
    private List<Task> model;

    private int currentTaskIndex;

    private Clip clip;

    public Controller(View view, List<Task> model) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.view = view;
        this.model = model;

        view.setController(this);

        shuffleModel();
        createMusic();

        updateStartView();
    }

    public void updateStartView() {
        currentTaskIndex = 0;
        view.update(ViewTypes.START, null);
    }

    public void updateTasksView() {
        var currentTask = model.get(currentTaskIndex);
        view.update(ViewTypes.TASKS, currentTask);

        view.getTasksView().setTaskBorder(currentTaskIndex);
    }

    public void nextTask() {

        ++currentTaskIndex;

        if (currentTaskIndex >= model.size()) {
            currentTaskIndex = 0;
        }

        updateTasksView();
    }

    public void prevTask() {

        --currentTaskIndex;

        if (currentTaskIndex < 0) {
            currentTaskIndex = model.size() - 1;
        }

        updateTasksView();
    }

    public void setCurrentTask(int taskIndex) {

        if ((taskIndex < 0) || (taskIndex >= model.size())) {
            throw new IndexOutOfBoundsException();
        }

        currentTaskIndex = taskIndex;

        updateTasksView();
    }

    public void selectAnswer(int answerIndex) {
        var currentTask = model.get(currentTaskIndex);
        currentTask.unselectAnswers();
        currentTask.setAnswerSelected(answerIndex, true);
        updateTasksView();
    }

    public void unselectAnswers() {

        for (var task : model) {
            task.unselectAnswers();
        }
    }

    public int getScore() {

        int correctAnswersNumber = 0;

        for (var task : model) {
            if (task.isCorrect()) {
                ++correctAnswersNumber;
            }
        }

        var score = (!model.isEmpty()) ? (correctAnswersNumber * 100 / model.size()) : 100;

        return score;
    }

    public void checkTasks() {

        int index = 0;

        for (var task : model) {
            view.getTasksView().markTask(index, task.isCorrect());
            ++index;
        }

        updateTasksView();
    }

    private void shuffleModel() {

        Collections.shuffle(model);

        for (var task : model) {
            Collections.shuffle(task.getAnswers());
        }
    }

    private void createMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        var musicFolder = new File(BACKGROUND_MUSIC_PATH);
        var musicFiles = Objects.requireNonNull(musicFolder.listFiles(((dir, name) -> name.endsWith(".wav"))));

        var rand = new Random();
        var index = rand.nextInt(musicFiles.length);
        var musicFile = musicFiles[index];

        var audioInputStream = AudioSystem.getAudioInputStream(musicFile);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        setMusic(true);
    }

    public void setMusic(boolean isRun) {
        if (isRun) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.stop();
        }
    }

    public boolean isMusicRunning() {
        return clip.isRunning();
    }
}
