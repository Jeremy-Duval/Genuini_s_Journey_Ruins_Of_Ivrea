/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;
import static genuini.main.MainGame.V_WIDTH;

/**
 * Defined the spell book screen
 * @since 29/11/2016
 * @author jeremy
 */
public class SpellBookScreen extends AbstractScreen{
    /*************************Button****************************************/
    private TextButton menuButton;
    /*************************Tile****************************************/
    private TiledMap map;
    private TiledMapRenderer tmr;
    private int tileMapWidth;
    private int tileMapHeight;
    private float tileSize;
    
    /**
     * Spell book constructor.
     * Call AbstractScreen constructor and create completrary elements.
     * @since 29/11/2016
     * @author jeremy
     */
    public SpellBookScreen(){
        super();
        menuButton = new TextButton("Menu", skin); // Use the initialized skin
    }
    
    /**
     * Create a listener for the menuButton.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void show() {
      Gdx.input.setInputProcessor(stage);
      menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
            }
        });
    }
    
    /**
     * Set the menu button position.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void buildStage() {
        menuButton=new TextButton("Menu", skin);
        menuButton.setPosition(V_WIDTH-tileSize*1.6f, tileSize*3);
        stage.addActor(menuButton);
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        /*draw*/
        batch.begin();
        batch.draw(background,0,0);
        /*TODO :
        *Draw screen (change background)
        *Draw font in an specific area
        */
        batch.end();
        stage.act(delta);
        stage.draw();
        
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
       super.dispose();
    }
    
    /**
     * Define action to do when we use an input key.
     * @since 29/11/2016
     * @author jeremy
     */
    public void handleInput() {
        //TODO : if you push a key : concat to a string and always drow the string
        if(Gdx.input.isKeyPressed(Input.Keys.Q)){
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Z)){
        }
        
    }
}
