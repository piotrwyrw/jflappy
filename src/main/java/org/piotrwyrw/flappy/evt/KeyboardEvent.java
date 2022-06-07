package org.piotrwyrw.flappy.evt;

public class KeyboardEvent extends GameEvent {

    private int key;
    private KeyboardAction action;

    public KeyboardEvent(int key, KeyboardAction action) {
        this.type = GameEventType.KEYBOARD;
        this.key = key;
        this.action = action;
    }

    public int key() {
        return key;
    }

    public KeyboardAction action() {
        return action;
    }
}
