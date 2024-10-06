/**
 * JavaSynth
 *
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.controls;

import de.mmth.javasynth.sound.Globals;
import de.mmth.javasynth.sound.Harmonic;
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
     * @param numberOfHarmonics
     */
    public MainPane(int numberOfHarmonics) {
        var box = new HBox();
        box.setSpacing(30);
        box.setStyle("-fx-padding: 25px;");
        box.getChildren().add(createControlSection());
        box.getChildren().add(createParamsSection(numberOfHarmonics));
        this.getChildren().add(box);
    }

    /**
     * Creates the right side with the parameter settings
     * for the given number of harmonics.
     *
     * @param numberOfHarmonics number of harmonics
     * @return JavaFx controls bound to the harmonics data
     */
    private Node createParamsSection(int numberOfHarmonics) {
        var harmonics = new Harmonic[numberOfHarmonics];
        for (var i = 0; i < harmonics.length; i++) {
            harmonics[i] = new Harmonic(i+1);
        }

        return new HarmonicsPane(harmonics);
    }

    /**
     * Creates the left side with the global settings.
     *
     * @return JavaFx controls bound to the globals data
     */
    private Node createControlSection() {
        var globals = new Globals();
        return new GlobalParams(globals);
    }
}
