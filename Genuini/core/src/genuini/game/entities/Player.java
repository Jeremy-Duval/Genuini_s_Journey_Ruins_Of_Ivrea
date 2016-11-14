/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import genuini.game.PhysicsManager;

/**
 *
 * @author Adrien
 */
public class Player extends Sprite implements InputProcessor{
    private Vector2 velocity;
    private float speed=140, gravity=8*9.81f ;
    private boolean canJump;
    private Body body;
    
    private TiledMapTileLayer collisionLayer;
    
    public Player(Sprite sprite, float posX, float posY,TiledMapTileLayer collisionLayer, World world){
        super(sprite);
        this.velocity = new Vector2();
        this.collisionLayer=collisionLayer;
        //setSize(collisionLayer.getTileWidth()/2,collisionLayer.getTileHeight());
        
        
       BodyDef bodyDef = new BodyDef();
       bodyDef.type = BodyDef.BodyType.DynamicBody;
       bodyDef.position.set(posX * PhysicsManager.PIXELS_TO_METERS, posY * PhysicsManager.PIXELS_TO_METERS);
       
       body = world.createBody(bodyDef);
       
       PolygonShape shape = new PolygonShape();
       shape.setAsBox(getWidth() * PhysicsManager.PIXELS_TO_METERS, getHeight() * PhysicsManager.PIXELS_TO_METERS);
       
       
       FixtureDef fixtureDef = new FixtureDef();
       fixtureDef.shape = shape;
       fixtureDef.density = 1f;
       
       Fixture fixture = body.createFixture(fixtureDef);
       
       shape.dispose();
    }
    
    public void draw(SpriteBatch spriteBatch){
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }
    
    public void update(float delta){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            body.applyForceToCenter(0f, 0.25f * PhysicsManager.METERS_TO_PIXELS, false);
        }
        setPosition(body.getPosition().x * PhysicsManager.METERS_TO_PIXELS, body.getPosition().y * PhysicsManager.METERS_TO_PIXELS);
    }
    
    
    
    
    /*
    //update position and velocity
    public void update(float delta){
        //apply gravity to player's vertical velocity
        velocity.y -= gravity*delta; 
        
        //
        if(velocity.y>speed){
            velocity.y = speed;
        }else if(velocity.y<-speed){
            velocity.y = -speed;
        }
        
        //save old positions
        float oldX = getX(), oldY =getY();
        
        
        //optimisation: check collision only on one side
        boolean collisionX =false, collisionY= false;
        
        
        //move on x direction
        setX(getX() + velocity.x * delta);
        
        
        if(velocity.x<0){
            collisionX=collidesLeft();  
        }else if(velocity.x>0){
            collisionX=collidesRight(); 
        }
        
        //reaction to X collision
        
        if(collisionX){
            setX(oldX);
            velocity.x=0;
        }
        
        //move on y direction
        setY(getY() + velocity.y * delta);
        
        if(velocity.y<0){
            collisionY=collidesBottom(); 
            canJump=collisionY;
        }else if(velocity.y>0){
            collisionY=collidesTop(); 
        }
        
        //reaction to X collision
        if(collisionY){
            setY(oldY);
            velocity.y=0;

        }
    }*/
    
    private boolean isCellSolid(float x, float y){
        Cell cell = collisionLayer.getCell((int)(x/collisionLayer.getTileWidth()),(int)(y/collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("solid");
    }
    
    public boolean collidesRight() {
	for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
		if(isCellSolid(getX() + getWidth(), getY() + step))
			return true;
	return false;
    }

    public boolean collidesLeft() {
            for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
                    if(isCellSolid(getX(), getY() + step))
                            return true;
            return false;
    }

    public boolean collidesTop() {
            for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
                    if(isCellSolid(getX() + step, getY() + getHeight()))
                            return true;
            return false;
    }

    public boolean collidesBottom() {
            for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
                    if(isCellSolid(getX() + step, getY()))
                            return true;
            return false;
    }
    
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    
    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
        case Keys.Z:
            if(canJump){
                velocity.y=speed;
                canJump=false;
            }
            break;
        case Keys.Q:
            velocity.x=-speed;
            break;
        case Keys.D:
            velocity.x=speed;
            break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode){
        case Keys.Z:
            break;
        case Keys.Q:
        case Keys.D:
            velocity.x=0;
            break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    
    
}
