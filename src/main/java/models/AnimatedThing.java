package models;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AnimatedThing {
    private double x;
    private double y;
    private ImageView sprite;
    private double attitude;

    private int index;
    private int maxIndex;
    private double windowSize;
    private double offset;
    private static String sit;

    Image spriteSheet = null;



    public AnimatedThing(double x, double y, String fileName, String sit) {
        this.x = x;
        this.y = y;
        switch(sit) {
            case "run":
                this.attitude = 0;
                this.index = 0;
                this.maxIndex = 6;
                this.windowSize = 85;
                this.offset = 100;
                break;
            case "jump":
                this.attitude = 1.6;
                this.index = 0;
                this.maxIndex = 2;
                this.windowSize = 85;
                this.offset = 100;
                break;
            case "shoot":
                this.attitude = 3.3;
                this.index = 0;
                this.maxIndex = 6;
                this.windowSize = 82;
                this.offset = 100;
                break;
            case "jShoot":
                this.attitude = 4.92;
                this.index = 0;
                this.maxIndex = 2;
                this.windowSize = 85;
                this.offset = 100;
                break;
        }

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

    public void setSit(String sit) {
        this.sit = sit;
    }

    public ImageView getSprite() {
        return sprite;
    }
    public void jump() {
        if (getY() == 415) {
            setY(315);
            sprite.setY(getY());
            down();
        }
    }

    public Rectangle2D getHitBox() {
        return new Rectangle2D(x, y, sprite.getFitWidth(), sprite.getFitHeight());
    }
    public void down(){
        if(getY() == 315){
            setY(415);

            double targetY = 415;
            KeyValue keyValue = new KeyValue(sprite.yProperty(), targetY);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(800), keyValue);
            Timeline timeline = new Timeline(keyFrame);
            timeline.play();

        }
    }
    public void back() {
        sprite.setY(415);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void updateFrame() {
        sprite.setViewport(new Rectangle2D(index * windowSize, attitude * offset, windowSize, offset));
    }
    public void update() {
        index = (index + 1) % maxIndex;
        //TranslateTransition
        for(int i=0;i<10000000;i++){
            index = (index ) % maxIndex;
        }
        updateFrame();
    }

    public static String getSit() {
        System.out.println(sit);
        if(sit==null) return "run";
        return sit;
    }
}
