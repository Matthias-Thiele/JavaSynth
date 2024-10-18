/**
 * JavaSynth
 * .
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.sound;

import javax.sound.sampled.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Performs the audio output.
 *
 * Needs a private player thread. Audio output in the
 * main message thread does not work.
 */
public class Audio extends Thread {
    AtomicBoolean startPlayer = new AtomicBoolean(false);
    AtomicBoolean stopPlayer = new AtomicBoolean(false);
    AudioBuffer audioBuffer;
    private Clip clip;

    /**
     * Waits for start or stop flags and plays the
     * audio buffer or stops playing.
     */
    @Override
    public void run() {
        System.out.println("Player thread started.");
        while (!this.isInterrupted()) {
            if (startPlayer.get()) {
                startPlayer.set(false);
                play(audioBuffer);
            } else if (stopPlayer.get()) {
                stopPlayer.set(false);
                clip.stop();
                clip.close();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Player thread interrupted.");
                    break;
                }
            }
        }

        System.out.println("Player thread terminated.");
    }

    /**
     * Starts the output of the given audio buffer.
     *
     * @param buffer sound to play
     */
    public void playAudioBuffer(AudioBuffer buffer) {
        audioBuffer = buffer;
        startPlayer.set(true);
    }

    /**
     * Stops the player.
     */
    public void stopPlayer() {
        stopPlayer.set(true);
    }

    /**
     * Plays the given audio buffer in the private thread.
     *
     * @param buffer sound to play
     */
    public void play(AudioBuffer buffer) {
        try {
            DataLine.Info info = new DataLine.Info(Clip.class, buffer.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            byte[] data = buffer.getBuffer();
            clip.open(buffer.getFormat(), data, 0, data.length);
            AtomicBoolean done = new AtomicBoolean(false);
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    stopPlayer.set(true);
                }
            });
            clip.start();
        } catch (LineUnavailableException e) {
            System.out.println("Line unavailable, player stopped.");
        }
    }

}
