package genuini.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import genuini.main.MainGame;
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
    float offsetY;
    private float walkTimer;
    Sound walkSound;
    Sound deathSound;
    Sound attackSound;

    

   
    public enum Attitude{NEUTRAL, HOSTILE, FRIENDLY};
        
    public Mobs(GameScreen screen, Vector2 initalPosition, Direction direction) {
        super(screen);
        atlas = new TextureAtlas(Gdx.files.internal("img/packed/mobs_output/mobs.atlas"));
        currentState = State.WALKING;
        previousState = State.WALKING;
        dead = false;
        stateTimer = 0;
        walkTimer=0;
        comingHome=false;
        attitude=Attitude.NEUTRAL;
        this.direction=direction;
        this.initialPosition=initalPosition;
    }
    
    @Override
    public void update(float delta) {
        Vector2 pos = new Vector2(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.setPosition(pos.x, pos.y+offsetY); //Corrected small transparent line at bottom
        sprite.setRegion(getFrame(delta,false));
        
        if(getState().equals(State.WALKING)){
            if(walkTimer>1f){
                walk();
                walkTimer=0;
            }else{
                walkTimer+=delta;
            }
        }

        //Handles death
        if(body!=null){
            if(body.getFixtureList()!=null){
                if(body.getFixtureList().size>0){
                    if(body.getFixtureList().first().getUserData().equals("setToDestroy") && !dead){
                        die();
                    }
                } 
            }
        }
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
        playWalkSound();
    }
    
    private void playWalkSound() {
       if(walkSound!=null){
           float distance =screen.getDistanceFromPlayer(this);
           if(distance<10f){
              walkSound.play(0.2f/(4*distance)); 
           }  
       }
    }
    
    private void playDeathSound() {
       if(deathSound!=null && screen.getDistanceFromPlayer(this)<5f){
           float distance =screen.getDistanceFromPlayer(this);
           if(distance<10f){
              deathSound.play(0.8f/(4*distance)); 
           }  
       }
    }
    
    
    @Override
    public void die() {
        dead=true;
        playDeathSound();
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    body.getFixtureList().first().setUserData("toDestroy");
                }
            },
            800
        );
    }
    
    Sound getAttackSound() {
        return attackSound;
    }
}
