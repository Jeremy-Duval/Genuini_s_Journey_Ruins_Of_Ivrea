/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import genuini.game.PhysicsManager;
import genuini.game.TextManager;
import genuini.game.Time;
import genuini.game.entities.Player;

/**
 *
 * @author Adrien
 */
public class PlayScreen implements Screen{
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private float initialPositionX,initialPositionY;
    
    
    
    //Viewport dimensions
    int width;
    int height;
    
    
    int levelPixelWidth;
    int levelPixelHeight;
    
    
    World world;
    
    @Override
    public void show(){
        
        //Storing the viewport's dimensions
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        
        //create batch
        batch=new SpriteBatch();
        
        
        //Load the map
        map = new TmxMapLoader().load("maps/test.tmx");
        TiledMapTileLayer mainLayer = (TiledMapTileLayer) map.getLayers().get(0);
        
        // Retrieve map properties
        MapProperties properties = map.getProperties();
        
        //Size of map in tiles
        int levelWidth = properties.get("width", Integer.class);
        int levelHeight = properties.get("height", Integer.class);
        
        //Size of tile
        int tilePixelWidth = properties.get("tilewidth", Integer.class);
        int tilePixelHeight = properties.get("tileheight", Integer.class);
        
        //Store the map size
        levelPixelWidth=levelWidth*tilePixelWidth;
        levelPixelHeight=levelHeight*tilePixelHeight;
        
        

        //create renderer object
        renderer= new OrthogonalTiledMapRenderer(map); //add parameter to change size of map
        
        //create camera object
        camera=new OrthographicCamera();
        camera.setToOrtho(false,width,height);
        camera.update();
        
        //set the Text batch
        TextManager.SetSpriteBatch(batch);
        

        //create the world
        world = new World(new Vector2(0f, -500f* PhysicsManager.PIXELS_TO_METERS),false);
        
        //create player object
        initialPositionX=1.5f*tilePixelWidth;
        initialPositionY=1.5f*tilePixelHeight;
        player=new Player(new Sprite(new Texture("img/p1_front.png")),initialPositionX,initialPositionY, mainLayer,world);
        
        
        
        
        Gdx.input.setInputProcessor(player); //to tell where the inputs event are processed
    }
    
    @Override
    public void render(float delta) {
        
        world.step(1f/60, 6 , 2);
        
        //Scales to world to the game screen
        //Matrix4 debugMatrix = batch.getProjectionMatrix().cpy().scale(PhysicsManager.PIXELS_TO_METERS, PhysicsManager.PIXELS_TO_METERS, 0);
        
        Gdx.gl.glClearColor(0, 0, 0, 0);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //Set the camera postion on the player but blocked on map limits
        camera.position.x = Math.min(Math.max(player.getX(), width/2), levelPixelWidth - (width/2));
        camera.position.y = Math.min(Math.max(player.getY(), height/2), levelPixelHeight - (height/2));
        camera.update();
        
        renderer.setView(camera);
        renderer.render(); //pass array of ints to render specific layers
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        TextManager.Draw("FPS: "+Gdx.graphics.getFramesPerSecond()+"    Time: "+Time.time+"\nPlayer \nX: "+player.getX()+"\nY: "+player.getY(),camera);
        //TextManager.Draw("Player \nX: "+player.getX()+"\nY: "+player.getY(),camera);
        //TextManager.Draw("Player \nX: "+player.getX()+"\nY: "+player.getY(),player.getX()-20,player.getY()+player.getHeight()+60); //Text folloxs player
        batch.end();
        
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }
    
    @Override
    public void resize(int width, int height) {     
    }
    
    @Override
    public void dispose(){
        map.dispose();
        world.dispose();
        renderer.dispose();
        player.getTexture().dispose();
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
    
    
    
}