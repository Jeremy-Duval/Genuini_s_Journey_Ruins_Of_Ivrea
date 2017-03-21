/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.game.PreferencesManager;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_MOB;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_BOX;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;
import genuini.world.ScenarioVariables;

/**
 *
 * @author Adrien
 */
public class QuestionBox extends StaticElements{

    private final Texture questionBoxTexture;
    private final Texture questionBoxDisabledTexture;
    private boolean active;
    private int questionNumber;

    public QuestionBox(GameScreen screen, Body body, int ID, String question) {
        super(screen, body, ID);
        questionBoxTexture = MainGame.contentManager.getTexture("questionBox");
        questionBoxDisabledTexture = MainGame.contentManager.getTexture("questionBoxDisabled");
        
        sprite= new Sprite(questionBoxTexture);
        
        sprite.setPosition(position.x,position.y);
        
        
        setQuestionNumber(question);
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
    
    public final void setQuestionNumber(String question) {
        if(question.equals("doubleJump")){
            questionNumber=ScenarioVariables.DOUBLE_JUMP;
        }else if(question.equals("fireball")){
            questionNumber=ScenarioVariables.FIREBALL;
        }else if(question.equals("gravity")){
            questionNumber=ScenarioVariables.GRAVITY;
        }else{
            questionNumber=0;
        }
    }
    
    
    @Override
    public void update(float delta){
        if(body.getFixtureList().first().getUserData().equals("questionBoxDisabled") && active){
            screen.displayText("Look at your spellbook, you have a new challenge !", 5000);
            PreferencesManager.stepProgression();
            screen.getPreferences().updateProg();
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
    
    public int getQuestionNumber(){
        return questionNumber;
    }
}
