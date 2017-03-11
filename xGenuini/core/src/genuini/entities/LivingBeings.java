/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.PPM;

/**
 *
 * @author Adrien
 */
public abstract class LivingBeings extends Sprites {

    //The texture atlas to create textures and animations from
    TextureAtlas atlas;
    int life;
    boolean dead;

    /**
     * Elapsed time since previous animation
     */
    float stateTimer;

    Direction direction;

    State currentState;
    State previousState;

    public enum State {
        STANDING, WALKING, DEAD, JUMPING, HURT, FIRING
    };

    public LivingBeings(GameScreen screen) {
        super(screen);
    }

    /**
     * Special thanks to Brent Aureli from whom the code is inspired
     *
     * @param atlasTowardsRight
     * @see <a href="https://github.com/BrentAureli/SuperMario">Brent Aureli's
     * Github - SuperMario</a>
     * @param delta time elapsed since previous update
     * @return the texture region of the atlas depending on the state
     */
    public TextureRegion getFrame(float delta, boolean atlasTowardsRight) {
        //get's genuini's current state
        currentState = getState();

        TextureRegion region = flipRegion(setRegion(),atlasTowardsRight);

        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + delta : 0;

        //update previous state
        previousState = currentState;

        return region;
    }

    private TextureRegion flipRegion(TextureRegion region, boolean atlasTowardsRight) {
        if (atlasTowardsRight) {
            //flips the region if player walking left and the region is not towards left
            if ((body.getLinearVelocity().x < 0 || direction == Direction.LEFT) && !region.isFlipX()) {
                region.flip(true, false);
                direction = Direction.LEFT;
            } //flips the region if player walking right and the region is not towards right
            else if ((body.getLinearVelocity().x > 0 || direction == Direction.RIGHT) && region.isFlipX()) {
                region.flip(true, false);
                direction = Direction.RIGHT;
            }
        }else{
            //flips the region if player walking left and the region is not towards left
            if ((body.getLinearVelocity().x < 0 || direction == Direction.LEFT) && region.isFlipX()) {
                region.flip(true, false);
                direction = Direction.LEFT;
            } //flips the region if player walking right and the region is not towards right
            else if ((body.getLinearVelocity().x > 0 || direction == Direction.RIGHT) && !region.isFlipX()) {
                region.flip(true, false);
                direction = Direction.RIGHT;
            }
        }
        
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

    public abstract void die();

    public abstract State getState();

    public abstract TextureRegion setRegion();

}
