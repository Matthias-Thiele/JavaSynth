package de.mmth.javasynth.controls;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Waveform extends Canvas {
    public Waveform(int width, int height) {
        super();
        this.setWidth(width);
        this.setHeight(height);
        this.setCache(false);
    }

    public void updateView(byte[] buffer) {
        var samples = buffer.length / 2;
        var gc = this.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());

        var samplesPerStep = (double)samples / getWidth();
        var intSamplesPerStep = Math.round(samplesPerStep);
        var scaler = Short.MAX_VALUE / getHeight();
        var h = getHeight();

        for (var column = 0; column < getWidth(); column++) {
            var start = (int) (2 * Math.round(column * samplesPerStep));
            var sum = 0;
            var max = 0;
            for (var part = 1; part < intSamplesPerStep; part++) {
                short value = (short) Math.abs(buffer[start] + (buffer[start + 1] << 8));
                sum += value;
                max = Math.max(max, value);
                start += 2;
            }

            var avg = sum / intSamplesPerStep;

            gc.beginPath();
            gc.setStroke(Color.GREEN);
            gc.moveTo(column, h);
            gc.lineTo(column, h - avg / scaler);
            gc.stroke();
            gc.beginPath();
            gc.setStroke(Color.ORANGE);
            gc.moveTo(column, h - avg / scaler);
            gc.lineTo(column, h - max / scaler);
            gc.stroke();
        }
    }
}
