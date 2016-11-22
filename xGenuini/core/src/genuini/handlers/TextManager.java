/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Adrien
 */
public class TextManager {
    private static BitmapFont bfont = new BitmapFont();
    private static SpriteBatch spriteBatchHandle;
    
    public static void SetSpriteBatch(SpriteBatch batch){
        spriteBatchHandle = batch;
    }
    /* position
    public static void Draw(java.lang.CharSequence msg, float x_position, float y_position){
        bfont.draw(spriteBatchHandle, msg , x_position, y_position);
    }
    */
    
    public static void Draw(java.lang.CharSequence msg,OrthographicCamera camera){
        Vector3 position = new Vector3(100,20,0);
        camera.unproject(position);
        bfont.draw(spriteBatchHandle, msg , position.x,position.y);
    }
}
