/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;

/**
 *
 * @author Adrien
 */
public class MainMenuScreen extends AbstractScreen{

    
     /* Sprite et texture pour image et fond d'écran */ 
    private SpriteBatch batch;
    //private Texture background;
    
    /* Variable de police */
    BitmapFont font;
    
    private Skin skin;
    private Stage stage;
    private Texture background;
    private TextButton newGameButton;
    private TextButton quitButton;
    
    
    public MainMenuScreen() {
        batch = new SpriteBatch();
        background = new Texture("background.jpg");
        font = new BitmapFont();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events
 
        createBasicSkin();
        newGameButton = new TextButton("Jouer", skin); // Use the initialized skin
        quitButton = new TextButton("Quitter", skin);
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        quitButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/3);
        stage.addActor(newGameButton);
        stage.addActor(quitButton);
    }

    @Override
    public void buildStage() {
        
    }
    
    @Override
    public void show() {
      newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
            }
        });
      
      quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        
        batch.begin();
        batch.draw(background,0,0);
        
        
        batch.end();
        
        stage.act();
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
       skin.dispose();
    }
   private void createBasicSkin(){
  //Create a font

  skin = new Skin();
  skin.add("default", font);

  //Create a texture
  Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
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
