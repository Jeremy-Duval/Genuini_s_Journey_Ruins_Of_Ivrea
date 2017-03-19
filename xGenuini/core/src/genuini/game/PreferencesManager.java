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
    
    private final Vector2 initialPosition;
    
    public PreferencesManager(){
        initialPosition=new Vector2(10f,5f);
    }

    /*
    public void setInitialPosition(Vector2 initialPosition){
        this.initialPosition=initialPosition;
    }*/
    
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
    
    public void setProgression(int milestoneNum) {
        data.putInteger("progression",  milestoneNum);
    }
    
    
    public int getProgression(){
        return data.getInteger("progression", 0);
    }
    
    public void setLife(int life){
        data.putInteger("life", life);
    }
    
    public int getLife(){
        return data.getInteger("life",100);
    }
    
    public void setPreviousMapName(String previousMapName){
        data.putString("previousMapName", previousMapName);
    }
    
    public String getPreviousMapName(){
        return data.getString("previousMapName", "village");
    }
    
    public void setCurrentMapName(String currentMapName){
        data.putString("currentMapName", currentMapName);
    }
    
    public String getCurrentMapName(){
        return data.getString("currentMapName", "village");
    }
    
    public void setSpawnName(String spawnName){
        data.putString("spawnName", spawnName);
    }
    
    public String getSpawnName(){
        return data.getString("spawnName", "initial_spawn");
    }
    
    public void setNewGame(boolean newGame){
        data.putBoolean("newGame", newGame);
    }
    
    public boolean getNewGame(){
        return data.getBoolean("newGame", true);
    }
    
    public void save(){
        data.flush();
    }
    
    public void save(float posX, float posY, int life) {
        data.putFloat("player_xPosition", posX);
        data.putFloat("player_YPosition", posY);
        data.putInteger("life", life);
        data.putString("previousMapName", data.getString("currentMapName"));
        data.putBoolean("newGame", false);
        data.flush();
    }
    
    public void reset(){
        data.putFloat("player_xPosition", initialPosition.x);
        data.putFloat("player_YPosition", initialPosition.y);
        data.putString("previousMapName", "village");
        data.putString("currentMapName", "village");
        data.putString("spawnName", "initial_spawn");
        data.putInteger("progression", 0);
        data.putInteger("life", 100);
        data.putBoolean("newGame", true);
        data.flush();
    }
    
    
    
}
