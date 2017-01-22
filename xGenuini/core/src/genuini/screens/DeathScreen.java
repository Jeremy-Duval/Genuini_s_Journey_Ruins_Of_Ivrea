/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import genuini.handlers.AudioManager;
import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;
import static genuini.screens.AbstractScreen.continueMusic;




/**
 *
 * @author Adrien
 */
public class DeathScreen extends AbstractScreen{
    private TextButton menuButton;
    private Table table;
    private final boolean debug=false;
    private final int buttonWidth;
    private final int buttonHeight;


    
    public DeathScreen(){
        super();
        buttonWidth=V_WIDTH/15;
        buttonHeight=V_HEIGHT/20;
        super.createButtonSkin(buttonWidth,buttonHeight);
        super.createTextSkin();
        /********************************music*********************************/
        music.setMusic("sounds/Death.mp3");
        music.playMusic(1.0f, true, -1);
        /**********************************************************************/
    }
    
    @Override
    public void buildStage() {
        menuButton=new TextButton("Menu", skin);
        menuButton.setPosition((V_WIDTH-buttonWidth)/2, (V_HEIGHT-buttonHeight)/2);
        

        
        stage.addActor(menuButton);

        table = new Table();
        stage.addActor(table);
        table.setSize(V_WIDTH,V_HEIGHT/8);
        table.setPosition(1, V_HEIGHT/2);
        table.center();
        // table.align(Align.right | Align.bottom);
        if(debug){
            table.debug();// Enables debug lines for tables.
        }
        
        Label lifeLabel = new Label("You are dead",textSkin,"default",Color.WHITE);
        table.add(lifeLabel).width(120);
    }
    
    
    
    @Override
    public void show() {
      Gdx.input.setInputProcessor(stage);
      menuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
}
