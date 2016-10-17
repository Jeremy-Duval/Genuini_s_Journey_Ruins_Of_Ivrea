/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera; /***/
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
 /****/
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer; /****/
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Genuini;

/**
 *
 * @author Valentin
 */
public class CreaMenu implements Screen{
    /* Sprite et texture pour image et fond d'écran */ 
    private SpriteBatch batch;
    //private Texture background;
    
    /* Variable de police */
    BitmapFont font;
    
    /*création du rectangle pour appui sur les boutons du menu (non fonctionnel)*/
    private Rectangle appuiJouer;
    
    /*variables de création de tiledMap en .tmx et penser à importer les librairies(classes marquées par /***/
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public CreaMenu(Genuini app){
        /*instanciation des variables*/
        batch = new SpriteBatch();
        //background = new Texture("background.jpg");
        font = new BitmapFont();
        //création du rectangle de click menu
        appuiJouer = new Rectangle(Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()-150,64,64);    
        
        map = new TmxMapLoader().load("essai.tmx"); //ajout de la tiledMap essai.tmx en fond de map
        TiledMapTileLayer mainLayer = (TiledMapTileLayer) map.getLayers().get(0);
        int tileSize = (int) mainLayer.getTileWidth();
        int mapWidth = mainLayer.getWidth() * tileSize;
        float ratio = mapWidth/Gdx.graphics.getWidth();
        renderer= new OrthogonalTiledMapRenderer(map,1/ratio);
        camera=new OrthographicCamera();

    }
    
    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        renderer.setView(camera);
        renderer.render();
        
        batch.begin();
        
        //batch.draw(background, 0, 0);
        
        //écriture du texte sur le screen + placement
        font.draw(batch, "Bienvenue dans notre monde extraordinaire", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-50);
        font.draw(batch, "Jouer", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()-150);
        
        batch.end();
        
        /*essai de création de zone de cliques*/
        if(Gdx.input.isButtonPressed(0)){ 
        if(Gdx.input.getX() == Gdx.graphics.getWidth()/3 && Gdx.input.getY() == Gdx.graphics.getHeight()-150){
            //debug
            System.out.println("click");
        }}
    }

    @Override
    public void resize(int width, int height) {
        //gestion de caméras
        camera.viewportWidth=width;
        camera.viewportHeight=height;
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        //gestion de caméras qui permettront "surement" le mouvement de la map, se renseigner
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
        map.dispose();
        renderer.dispose();

    }
    
}
