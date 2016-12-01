/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;

/**
 * Defined the spell book screen
 * @since 29/11/2016
 * @author jeremy
 */
public class SpellBookScreen extends AbstractScreen implements TextInputListener{
    /*************************Button****************************************/
    private TextButton menuButton;
    private final int buttonWidth;
    private final int buttonHeight;
    /*************************Tile****************************************/
    private float tileSize;
    
    
    
    /**
     * Spell book constructor.
     * Call AbstractScreen constructor and create completrary elements.
     * Override of AbstractScreen.
     * @since 29/11/2016
     * @author jeremy
     */
    public SpellBookScreen(){
        super();
        buttonWidth=V_WIDTH/6;
        buttonHeight=V_HEIGHT/10;
        super.createButtonSkin(buttonWidth,buttonHeight); 
    }
    
    /**
     * Create a listener for the menuButton.
     * Override of AbstractScreen.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void show() {
      Gdx.input.setInputProcessor(stage);
      menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
            }
        });
    }
    
    /**
     * Set the menu button position.
     * Override of AbstractScreen.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void buildStage() {
        menuButton = new TextButton("Menu", skin); // Use the initialized skin
        menuButton.setPosition((V_WIDTH-buttonWidth)/2 , (V_HEIGHT+buttonHeight)/3+10);
        stage.addActor(menuButton);
    }
    
    /**
     * Render of the screen.
     * Override of AbstractScreen.
     * @param delta
     * @since 29/11/2016
     * @author jeremy
     */
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
    
    /**
     * Unmodify overrided function.
     * Override of AbstractScreen.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void resize(int width, int height) {
        
    }
    
    /**
     * Unmodify overrided function.
     * Override of AbstractScreen.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void pause() {
    }
    
    /**
     * Unmodify overrided function.
     * Override of AbstractScreen.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void resume() {
    }
    
    /**
     * Unmodify overrided function.
     * Override of AbstractScreen.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void hide() {
    }
    
    /**
     * Unmodify overrided function.
     * Override of AbstractScreen.
     * @since 29/11/2016
     * @author jeremy
     */
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
    
    /*********************Override of TextInputListener************************/
    
    /**
     * 
     * Override of TextInputListener.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void input(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * Override of TextInputListener.
     * @since 29/11/2016
     * @author jeremy
     */
    @Override
    public void canceled() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
