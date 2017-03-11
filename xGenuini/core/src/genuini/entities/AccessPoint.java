/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_POI;

/**
 *
 * @author Adrien
 */
public class AccessPoint extends StaticElements{
    private boolean active;
    private final String type;
    private final String name;
    private final String linkedMapName;
    private final String linkedAccessPointName;
    
    
    public AccessPoint(GameScreen screen, Body body, int ID, String type, String name,  String linkedMapName, String linkedAccessPointName) {
        super(screen, body, ID);
        active=true;
        this.type=type;
        this.name=name;
        createFilter();
        this.linkedMapName=linkedMapName;
        this.linkedAccessPointName=linkedAccessPointName;
        sprite= new Sprite();
        sprite.setPosition(position.x,position.y);
        
    }
    
    
    @Override
    public final void createFilter() {
        filter.categoryBits = BIT_POI;
        body.getFixtureList().first().setFilterData(filter);
        if(type.equals("entry")){
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
    
    public String getType() {
        return type;
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
