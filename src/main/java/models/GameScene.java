package models;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene {
    private Camera camera;
    private StaticThing lb;
    private StaticThing rb;
    private int numberOfLives;
    private Hero hero;
    private long lastUpdateTime = 0;

    private List<ImageView> backgroundImages = new ArrayList<>();
    private int imageCount = 50;
    private int timerCounter = 0;
    private int addImageInterval = 60;

    Image mapImage = null;


    public GameScene(Group root,Camera camera) {
        super(root, 800, 600);
        this.camera = camera;
        this.numberOfLives = 3;

        try {
            mapImage = new Image(new FileInputStream("C:\\Users\\amira\\Desktop\\img\\desert.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < imageCount; i++) {
            ImageView imageView = new ImageView(mapImage);
            imageView.setLayoutX(mapImage.getWidth() * i);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);

            backgroundImages.add(imageView);
            root.getChildren().add(imageView);
        }


        lb = new StaticThing(20, 20, "C:\\Users\\amira\\Desktop\\img\\heros.png");
        rb = new StaticThing(20, 20, "C:\\Users\\amira\\Desktop\\img\\heros.png");

        lb.getImageView().setTranslateX(0);
        rb.getImageView().setTranslateX(600);

        ((Group) this.getRoot()).getChildren().addAll(lb.getImageView(), rb.getImageView());

        double totalWidth = numberOfLives * 30;
        double startX = 240;

        for (int i = 0; i < numberOfLives; i++) {
            StaticThing heart = new StaticThing(20, 20, "C:\\Users\\amira\\Desktop\\img\\heart.png");
            heart.getImageView().setTranslateX(startX + i * 30);
            heart.getImageView().setTranslateY(10);
            ((Group) this.getRoot()).getChildren().add(heart.getImageView());
        }

        hero = new Hero(115, 415, "C:\\Users\\amira\\Desktop\\img\\heros.png", AnimatedThing.getSit());

        root.getChildren().add(hero.getSprite());

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long time) {
                if (lastUpdateTime == 0) {
                    lastUpdateTime = time;
                    return;
                }

                hero.update();
                GameScene.update(camera);
                for (ImageView imageView : backgroundImages) {
                    imageView.setLayoutX(imageView.getLayoutX() - 10);
                }

                if (timerCounter >= addImageInterval) {
                    ImageView newImage = new ImageView(mapImage);
                    newImage.setLayoutX(mapImage.getWidth() * imageCount);
                    backgroundImages.add(newImage);
                    root.getChildren().add(newImage);
                    imageCount++;
                    timerCounter = 0;
                } else {
                    timerCounter++;
                }
            }
        };
        this.setOnMouseClicked(event -> {
            hero.jump();
        });

        timer.start();

    }

    public static void update(Camera camera) {
        camera.setX(camera.getX() + 1);

    }

    public Camera getGameCamera() {
        return camera;
    }

    public void render() {
        double cameraX = camera.getX();
        double width = lb.getImageView().getFitWidth();
        double height = lb.getImageView().getFitHeight();
        double lbX = - cameraX % width;
        double rbX = width + lbX;
        lb.getImageView().setX(lbX);
        lb.getImageView().setY(0);
        rb.getImageView().setX(rbX);
        rb.getImageView().setY(0);

    }

}