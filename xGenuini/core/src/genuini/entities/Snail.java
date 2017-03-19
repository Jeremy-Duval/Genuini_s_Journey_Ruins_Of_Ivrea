/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_MOB;
import static genuini.world.PhysicsVariables.BIT_OBJECT;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;
import static genuini.world.PhysicsVariables.BIT_TURRET;
import static genuini.world.PhysicsVariables.PPM;

/**
 *
 * @author Adrien
 */
public class Snail extends Mobs{
    private final Animation walkingAnimation;
    private final TextureAtlas.AtlasRegion deadTexture;
    private final TextureAtlas.AtlasRegion sleepingTexture;
    private float sleepTimer;

    public Snail(GameScreen screen, Vector2 initalPosition, Direction direction) {
        super(screen, initalPosition, direction);
        //Retrieving and setting textures
        sprite = atlas.createSprite("snailShell");
        walkingAnimation = new Animation(0.5f, atlas.findRegions("snailWalk"));
        sleepingTexture= atlas.findRegion("snailShell");
        deadTexture = atlas.findRegion("snailShell_upsidedown");
        speed=40f;
        offsetY=-2f;
        sleepTimer=0;
        createBody();
        walkSound=MainGame.contentManager.getSound("snailWalk");
        attackSound=MainGame.contentManager.getSound("snailAttack");
    }
    
    
    /**
     * Creates the slime's body
     */
    private void createBody() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        bdef.position.set(initialPosition.x, initialPosition.y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        body = world.createBody(bdef);
        fdef.friction = 0.1f;
        shape.setAsBox(sprite.getWidth()/2/PPM, sprite.getHeight()/2/PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_MOB;
        fdef.filter.maskBits = BIT_TERRAIN | BIT_TURRET | BIT_FIREBALL | BIT_PLAYER | BIT_OBJECT;
        
        body.createFixture(fdef).setUserData("snail");
    }
    
    
    /**
     *
     * @return the slime's current state
     */
    @Override
    public State getState() {
        if (dead) {
            return State.DEAD;
        } else if (Math.abs(body.getLinearVelocity().x) > 0.1f  || attitude==Attitude.HOSTILE || sleepTimer<10f) {
            return State.WALKING;
        }else{
            return State.SLEEPING;
        }
    }
    
    @Override
    public TextureRegion setRegion(){
        //depending on the state, get corresponding texture region or animation frame.
        switch (currentState) {
            case WALKING:
                return walkingAnimation.getKeyFrame(stateTimer, true);
            case DEAD:
                return deadTexture;
            case SLEEPING:
                return sleepingTexture;
            default:
                return sleepingTexture;
        }
    }
    
    @Override
    public void update(float delta){
        super.update(delta);
        if(attitude==Attitude.HOSTILE){
            sleepTimer=0;
        }
        sleepTimer+=delta;
    }
}
