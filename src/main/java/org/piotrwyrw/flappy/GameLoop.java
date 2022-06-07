package org.piotrwyrw.flappy;

import org.piotrwyrw.flappy.evt.GameEvent;
import org.piotrwyrw.flappy.evt.GameEventType;
import org.piotrwyrw.flappy.state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class GameLoop {

    private Stack<GameEvent> eventQueue;
    private boolean exit;
    private StateManager mgr;
    private Thread thread;
    private Graphics2D g;
    private JPanel panel;

    private BufferedImage front;
    private BufferedImage back;

    private Game game;

    public GameLoop(StateManager mgr, Graphics2D g, JPanel panel, Game game) {
        this.eventQueue = new Stack<>();
        this.exit = false;
        this.mgr = mgr;
        this.g = g;
        this.panel = panel;
        this.thread = new Thread(this::loop);
        this.game = game;
    }

    public void start() {
        this.thread.start();
    }

    /* Set the exit flag. The program will close on the closest tick. */
    public void stop() {
        this.exit = true;
    }

    private void loop() {
        while (true) {
            tick();
            if (exit) {
                System.exit(0);
            }
        }
    }

    private void tick() {
        GameEvent evt;

        if (this.eventQueue.empty())
            evt = null;
        else {
            evt = this.eventQueue.pop();
            if (evt.type() == GameEventType.EXIT) {
                stop();
                return;
            }
        }

        this.mgr.currentState().render(evt, this, g, this.game);
        this.panel.repaint();
    }

    public void queueEvent(GameEvent evt) {
        this.eventQueue.push(evt);
    }

}
