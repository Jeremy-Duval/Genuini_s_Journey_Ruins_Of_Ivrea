/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.physics.box2d.Body;
/**
 *
 * @author Adrien
 */
public class Player extends Character{
    private int life;
    private int status;
    
    public Player(Body body) {
        super(body,"p1",1,11,"right");
        life=100;
        status=1;// means he is alive
    }
    
    public void changeLife(int delta){
        life+=delta;
    }
    
    public int getLife(){
        return life;
    }
    
    public void setStatus(int status){
        this.status=status;
    }
    
    public int getStatus(){
        return status;
    }
    
}
