package genuini.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.PPM;

/**
 * The parent class to all moving NPCs
 *
 * @author Adrien Techer
 */
public abstract class Characters extends LivingBeings {

    
    private final Animation walkingAnimation;
    private final TextureAtlas.AtlasRegion jumpingTexture;
    private final TextureAtlas.AtlasRegion hurtTexture;
    private final TextureAtlas.AtlasRegion standingTexture;
    
    

    final float bodyWidth = 22f / PPM;
    final float bodyHeight = 44f / PPM;
    final float feetWidth = 14f / PPM;
    final float feetHeight = 14f / PPM;


    /**
     * 
     * @param screen the screen to which the sprite belongs
     * @param characterName the name of the character to create
     */
    public Characters(GameScreen screen,String characterName) {
        super(screen);
        currentState = State.STANDING;
        previousState = State.STANDING;
        dead = false;
        direction = Direction.RIGHT;
        stateTimer = 0;
        
        
        //Retrieving and setting textures
        atlas = new TextureAtlas(Gdx.files.internal("img/packed/"+characterName+"_output/"+characterName+".atlas"));
        sprite = atlas.createSprite("front");
        walkingAnimation = new Animation(0.1f, atlas.findRegions("walk"));
        standingTexture = atlas.findRegion("stand");
        jumpingTexture = atlas.findRegion("jump");
        hurtTexture = atlas.findRegion("hurt");
    }  
    
    @Override
    public TextureRegion setRegion(){
        //depending on the state, get corresponding texture region or animation frame.
        //depending on the state, get corresponding texture region or animation frame.
        switch (currentState) {
            case WALKING:
                return walkingAnimation.getKeyFrame(stateTimer, true);
            case JUMPING:
                return jumpingTexture;
            case HURT:
                return hurtTexture;
            default:
                return standingTexture;
        }
    }
    
    public float getBodyWidth() {
        return bodyWidth;
    }

    public float getBodyHeight() {
        return bodyHeight;
    }
    
    

    /**
     *
     * @return genuini's current state
     */
    @Override
    public State getState() {
        if (dead) {
            return State.DEAD;
        } else if (screen.getContactManager().isPlayerHurt()) {
            return State.HURT;
        } else if (!screen.getContactManager().playerCanJump()) {
            return State.JUMPING;
        } else if (Math.abs(body.getLinearVelocity().x) > 0.1f) {
            return State.WALKING;
        } else {
            return State.STANDING;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Makes Genuini's body move along the X-Axis
     *
     * @param direction the direction towards which genuini should move
     */
    public void walk(Direction direction) {
        if (direction == Direction.LEFT) {
            body.applyLinearImpulse(-5 / PPM, 0, 0, 0, true);
        } else {
            body.applyLinearImpulse(5 / PPM, 0, 0, 0, true);
        }

    }

    /**
     * Makes Genuini's body move along the Y-Axis
     *
     * @param impulse the intensity of the impulse
     */
    public void jump(float impulse) {
        body.applyLinearImpulse(0, impulse / PPM, 0, 0, true);
    }
    
    @Override
    public void update(float delta) {
        Vector2 pos = new Vector2(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.setPosition(pos.x, pos.y);
        sprite.setRegion(getFrame(delta,true));
    }
}
