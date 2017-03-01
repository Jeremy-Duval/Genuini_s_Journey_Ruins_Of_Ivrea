/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
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
public class SpellBookMenu extends AbstractScreen {
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


    /**
     * Spell book constructor. Call AbstractScreen constructor and create
     * completrary elements. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    public SpellBookMenu() {
        super();
        buttonWidth = V_WIDTH / 15;
        buttonHeight = V_HEIGHT / 20;
        areaWidth = V_WIDTH / 6;
        areaHeight = V_HEIGHT / 10;
        bookFont = new BitmapFont();
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
                ScreenManager.getInstance().showScreen(ScreenEnum.LOAD);
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
                ScreenManager.getInstance().showScreen(ScreenEnum.LOAD);
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
        super.buildStage();
        Skin skin = skinManager.whiteTextSkin(areaWidth, areaHeight);
        codeArea = new TextField("What is the initialization method of an Arduino program ?", skin);
        codeArea.setX(V_WIDTH / 3 - 100);
        codeArea.setY(V_HEIGHT - 200);
        codeArea.setWidth(800);
        codeArea.setHeight(100);
        
        response = new TextField("", skin);
        response.setX(V_WIDTH/2 - 140);
        response.setY(V_HEIGHT-400);
        response.setWidth(600);
        
        Skin skin2=skinManager.createButtonSkin(buttonWidth, buttonHeight);
        
        
        initBox = new TextButton("void init()", skin2);
        setupBox = new TextButton("void setup()", skin2);
        configureBox = new TextButton("void config()", skin2);
        intsetupBox = new TextButton("int setup()", skin2);
        
        initBox.setPosition(V_WIDTH / 3 + 40, V_HEIGHT - 250);
        setupBox.setPosition(V_WIDTH / 3 + 180, V_HEIGHT - 250);
        configureBox.setPosition(V_WIDTH / 3 + 350, V_HEIGHT - 250);
        intsetupBox.setPosition(V_WIDTH / 3 + 180, V_HEIGHT - 300);
        
        
        skin = skinManager.whiteTextSkin(areaWidth, areaHeight);

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
