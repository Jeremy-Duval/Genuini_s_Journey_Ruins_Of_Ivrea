/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import genuini.entities.Mobs.Attitude;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_POI;
import static java.lang.Math.random;

/**
 *
 * @author Adrien
 */
public class MobSpawnPoint extends StaticElements{
    private boolean active;
    private final MobType mobType;
    private final float area;
    private final Array<Mobs> mobs;
    private float stateTime;
    private final int maxMobs;
    
    public enum MobType {SLIME, SNAIL}
    
    public MobSpawnPoint(GameScreen screen, Body body, int ID, String mobType, float area, int maxMobs) {
        super(screen, body, ID);
        active=true;
        createFilter();
        sprite= new Sprite();
        sprite.setPosition(position.x,position.y);
        stateTime=0;
        
        if(mobType.equals("slime")){
            this.mobType=MobType.SLIME;
        }else if(mobType.equals("snail")){
            this.mobType=MobType.SNAIL;
        }else{
            this.mobType=MobType.SNAIL;
        }
        
        this.area=area;
        this.maxMobs=maxMobs;
        
        mobs=new Array<Mobs>();
    }
    
    @Override
    public void update(float delta){
        if(!screen.getWorld().isLocked()){
            
            for (Mobs mob : mobs) {
                if(mob.getBody()==null){
                    mobs.removeValue(mob, true);
                }else if (!mob.getBody().isActive()){
                    mobs.removeValue(mob, true);
                }else{
                    mob.update(delta);
                }
            }
            
            
            
            Vector2 spawnPosition = new Vector2(body.getPosition().x+((float) random()*area),body.getPosition().y);
            Direction direction = Direction.RIGHT;
            if(random()>0.5){
                spawnPosition = new Vector2(body.getPosition().x+((float) random()*-area),body.getPosition().y);
                direction = Direction.LEFT;
            }

            if(screen.getDistanceFromPlayer(this)<0.5){
                spawnPosition.x+=3;
            }

            if(active && mobs.size<maxMobs && stateTime>10f){
                if(mobType==MobType.SLIME){
                    Slime slimy = new Slime(this.screen, spawnPosition, direction);
                    mobs.add(slimy);
                }else if(mobType==MobType.SNAIL){
                    Snail snail = new Snail(this.screen, spawnPosition, direction);
                    mobs.add(snail);
                }
                stateTime=0;
            }

            for (Mobs mob : mobs) {
                if((Math.abs(mob.getInitialPosition().x-mob.getPosition().x)>area) && !mob.isComingHome()){
                    mob.comeHome();
                }else if((Math.abs(mob.getInitialPosition().x-mob.getPosition().x)<=area) && mob.isComingHome()){
                    mob.isHome();
                }

                if(screen.getDistanceFromPlayer(mob)<2f){ 
                    if(mob.attitude!=Attitude.HOSTILE){
                        if(mob.getAttackSound()!=null){
                            mob.getAttackSound().play();
                        }
                    }
                    mob.changeAttitude(Attitude.HOSTILE);
                }else{
                    mob.changeAttitude(Attitude.NEUTRAL);
                }
            }
            stateTime+=delta;
        }     
    }
    
    @Override
    public void draw(SpriteBatch spriteBatch){
        for(Mobs mob : mobs){
            mob.draw(spriteBatch);
        }
    }
    
    @Override
    public final void createFilter() {
        filter.categoryBits = BIT_POI;
        body.getFixtureList().first().setFilterData(filter);      
    }
    

    
    public void activate(){
        active=true;
    }
    
    public void deactivate(){
        active=false;
    }
    
    public boolean isActive(){
        return active;
    }
    
    public MobType getType() {
        return mobType;
    }
          
}
