/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Genuini;

/**
 *
 * @author Valentin
 */
public class CreaMenu implements Screen{

    private SpriteBatch batch;
    private Texture background;
    
    BitmapFont font;
    private Rectangle appuiJouer;
    
    public CreaMenu(Genuini app){
        batch = new SpriteBatch();
        background = new Texture("background.jpg");
        font = new BitmapFont();
        appuiJouer = new Rectangle(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()-150,64,64);
    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(background, 0, 0);
        font.draw(batch, "Bienvenue dans notre monde extraordinaire", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-50);
        font.draw(batch, "Jouer", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-150);
        
        batch.end();
        
        if(Gdx.input.isButtonPressed(0)){ 
        if(Gdx.input.getX() == Gdx.graphics.getWidth()/3 && Gdx.input.getY() == Gdx.graphics.getHeight()-150){
            System.out.println("click");
        }}
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
    
}
