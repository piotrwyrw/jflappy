package org.piotrwyrw.flappy.state;

import org.piotrwyrw.flappy.Game;
import org.piotrwyrw.flappy.GameLoop;
import org.piotrwyrw.flappy.ParallaxImage;
import org.piotrwyrw.flappy.Tools;
import org.piotrwyrw.flappy.evt.GameEvent;
import org.piotrwyrw.flappy.evt.GameEventType;
import org.piotrwyrw.flappy.evt.KeyboardEvent;
import org.piotrwyrw.flappy.physics.Bird;
import org.piotrwyrw.flappy.physics.PhysicalEnvironment;
import org.piotrwyrw.flappy.physics.Vector;

import javax.print.DocFlavor;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class MenuState extends GameState {

    private BufferedImage title;
    private ParallaxImage background;
    private PhysicalEnvironment world;

    @Override
    public void initialise() {
        background = new ParallaxImage();
        background.addLayer(Tools.grabImage("landscape/1.png"));
        background.addLayer(Tools.grabImage("landscape/2.png"));
        background.addLayer(Tools.grabImage("landscape/3.png"));
        background.addLayer(Tools.grabImage("landscape/4.png"));
        background.addLayer(Tools.grabImage("landscape/5.png"));
        background.addLayer(Tools.grabImage("landscape/static.png"));

        world = new PhysicalEnvironment(0.2);
        world.addObject(new Bird(new Vector(200.0, 100.0), 50.0));
    }

    @Override
    public void tick(GameEvent evt) {
        world.update();

        if (evt == null)
            return;

        if (evt.type() != GameEventType.KEYBOARD)
            return;

        KeyboardEvent e = (KeyboardEvent) evt;
        if (e.key() != KeyEvent.VK_SPACE)
            return;

        Bird b = world.extractBird();
        b.jump(7.0);
    }

    @Override
    public void render(GameEvent evt, GameLoop loop, Graphics2D g, Game game) {
        game.clearBack();

        background.render(g, 0, 0, 0);

        RadialGradientPaint paint = new RadialGradientPaint(Game.WIDTH / 2.0f, Game.HEIGHT / 2.0f, Game.WIDTH, new float[] {0.0f, 1.0f}, new Color[] {new Color(0, 0, 0, 0), new Color(0, 0, 0, 255)});

        Paint p = g.getPaint();

        g.setPaint(paint);

        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        g.setPaint(p);
        g.setColor(Color.RED);

        Bird b = world.extractBird();

        g.fillRect((int) b.location().x(), (int) b.location().y(), 20, 20);

        game.commitBuffer();
    }
}
