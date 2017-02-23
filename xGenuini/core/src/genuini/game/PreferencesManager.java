/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 *
 * @author Adrien
 */
public class PreferencesManager {
    Preferences data = Gdx.app.getPreferences("game_data");
    
    Float init_x = 6f;
    Float init_y = 19f;
    
    public PreferencesManager(){}

    
    
    public void setPositionX(float posX){
        data.putFloat("player_xPosition", posX);
    }
     
    public float getPositionX(){
        return data.getFloat("player_xPosition", init_x);
    }
    
    public void setPositionY(float posY){
        data.putFloat("player_YPosition", posY);
    }
    
    public float getPositionY(){
        return data.getFloat("player_YPosition", init_y);
    }
    
    public void setBook(boolean active){
        data.putBoolean("book_activated", active);
    }
     
    public boolean getBook(){
        return data.getBoolean("book_activated", false);
    }
    
    public void setChallenge(boolean valid) {
        data.putBoolean("challengeValid", valid);
    }
    
    public boolean getChallenge(){
        return data.getBoolean("challengeValid", false);
    }
    
    public void setLife(int life){
        data.putInteger("life", life);
    }
    
    public int getLife(){
        return data.getInteger("life",100);
    }
    
    public void save() {
        data.flush();
    }
    public void reset(){
        data.putFloat("player_xPosition", init_x);
        data.putFloat("player_YPosition", init_y);
        data.putBoolean("book_activated", false);
        data.putBoolean("challengeValid", false);
        data.putInteger("life", 100);
    }

    
}
