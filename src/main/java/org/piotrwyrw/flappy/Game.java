package org.piotrwyrw.flappy;

import org.piotrwyrw.flappy.evt.ExitEvent;
import org.piotrwyrw.flappy.evt.KeyboardAction;
import org.piotrwyrw.flappy.evt.KeyboardEvent;
import org.piotrwyrw.flappy.state.GameState;
import org.piotrwyrw.flappy.state.MenuState;
import org.piotrwyrw.flappy.state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Game extends JPanel {

    private static final String     TITLE   = "Flappy Bird";
    public static final int        WIDTH   = 1500;
    public static final int        HEIGHT  = 800;

    private JFrame frame;

    private GameLoop loop;
    private StateManager mgr;

    private Graphics2D graphics;

    private BufferedImage front;
    private BufferedImage back;

    public Game() {
        super();

        // Set up the game window
        frame = new JFrame(TITLE);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // We'd like to handle window closing by ourselves
        frame.add(this);
        frame.setVisible(true);

        this.back = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.graphics = this.back.createGraphics();

        this.front = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        this.mgr = new StateManager();

        GameState state = new MenuState();
        this.mgr.addState("menu", state);
        this.mgr.switchState("menu");

        this.loop = new GameLoop(mgr, this.graphics, this, this);

        setupEventHandlers();

        this.loop.start();
    }

    private void setupEventHandlers() {
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                super.windowClosing(e);
                loop.queueEvent(new ExitEvent());
            }
        });

        this.frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                loop.queueEvent(new KeyboardEvent(e.getKeyCode(), KeyboardAction.KEY_DOWN));
            }

            @Override
            public void keyReleased(KeyEvent e) {
                loop.queueEvent(new KeyboardEvent(e.getKeyCode(), KeyboardAction.KEY_UP));

            }
        });
    }

    public void clearBack() {
        this.graphics.setColor(Color.WHITE);
        this.graphics.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public void commitBuffer() {
        this.front = Tools.deepCopy(this.back);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(this.front, 0, 0, null);
    }

}
