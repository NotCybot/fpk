package de.thro.inf.prg3.a03;

public class HungryState extends State{

    HungryState(int duration) {
        super(duration);
    }

    @Override
    public State successor(Cat cat) {
        return new DeadState();
    }

    public State feed(Cat cat){
        return new DigestingState(cat.getDigest(), duration - time);
    }

}
