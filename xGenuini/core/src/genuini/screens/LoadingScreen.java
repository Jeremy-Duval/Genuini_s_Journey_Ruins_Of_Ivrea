/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import genuini.game.ScreenEnum;
import genuini.game.ScreenManager;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;

/**
 *
 * @author Adrien
 */
public class LoadingScreen extends AbstractScreen{

    private Table table;
    private final int buttonWidth;
    private final int buttonHeight;
    private TextField loadingText;
    private final boolean debug=false;
    private float stateTime;

    
    public LoadingScreen(){
        super();
        buttonWidth=V_WIDTH/15;
        buttonHeight=V_HEIGHT/20;
        stateTime=0;
    }
    
    public void update(float delta){
        if(stateTime>3f){
            stateTime=0;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // post a Runnable to the rendering thread that processes the result
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME);
                        }
                    });
                }
            }).start();
        }else if(stateTime>2.4f){
            
            loadingText.setText("Loading...");
            stateTime+=delta;
        }else if(stateTime>1.6f){
            loadingText.setText("Loading..");
            stateTime+=delta;
        }else if(stateTime>0.8f){
            loadingText.setText("Loading.");
            stateTime+=delta;
        }else{
            stateTime+=delta;
        }
    }
    @Override
    public void buildStage() {
        loadingText= new TextField("Loading", skinManager.textFieldSkin(buttonWidth, buttonHeight, Color.WHITE, false, Color.CLEAR, Color.CLEAR, Color.DARK_GRAY, 1f));
        table = new Table();
        table.setSize(V_WIDTH,V_HEIGHT/8);
        table.add(loadingText).width(150);
        table.setPosition(1, V_HEIGHT/2);
        table.center();
        // table.align(Align.right | Align.bottom);
        if(debug){
            table.debug();// Enables debug lines for tables.
        }
        stage.addActor(table);
    }
    
    
    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
        update(delta);
    }
}
