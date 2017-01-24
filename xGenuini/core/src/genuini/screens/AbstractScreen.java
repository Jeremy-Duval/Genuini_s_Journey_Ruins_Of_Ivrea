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
import genuini.handlers.PreferencesManager;
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
    Texture textureBookButton;
    Stage stage;
    Skin skin;
    Skin bookButtonSkin;
    Skin textSkin;
    BitmapFont font,font2;
    SpriteBatch batch;
    PreferencesManager prefs;
    Music music;
 
    

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/grundchift.otf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    protected AbstractScreen() {
        super(new StretchViewport(MainGame.V_WIDTH, MainGame.V_HEIGHT, new OrthographicCamera()));
        prefs = new PreferencesManager();
        stage = new Stage();
        batch = new SpriteBatch();
        textureBookButton = new Texture("img/book/redbook.png");
        //define the font
        parameter.size = 30; //set the font size: 12px
        parameter.color = Color.BLACK; //set the color size
        font = generator.generateFont(parameter); //set the font size: 12px
        parameter.size = 24;
        parameter.color = Color.WHITE;       
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        font2 = generator.generateFont(parameter);
        generator.dispose(); // free memory space

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
        skin.dispose();       
        stage.dispose();
        batch.dispose();
        connectArduino.dispose();
    }

    void createButtonSkin(float width, float height) {
        //Create a font

        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.WHITE);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }

    /**
     * Create a skin for the spell book button.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    void createBookButtonSkin(float width, float height) {
        //Create a font

        bookButtonSkin = new Skin();
        bookButtonSkin.add("default", font2);

        bookButtonSkin.add("textureBookButton", textureBookButton);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = bookButtonSkin.newDrawable("textureBookButton", Color.GRAY);
        textButtonStyle.down = bookButtonSkin.newDrawable("textureBookButton", Color.DARK_GRAY);
        textButtonStyle.checked = bookButtonSkin.newDrawable("textureBookButton", Color.DARK_GRAY);
        textButtonStyle.over = bookButtonSkin.newDrawable("textureBookButton", Color.LIGHT_GRAY);
        textButtonStyle.font = bookButtonSkin.getFont("default");
        bookButtonSkin.add("default", textButtonStyle);

    }
    

    void createTextSkin() {
        //Create a font
        textSkin = new Skin();
        textSkin.add("default", font);

    }

    void performClick(Actor actor) {
        Array<EventListener> listeners = actor.getListeners();
        for (int i = 0; i < listeners.size; i++) {
            if (listeners.get(i) instanceof ClickListener) {
                ((ClickListener) listeners.get(i)).clicked(null, 0, 0);
            }
        }
    }
    
    
}
