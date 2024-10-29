package de.thro.inf.prg3.a03;

public class SleepingState extends State{

    public SleepingState(int duration) {
        super(duration);
    }

    @Override
    public State successor(Cat cat) {
        return new HungryState(cat.getAwake());
    }

}
