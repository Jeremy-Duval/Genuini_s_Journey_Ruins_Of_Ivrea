/**
 *
 * @author Adrien
 */

package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import genuini.handlers.GameScreenManager;
import static genuini.handlers.PhysicsVariables.PPM;
import genuini.main.Game;



public class Play extends GameScreen{
    private BitmapFont font = new BitmapFont();
    
    private OrthographicCamera b2dcam;
    
    private final World world;
    private Box2DDebugRenderer b2dr;
    
    
    
    public Play(GameScreenManager gsm){
        super(gsm);
        
        world = new World(new Vector2(0, -9.81f), true); //Create world, any inactive bodies are asleep (not calculated)
        b2dr = new Box2DDebugRenderer();
        
        //create platform
        BodyDef bdef = new BodyDef();
        bdef.position.set(160 / PPM , 120 / PPM);
        bdef.type = BodyType.StaticBody;
        Body body = world.createBody(bdef);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / PPM , 5 / PPM );
        
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);
        
        //create falling box
        bdef.position.set(160 / PPM , 200 / PPM);
        bdef.type = BodyType.DynamicBody;
        body = world.createBody(bdef);
        
        
        shape.setAsBox(5/PPM ,5/PPM);
        body.createFixture(fdef);
        
        b2dcam = new OrthographicCamera();
        b2dcam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT /PPM);
        
    }
    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        world.step(dt, 6, 2);
    }

    @Override
    public void render() {
        //clear screen
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        b2dr.render(world, b2dcam.combined);
        
        
        //spriteBatch.setProjectionMatrix();
        //spriteBatch.begin();
        //font.draw(spriteBatch, "bla", 100, 100);
        //spriteBatch.end();
    }

    @Override
    public void dispose() {
    }
    
}
