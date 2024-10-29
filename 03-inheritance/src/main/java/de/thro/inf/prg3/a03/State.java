package de.thro.inf.prg3.a03;

public abstract class State {
    protected int time = 0;
    protected final int duration;

    protected State(int duration){
        this.duration = duration;
    }

    final State tick(Cat cat) {
        if(duration < 0)
            return this;

        time = time + 1;

        if(time < duration) {
            return this;
        } else {
            return successor(cat);
        }
    }
    public abstract State successor(Cat cat);
}
