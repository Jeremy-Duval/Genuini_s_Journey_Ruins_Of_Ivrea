/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_jeu.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import net.java.games.input.Component;
import test_jeu.Test_Jeu;

/**
 * Classe servant à definir notre ecran.
 * Implemente la classe Screen de LibGDX
 * @since  1.0
 * @author jeremy
 */
public class TestGameScreen implements Screen{
    
    private SpriteBatch batch;
    
    /*Perso*/
    private Texture texture_perso;
    private Texture texture_perso_g;
    private Texture texture_perso_d;
    private Vector2 perso_pos;
    
    /**
     * Constructeur de l'ecran.
     * @param application : Test_Jeu 
     * @since  1.0
     * @author jeremy
     */
    public TestGameScreen(Test_Jeu application) {
        batch = new SpriteBatch();
        texture_perso = new Texture("img/gd.jpg");
        texture_perso_g = new Texture("img/gpd.jpg");
        texture_perso_d = new Texture("img/gpg.jpg");
        perso_pos = new Vector2(100,100);
    }
    
    /**
     * methode override servant à afficher l'ecran
     * @since  1.0
     * @author jeremy
     */
    @Override
    public void show() {

    }
    
    /**
     * methode override servant au rendu de l'ecran
     * @since  1.0
     * @author jeremy
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float) 0.5, 1, 0, 1);//couleur de fond
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//netoie l'écran en recoloriant
        
        batch.begin();//début la zone de dessin
        
        //batch.draw(texture_perso, 100, 100, 64, 64);//dessine le perso à la position 100,100 de taille 64x64
        batch.draw(texture_perso, perso_pos.x, perso_pos.y);
        
        batch.end();//termine la zone de dessin
        
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            perso_pos.x = perso_pos.x + 10;
            texture_perso = texture_perso_g;
        }
        if(Gdx.input.isKeyPressed(Keys.LEFT)){
            perso_pos.x = perso_pos.x - 10;
            texture_perso = texture_perso_d;
        }
                
    }
    
    /**
     * methode override servant au redimentionnement l'ecran
     * @since  1.0
     * @author jeremy
     */
    @Override
    public void resize(int width, int height) {

    }
    
    /**
     * methode override servant à mettre en pause l'ecran
     * @since  1.0
     * @author jeremy
     */
    @Override
    public void pause() {

    }
    
    /**
     * methode override servant à mettre fin à une pause
     * @since  1.0
     * @author jeremy
     */
    @Override
    public void resume() {

    }
    
    /**
     * methode override servant à cacher l'ecran
     * @since  1.0
     * @author jeremy
     */
    @Override
    public void hide() {

    }
    
    /**
     * methode override servant à quitter l'ecran
     * @since  1.0
     * @author jeremy
     */
    @Override
    public void dispose() {

    }
    
}
