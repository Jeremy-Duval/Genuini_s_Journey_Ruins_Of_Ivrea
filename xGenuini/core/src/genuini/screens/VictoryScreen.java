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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;
import static genuini.main.MainGame.contentManager;




/**
 *
 * @author Adrien
 */
public class VictoryScreen extends AbstractScreen{
    private TextButton menuButton;
    private Table table;
    private final boolean debug=false;
    private final int buttonWidth;
    private final int buttonHeight;

    private final int areaWidth;
    private final int areaHeight;
    private TextField gameOver;
    private Skin bookSkin;
    
    public VictoryScreen(){
        super();
        buttonWidth=V_WIDTH/15;
        buttonHeight=V_HEIGHT/20;
        super.createButtonSkin(buttonWidth,buttonHeight);
        areaWidth = V_WIDTH / 6;
        areaHeight = V_HEIGHT / 10;
        createBookSkin(areaWidth, areaHeight);
        contentManager.getMusic("deathMusic").play();

    }
    
    @Override
    public void buildStage() {
        menuButton=new TextButton("Menu", skin);
        menuButton.setPosition((V_WIDTH-buttonWidth)/2-40, (V_HEIGHT-buttonHeight)/2 - 50);
        

        
        stage.addActor(menuButton);

        
        gameOver = new TextField("Congratulations, demo finished !", bookSkin);
        table = new Table();
        table.setSize(V_WIDTH,V_HEIGHT/8);
        table.add(gameOver).width(200);
        table.setPosition(1, V_HEIGHT/2);
        table.center();
        // table.align(Align.right | Align.bottom);
        if(debug){
            table.debug();// Enables debug lines for tables.
        }
        stage.addActor(table);
    }
    
    
    
    @Override
    public void show() {
      Gdx.input.setInputProcessor(stage);
      menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               contentManager.getMusic("deathMusic").stop();
               ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
            }
        });
    }
    
    
    
    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();  
    }
    
    
    @Override
    public void dispose() {
       super.dispose();
    }
    
    void createBookSkin(float width, float height) {
        //Create a font
        bookSkin = new Skin();
        bookSkin.add("default", font2);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        bookSkin.add("background", new Texture(pixmap));

        //Create a button style
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = bookSkin.newDrawable("background", Color.CLEAR);
        textFieldStyle.focusedBackground = bookSkin.newDrawable("background", Color.CLEAR);
        textFieldStyle.cursor = bookSkin.newDrawable("background", Color.DARK_GRAY);
        textFieldStyle.cursor.setMinWidth(1f);
        textFieldStyle.font = bookSkin.getFont("default");
        bookSkin.add("default", textFieldStyle);
    }
}
