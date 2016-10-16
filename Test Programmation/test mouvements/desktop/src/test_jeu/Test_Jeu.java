/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_jeu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import test_jeu.screen.TestGameScreen;

/**
 *Cette classe defini le jeu
 * @author jeremy
 * @since 1.0
 */
public class Test_Jeu extends Game {
    
    //Test_Jeu testGameScreen;
    
	/*
        SpriteBatch batch;
	Texture img;
        */
    
	/**
         * methode override permettant de creer un ecran
         * @since  1.0
         * @author jeremy
         */
	@Override
	public void create () {
            /*
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
            */
            super.setScreen(new TestGameScreen(this));
	}
        
        /**
         * methode override permettant de lancer l'ecran
         * @since  1.0
         * @author jeremy
         */
	@Override
	public void render () {
		/*
                Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
                        */
            super.render();
	}
	
        /**
         * methode override permettant de liberer l'ecran lors de sa fermeture
         * @since  1.0
         * @author jeremy
         */
	@Override
	public void dispose () {
            /*
		batch.dispose();
		img.dispose();
                    */
	}
}

