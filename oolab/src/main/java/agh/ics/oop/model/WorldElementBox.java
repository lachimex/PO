package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WorldElementBox{
    WorldElement worldElement;
    ImageView imageView;
    VBox vBox;
    Vector2d previousVector;
    String previousFileName;
    static Map<String, Image> imageMap = new HashMap<>();
    static{
        imageMap.put("up.png", new Image("/photos/up.png"));
        imageMap.put("left.png", new Image("/photos/left.png"));
        imageMap.put("down.png", new Image("/photos/down.png"));
        imageMap.put("right.png", new Image("/photos/right.png"));
        imageMap.put("grass.png", new Image("/photos/grass.png"));
    }

    public WorldElementBox(WorldElement worldElement) {
        this.worldElement = worldElement;
    }

    public VBox getImageBox() {
        if (previousVector == null || previousFileName == null){
            imageView = new ImageView(imageMap.get(worldElement.getFileName()));
            previousVector = worldElement.getPosition();
            previousFileName = worldElement.getFileName();
        }
        else if (previousVector != worldElement.getPosition() || !Objects.equals(previousFileName, worldElement.getFileName())){
            imageView = new ImageView(imageMap.get(worldElement.getFileName()));
        }
        else{
            return vBox;
        }
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        Label positionLabel = new Label(worldElement.getPosition().toString());
        VBox vbox = new VBox(imageView, positionLabel);
        this.vBox = vbox;
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    public WorldElement getWorldElement() {
        return worldElement;
    }
}
