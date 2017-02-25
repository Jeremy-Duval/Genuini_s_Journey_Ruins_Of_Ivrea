/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;

/**
 *
 * @author Adrien
 */
public class Spring extends StaticElements{

    private final Texture springboardDown;
    private final Texture springboardUp;
    private boolean active;
    
    
    public Spring(GameScreen screen, Body body, int ID) {
        super(screen, body, ID);
        
        springboardDown = MainGame.contentManager.getTexture("springboardDown");
        springboardUp = MainGame.contentManager.getTexture("springboardUp");
        
        sprite= new Sprite(springboardDown);
        sprite.setPosition(position.x,position.y);
        
        createFilter();
    }
    
    
    public void activate(){
        if(!active){
            active=true;
            sprite.setRegion(springboardUp);
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        active=false;
                        sprite.setRegion(springboardDown);
                    }
                },
                        950
            );
        }
    }

    @Override
    public final void createFilter() {
        filter.categoryBits = BIT_TERRAIN;
        filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
        body.getFixtureList().first().setFilterData(filter);
        body.getFixtureList().first().setUserData("spring");
    }
    
}
