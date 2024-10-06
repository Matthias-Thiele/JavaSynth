package de.mmth.javasynth.sound;

public class AudioBuffer {
    private final Globals globals;
    private final Harmonic[] harmonics;
    private byte[] buffer = new byte[0];

    public AudioBuffer(Globals globals, Harmonic[] harmonics) {
        this.globals = globals;
        this.harmonics = harmonics;
    }

    public void updateBuffer() {
        int seconds = (int) (globals.getAttack() + globals.getSustain() + calcToneLength());
        int sampleCount = seconds * globals.getSampleRate();

        if (buffer.length != (2 * sampleCount)) {
            buffer = new byte[2 * sampleCount];
        }

        for (var h: harmonics) {
            h.delta = (Math.PI * h.getOvertoneNumber() + globals.getPitch()) / globals.getSampleRate();
        }

        fill();
    }

    private void fill() {
        int startSustain = fillAttack();
    }

    private int fillAttack() {
        if (globals.getAttack() == 0) {
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

        return length;
    }

    private short calcOneSample(int position, double attackLoudness) {
        double sum = 0.0;

        for (var h: harmonics) {
            var part = Math.sin(h.delta * position) * h.loudness;
            sum += part;
        }

        sum *= attackLoudness;
        if (sum > Short.MAX_VALUE) {
            sum = Short.MAX_VALUE;
        } else if (sum < Short.MIN_VALUE) {
            sum = Short.MIN_VALUE;
        }

        System.out.println(sum);
        return (short) sum;
    }

    private double calcToneLength() {
        double harmonicLength = 0;
        for (var h: harmonics) {
            harmonicLength = Math.max(harmonicLength, harmonics[0].getDecay());
        }

        return globals.getAttack() + globals.getSustain() + harmonicLength;
    }
}
