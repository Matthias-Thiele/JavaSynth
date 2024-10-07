/**
 * JavaSynth
 *
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.sound;

/**
 * Calculates the audio byte buffer from the global
 * and harmonics parameters.
 */
public class AudioBuffer {
    private final Globals globals;
    private final Harmonic[] harmonics;
    private byte[] buffer = new byte[0];
    private Harmonic[] activeHarmonics;

    /**
     * Constructor gets the parameter lists injected.
     * @param globals global parameter
     * @param harmonics array of harmonics parameters
     */
    public AudioBuffer(Globals globals, Harmonic[] harmonics) {
        this.globals = globals;
        this.harmonics = harmonics;
    }

    /**
     * Returns the active byte buffer.
     * @return audio data
     */
    public byte[] getBuffer() {
        return buffer;
    }

    /**
     * Recalcs the audio byte buffer.
     */
    public void updateBuffer() {
        updateActiveHarmonics();
        int seconds = (int) (globals.getAttack() + globals.getSustain() + calcToneLength());
        int sampleCount = seconds * globals.getSampleRate();

        if (buffer.length != (2 * sampleCount)) {
            buffer = new byte[2 * sampleCount];
        }

        for (var h: activeHarmonics) {
            h.delta = (Math.PI * h.getOvertoneNumber() + globals.getPitch()) / globals.getSampleRate();
        }

        fill();
    }

    /**
     * Filter all inactive harmonics.
     * All internal processing uses the (shorter) active
     * harmonics list.
     */
    private void updateActiveHarmonics() {
        var activeCount = 0;
        for (var h: harmonics) {
            if (h.getActive()) {
                activeCount++;
            }
        }

        var insertPos = 0;
        activeHarmonics = new Harmonic[activeCount];
        for (var h: harmonics) {
            if (h.getActive()) {
                activeHarmonics[insertPos++] = h;
            }
        }
    }

    /**
     * Fills the audio byte buffer.
     */
    private void fill() {
        int startSustain = fillAttack();
        var startDecay = fillSustain(startSustain);
        fillDecay(startDecay);
    }

    /**
     * Fills the attack data into the audio byte buffer.
     * @return next free byte buffer position
     */
    private int fillAttack() {
        if (globals.getAttack() < 0.01) {
            return 0;
        }

        int length = (int) Math.round(globals.getAttack() * globals.getSampleRate());
        int bytePos = 0;
        double deltaLoudness = globals.getLoudness() / length;
        double globalLoudness = 0.0;

        for (var i = 0; i < length; i++) {
            var value = calcOneSample(i, globalLoudness);
            globalLoudness += deltaLoudness;
            buffer[bytePos++] = (byte) value;
            buffer[bytePos++] = (byte) (value >> 8);
        }

        return bytePos;
    }

    /**
     * Fills the sustain data into the audio byte buffer.
     *
     * @param bytePos first free byte buffer position
     * @return next free byte buffer position
     */
    private int fillSustain(int bytePos) {
        if (globals.getSustain() < 0.01) {
            return bytePos;
        }

        int length = (int) Math.round(globals.getSustain() * globals.getSampleRate());
        double globalLoudness = globals.getLoudness();

        for (var i = 0; i < length; i++) {
            var value = calcOneSample(i, globalLoudness);
            buffer[bytePos++] = (byte) value;
            buffer[bytePos++] = (byte) (value >> 8);
        }

        return bytePos;
    }

    /**
     * Fills the decay data into the audio byte buffer.
     *
     * @param bytePos first free byte buffer position
     */
    private void fillDecay(int bytePos) {
        double harmonicLength = 0;
        for (var h: activeHarmonics) {
            harmonicLength = Math.max(harmonicLength, h.getDecay());
        }

        var length = harmonicLength * globals.getSampleRate();
        var deltaTime = 1.0d / length;
        var time = 0.0d;

        for (var i = 0; i < length; i++) {
            var value = calcOneDecaySample(i, time);
            time += deltaTime;
            buffer[bytePos++] = (byte) value;
            buffer[bytePos++] = (byte) (value >> 8);
        }
    }

    /**
     * Calculates the decay value at the given sample position
     * and time.
     *
     * @param position sample position (time times sample rate)
     * @param time actual time
     * @return audio value
     */
    private short calcOneDecaySample(int position, double time) {
        double sum = 0.0;

        for (var h: activeHarmonics) {
            if (time < h.decay) {
                var part = Math.sin(h.delta * position) * (h.loudness * (h.decay - time) / h.decay);
                sum += part;
            }
        }

        if (sum > Short.MAX_VALUE) {
            sum = Short.MAX_VALUE;
        } else if (sum < Short.MIN_VALUE) {
            sum = Short.MIN_VALUE;
        }

        return (short) sum;
    }

    /**
     * Calculates an attack or sustain audio value.
     *
     * @param position sample position
     * @param attackLoudness loudness at this position (constant when sustain)
     * @return audio value
     */
    private short calcOneSample(int position, double attackLoudness) {
        double sum = 0.0;

        for (var h: activeHarmonics) {
            var part = Math.sin(h.delta * position) * h.loudness;
            sum += part;
        }

        sum *= attackLoudness;
        if (sum > Short.MAX_VALUE) {
            sum = Short.MAX_VALUE;
        } else if (sum < Short.MIN_VALUE) {
            sum = Short.MIN_VALUE;
        }

        return (short) sum;
    }

    /**
     * Calculates the tone length.
     * Uses the length of the longest harmonic and the attack and sustain length.
     *
     * @return length in seconds
     */
    private double calcToneLength() {
        double harmonicLength = 0;
        for (var h: activeHarmonics) {
            harmonicLength = Math.max(harmonicLength, h.getDecay());
        }

        return globals.getAttack() + globals.getSustain() + harmonicLength;
    }
}
