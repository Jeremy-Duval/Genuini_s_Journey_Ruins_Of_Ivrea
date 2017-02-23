/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import genuini.world.PhysicsVariables;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;
import static genuini.world.PhysicsVariables.BIT_TURRET;
import static genuini.world.PhysicsVariables.PPM;

/**
 *
 * @author Adrien
 */
public class Genuini extends Character{
    int life;
    private boolean dead;
    private State currentState;
    private State previousState;
    private int stateTimer;
    private boolean runningRight;
    public enum State {STANDING,WALKING,DEAD};
    private final Animation walkAnimation; // Must declare frame type (TextureRegion)
    private final Texture walkSheet;

    public Genuini(GameScreen screen) {
        super(screen);
        life=screen.getPreferences().getLife();
        dead=false;
        runningRight=true;
        createBody();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        // Load the sprite sheet as a Texture
        walkSheet = MainGame.contentManager.getTexture("p1_walk");

        // Use the split utility method to create a 2D array of TextureRegions. This is 
        // possible because this sprite sheet contains frames of equal size and they are 
        // all aligned.
        int cols = 3;
        int rows = 3;
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, 
                walkSheet.getWidth() / cols,
                walkSheet.getHeight() / rows);

        // Place the regions into a 1D array in the correct order, starting from the top 
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[cols * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        walkAnimation = new Animation(0.1f, walkFrames);

        
    }

    private void createBody() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        bdef.position.set(screen.getPreferences().getPositionX(), screen.getPreferences().getPositionY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        body = world.createBody(bdef);
        fdef.friction=2f;
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = BIT_TERRAIN | BIT_TURRET | BIT_FIREBALL;
        float bodyWidth = 22f/PPM;
        float bodyHeight = 44f/PPM;
        float feetWidth = 14f/PPM;
        float feetHeight = 14f/PPM;
        
        
        //Create player shape
        Vector2[] playerShapeVertices = new Vector2[6];
        playerShapeVertices[0] = new Vector2(-feetWidth, -bodyHeight);
        playerShapeVertices[1] = new Vector2(feetWidth, -bodyHeight);
        playerShapeVertices[2] = new Vector2(bodyWidth, -bodyHeight + feetHeight);
        playerShapeVertices[3] = new Vector2(bodyWidth, bodyHeight);
        playerShapeVertices[4] = new Vector2(-bodyWidth, bodyHeight);
        playerShapeVertices[5] = new Vector2(-bodyWidth, -bodyHeight + feetHeight);
        shape.set(playerShapeVertices);
        //shape.setAsBox(bodyWidth, bodyHeight);
        body.createFixture(fdef).setUserData("player");

        //create foot sensor
        shape.setAsBox(feetWidth, feetHeight / 2, new Vector2(0, -bodyHeight), 0);
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

        
    }
    
    
    public void update(float dt){
        //update our sprite to correspond with the position of our Box2D body
        Vector2 pos = new Vector2(body.getPosition().x * PPM  - getWidth() / 2, body.getPosition().y * PPM - getHeight() / 2);

        setPosition(pos.x,pos.y);
        //update sprite with the correct frame depending on marios current action
        //System.out.println(getFrame(dt));
        //setRegion(getFrame(dt));
        setRegion(new TextureRegion(MainGame.contentManager.getTexture("p1")));

    }

    
    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case WALKING:
                region = walkAnimation.getKeyFrame(stateTimer, true);
            default:
                region = walkAnimation.getKeyFrames()[0];
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = (int) (currentState == previousState ? stateTimer + dt : 0);
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }
    
    public void setLife(int life) {
       this.life=life;
    }

    public int getLife() {
        return life;
    }


    public void changeLife(int i) {
        life+=i;
    }
    
    public State getState(){
        if(dead){
            return State.DEAD;
        //if mario is positive or negative in the X axis he is running
        }else if(body.getLinearVelocity().x != 0){
            return State.WALKING;
        }else{
            return State.STANDING;
        }
    }
    
    public void die(){
        if(!dead){
            dead=true;
        }
    }
    
}
