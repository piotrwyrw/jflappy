package org.piotrwyrw.flappy.state;

import org.piotrwyrw.flappy.*;
import org.piotrwyrw.flappy.evt.GameEvent;
import org.piotrwyrw.flappy.evt.GameEventType;
import org.piotrwyrw.flappy.evt.KeyboardEvent;
import org.piotrwyrw.flappy.physics.*;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class InGameState extends GameState {

    public static final int         BIRD_DIMENSION  = 50;
    public static final double      GRAVITY_ACCEL   = 0.3;
    public static final double      JUMP_STRENGTH   = 7.0;
    public int         PIPE_FREE       = 350;
    public static final int         PIPE_WIDTH      = 100;

    private BufferedImage title;
    private ParallaxImage background;
    private PhysicalEnvironment world;
    private AnimatedImageObject bird;
    private Trail birdTrail;

    private Pair<ImageObject, ImageObject> lastPipe;

    private ArrayList<Pair<ImageObject, ImageObject>> pipes;
    private boolean canJump;
    private boolean collide;
    private boolean trail;
    private boolean pause = false;

    private int score = 0;

    @Override
    public void initialise() {
        background = new ParallaxImage(0.25);

        background.addLayer(GameAssets.getInstance().getAsset("layer1"));
        background.addLayer(GameAssets.getInstance().getAsset("layer2"));
        background.addLayer(GameAssets.getInstance().getAsset("layer3"));
        background.addLayer(GameAssets.getInstance().getAsset("layer4"));
        background.addLayer(GameAssets.getInstance().getAsset("layer5"));
        background.addLayer(GameAssets.getInstance().getAsset("layer6"));

        world = new PhysicalEnvironment(GRAVITY_ACCEL);

        // Create the Physical bird object
        CircularObject birdObject = new CircularObject(new Vector(Game.WIDTH / 2.0, Game.HEIGHT - Game.HEIGHT / 3.0), BIRD_DIMENSION / 2.0);
        world.addObject(birdObject);

        // Create the physics bound image object
        this.bird = new AnimatedImageObject(
                birdObject,
                GameAssets.getInstance().getAsset("bird1"),
                GameAssets.getInstance().getAsset("bird2"),
                -(BIRD_DIMENSION / 2.0), -(BIRD_DIMENSION / 2.0)
        );

        this.birdTrail = new Trail(Color.decode("#f6e58d"), 10.0, 30);

        // Initialize the pipes array with the initial pipe (200px offset)
        this.pipes = new ArrayList<>();

        this.lastPipe = randomImageObjectPipe(-200);

        this.canJump = true;
        this.collide = true;
        this.trail = false;
    }

    @Override
    public void tick(GameEvent evt, GameLoop game, StateManager mgr) {
        if (pause) {
            return;
        }
        world.update();

        CircularObject b = world.extractBird();

        this.pipes.forEach((pipe) -> {
            double off = 10;
            pipe.first().parent().location().setX(pipe.first().parent().location().x() - off);
            pipe.last().parent().location().setX(pipe.first().parent().location().x() - off);

            if (!this.collide)
                return;

            RectangularObject first = ((RectangularObject) pipe.first().parent());
            RectangularObject last = ((RectangularObject) pipe.last().parent());

            if (
                    b.location().x() >= first.location().x() &&                         // } Check if the bird is between the current pipe
                    b.location().x() <= first.location().x() + first.bounds().x()       // }
            ) {
                if (!first.collidesWith(b) && !last.collidesWith(b) && !pipe.equals(lastPipe)) {
                    this.lastPipe = pipe;    // Avoid multiple points from one pipe
                    this.score ++;
                    PIPE_FREE --;
                    if (PIPE_FREE <= BIRD_DIMENSION + 10) {
                        PIPE_FREE = 350;
                    }
                } else  if (!pipe.equals(lastPipe)) {
                    b.pushLeft(20);

                    this.canJump = false;
                    this.collide = false;
                }
            }

        });

        if (trail) {
            this.birdTrail.add(this.bird.parent().location());
            this.birdTrail.update();
        }

        if (b.location().x() < -500 || b.location().y() > Game.HEIGHT + 500) {
            mgr.swapState("game", new InGameState());
            mgr.switchState("game");
        }

        if (evt == null) {
            return;
        }

        if (evt.type() != GameEventType.KEYBOARD) {
            return;
        }

        KeyboardEvent e = (KeyboardEvent) evt;
        if (e.key() == KeyEvent.VK_P) {
            this.pause = true;
        }
        if (e.key() != KeyEvent.VK_SPACE) {
            return;
        }

        if (!world.gravity()) {
            pipes.add(randomImageObjectPipe(Game.WIDTH + 200));
            world.enableGravity();
            this.trail = true;
        }

        if (canJump)
            b.jump(JUMP_STRENGTH);
    }

    @Override
    public void render(GameEvent evt, GameLoop loop, Graphics2D g, Game game) {
        // Clear the back buffer (= current buffer)
        game.clearBack();

        // Render the parallax background
        background.render(g, 0, 0, 0);

        // Render the pipe
        for (int i = 0; i < this.pipes.size(); i ++) {
            Pair<ImageObject, ImageObject> pipe = this.pipes.get(i);
            // Automatically remove invisible pipes (to avoid increased memory usage)
            if (pipe.first().parent().location().x() + PIPE_WIDTH < 0) {
                pipes.remove(pipe);
                pipes.add(randomImageObjectPipe(Game.WIDTH));
            }
            pipe.first().render(g);
            pipe.last().render(g);
        }

        // Render the bird's trail
        this.birdTrail.render(g);

        // Render the bird
        CircularObject b = world.extractBird();
        bird.render(g);

        // Render the highlight on the bird
        RadialGradientPaint paint = new RadialGradientPaint((int) bird.parent().location().x(), (int) bird.parent().location().y(), Math.max(Game.WIDTH, Game.HEIGHT), new float[]{0.0f, 1.0f}, new Color[]{new Color(0, 0, 0, 0), new Color(0, 0, 0, 220)});
        Paint p = g.getPaint();
        g.setPaint(paint);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setPaint(p);

        // Draw the score
        g.setColor(Color.WHITE);
        g.setFont(new Font("VT323", Font.PLAIN, 72));
        String str =
                (trail) ? "SCORE: " + String.valueOf(this.score) : "Press SPACE to start";
        int textWidth = g.getFontMetrics().stringWidth(str);
        g.drawString(str, Game.WIDTH / 2 - textWidth / 2, 100);

        // Commit the back buffer to the front buffer
        game.commitBuffer();
    }

    public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
        x = x-(r/2);
        y = y-(r/2);
        g.fillOval(x,y,r,r);
    }

    public Pair<ImageObject, ImageObject> randomImageObjectPipe(int x) {

        Pair<RectangularObject, RectangularObject> pipe = randomPipe(x);

        return new Pair<>(
                new ImageObject(
                        pipe.first(),
                        GameAssets.getInstance().getAssetScaled("pipe", PIPE_WIDTH, (int) pipe.first().bounds().y())
                ),
                new ImageObject(
                        pipe.last(),
                        GameAssets.getInstance().getAssetScaled("pipe", PIPE_WIDTH, (int) pipe.last().bounds().y())
                )
        );
    }

    public Pair<RectangularObject, RectangularObject> randomPipe(int x) {
        int topHeight = 50 + (int) (Math.random() * 300);

        int bottomHeight = Game.HEIGHT - topHeight - PIPE_FREE;

        RectangularObject top = new RectangularObject(new Vector((double) x, 0), new Vector(PIPE_WIDTH, topHeight));
        RectangularObject bottom = new RectangularObject(new Vector((double) x, Game.HEIGHT - bottomHeight), new Vector(PIPE_WIDTH, bottomHeight));

        return new Pair<>(top, bottom);
    }

}
