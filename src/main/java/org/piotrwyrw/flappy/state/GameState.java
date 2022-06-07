package org.piotrwyrw.flappy.state;

import org.piotrwyrw.flappy.Game;
import org.piotrwyrw.flappy.GameLoop;
import org.piotrwyrw.flappy.evt.GameEvent;

import java.awt.*;

public abstract class GameState {

    public abstract void initialise();
    public abstract void render(GameEvent evt, GameLoop loop, Graphics2D g, Game game);

}
