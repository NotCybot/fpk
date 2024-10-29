package de.thro.inf.prg3.a03;

public class DigestingState extends State {
    private final int remainingWakeTime;

    DigestingState(int duration, int remainingWakeTime) {
        super(duration);
        this.remainingWakeTime = remainingWakeTime;
    }

    @Override
    public State successor(Cat cat) {
        return new PlayfulState(remainingWakeTime - cat.getDigest());
    }

}
