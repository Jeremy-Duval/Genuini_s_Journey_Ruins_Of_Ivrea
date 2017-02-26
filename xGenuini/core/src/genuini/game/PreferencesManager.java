package genuini.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;

/**
 * Deals with setting,returning,reseting and saving the different preferences values.
 * @author Adrien Techer
 */
public class PreferencesManager {
    Preferences data = Gdx.app.getPreferences("game_data");
    
    private Vector2 initialPosition;
    
    public PreferencesManager(){}

    public void setInitialPosition(Vector2 initialPosition){
        this.initialPosition=initialPosition;
    }
    
    public void setPositionX(float posX){
        data.putFloat("player_xPosition", posX);
    }
     
    public float getPositionX(){
        return data.getFloat("player_xPosition", initialPosition.x);
    }
    
    public void setPositionY(float posY){
        data.putFloat("player_YPosition", posY);
    }
    
    public float getPositionY(){
        return data.getFloat("player_YPosition", initialPosition.y);
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
    
    public void setMapName(String mapName){
        data.putString("mapName", mapName);
    }
    
    public String getMapName(){
        return data.getString("mapName", "village");
    }
    
    public void setSpawnName(String spawnName){
        data.putString("spawnName", spawnName);
    }
    
    public String getSpawnName(){
        return data.getString("spawnName", "spawn");
    }
    
    public void save() {
        data.flush();
    }
    
    
    public void reset(){
        data.putFloat("player_xPosition", initialPosition.x);
        data.putFloat("player_YPosition", initialPosition.y);
        data.putString("mapName", "village");
        data.putString("spawnName", "spawn");
        data.putBoolean("book_activated", false);
        data.putBoolean("challengeValid", false);
        data.putInteger("life", 100);
    }

    
}
