/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 *
 * @author Adrien
 */
public class ContactHandler implements ContactListener{
    
    private int numFootContacts;

    private boolean bounce;
    private boolean dangerous;
    private boolean bookActive;
    private boolean victory=false;
    private boolean playerHurt;
    private boolean button;
    
    public ContactHandler(){
        super();
    }
    
    
    //Called when 2 fixtures collide
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
                numFootContacts++;
        }
        
        if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
                numFootContacts++;
        }
        
        if((fa.getUserData() != null && fa.getUserData().equals("foot") && fb.getUserData() != null && fb.getUserData().equals("spring"))
                || (fa.getUserData() != null && fa.getUserData().equals("spring") && fb.getUserData() != null && fb.getUserData().equals("foot"))) {
            bounce=true;
        }
        
        if((fa.getUserData() != null && fa.getUserData().equals("foot") && fb.getUserData() != null && fb.getUserData().equals("button"))
                || (fa.getUserData() != null && fa.getUserData().equals("button") && fb.getUserData() != null && fb.getUserData().equals("foot"))) {
            button=true;
        }
      
        
        if(fa.getUserData() != null && fa.getUserData().equals("foot") && fb.getUserData() != null && fb.getUserData().equals("spike")
                || fa.getUserData() != null && fa.getUserData().equals("spike") && fb.getUserData() != null && fb.getUserData().equals("foot")) {
            bounce=true;    
            dangerous=true;   
            playerHurt();
        }
        
        
        if(fa.getUserData() != null && fa.getUserData().equals("fireball")){
            if(fb.getUserData() != null && fb.getUserData().equals("player")){
                dangerous=true;     
                playerHurt();
            }
        }
        
        if(fb.getUserData() != null && fb.getUserData().equals("fireball")){
            if(fa.getUserData() != null && fa.getUserData().equals("player")){
                dangerous=true;  
                playerHurt();
            }
        }
        
        if(fa.getUserData() != null && (fa.getUserData().equals("slime") || fa.getUserData().equals("snail"))){
            if(fb.getUserData() != null && fb.getUserData().equals("player")){
                dangerous=true;
                fb.getBody().applyLinearImpulse(0, 8f, 0, 0, true);
                playerHurt();
            }
        }
        
        if(fb.getUserData() != null && (fb.getUserData().equals("slime") || fb.getUserData().equals("snail"))){
            if(fa.getUserData() != null && fa.getUserData().equals("player")){
                dangerous=true;
                fa.getBody().applyLinearImpulse(0, 8f, 0, 0, true);
                playerHurt();
            }
        }
        
        
        if((fa.getUserData() != null && fa.getUserData().equals("victory")) || (fb.getUserData() != null && fb.getUserData().equals("victory"))){
            victory=true;                
        }
        
        if(fa.getUserData() != null && fa.getUserData().equals("questionBox")){
            fa.setUserData("questionBoxDisabled");
        }else if(fb.getUserData() != null && fb.getUserData().equals("questionBox")){
            fb.setUserData("questionBoxDisabled");
        }

}
    
    //Called when 2 fixtures no longer collide
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa == null || fb == null) return;

        if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
                numFootContacts--;       
        }
        if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
                numFootContacts--;
        }
        
        if(fa.getUserData() != null && fa.getUserData().equals("foot") && fb.getUserData() != null && fb.getUserData().equals("spring")
                || fa.getUserData() != null && fa.getUserData().equals("spring") && fb.getUserData() != null && fb.getUserData().equals("foot")) {
                bounce=false;
        }
        
        if((fa.getUserData() != null && fa.getUserData().equals("foot") && fb.getUserData() != null && fb.getUserData().equals("button"))
                || (fa.getUserData() != null && fa.getUserData().equals("button") && fb.getUserData() != null && fb.getUserData().equals("foot"))) {
            button=false;
        }
        
        
        if(fa.getUserData() != null && fa.getUserData().equals("foot") && fb.getUserData() != null && fb.getUserData().equals("spike")
                || fa.getUserData() != null && fa.getUserData().equals("spike") && fb.getUserData() != null && fb.getUserData().equals("foot")) {
                bounce=false;
                dangerous=false;
        }

        if(fa.getUserData() != null && fa.getUserData().equals("fireball")){
            if(fb.getUserData() != null && fb.getUserData().equals("player")){
                dangerous=false;       
            }
            if(fb.getUserData() != null && (fb.getUserData().equals("slime") || fb.getUserData().equals("snail"))){
                fb.setUserData("setToDestroy");
            }
            fa.setUserData("toDestroy");
        }
        
        if(fb.getUserData() != null && fb.getUserData().equals("fireball")){
            if(fa.getUserData() != null && fa.getUserData().equals("player")){
                dangerous=false;
            }
            if(fa.getUserData() != null && (fa.getUserData().equals("slime") || fa.getUserData().equals("snail"))){
                fa.setUserData("setToDestroy");
            }
            fb.setUserData("toDestroy");
        }
        
        if(fa.getUserData() != null && fa.getUserData().equals("slime")){
            if(fb.getUserData() != null && fb.getUserData().equals("player")){
                dangerous=false;
                fa.setUserData("setToDestroy");
            }   
        }
        
        if(fb.getUserData() != null && fb.getUserData().equals("slime")){
            if(fa.getUserData() != null && fa.getUserData().equals("player")){
                dangerous=false;
                fb.setUserData("setToDestroy");
            }   
        }
        
        
    }
    
    //Before collision handling
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
       
    }
    
    //After collision handling
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse){
        
    }
    
    public void playerHurt(){
        playerHurt=true;
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                playerHurt=false;
            }
        },
                950
        );
    }
    public boolean isPlayerHurt() {return playerHurt;}
    public boolean playerCanJump() { return numFootContacts > 0; }
    public boolean isBouncy() { return bounce; }
    public boolean isButton() { return button; }
    public boolean isDangerous() { return dangerous; }
    public boolean isbookActive(){return bookActive;}
    public boolean hasWon(){return victory;}

    public void setDangerous(boolean dangerous) {
        this.dangerous = dangerous;
    }


    
}
