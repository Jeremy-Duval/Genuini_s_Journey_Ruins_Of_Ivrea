/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import genuini.handlers.BoundedCamera;
import genuini.handlers.GameScreenManager;
import genuini.main.Game;

/**
 *
 * @author Adrien
 */
public abstract class GameScreen {
    protected GameScreenManager gameScreenManager;
    protected Game game;
	
    protected SpriteBatch spriteBatch;
    protected BoundedCamera cam;
    protected OrthographicCamera hudCam;
	
    protected GameScreen(GameScreenManager gameScreenManager) {
        this.gameScreenManager = gameScreenManager;
        game = gameScreenManager.game();
        spriteBatch = game.getSpriteBatch();
        cam = game.getCamera();
        hudCam = game.getHUDCamera();
    }
	
    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
	
}

