package org.piotrwyrw.flappy.state;

import org.piotrwyrw.flappy.Game;
import org.piotrwyrw.flappy.GameLoop;
import org.piotrwyrw.flappy.ParallaxImage;
import org.piotrwyrw.flappy.Tools;
import org.piotrwyrw.flappy.evt.GameEvent;

import javax.print.DocFlavor;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuState extends GameState {

    private BufferedImage title;
    private ParallaxImage background;

    @Override
    public void initialise() {
        background = new ParallaxImage();
        background.addLayer(Tools.grabImage("landscape/1.png"));
        background.addLayer(Tools.grabImage("landscape/2.png"));
        background.addLayer(Tools.grabImage("landscape/3.png"));
        background.addLayer(Tools.grabImage("landscape/4.png"));
        background.addLayer(Tools.grabImage("landscape/5.png"));
        background.addLayer(Tools.grabImage("landscape/static.png"));
    }

    @Override
    public void render(GameEvent evt, GameLoop loop, Graphics2D g, Game game) {
        game.clearBack();

        background.render(g, 0, 0, 0);

        RadialGradientPaint paint = new RadialGradientPaint(Game.WIDTH / 2.0f, Game.HEIGHT / 2.0f, Game.WIDTH, new float[] {0.0f, 1.0f}, new Color[] {new Color(0, 0, 0, 0), new Color(0, 0, 0, 255)});

        g.setPaint(paint);

        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        game.commitBuffer();
    }
}
