/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import genuini.main.MainGame;

/**
 *
 * @author Adrien
 */
public abstract class AbstractScreen extends Stage implements Screen {
 
    protected AbstractScreen() {
        super( new StretchViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera()) );
    }
 
    // Subclasses must load actors in this method
    public abstract void buildStage();
 
    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Calling to Stage methods
        super.act(delta);
        super.draw();
    }
 
    @Override
    public void show() {
    }
    
    @Override
    public void resize(int width, int height) {
        getViewport().update(width, height, true);
    }
    
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}