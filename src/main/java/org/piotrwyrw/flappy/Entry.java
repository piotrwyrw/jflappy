package org.piotrwyrw.flappy;

/**
 * The Entry point of the program
 */
public class Entry {

    public static void main(String[] args) {
        new Thread(Game::new).start();
    }

}
