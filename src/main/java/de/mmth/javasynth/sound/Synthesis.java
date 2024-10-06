package de.mmth.javasynth.sound;

public class Synthesis {
    private Harmonic[] harmonics;
    private Globals globals;
    private AudioBuffer audioBuffer;

    public void init(int numberOfHarmonics) {
        harmonics = new Harmonic[numberOfHarmonics];
        for (var i = 0; i < harmonics.length; i++) {
            harmonics[i] = new Harmonic(i+1);
        }

        globals = new Globals();
        audioBuffer = new AudioBuffer(globals, harmonics);
    }

    public Harmonic[] getHarmonics() {
        return harmonics;
    }

    public Globals getGlobals() {
        return globals;
    }

    public void run() {
        System.out.println("Run");
        audioBuffer.updateBuffer();
    }
}
