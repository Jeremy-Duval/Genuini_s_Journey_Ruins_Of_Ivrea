/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.game.ScreenEnum;
import genuini.game.ScreenManager;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;

/**
 * Defined the spell book screen
 *
 * @since 01/12/2016
 * @author jeremy
 */
public class LessonsScreen extends AbstractScreen {
    /**
     * ************************Button*****************************************
     */
    private TextButton spellBookButton;
    private final int buttonWidth;
    private final int buttonHeight;
   
    /*********************************Lessons*********************************/
    //BufferedInputStream lesson_file;


    /**
     * Spell book constructor. Call AbstractScreen constructor and create
     * completrary elements. Override of AbstractScreen.
     *
     * @since 01/12/2016
     * @author jeremy
     */
    public LessonsScreen() {
        super();
        buttonWidth = V_WIDTH / 15;
        buttonHeight = V_HEIGHT / 20;
        /*
        try {
            BufferedInputStream  lesson_file = new BufferedInputStream(
                    new DataInputStream(
                            new FileInputStream(
                                    new File("cours/test.txt"))));
            
        } catch (FileNotFoundException ex) {
            System.out.println("Oppening error");
        }*/


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
        spellBookButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().showScreen(ScreenEnum.SPELLBOOK);
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
        
        Skin skin = skinManager.createButtonSkin(buttonWidth, buttonHeight);
        
        
        skin = skinManager.textFieldSkin(buttonWidth, buttonHeight, Color.WHITE, false, Color.CLEAR, Color.CLEAR, Color.DARK_GRAY, 1f);

        spellBookButton = new TextButton("Spell Book", skin); // Use the initialized skin
        spellBookButton.setPosition(buttonWidth / 2, V_HEIGHT - 2 * buttonHeight);
        
        stage.addActor(spellBookButton);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        /*draw*/
        batch.begin();
        
        /*try {
            System.out.println(lesson_file);
        } catch (IOException ex) {
            System.out.println("Reading error");
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

