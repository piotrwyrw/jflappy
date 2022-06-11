package org.piotrwyrw.flappy;

import org.piotrwyrw.flappy.evt.ExitEvent;
import org.piotrwyrw.flappy.evt.KeyboardAction;
import org.piotrwyrw.flappy.evt.KeyboardEvent;
import org.piotrwyrw.flappy.state.GameState;
import org.piotrwyrw.flappy.state.InGameState;
import org.piotrwyrw.flappy.state.MenuState;
import org.piotrwyrw.flappy.state.StateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends JPanel {

    private static final String    TITLE   = "Flappy Bird";
    public static final int        WIDTH   = 800;
    public static final int        HEIGHT  = 800;

    private JFrame frame;

    private GameLoop loop;
    private StateManager mgr;

    private Graphics2D graphics;

    private BufferedImage front;
    private BufferedImage back;

    public Game() {
        super();

        // Load all required game assets (textures)
        GameAssets assets = GameAssets.getInstance();

        assets.addAsset("layer1", Tools.grabImage("landscape/1.png"));
        assets.addAsset("layer2", Tools.grabImage("landscape/2.png"));
        assets.addAsset("layer3", Tools.grabImage("landscape/3.png"));
        assets.addAsset("layer4", Tools.grabImage("landscape/4.png"));
        assets.addAsset("layer5", Tools.grabImage("landscape/5.png"));
        assets.addAsset("layer6", Tools.grabImage("landscape/6.png"));

        assets.addAsset("bird1", Tools.grabScaledImage("bird/1.png", InGameState.BIRD_DIMENSION, InGameState.BIRD_DIMENSION));
        assets.addAsset("bird2", Tools.grabScaledImage("bird/2.png", InGameState.BIRD_DIMENSION, InGameState.BIRD_DIMENSION));

        assets.addAsset("pipe", Tools.grabImage("pipe/pipe.png"));
        assets.addAsset("topper", Tools.grabImage("pipe/topper.png"));
        assets.addAsset("topper2", Tools.grabImage("pipe/topper2.png"));

        // Register the font
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        File f = new File("assets/fonts/vt323.ttf");
        if (!f.exists()) {
            System.out.println("Error: Critical resource 'assets/fonts/vt323.ttf' does not exist.");
            System.exit(0);
        }
        try {
            genv.registerFont(Font.createFont(Font.TRUETYPE_FONT, f));
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

        GameState menu = new MenuState();
        GameState inGame = new InGameState();

        this.mgr.addState("menu", menu);
        this.mgr.addState("game", inGame);

        this.mgr.switchState("game");

        this.loop = new GameLoop(mgr, this.graphics, this, this);

        setupEventHandlers();

        this.loop.start();
    }

    private void setupEventHandlers() {
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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
