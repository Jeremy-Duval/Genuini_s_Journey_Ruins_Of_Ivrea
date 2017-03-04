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
    
    public Mobs(GameScreen screen) {
        super(screen);
        atlas = new TextureAtlas(Gdx.files.internal("img/packed/mobs_output/mobs.atlas"));
        currentState = State.WALKING;
        previousState = State.WALKING;
        dead = false;
        direction = Direction.RIGHT;
        stateTimer = 0;
    }
    
    @Override
    public void update(float delta) {
        Vector2 pos = new Vector2(body.getPosition().x * PPM - sprite.getWidth() / 2, body.getPosition().y * PPM - sprite.getHeight() / 2);
        sprite.setPosition(pos.x, pos.y-0.2f); //Corrected small transparent line at bottom
        sprite.setRegion(getFrame(delta,false));
    }
}
