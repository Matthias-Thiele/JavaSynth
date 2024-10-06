/**
 * JavaSynth
 *
 * (c) 2024 Matthias Thiele
 */
package de.mmth.javasynth.sound;

/**
 * Data object of the settings of one harmonic.
 * Not a record since this data is not immutable.
 */
public class Harmonic {
    private final int overtoneNumber;
    private double loudness;
    private double decay;
    private boolean active;

    /**
     * Initialize the harmonic object depending
     * on the overtone position.
     *
     * @param overtoneNumber overtone position
     */
    public Harmonic(int overtoneNumber) {
        this.overtoneNumber = overtoneNumber;
        this.loudness = 1.0 / overtoneNumber;
        this.decay = 2.0;
    }

    /**
     * Sets the loudness of this harmonic in the range from 0 to 1.
     * @param loudness new loudness value
     */
    public  void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    /**
     * Returns the actual loudness setting in the range from 0 to 1.
     * @return loudness value
     */
    public double getLoudness() {
        return loudness;
    }

    /**
     * Sets the decay duration of this harmonic in seconds.
     * @param decay new decay value
     */
    public void setDecay(double decay) {
        this.decay = decay;
        System.out.println(decay);
    }

    /**
     * Returns the decay duration of this harmonic in seconds.
     * @return decay value
     */
    public double getDecay() {
        return decay;
    }

    /**
     * Sets the active state of this harmonic.
     * When false then the loudness will be zero
     * without destroying the loudness value.
     *
     * @param active de/activate this harmonic
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the active state of this harmonic.
     * When false then the loudness should be
     * treated as zero without changing the
     * actual value.
     *
     * @return active state
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Returns the overtone number of this harmonic.
     * @return overtone number
     */
    public int getOvertoneNumber() {
        return overtoneNumber;
    }

    @Override
    public String toString() {
        return "Harmonic{" +
                "number" + overtoneNumber +
                ", loudness=" + loudness +
                ", decay=" + decay +
                '}';
    }
}
