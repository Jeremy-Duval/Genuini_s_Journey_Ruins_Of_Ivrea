/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import static genuini.world.PhysicsVariables.PPM;
import java.util.ArrayList;

/**
 *
 * @author Adrien
 */
public class TextManager {
    private  SpriteBatch spriteBatchHandle;

    private  ArrayList<CharSequence> tuto;
    
    private BitmapFont bmf;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private String textToDisplay;
    private Vector2 position;
    
    
    public TextManager(){
         //define the font
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/lobster-two/LobsterTwo-Regular.otf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30; //set the font size: 12px
        parameter.color = Color.BLACK;
        this.updateText();
    }

    public void setSize(int size){
        parameter.size = size;   
        updateText();
    }
    
    public void setColor(Color c){
        parameter.color = c;
        updateText();
    }
    
    public void setText(String s){
        textToDisplay = s;
        updateText();
    }
    
    private void updateText(){
        bmf = generator.generateFont(parameter);
    }
    
    public void setPosition(Vector2 v){
        position = v;
    }
    
    public void draw(SpriteBatch batch){
            bmf.draw(batch, textToDisplay, position.x*PPM, position.y*PPM);      
    }
    
     
}
