/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import static genuini.handlers.PhysicsVariables.PPM;

/**
 *
 * @author Adrien
 */
public class PreferencesManager {
    Preferences data = Gdx.app.getPreferences("game_data");
    public PreferencesManager(){}

    
    
    public void setPositionX(float posX){
        data.putFloat("player_xPosition", posX);
    }
     
    public float getPositionX(){
        return data.getFloat("player_xPosition", 160f/PPM);
    }
    
    public void setPositionY(float posY){
        data.putFloat("player_YPosition", posY);
    }
    
    public float getPositionY(){
        return data.getFloat("player_YPosition", 200f/PPM);
    }
    
    public void setBook(boolean active){
        data.putBoolean("book_activated", active);
    }
     
    public boolean getBook(){
        return data.getBoolean("book_activated", false);
    }

    public void save() {
        data.flush();
    }
    public void reset(){
        //System.out.println("DEBBB"+Gdx.files.external(".prefs/game_data").exists());
    }

    public void setChallenge(boolean valid) {
        data.putBoolean("challengeValid", valid);
    }
    
    public boolean getChallenge(){
        return data.getBoolean("challengeValid", false);
    }
}
