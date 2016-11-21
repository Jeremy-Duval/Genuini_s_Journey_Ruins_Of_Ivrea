/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.physics.box2d.Body;
import genuini.main.Game;
/**
 *
 * @author Adrien
 */
public class Player extends Character{

    public Player(Body body) {
        super(body,"p1",76,92,1,11,"right");  
    }
    
}
