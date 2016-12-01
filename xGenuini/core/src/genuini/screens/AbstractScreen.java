/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import genuini.handlers.PreferencesManager;
import genuini.main.MainGame;

/**
 *
 * @author Adrien
 */
public class AbstractScreen extends Stage implements Screen {
    
    Texture background;
    Stage stage;
    Skin skin;
    BitmapFont font;
    SpriteBatch batch;
    PreferencesManager prefs;
    
    protected AbstractScreen() {
        super( new StretchViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera()) );
        prefs = new PreferencesManager();
        stage = new Stage();
        font = new BitmapFont();
        batch=new SpriteBatch();
        background = new Texture("background.jpg");     
    }
 
    // Subclasses must load actors in this method
    public  void buildStage(){
        
    };
 
    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
    
    @Override
    public void dispose(){
        skin.dispose();
        background.dispose();
        font.dispose();
        stage.dispose();
        batch.dispose();
    }
    
    void createButtonSkin(float width, float height){
        //Create a font

        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int)width,(int)height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

      }
}