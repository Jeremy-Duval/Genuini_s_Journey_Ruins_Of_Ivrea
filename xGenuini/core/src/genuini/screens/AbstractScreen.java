/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import genuini.arduino.ArduinoLink;
import genuini.arduino.UnobtainableComPortException;
import genuini.game.PreferencesManager;
import genuini.game.SkinManager;
import genuini.main.MainGame;
import gnu.io.SerialPort;

/**
 *
 * @author Adrien
 */
public class AbstractScreen extends Stage implements Screen {

    public static ArduinoLink arduinoInstance; //Arduino Connection
    SerialPort arduinoPort; //Port Use
    public static boolean connected = false;// arduino connected or no
    Texture connectArduino; //image of arduino connected
    Stage stage;
    SpriteBatch batch;
    PreferencesManager prefs;
    Music music;
    SkinManager skinManager;
 
    

    

    protected AbstractScreen() {
        super(new StretchViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera()));
        prefs = new PreferencesManager();
        stage = new Stage();
        //batch = new SpriteBatch();
        skinManager = new SkinManager();
        connectArduino = new Texture("img/arduinoconnected.png");

        if (!connected) {
            //connection with ArduinoLink class
            arduinoInstance = new ArduinoLink();
            try {
                connected = true;
                arduinoPort = arduinoInstance.initialize();
            } catch (UnobtainableComPortException e) {
                    connected = false;
                    System.out.println(e.getMessage());
                    connectArduino = new Texture("img/errorarduino.png");                
            }
        }
    }

    // Subclasses must load actors in this method
    public void buildStage() {
        batch = new SpriteBatch();
    }

    
 
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

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {    
        stage.dispose();
        //batch.dispose();
        connectArduino.dispose();
        skinManager.dispose();
    }


    


    
    
}
