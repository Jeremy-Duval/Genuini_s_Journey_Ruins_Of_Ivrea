/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author Adrien
 */
public class Turret {
    private Vector2 pos;
    private boolean activated = true;
    private Fireball fireball;
    private boolean hasFireball = false;
       
    public Turret(Vector2 pos) {
        this.pos = pos;
    }
    
    
    public void activate(){
        activated = true;
    }
    
    public void deactivate(){
        activated = false;
    }
    
    
    public Vector2 getPos(){return pos;}
    
    public boolean isActive(){return activated;}
    
    public boolean hasFireball(){return hasFireball;}
    
    public void setFireball(Fireball fireball){
        this.fireball=fireball;
        hasFireball = true;
    }
    
    public void destroyFireball(){
        hasFireball = false;
    }
    
}
