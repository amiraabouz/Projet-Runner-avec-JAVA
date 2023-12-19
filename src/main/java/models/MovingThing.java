package models;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MovingThing {
    private double x;
    private double y;
    private ImageView sprite;
    private double attitude;

    private int index;
    private int maxIndex;
    private double windowSize;
    private double offset;

    private Image spriteSheet;

    public MovingThing(double x, double y, String fileName, String sit) {
        this.x = x;
        this.y = y;
        this.attitude = 0;
        this.index = 0;
        this.maxIndex = 6;
        this.windowSize = 85;
        this.offset = 100;

        try {
            spriteSheet = new Image(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        sprite = new ImageView(spriteSheet);

        updateFrame();

        sprite.setX(x);
        sprite.setY(y);
    }

    public ImageView getSprite() {
        return sprite;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return sprite.getFitWidth();
    }

    public double getHeight() {
        return sprite.getFitHeight();
    }

    public void updateFrame() {
        sprite.setViewport(new Rectangle2D(index * windowSize, attitude * offset, windowSize, offset));
    }

    public void update() {
        index = (index + 1) % maxIndex;
        updateFrame();
    }
}
