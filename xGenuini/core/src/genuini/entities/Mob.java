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
public class Mob extends Character{
    private int life;
    private int status;
    
    public Mob(Body body) {
        super(body,"p1",76,92,1,11,"right");
        life=500;
        status=1;// 1 : alive -> 0 : dead
    }
    
    public void setLife(int delta){
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
