/**
 * JavaSynth
 * .
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.controls;

import de.mmth.javasynth.sound.Audio;
import de.mmth.javasynth.sound.Globals;
import de.mmth.javasynth.sound.Synthesis;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * JavaFx user interface for all global properties
 * like loudness or pitch.
 */
public class GlobalParams extends VBox {
    private final Globals globals;
    private final Synthesis synthesis;
    private final Audio audio;
    private Waveform waveform;

    /**
     * Constructor with injected globals data.
     * All user interface settings will be
     * bound to this object.
     *
     * @param synthesis data like loudness or pitch
     */
    public GlobalParams(Synthesis synthesis, Audio audio) {
        this.synthesis = synthesis;
        this.audio = audio;
        this.globals = synthesis.getGlobals();
        this.setSpacing(20.0);
        addHeader();
        addSliders();
        addActionButtons();
        addWaveform();
        audio.start();
    }

    private void addWaveform() {
        var wavePane = new Pane();
        wavePane.setStyle("-fx-border-color: #808080; -fx-border-radius: 5px; -fx-background-color: #f0f0f0;");
        var shadow = new DropShadow(20.0, 3.0, 3.0, new Color(1.0, 0.75, 1.0, 1.0));
        wavePane.setEffect(shadow);

        waveform = new Waveform(250, 200);
        wavePane.getChildren().add(waveform);
        this.getChildren().add(wavePane);
    }

    /**
     * Adds the user actions like play or quit
     * to the user interface.
     */
    private void addActionButtons() {
        var box = new VBox();
        box.setSpacing(5.0);
        box.setStyle("-fx-border-color: #808080; -fx-border-radius: 5px; -fx-background-color: #f0f0f0;");
        var shadow = new DropShadow(20.0, 3.0, 3.0, new Color(1.0, 1.0, 0.75, 1.0));
        box.setEffect(shadow);

        var startButton = new Button("Play");
        startButton.setOnAction(ev -> {
            synthesis.run();
            audio.playAudioBuffer(synthesis.getAudioBuffer());
            waveform.updateView(synthesis.getAudioBuffer().getBuffer());
        });

        var stopButton = new Button("Stop");
        stopButton.setOnAction(ev -> {
            audio.stopPlayer();
        });

        var quitButton = new Button("Quit");
        quitButton.setOnAction(ev -> {
            Platform.exit();
        });
        box.getChildren().addAll(startButton, stopButton, quitButton);
        this.getChildren().add(box);
    }

    /**
     * Adds a header information to the globals column.
     */
    private void addHeader() {
        var headerLabel = new Label("JavaSynth Params");
        headerLabel.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold");
        this.getChildren().add(headerLabel);
    }

    /**
     * Adds the slider group with loudness, attach, sustain and pitch.
     * Note: there is no global decay slider since the harmonics do have
     * their own decay information.
     */
    private void addSliders() {
        var grid = new GridPane();

        var loudness = addSlider(grid, 1,"Loudness", 0.0, 1.0, globals.getLoudness());
        loudness.valueProperty().addListener(value -> globals.setLoudness(loudness.getValue()));

        var attack = addSlider(grid, 2,"Attack", 0.0, 10.0, globals.getAttack());
        attack.valueProperty().addListener(value -> globals.setAttack(attack.getValue()));

        var sustain = addSlider(grid, 3,"Sustain", 0.0, 10.0, globals.getSustain());
        sustain.valueProperty().addListener(value -> globals.setSustain(sustain.getValue()));

        var pitch = addSlider(grid, 4,"Pitch", 20.0, 4000.0, globals.getPitch());
        pitch.valueProperty().addListener(value -> globals.setPitch(pitch.getValue()));

        grid.setStyle("-fx-border-color: #808080; -fx-border-radius: 5px; -fx-background-color: #f0f0f0");
        var shadow = new DropShadow(20.0, 3.0, 3.0, new Color(0.75, 1.0, 0.75, 1.0));
        grid.setEffect(shadow);
        grid.setHgap(25.0);
        grid.setVgap(10.0);
        this.getChildren().add(grid);
    }

    /**
     * Adds one generic slider to the given grid.
     *
     * @param grid destination grid
     * @param insertLine grid line number
     * @param labelText slider name
     * @param startValue slider start value
     * @param endValue slider end value
     * @param actValue slider act value
     * @return created slider for further processing
     */
    private Slider addSlider(GridPane grid, int insertLine, String labelText, double startValue, double endValue, double actValue) {
        var label = new Label(labelText);
        grid.add(label, 1, insertLine);
        var slider = new Slider(startValue, endValue, actValue);
        grid.add(slider, 2, insertLine);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        return slider;
    }
}
