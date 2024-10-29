package de.thro.inf.prg3.a03;

public class PlayfulState extends State{

    PlayfulState(int duration) {
        super(duration);
    }

    @Override
    public State successor(Cat cat) {
        return new SleepingState(cat.getSleeping());
    }

}
