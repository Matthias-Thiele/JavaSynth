/**
 * JavaSynth
 * .
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth;

import de.mmth.javasynth.controls.MainPane;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Yet another Java Synthesizer.
 * .
 * Tone generation is done additive instead of the more
 * analog subtractive filter approach.
 */
public class JavaSynth extends Application {
    @Override
    public void start(Stage stage) {
        var root = new Group();
        root.getChildren().add(new MainPane(8));

        Scene scene = new Scene(root, 1100, 550);
        stage.setTitle("JavaSynth");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}