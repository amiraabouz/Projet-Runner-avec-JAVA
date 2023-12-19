package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Heart {

    private ImageView imageView;

    public Heart(double x, double y) throws FileNotFoundException {
        Image image = new Image(new FileInputStream("C:\\Users\\amira\\Desktop\\img\\heart.png"));
        imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
    }

    public ImageView getImageView() {
        return imageView;
    }
}

