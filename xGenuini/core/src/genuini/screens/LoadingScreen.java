/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private final boolean debug=false;
    private float stateTime;
    private final Animation loadAnimation;
    private boolean changingScreen;
    
    public LoadingScreen(){
        super();
        stateTime=0;
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("img/packed/load_output/load.atlas"));
        loadAnimation = new Animation(0.8f, atlas.findRegions("load"));
        changingScreen=false;
    }
    
    public void update(float delta){
        if(!changingScreen){
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
            changingScreen=true;
        }
        

        
        stateTime = stateTime<3f ? stateTime+delta : 0;
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(loadAnimation.getKeyFrame(stateTime), 0, 0, V_WIDTH,V_HEIGHT);
        batch.end();
        stage.act(delta);
        stage.draw();
        update(delta);
    }
    
}
