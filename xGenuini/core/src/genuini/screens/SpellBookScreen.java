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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;
import static genuini.screens.AbstractScreen.music;

/**
 * Defined the spell book screen
 *
 * @since 01/12/2016
 * @author jeremy
 */
public class SpellBookScreen extends AbstractScreen {

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
    private TextArea codeArea;
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
        background = new Texture("img/book/SpellBookWrite.png");


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
                music.stopMusic();
                continueMusic=false;
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
            }
        });

        gameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continueMusic=true;
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
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
        codeArea = new TextArea("Entrez votre code Arduino", bookSkin);
        codeArea.setX(V_WIDTH / 7 + 10);
        codeArea.setY(55);
        codeArea.setWidth(450);
        codeArea.setHeight(580);

        gameButton = new TextButton("Jeu", skin); // Use the initialized skin
        gameButton.setPosition(buttonWidth / 2, V_HEIGHT - 2 * buttonHeight);



        stage.addActor(codeArea);


        menuButton = new TextButton("Menu", skin); // Use the initialized skin
        menuButton.setPosition(buttonWidth / 2, V_HEIGHT - 2 * buttonHeight);
        gameButton = new TextButton("Game", skin); // Use the initialized skin
        gameButton.setPosition(buttonWidth / 2, V_HEIGHT - 4 * buttonHeight);

        stage.addActor(codeArea);
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
        /*TODO :
         *Draw screen (change background)
         *Draw font in an specific area
         */
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
        if(!continueMusic){
            music.dispose();
        }
    }

    /**
     * Create a skin for the spell book in the background.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    private void createBookSkin(float width, float height) {
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
