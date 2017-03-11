package genuini.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.PPM;

/**
 *
 * @author Adrien Techer
 */
public abstract class Mobs extends LivingBeings{
    final Vector2 initialPosition;
    private boolean comingHome;
    Attitude attitude;
    float speed;
   
    public enum Attitude{NEUTRAL, HOSTILE, FRIENDLY};
        
    public Mobs(GameScreen screen, Vector2 initalPosition, Direction direction) {
        super(screen);
        atlas = new TextureAtlas(Gdx.files.internal("img/packed/mobs_output/mobs.atlas"));
        currentState = State.WALKING;
        previousState = State.WALKING;
        dead = false;
        stateTimer = 0;
        comingHome=false;
        attitude=Attitude.NEUTRAL;
        this.direction=direction;
        this.initialPosition=initalPosition;
    }
    
    @Override
    public void update(float delta) {
        Vector2 pos = new Vector2(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.setPosition(pos.x, pos.y-2f); //Corrected small transparent line at bottom
        sprite.setRegion(getFrame(delta,false));
    }
    
    public Vector2 getInitialPosition(){
        return initialPosition;
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    
    public void comeHome(){
        comingHome=true;
        switchDirection();
    } 
    
    
    public void switchDirection() {
        if(direction==Direction.LEFT){
            body.applyLinearImpulse(0.5f, 0, 0, 0, true);
        }else{
            body.applyLinearImpulse(-0.5f, 0, 0, 0, true);
        }
    }

    public boolean isComingHome() {
        return comingHome;
    }
   

    public void changeAttitude(Attitude attitude) {
        this.attitude=attitude;
    }
    
    public void isHome() {
        comingHome=false;
    }
    
    public Attitude getAttitude(){
        return attitude;
    }
    
    public void walk(){
        float impulse=speed;
        if(attitude==Attitude.HOSTILE){
            impulse+=100f;
        }
        impulse = (screen.getGenuini().getPosition().x-body.getPosition().x)<0 ? -impulse : impulse;
        
        body.applyLinearImpulse(impulse / PPM, 0, 0, 0, true);
    }
}
