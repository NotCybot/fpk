package de.thro.inf.prg3.a03;

public class DeadState extends State{

    DeadState() {
        super(-1);
    }

    @Override
    public State successor(Cat cat) {
        return this;
    }

}
