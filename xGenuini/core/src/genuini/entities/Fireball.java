/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.main.MainGame;

/**
 *
 * @author Adrien
 */
public class Fireball extends EnvironmentObject{
   private Turret turret;
   
   public Fireball(Body body,Turret turret) {
        super(body,MainGame.contentManager.getTexture("fireball"));
        this.turret=turret;
   }
    
   @Override
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
        rotationAngle+=45;
    }
    
    public Turret getTurret(){return turret;}
    
}
