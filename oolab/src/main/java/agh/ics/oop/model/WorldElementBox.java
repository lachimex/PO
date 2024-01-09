package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class WorldElementBox{
    WorldElement worldElement;
    ImageView imageView;

    public WorldElementBox(WorldElement worldElement) {
        this.worldElement = worldElement;
    }

    public VBox getImageBox() {
        imageView = new ImageView(new Image("/photos/" + worldElement.getFileName()));
        // Ustaw rozmiary obrazka na 20x20
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        // Utwórz etykietę informującą o pozycji zwierzaka
        Label positionLabel = new Label(worldElement.getPosition().toString());

        // Utwórz obiekt VBox i dodaj do niego obrazek i etykietę
        VBox vbox = new VBox(imageView, positionLabel);

        // Wyśrodkuj elementy wewnątrz kontenera
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
}
