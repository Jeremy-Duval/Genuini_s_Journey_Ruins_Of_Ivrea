/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;

/**
 *
 * @author Adrien
 */
public class Button extends StaticElements{

    private final Texture buttonTexture;
    private final Texture buttonPressedTexture;
    private boolean pressed;
    private final String linkedObjectType;
    private final int linkedObjectID;

    public Button(GameScreen screen,Body body,int ID, int linkedObjectID, String linkedObjectType) {
        super(screen,body,ID);
        this.linkedObjectType = linkedObjectType;
        buttonTexture = MainGame.contentManager.getTexture("buttonRed");
        buttonPressedTexture = MainGame.contentManager.getTexture("buttonRed_pressed");
        
        sprite= new Sprite(buttonTexture);
        sprite.setPosition(position.x,position.y);
        this.linkedObjectID=linkedObjectID;
        pressed=false;
        createFilter();
    }

    
    public void press(){
        sprite.setRegion(buttonPressedTexture);
        pressed=true;
    }

    @Override
    public final void createFilter() {
        filter.categoryBits = BIT_TERRAIN;
        filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
        body.getFixtureList().first().setFilterData(filter);
        body.getFixtureList().first().setUserData("button");
    }

    public boolean isPressed() {
        return pressed;
    }

    public String getLinkedObjectType() {
        return linkedObjectType;
    }
    
    public int getLinkedObjectID() {
        return linkedObjectID;
    }
    
    
    
}
