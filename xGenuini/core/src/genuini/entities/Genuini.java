package genuini.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import static genuini.world.PhysicsVariables.BIT_MOB;
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
    private Fireball fireball;
    private boolean hasFireball;
    private boolean rejump;

    /**
     *
     * @param screen the screen to which the sprite belongs
     */
    public Genuini(GameScreen screen) {
        super(screen,"player");

        life = screen.getPreferences().getLife();
        hasFireball=false;
        createBody();
        rejump=false;
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
        fdef.filter.maskBits = BIT_TERRAIN | BIT_TURRET | BIT_FIREBALL | BIT_MOB ;

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


    /**
     * Makes Genuini die : stops music, chang's screen
     */
    @Override
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
    
    public void throwFireball(){
        if(!hasFireball){
            float impulse=2f;
            if(direction==Direction.LEFT){
                impulse*=-1;
            }
            
            float offsetX = impulse<0 ? -sprite.getWidth()*1.2f : sprite.getWidth()/4;
            float posX = body.getPosition().x*PPM+offsetX;
            
            float offsetY = -sprite.getHeight()/2;
            float posY = body.getPosition().y*PPM+offsetY;
            
            Fireball f = new Fireball(screen, new Vector2(posX,posY), new Vector2(35f,35f));
            fireball=f;
            fireball.getBody().applyLinearImpulse(impulse, 0.1f, 0, 0, true);
            MainGame.contentManager.getSound("fireball").play();
            
            firing=true;
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        firing=false;
                    }
                },
                500
            );
            hasFireball=true;
        }
    }
    
    
    @Override
    public void update(float delta){
        super.update(delta);
        if(fireball!=null){
            if(fireball.getBody()!=null){
                fireball.update(delta);
            }else{
                fireball=null;
                hasFireball=false;
            }
            if(!fireball.getBody().isActive()){
                fireball=null;
                hasFireball=false;
            }
        }
    }
    
    
    @Override
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
        if(fireball!=null){
            if(fireball.getBody()!=null){
                fireball.draw(spriteBatch);
            }
        }
    }
    
    public boolean canReJump(){
        return rejump;
    }

    public void setReJump(boolean canJumpAgain) {
        rejump=canJumpAgain;
    }

}
