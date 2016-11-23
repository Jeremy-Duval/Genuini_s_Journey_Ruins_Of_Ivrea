/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.handlers;


import genuini.main.Game;
import genuini.screens.GameScreen;
import genuini.screens.Play;
import java.util.Stack;

/**
 *
 * @author Adrien
 */
public class GameScreenManager {
    private Game game;

    private Stack<GameScreen> gameScreens;

    public static final int MENU = 83774392;
    public static final int PLAY = 388031654;
    public static final int LEVEL_SELECT = -9238732;

    public GameScreenManager(Game game) {
            this.game = game;
            gameScreens = new Stack<GameScreen>();
            pushScreen(PLAY);
    }

    public void update(float dt) {
            gameScreens.peek().update(dt);
    }

    public void render() {
            gameScreens.peek().render();
    }

    public Game game() { return game; }

    private GameScreen getScreen(int state) {
            //if(state == MENU) return new Menu(this);
            if(state == PLAY) return new Play(this);
            //if(state == LEVEL_SELECT) return new LevelSelect(this);
            return null;
    }

    public void setScreen(int state) {
            popScreen();
            pushScreen(state);
    }

    public void pushScreen(int state) {
            gameScreens.push(getScreen(state));
    }

    public void popScreen() {
            GameScreen g = gameScreens.pop();
            g.dispose();
    }

}
