/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea.TextAreaListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
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
public class SpellBookScreen extends AbstractScreen{
    /*************************Button****************************************/
    private TextButton menuButton;
    private final int buttonWidth;
    private final int buttonHeight;
    /*************************Text area****************************************/
    private TextArea codeArea;
    private final int areaWidth;
    private final int areaHeight;
    Skin bookSkin;
    
    
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
        areaWidth=V_WIDTH/6;
        areaHeight=V_HEIGHT/10;
        createBookSkin(areaWidth,areaHeight);
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
      
      codeArea.setTextFieldListener(new TextFieldListener() {

          @Override
          public void keyTyped(TextField textField, char c) {
              System.out.println("ok");
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
        codeArea = new TextArea("A text", bookSkin);
        codeArea.setX(10);
	codeArea.setY(10);
	codeArea.setWidth(200);
	codeArea.setHeight(200);
        
        menuButton = new TextButton("Menu", skin); // Use the initialized skin
        menuButton.setPosition((V_WIDTH-buttonWidth)/8 , (V_HEIGHT+buttonHeight)/2);
        
        stage.addActor(codeArea);
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
        //batch.draw(background,0,0);
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
     * @param width
     * @param height
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
    
    void createBookSkin(float width, float height){
        //Create a font
        bookSkin = new Skin();
        bookSkin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int)width,(int)height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        bookSkin.add("background",new Texture(pixmap));

        //Create a button style
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.fontColor = Color.FOREST;
        textFieldStyle.background = bookSkin.newDrawable("background", Color.WHITE);
        textFieldStyle.cursor = bookSkin.newDrawable("background", Color.BLACK);
        textFieldStyle.focusedBackground = bookSkin.newDrawable("background", Color.LIGHT_GRAY);
        textFieldStyle.font = bookSkin.getFont("default");
        bookSkin.add("default", textFieldStyle);

      }

}
