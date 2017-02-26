/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_ACCESSPOINT;
import static genuini.world.PhysicsVariables.BIT_PLAYER;

/**
 *
 * @author Adrien
 */
public class AccessPoint extends StaticElements{
    private boolean active;
    private final boolean spawn;
    private final String name;
    private final String linkedMapName;
    private final String linkedAccessPointName;
    
    
    public AccessPoint(GameScreen screen, Body body, int ID, boolean spawn, String name, String linkedMapName, String linkedAccessPointName) {
        super(screen, body, ID);
        active=true;
        this.spawn=spawn;
        this.name=name;
        createFilter();
        this.linkedMapName=linkedMapName;
        this.linkedAccessPointName=linkedAccessPointName;
        sprite= new Sprite();
        sprite.setPosition(position.x,position.y);
        
    }
    
    
    @Override
    public final void createFilter() {
        filter.categoryBits = BIT_ACCESSPOINT;
        body.getFixtureList().first().setFilterData(filter);
        if(!spawn){
            body.getFixtureList().first().setUserData("accessPoint"); 
        }
        
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
    
    public boolean isSpawn(){
        return spawn;
    }
    
    public String getName() {
        return name;
    }
    
    public String getLinkedMapName() {
        return linkedMapName;
    }

    public String getLinkedAccessPointName() {
        return linkedAccessPointName;
    }
    
    
}
