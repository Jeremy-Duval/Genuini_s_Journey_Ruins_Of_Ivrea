/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.main.MainGame;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Adrien
 */
public class Spring extends EnvironmentObject{
    
    public Spring(Body body) {
        super(body, MainGame.contentManager.getTexture("spring"));
    }
    
    public void drawUp(Texture texture) throws InterruptedException{
        Texture prevTexture = this.getTexture();
        System.out.println(prevTexture.toString());
        /*this.setRegion(texture);
        TimeUnit.MINUTES.sleep(1);
        this.setRegion(prevTexture);*/
    }
    
    @Override
    public void draw(SpriteBatch spriteBatch){
        super.draw(spriteBatch);
    }
    
}
