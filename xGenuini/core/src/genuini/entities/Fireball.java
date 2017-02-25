/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_OBJECT;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;
import static genuini.world.PhysicsVariables.PPM;

/**
 *
 * @author Adrien
 */
public class Fireball extends Sprites{

    private final Texture fireballTexture;
    private boolean setToDestroy;
    private boolean destroyed;
    private int stateTime;
    private final Vector2 offset;
    
    public Fireball(GameScreen screen, Vector2 position, Vector2 offset) {
        super(screen);
        this.offset=offset;
        createBody(position);
        fireballTexture = MainGame.contentManager.getTexture("fireball");
        sprite = new Sprite(fireballTexture);
    }

    @Override
    public void update(float delta) {
        Vector2 pos = new Vector2((body.getPosition().x * PPM)-offset.x, (body.getWorldCenter().y * PPM)-offset.y);
        sprite.setPosition(pos.x,pos.y);
        sprite.rotate(45);
        stateTime+=delta;
        if((stateTime>3 || setToDestroy)&&!destroyed&&!screen.getWorld().isLocked()){
            screen.getWorld().destroyBody(body);
            destroyed=true;
        }else if(body.getFixtureList().size>0&&!destroyed){
            if(body.getFixtureList().first().getUserData().equals("toDestroy")&&!destroyed){
                setToDestroy();
            }
            
        }
    }
    
    private void createBody(Vector2 position){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.bullet=true;
        bdef.position.set((position.x+offset.x)/PPM,(position.y+offset.y)/PPM);
        body = screen.getWorld().createBody(bdef);
        CircleShape circle = new CircleShape();
        circle.setRadius(0.15f);
        FixtureDef fd = new FixtureDef();
        fd.shape=circle;
        fd.density=2.5f;
        fd.friction=1f;
        fd.restitution=0.6f;
        fd.filter.categoryBits = BIT_FIREBALL;
        fd.filter.maskBits = BIT_PLAYER | BIT_TERRAIN | BIT_PLAYER | BIT_OBJECT;
        body.createFixture(fd).setUserData("fireball");
    }
    
    public void targetBody(Body targetBody, float speed) { 
        body.applyLinearImpulse((targetBody.getPosition().x-body.getPosition().x)*speed/PPM, (targetBody.getPosition().y-body.getPosition().y-body.getLocalCenter().y)*speed/PPM, 0, 0, true);
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void setToDestroy(){
        setToDestroy=true;
    }
    

}
