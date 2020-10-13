package com.mountainbreaker.input;

public class InputAction {

    private final int boundKey;
    private final ActionTiming timing;

    public InputAction(int boundKey, ActionTiming timing) {
        this.boundKey = boundKey;
        this.timing = timing;
    }

    public void boundAction() {

    }

    public int getBoundKey() {
        return boundKey;
    }

    public ActionTiming getTiming() {
        return timing;
    }
}
