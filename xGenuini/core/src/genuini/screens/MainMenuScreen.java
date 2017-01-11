/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;

/**
 *
 * @author Adrien
 */
public class MainMenuScreen extends AbstractScreen{

    private TextButton newGameButton;
    private TextButton quitButton;
    
    private TextButton arduinoButton;
    private boolean etatArduino = false;
    
    private final int buttonWidth;
    private final int buttonHeight;
    
    public MainMenuScreen() {
        super();
        buttonWidth=V_WIDTH/6;
        buttonHeight=V_HEIGHT/10;
        super.createButtonSkin(buttonWidth,buttonHeight);
    }

    @Override
    public void buildStage() {
        batch = new SpriteBatch();
        newGameButton = new TextButton("Jouer", skin); // Use the initialized skin
        quitButton = new TextButton("Quitter", skin);
        arduinoButton = new TextButton("Arduino Connexion", skin);
        
        newGameButton.setPosition((V_WIDTH-buttonWidth)/2 , (V_HEIGHT+buttonHeight)/2+10);
        quitButton.setPosition((V_WIDTH-buttonWidth)/2 , (V_HEIGHT-buttonHeight)/2-10);
        arduinoButton.setPosition((V_WIDTH-buttonWidth)/2 , (V_HEIGHT-buttonHeight)/2-100);
        stage.addActor(newGameButton);
        stage.addActor(quitButton);
        stage.addActor(arduinoButton);
        arduinoInstance.write("menu;0");
    }
    
    @Override
    public void show() {
      Gdx.input.setInputProcessor(stage);
      newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
            }
        });
      
      quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               prefs.save();
               Gdx.app.exit();
            }
        });
      
      arduinoButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
              if(!etatArduino){  
//               arduinoInstance.write('e');
               etatArduino = true;
              }else{
//                 arduinoInstance.write('u');
                 etatArduino = false; 
              }
            }
        });
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(background,0,0);
        batch.draw(connectArduino, 50, 600);
        /*if(connected){
          font.draw(batch, "Le port est " + arduinoPort.toString(), 50, 550);  
        }*/
            
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
    
}
