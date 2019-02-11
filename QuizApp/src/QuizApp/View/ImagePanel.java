package QuizApp.View;

import javax.swing.*;
import java.awt.*;



public class ImagePanel extends JPanel {

    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        var width = getParent().getWidth();
        var height = getParent().getHeight();

        graphics.drawImage(image, 0, 0, width, height, this);
    }
}
