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
import static genuini.world.PhysicsVariables.BIT_MOB;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_BOX;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;

/**
 *
 * @author Adrien
 */
public class QuestionBox extends StaticElements{

    private final Texture questionBoxTexture;
    private final Texture questionBoxDisabledTexture;
    private boolean active;
    private final String skill;

    public QuestionBox(GameScreen screen, Body body, int ID, String skill) {
        super(screen, body, ID);
        questionBoxTexture = MainGame.contentManager.getTexture("questionBox");
        questionBoxDisabledTexture = MainGame.contentManager.getTexture("questionBoxDisabled");
        
        sprite= new Sprite(questionBoxTexture);
        
        sprite.setPosition(position.x,position.y);
        
        this.skill=skill;
        createFilter();
        active=true;
    }

    @Override
    public final void createFilter() {
        filter.categoryBits = BIT_BOX;
        filter.maskBits = BIT_PLAYER | BIT_MOB | BIT_TERRAIN;
        body.getFixtureList().first().setFilterData(filter);
        body.getFixtureList().first().setUserData("questionBox");
    }   
    
    @Override
    public void update(float delta){
        if(body.getFixtureList().first().getUserData().equals("questionBoxDisabled") && active){
            screen.getPreferences().giveSkill(skill);
            screen.getTextManager().displayText("Look at your spellbook, you have a new challenge !", 5);
            screen.getTextManager().displayText("Congratulations you have acquired a new ability",5,6);
            screen.getTextManager().displayText(screen.getTextManager().getHint(),5,12);
            disable();
        }
    }
    
    public void disable(){
        sprite.setRegion(questionBoxDisabledTexture);
        filter.categoryBits = BIT_BOX;
        filter.maskBits = BIT_TERRAIN;
        body.getFixtureList().first().setFilterData(filter);
        active=false;
    }
    
    public String getSkill(){
        return skill;
    }
    
}
