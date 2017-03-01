package genuini.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import genuini.game.ScreenEnum;
import genuini.game.ScreenManager;
import genuini.main.MainGame;
import static genuini.screens.AbstractScreen.arduinoInstance;
import static genuini.screens.AbstractScreen.connected;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;
import static genuini.world.PhysicsVariables.BIT_TURRET;
import static genuini.world.PhysicsVariables.PPM;

/**
 * Class used to create the player
 *
 * @author Adrien Techer
 */
public class Genuini extends Characters {
    private boolean inAnotherDimension;
    private int life;

    private final float bodyWidth = 22f / PPM;
    private final float bodyHeight = 44f / PPM;
    private final float feetWidth = 14f / PPM;
    private final float feetHeight = 14f / PPM;

    private boolean dead;
    private State currentState;
    private State previousState;
    /**
     * Elapsed time since previous animation
     */
    private float stateTimer;

    private final Animation genuiniWalking;
    private final TextureAtlas.AtlasRegion genuiniJumping;
    private final TextureAtlas.AtlasRegion genuiniHurt;
    private final TextureAtlas.AtlasRegion genuiniStanding;

    private Direction direction;

    public enum State {
        STANDING, WALKING, DEAD, JUMPING, HURT
    };

    public enum Direction {
        LEFT, RIGHT
    };

    /**
     *
     * @param screen the screen to which the sprite belongs
     */
    public Genuini(GameScreen screen) {
        super(screen);

        life = screen.getPreferences().getLife();
        dead = false;
        createBody();
        currentState = State.STANDING;
        previousState = State.STANDING;
        direction = Direction.RIGHT;
        stateTimer = 0;
        atlas = new TextureAtlas(Gdx.files.internal("img/packed/player_output/player.atlas"));
        sprite = atlas.createSprite("front");
        genuiniWalking = new Animation(0.1f, atlas.findRegions("walk"));
        genuiniStanding = atlas.findRegion("stand");
        genuiniJumping = atlas.findRegion("jump");
        genuiniHurt = atlas.findRegion("hurt");
        
    }

    /**
     * Creates the genuini's body
     */
    private void createBody() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        bdef.position.set(screen.getPreferences().getPositionX(), screen.getPreferences().getPositionY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        body = world.createBody(bdef);
        fdef.friction = 0.1f;
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = BIT_TERRAIN | BIT_TURRET | BIT_FIREBALL ;

        //Create player shape
        Vector2[] playerShapeVertices = new Vector2[6];
        playerShapeVertices[0] = new Vector2(-feetWidth, -bodyHeight);
        playerShapeVertices[1] = new Vector2(feetWidth, -bodyHeight);
        playerShapeVertices[2] = new Vector2(bodyWidth, -bodyHeight + feetHeight);
        playerShapeVertices[3] = new Vector2(bodyWidth, bodyHeight);
        playerShapeVertices[4] = new Vector2(-bodyWidth, bodyHeight);
        playerShapeVertices[5] = new Vector2(-bodyWidth, -bodyHeight + feetHeight);
        shape.set(playerShapeVertices);
        body.createFixture(fdef).setUserData("player");

        //create foot sensor
        shape.setAsBox(feetWidth, feetHeight / 2, new Vector2(0, -bodyHeight), 0);
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

    }

    @Override
    public void update(float dt) {
        Vector2 pos = new Vector2(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.setRegion(getFrame(dt));
        sprite.setPosition(pos.x, pos.y);
    }

    /**
     * Special thanks to Brent Aureli from whom the code is inspire
     *
     * @see <a href="https://github.com/BrentAureli/SuperMario">Brent Aureli's Github - SuperMario</a>
     * @param delta time elapsed since previous update
     * @return the texture region of the atlas depending on the state
     */
    public TextureRegion getFrame(float delta) {
        //get's genuini's current state
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding texture region or animation frame.
        switch (currentState) {
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

        //flips the region if player walking left and the region is not towards left
        if ((body.getLinearVelocity().x < 0 || direction == Direction.LEFT) && !region.isFlipX()) {
            region.flip(true, false);
            direction = Direction.LEFT;
        } //flips the region if player walking right and the region is not towards right
        else if ((body.getLinearVelocity().x > 0 || direction == Direction.RIGHT) && region.isFlipX()) {
            region.flip(true, false);
            direction = Direction.RIGHT;
        }

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + delta : 0;

        //update previous state
        previousState = currentState;

        return region;

    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    /**
     *
     * @param deltaLife the life to add, if life is lost this value should be
     * negative
     */
    public void changeLife(int deltaLife) {
        life += deltaLife;
    }

    /**
     *
     * @return genuini's current state
     */
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

    /**
     * Makes Genuini die : stops music, chang's screen
     */
    public void die() {
        if (!dead) {
            dead = true;
        }
        screen.getPreferences().reset();
        if (connected) {
            arduinoInstance.write("death;");
        }
        MainGame.contentManager.getMusic("gameMusic").stop();
        ScreenManager.getInstance().showScreen(ScreenEnum.DEATH);
    }

    public float getBodyWidth() {
        return bodyWidth;
    }

    public float getBodyHeight() {
        return bodyHeight;
    }

}
