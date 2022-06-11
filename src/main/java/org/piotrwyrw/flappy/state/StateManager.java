package org.piotrwyrw.flappy.state;

import java.util.HashMap;

public class StateManager {

    private HashMap<String, GameState> states;
    private String state;

    public StateManager() {
        this.states = new HashMap<>();
        this.state = "";
    }

    public boolean addState(String id, GameState state) {
        if (this.states.containsKey(id)) {
            System.out.println("Warn: State '" + id + "' already exists.");
            return false;
        }
        this.states.put(id, state);
        return true;
    }

    public void swapState(String id, GameState newState) {
        if (!this.states.containsKey(id)) {
            System.out.println("Warn: State '" + id + "' does not exist.");
            return;
        }
        this.states.remove(id);
        this.states.put(id, newState);
        this.states.get(state).initialise();
    }

    public void switchState(String id) {
        if (this.state.equalsIgnoreCase(id)) {
            return;
        }
        this.state = id;
        this.states.get(id).initialise();
    }

    public GameState currentState() {
        return this.states.get(this.state);
    }

}
