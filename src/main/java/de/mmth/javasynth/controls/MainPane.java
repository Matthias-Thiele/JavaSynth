/**
 * JavaSynth
 * .
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.controls;

import de.mmth.javasynth.sound.Synthesis;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Creates the JavaSynth user interface.
 */
public class MainPane extends Pane {

    /**
     * Constructor
     *
     * @param numberOfHarmonics configuration parameter
     */
    public MainPane(int numberOfHarmonics) {
        var synthesis = new Synthesis();
        synthesis.init(numberOfHarmonics);

        var box = new HBox();
        box.setSpacing(30);
        box.setStyle("-fx-padding: 25px;");
        box.getChildren().add(createControlSection(synthesis));
        box.getChildren().add(createParamsSection(synthesis));
        this.getChildren().add(box);
    }

    /**
     * Creates the right side with the parameter settings
     * for the given number of harmonics.
     *
     * @param synthesis contains the harmonics array
     * @return JavaFx controls bound to the harmonics data
     */
    private Node createParamsSection(Synthesis synthesis) {
        return new HarmonicsPane(synthesis.getHarmonics());
    }

    /**
     * Creates the left side with the global settings.
     *
     * @return JavaFx controls bound to the globals data
     */
    private Node createControlSection(Synthesis synthesis) {
        return new GlobalParams(synthesis);
    }
}
