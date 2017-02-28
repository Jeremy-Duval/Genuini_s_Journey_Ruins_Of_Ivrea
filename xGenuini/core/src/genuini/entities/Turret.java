package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TURRET;
import static java.lang.Math.random;
import java.util.Iterator;

/**
 * Class used to create a turret
 * @author Adrien Techer
 */
public class Turret extends StaticElements{
    
    private final Texture turretTexture;
    private final Texture turretActiveTexture;
    
    private final Array<Fireball> fireballs;
    private boolean active;
    
    /**
     * Is it possible to actvate/deactivate the turret
     */
    private boolean isLocked;
    
    /**
     * Elapsed time since last fireball creation
     */
    private float stateTime;
    /**
     * Delay before new fireball creation
     */
    private float delayFireball;
    private float activationDistance;
    private final TurretType type;
    private float fireballSpeed;

    public enum TurretType {FIREWALL, BOMBER, SNIPER}
    
    
    /**
     * 
     * @param screen the screen to which the sprite belongs
     * @param body the sprite's body
     * @param ID the ID of the map object
     * @param type the turret's type
     */
    public Turret(GameScreen screen, Body body, int ID, String type) {
        super(screen,body,ID);
        if(type.equals("firewall")){
            this.type=TurretType.FIREWALL;
        }else if(type.equals("sniper")){
            this.type=TurretType.SNIPER;
        }else if(type.equals("bomber")){
            this.type=TurretType.BOMBER;
        }else{
            this.type=TurretType.SNIPER;
        }
        
        
        turretTexture = MainGame.contentManager.getTexture("turret");
        turretActiveTexture = MainGame.contentManager.getTexture("turretActive");
        
        
        sprite= new Sprite(turretTexture);
        
        sprite.setPosition(position.x,position.y);
        
        defineType();
        createFilter();
        
        fireballs=new Array<Fireball>();

    }
    
    @Override
    public void update(float dt){
        if(!screen.getWorld().isLocked()){
            if(type==TurretType.BOMBER){
                delayFireball=(float) random()*2+2;
            }
            for (Fireball fireball : fireballs) {
                if(fireball.getBody()==null){
                    fireballs.removeValue(fireball, true);
                }else if (!fireball.getBody().isActive()){
                    fireballs.removeValue(fireball, true);
                }else{
                    fireball.update(dt);
                }
            }
            stateTime+=dt;
            if(active && stateTime>delayFireball){
                Vector2 offset = new Vector2(sprite.getWidth()/2,sprite.getHeight()/2);
                Fireball f = new Fireball(screen, position, offset);
                fireballs.add(f);
                f.targetBody(screen.getGenuini().getBody(), fireballSpeed);
                stateTime=0;
            }
        }
        
    }
    
    /**
     * Activates the turret : it starts shooting fireballs
     * @param unlock true if the activation procedure should be unlocked, false otherwise
     */
    public void activate(boolean unlock){
        if(!active&&!isLocked){
            active=true;
            sprite.setRegion(turretActiveTexture);
        }
        if(unlock){
            isLocked=false;
        }
    }
    
    /**
     * DeActivates the turret : it starts shooting fireballs
     * @param lock true if the activation procedure should be locked, false otherwise
     */
    public void deactivate(boolean lock){
        if(active){
            active=false;
            sprite.setRegion(turretTexture);
        }
        if(lock){
            isLocked=true;
        }
    }

    public boolean isActive() {
        return active;
    }
    
   
    @Override
    public void draw(SpriteBatch spriteBatch){
        if(type!=TurretType.BOMBER){
            super.draw(spriteBatch);
        }
        for(Fireball fireball : fireballs){
            fireball.draw(spriteBatch);
        }
    }

    @Override
    /**
     * @inheritDoc
     */
    public final void createFilter() {
        filter.categoryBits = BIT_TURRET;
        filter.maskBits = BIT_PLAYER;
        body.getFixtureList().first().setFilterData(filter);
        body.getFixtureList().first().setUserData("turret");
    }

    /**
     * Sets each parameter that depends on the turret's type
     */
    private void defineType(){
        switch (type){
            case FIREWALL:
                activationDistance=screen.getWidth();
                delayFireball=0.1f;
                fireballSpeed=0f;
                break;
            case SNIPER:
                activationDistance=5f;
                delayFireball=1f;
                fireballSpeed=50f;
                break;
            case BOMBER:
                activationDistance=body.getPosition().y-13;
                delayFireball=3f;
                fireballSpeed=5f;
                break;
            default:
                break;
        }
    }

    public TurretType getType() {
        return type;
    }

    public float getActivationDistance() {
        return activationDistance;
    }
    
    
    public float getFireballSpeed() {
        return fireballSpeed;
    }
    
}
