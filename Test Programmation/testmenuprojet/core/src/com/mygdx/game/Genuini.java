package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.screen.CreaMenu;

public class Genuini extends Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
            setScreen(new CreaMenu());    
	}

	@Override
	public void render () {
		super.render();
                
	}
        
        @Override
    public void dispose() {
        super.dispose();
    }
    
    @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }
    
    @Override
    public void pause(){
        super.pause();
        
    }
}
