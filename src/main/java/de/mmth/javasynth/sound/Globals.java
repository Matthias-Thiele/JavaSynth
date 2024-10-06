/**
 * JavaSynth
 *
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.sound;

/**
 * Data object of the global settings.
 * Not a record since this data is not immutable.
 */
public class Globals {
    private double loudness = 0.5;
    private double attack = 0.5;
    private double sustain = 0.5;
    private double pitch = 440.0;
    private int sampleRate = 44100;

    /**
     * get the loudness of the given harmonic in the range from 0 to 1
     * @return actual loudness
     */
    public double getLoudness() {
        return loudness;
    }

    /**
     * set the loudness of the given harmonic in the range from 0 to 1
     * @param loudness new loudness
     */
    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    /**
     * get the attack duration of the given harmonic in seconds
     * @return actual attack duration
     */
    public double getAttack() {
        return attack;
    }

    /**
     * set the attack duration of the given harmonic in seconds
     * @param attack new loudness
     */
    public void setAttack(double attack) {
        this.attack = attack;
    }

    /**
     * get the sustain duration of the given harmonic in seconds
     * @return actual sustain duration
     */
    public double getSustain() {
        return sustain;
    }

    /**
     * set the sustain duration of the given harmonic in seconds
     * @param sustain new loudness
     */
    public void setSustain(double sustain) {
        this.sustain = sustain;
    }

    /**
     * get the pitch frequency of the given harmonic in herz
     * @return actual pitch
     */
    public double getPitch() {
        return pitch;
    }

    /**
     * set the pitch frequency duration of the given harmonic in herz
     * @param pitch new loudness
     */
    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    /**
     * Returns the sample rate in samples per second.
     *
     * @return sample rate
     */
    public  int getSampleRate() {
        return sampleRate;
    }
}
