/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import genuini.game.ScreenEnum;
import genuini.game.ScreenManager;
import genuini.main.MainGame;
import static genuini.screens.AbstractScreen.arduinoInstance;
import static genuini.screens.AbstractScreen.connected;
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
    private float stateTimer;
    private boolean runningRight;
    private final Animation genuiniWalking;
    private final TextureAtlas.AtlasRegion genuiniJumping;
    private final TextureAtlas.AtlasRegion genuiniHurt;
    private Direction direction;
    public enum State {STANDING,WALKING,DEAD, JUMPING, HURT};
    public enum Direction {LEFT,RIGHT};
    private final TextureRegion genuiniStanding;

    public Genuini(GameScreen screen) {
        super(screen);
        
        life=screen.getPreferences().getLife();
        dead=false;
        createBody();
        currentState = State.STANDING;
        previousState = State.STANDING;
        direction=Direction.RIGHT;
        stateTimer = 0;
        atlas = new TextureAtlas(Gdx.files.internal("img/packed/player_output/player.atlas"));
        sprite = atlas.createSprite("front");
        genuiniWalking = new Animation(0.1f, atlas.findRegions("walk"));
        genuiniStanding = atlas.findRegion("stand");
        genuiniJumping = atlas.findRegion("jump");
        genuiniHurt = atlas.findRegion("hurt");
    }

    private void createBody() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        bdef.position.set(screen.getPreferences().getPositionX(), screen.getPreferences().getPositionY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        body = world.createBody(bdef);
        fdef.friction=0.1f;
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
        Vector2 pos = new Vector2(body.getPosition().x * PPM  - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);

        sprite.setPosition(pos.x,pos.y);
        //update sprite with the correct frame depending on marios current action
        //System.out.println(getFrame(dt));
        sprite.setRegion(getFrame(dt));
        //setRegion(new TextureRegion(MainGame.contentManager.getTexture("p1")));
        //sprite.setRegion(getFrame(dt));
    }

    
    public TextureRegion getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case WALKING:
                region = genuiniWalking.getKeyFrame(stateTimer, true);
                break;
            case JUMPING:
                region = genuiniJumping;
                break;
            case HURT:
                region = genuiniHurt;
                break;
            default:
                region = genuiniStanding;
                break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if((body.getLinearVelocity().x < 0 || direction==Direction.LEFT) && !region.isFlipX()){
            region.flip(true, false);
            direction=Direction.LEFT;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((body.getLinearVelocity().x > 0 || direction==Direction.RIGHT) && region.isFlipX()){
            region.flip(true, false);
            direction=Direction.RIGHT;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        

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
        //System.out.println(body.getLinearVelocity().y);
        if(dead){
            return State.DEAD;
        }else if(screen.getContactManager().isPlayerHurt()){
            return State.HURT;
        }else if(!screen.getContactManager().playerCanJump()){
            return State.JUMPING;
        }else if(Math.abs(body.getLinearVelocity().x) > 0.1f){
            return State.WALKING;
        }else{
            return State.STANDING;
        }
    }
    
    public Direction getDirection(){
        return direction;
    }
    
    public void walk(Direction direction){
        if(direction==Direction.LEFT){
            body.applyLinearImpulse(-5 / PPM, 0, 0, 0, true);
        }else{
            body.applyLinearImpulse(5 / PPM, 0, 0, 0, true);
        }
        
    }
    
    
    public void jump(){
        body.applyLinearImpulse(0, 160 / PPM, 0, 0, true);
    }
    public void die(){
        if(!dead){
            dead=true;
        }
        screen.getPreferences().reset();
        screen.getPreferences().save();
        if (connected) {
            arduinoInstance.write("death;");
        }
        MainGame.contentManager.getMusic("gameMusic").stop();
        ScreenManager.getInstance().showScreen(ScreenEnum.DEATH);
    }
    
    

}
