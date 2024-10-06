/**
 * JavaSynth
 *
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.controls;

import de.mmth.javasynth.sound.Harmonic;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Creates a pane with all user interface elements
 * for the settings of one harmonic.
 */
public class HarmonicParams extends Pane {
    private final Insets stdMargin = new Insets(5.0);
    private final Harmonic harmonic;

    /**
     * Constructor injects the harmonic data object.
     * All settings will be bound to this data.
     *
     * @param harmonic harmonic settings
     */
    public HarmonicParams(Harmonic harmonic) {
        this.harmonic = harmonic;

        HBox base = new HBox();
        base.setSpacing(25.0);
        var leftControls = addLeftControls();
        var rightControls = addRightControls();
        base.getChildren().addAll(leftControls, rightControls);
        this.getChildren().add(base);

        this.setStyle("-fx-border-color: #808080; -fx-border-radius: 5px; -fx-background-color: #f0f0f0");
        var shadow = new DropShadow(20.0, 3.0, 3.0, Color.LIGHTSKYBLUE);
        this.setEffect(shadow);
    }

    /**
     * Adds the left side controls like header information
     * and switch off.
     *
     * @return left side node
     */
    private Node addLeftControls() {
        var box = new VBox();
        box.setSpacing(5.0);
        var id = new Label("Harmonic " + harmonic.getOvertoneNumber());
        var activate = new CheckBox("Active");
        activate.setSelected(true);
        activate.selectedProperty().addListener((observable, oldValue, newValue) -> harmonic.setActive(newValue));
        box.getChildren().addAll(id, activate);
        VBox.setMargin(id, stdMargin);
        VBox.setMargin(activate, stdMargin);
        return box;
    }

    /**
     * Adds the right side controls - sliders with labels.
     *
     * @return right side controls
     */
    private Node addRightControls() {
        var loudnessLabel = new Label("Loudness");
        var loudnessSlider = new Slider(0.0, 1.0, harmonic.getLoudness());
        loudnessSlider.setShowTickLabels(true);
        loudnessSlider.setShowTickMarks(true);
        loudnessSlider.valueProperty().addListener(ev -> harmonic.setLoudness(loudnessSlider.valueProperty().getValue()));

        var decayLabel = new Label("Decay (s)");
        var decaySlider = new Slider(0.01, 20.0, harmonic.getDecay());
        decaySlider.setShowTickLabels(true);
        decaySlider.setShowTickMarks(true);
        decaySlider.valueProperty().addListener(ev -> harmonic.setDecay(decaySlider.valueProperty().getValue()));

        var sliders = new GridPane();
        sliders.setHgap(25.0);
        sliders.setVgap(10.0);
        sliders.add(loudnessLabel, 1, 1);
        sliders.add(loudnessSlider, 2, 1);
        sliders.add(decayLabel, 1, 2);
        sliders.add(decaySlider, 2, 2);
        return sliders;
    }
}
