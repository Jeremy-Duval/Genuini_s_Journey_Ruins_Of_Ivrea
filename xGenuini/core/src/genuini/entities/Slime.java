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
public class Slime extends Mobs{
    private final Animation walkingAnimation;
    private final TextureAtlas.AtlasRegion deadTexture;

    public Slime(GameScreen screen, Vector2 initalPosition, Direction direction) {
        super(screen, initalPosition, direction);
        //Retrieving and setting textures
        sprite = atlas.createSprite("slimeWalk",1);
        walkingAnimation = new Animation(0.5f, atlas.findRegions("slimeWalk"));
        deadTexture = atlas.findRegion("slimeDead");
        speed=40f;
        offsetY=-2f;
        createBody();
        walkSound = MainGame.contentManager.getSound("slimeWalk");
        deathSound = MainGame.contentManager.getSound("slimeDeath");
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
        
        body.createFixture(fdef).setUserData("slime");
    }
    
    
    /**
     *
     * @return the slime's current state
     */
    @Override
    public State getState() {
        if (dead) {
            return State.DEAD;
        } else if (Math.abs(body.getLinearVelocity().x) > 0.1f) {
            return State.WALKING;
        } else {
            return State.WALKING;
        }
    }
    
    @Override
    public TextureRegion setRegion(){
        //depending on the state, get corresponding texture region or animation frame.
        switch (currentState) {
            case WALKING:
                return walkingAnimation.getKeyFrame(stateTimer, true);
            case DEAD:
                offsetY=-8f;
                sprite.setScale(1, 0.6f);
                return deadTexture;
            default:
                return walkingAnimation.getKeyFrame(stateTimer, true);
        }
    }   
}
