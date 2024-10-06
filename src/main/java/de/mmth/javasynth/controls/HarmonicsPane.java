/**
 * JavaSynth
 *
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.controls;

import de.mmth.javasynth.sound.Harmonic;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Creates a grid with the user interface of the
 * given harmonics.
 */
public class HarmonicsPane extends Pane {

    /**
     * Constructor, injects the harmonics array and
     * creates the user interface for all harmonics
     * settings.
     *
     * @param harmonics harmonics data
     */
    public HarmonicsPane(Harmonic[] harmonics) {
        var grid = new GridPane();
        grid.setVgap(15.0);
        grid.setHgap(20.0);

        int row = 1;
        int col = 1;
        for (var item: harmonics) {
            var node = new HarmonicParams(item);
            grid.add(node, col, row);

            // alternate odd, even harmonics to the left, right
            if (col == 1) {
                col = 2;
            } else {
                col = 1;
                row++;
            }
        }

        this.getChildren().add(grid);
    }
}
