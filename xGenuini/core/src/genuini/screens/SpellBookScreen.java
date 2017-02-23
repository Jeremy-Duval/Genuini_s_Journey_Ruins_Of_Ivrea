/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.game.ScreenEnum;
import genuini.game.ScreenManager;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;
import static genuini.main.MainGame.contentManager;

/**
 * Defined the spell book screen
 *
 * @since 01/12/2016
 * @author jeremy
 */
public class SpellBookScreen extends AbstractScreen {
    Texture background;
    /**
     * ************************Button*****************************************
     */
    private TextButton menuButton;
    private TextButton gameButton;
    private final int buttonWidth;
    private final int buttonHeight;
    /**
     * ***********************Text area***************************************
     */
    private TextField codeArea;
    private TextField response;
    
    
    //differents soluces
    private TextButton setupBox;
    private TextButton intsetupBox;
    private TextButton configureBox;
    private TextButton initBox;
    
    private final int areaWidth;
    private final int areaHeight;
    BitmapFont bookFont;
    Skin bookSkin;


    /**
     * Spell book constructor. Call AbstractScreen constructor and create
     * completrary elements. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    public SpellBookScreen() {
        super();
        buttonWidth = V_WIDTH / 15;
        buttonHeight = V_HEIGHT / 20;
        super.createButtonSkin(buttonWidth, buttonHeight);
        areaWidth = V_WIDTH / 6;
        areaHeight = V_HEIGHT / 10;
        bookFont = new BitmapFont();
        createBookSkin(areaWidth, areaHeight);
        background = new Texture("img/book/book.png");


    }

    /**
     * Create a listener for the gameButton. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                contentManager.getMusic("gameMusic").pause();
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
            }
        });

        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
            }
        });
        
        initBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                response.setText("init isn't the good answer, Try again ;)");
            }
        });
        
        configureBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                response.setText("configure isn't the good answer, Try again ;)");
            }
        });
        
        setupBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.setChallenge(true);
                if(connected){
                    arduinoInstance.write("gravity;");
                }
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
            }
        });
        
        intsetupBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                response.setText("int setup isn't the good answer, Try again ;)");
            }
        });

        codeArea.setTextFieldListener(new TextFieldListener() {

            @Override
            public void keyTyped(TextField textField, char c) {
            }

        });
    }

    /**
     * Set the menu button position. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    @Override
    public void buildStage() {
        codeArea = new TextField("What is the initialization method of an Arduino program ?", bookSkin);
        codeArea.setX(V_WIDTH / 3 - 100);
        codeArea.setY(V_HEIGHT - 200);
        codeArea.setWidth(800);
        codeArea.setHeight(100);
        
        response = new TextField("", bookSkin);
        response.setX(V_WIDTH/2 - 140);
        response.setY(V_HEIGHT-400);
        response.setWidth(600);
        
        
        initBox = new TextButton("void init()", skin);
        setupBox = new TextButton("void setup()", skin);
        configureBox = new TextButton("void config()", skin);
        intsetupBox = new TextButton("int setup()", skin);
        
        initBox.setPosition(V_WIDTH / 3 + 40, V_HEIGHT - 250);
        setupBox.setPosition(V_WIDTH / 3 + 180, V_HEIGHT - 250);
        configureBox.setPosition(V_WIDTH / 3 + 350, V_HEIGHT - 250);
        intsetupBox.setPosition(V_WIDTH / 3 + 180, V_HEIGHT - 300);
        
        
        

        menuButton = new TextButton("Menu", skin); // Use the initialized skin
        menuButton.setPosition(buttonWidth / 2, V_HEIGHT - 2 * buttonHeight);
        gameButton = new TextButton("Game", skin); // Use the initialized skin
        gameButton.setPosition(buttonWidth / 2, V_HEIGHT - 4 * buttonHeight);

        stage.addActor(codeArea);
        stage.addActor(response);
        stage.addActor(initBox);
        stage.addActor(setupBox);
        stage.addActor(configureBox);
        stage.addActor(intsetupBox);
        
        stage.addActor(menuButton);
        stage.addActor(gameButton);
    }

    /**
     * Render of the screen. Override of AbstractScreen.
     *
     * @param delta
     * @since 01/12/2016
     * @author jeremy
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        /*draw*/
        batch.begin();
        batch.draw(background, V_WIDTH / 8, 0);
        batch.end();
        stage.act(delta);
        stage.draw();

    }

    /**
     * Unmodify overrided function. Override of AbstractScreen.
     *
     * @param width
     * @param height
     * @since 01/12/2016
     * @author jeremy
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Unmodify overrided function. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    @Override
    public void pause() {
    }

    /**
     * Unmodify overrided function. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    @Override
    public void resume() {
    }

    /**
     * Unmodify overrided function. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    @Override
    public void hide() {
    }

    /**
     * Unmodify overrided function. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    @Override
    public void dispose() {
        super.dispose();
        bookFont.dispose();
    }

    /**
     * Create a skin for the spell book in the background.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    void createBookSkin(float width, float height) {
        //Create a font
        bookSkin = new Skin();
        bookSkin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        bookSkin.add("background", new Texture(pixmap));

        //Create a button style
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.fontColor = Color.GOLDENROD;
        textFieldStyle.background = bookSkin.newDrawable("background", Color.CLEAR);
        textFieldStyle.focusedBackground = bookSkin.newDrawable("background", Color.CLEAR);
        textFieldStyle.cursor = bookSkin.newDrawable("background", Color.DARK_GRAY);
        textFieldStyle.cursor.setMinWidth(1f);
        textFieldStyle.font = bookSkin.getFont("default");
        bookSkin.add("default", textFieldStyle);
    }

}
