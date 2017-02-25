/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.PPM;

/**
 *
 * @author Adrien
 */
public class Turret extends Sprites{

    private final Texture turretTexture;
    private final Array<Fireball> fireballs;
    private boolean active;
    private final Texture turretActiveTexture;
    private boolean isFirewall;
    private float stateTime;
    
    
    public Turret(GameScreen screen, Body body, boolean isFirewall) {
        super(screen);
        this.body=body;   
        turretActiveTexture = MainGame.contentManager.getTexture("turretActive");
        turretTexture = MainGame.contentManager.getTexture("turret");
        sprite= new Sprite(turretTexture);
        fireballs=new Array<Fireball>();
        this.isFirewall=isFirewall;
    }
    
    @Override
    public void update(float dt){
        Vector2 pos = new Vector2(body.getPosition().x * PPM , body.getPosition().y * PPM);
        sprite.setPosition(pos.x,pos.y);
        for(Fireball fireball : fireballs){    
            if(fireball.isDestroyed()){
                fireballs.removeValue(fireball, true);
            }else{
               fireball.update(dt);
            }
        }
        stateTime+=dt;
        float delayFireball = isFirewall ? 0.1f : 1f;
        if(active && stateTime>delayFireball){
            Vector2 offset = new Vector2(sprite.getWidth()/2,sprite.getHeight()/2);
            Fireball f = new Fireball(screen, pos, offset);
            fireballs.add(f);
            if(!isFirewall){
                f.targetBody(screen.getGenuini().getBody(), 50f);
            }
            stateTime=0;
        }
    }
    
    public void activate(){
        if(!active){
            active=true;
            sprite.setRegion(turretActiveTexture);
        }
    }
    
    public void deactivate(){
        if(active){
            active=false;
            sprite.setRegion(turretTexture);
        }
    }

    public boolean isActive() {
        return active;
    }
    
   
    @Override
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
        for(Fireball fireball : fireballs){
            fireball.draw(spriteBatch);
        }
    }
    
    
    
}
