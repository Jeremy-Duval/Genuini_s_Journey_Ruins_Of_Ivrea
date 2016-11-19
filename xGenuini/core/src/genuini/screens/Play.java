/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import genuini.handlers.GameScreenManager;

/**
 *
 * @author Adrien
 */
public class Play extends GameScreen{
    private BitmapFont font = new BitmapFont();
    
    public Play(GameScreenManager gsm){
        super(gsm);
    }
    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        font.draw(spriteBatch, "bla", 100, 100);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
    }
    
}
