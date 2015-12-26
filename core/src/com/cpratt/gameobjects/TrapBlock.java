package com.cpratt.gameobjects;

public class TrapBlock extends Block {

    private boolean triggered = false;

    public TrapBlock(int x, int y) {
        super(x, y);
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }
}
