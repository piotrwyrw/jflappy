package org.piotrwyrw.flappy.state;

import org.piotrwyrw.flappy.Game;
import org.piotrwyrw.flappy.GameLoop;
import org.piotrwyrw.flappy.evt.GameEvent;

import javax.swing.plaf.nimbus.State;
import java.awt.*;

public class MenuState extends GameState {
    @Override
    public void initialise() {

    }

    @Override
    public void tick(GameEvent evt, GameLoop game, StateManager mgr) {

    }

    @Override
    public void render(GameEvent evt, GameLoop loop, Graphics2D g, Game game) {
        game.clearBack();
        game.commitBuffer();
    }
}
