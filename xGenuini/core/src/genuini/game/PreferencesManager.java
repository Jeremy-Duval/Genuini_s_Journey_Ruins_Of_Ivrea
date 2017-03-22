package genuini.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import java.util.HashMap;
import java.util.Map;

/**
 * Deals with setting,returning,reseting and saving the different preferences values.
 * @author Adrien Techer
 */
public class PreferencesManager {
   
    Preferences gameData = Gdx.app.getPreferences("game_data");
    Preferences playerData = Gdx.app.getPreferences("player_data");
    Preferences turretsData = Gdx.app.getPreferences("turrets_data");
    
    private final Vector2 initialPosition;
    private String lastSkill;
    
    public PreferencesManager(){
        initialPosition=new Vector2(10f,5f);
        lastSkill=getLastSkill();
    }
    
    
    
    /*Location and general data */
    
    public void setPositionX(float posX){
        gameData.putFloat("player_xPosition", posX);
    }
     
    public float getPositionX(){
        return gameData.getFloat("player_xPosition", initialPosition.x);    
    }
    
    public void setPositionY(float posY){
        gameData.putFloat("player_YPosition", posY);
    }
    
    public float getPositionY(){
        return gameData.getFloat("player_YPosition", initialPosition.y);
    }
    
    public void setPreviousMapName(String previousMapName){
        gameData.putString("previousMapName", previousMapName);
    }
    
    public String getPreviousMapName(){
        return gameData.getString("previousMapName", "village");
    }
    
    public void setCurrentMapName(String currentMapName){
        gameData.putString("currentMapName", currentMapName);
    }
    
    public String getCurrentMapName(){
        return gameData.getString("currentMapName", "village");
    }
    
    public void setSpawnName(String spawnName){
        gameData.putString("spawnName", spawnName);
    }
    
    public String getSpawnName(){
        return gameData.getString("spawnName", "initial_spawn");
    }
    
    public void setNewGame(boolean newGame){
        gameData.putBoolean("newGame", newGame);
    }
    
    public boolean getNewGame(){
        return gameData.getBoolean("newGame", true);
    }
    
    
    public Map<String, ?> getGameData(){
        return gameData.get();
    }
    
    
    
    
    /* Life and Skills */
    
    
    public void giveSkill(String skillName) {
        playerData.putBoolean(skillName, true);
        lastSkill=skillName;
    }
    
    public boolean hasSkill(String skillName) {
        return playerData.getBoolean(skillName, false);
    }

    public String getLastSkill(){
        return playerData.getString("lastSkill","none");
    }
    
    
    public void setLife(int life){
        playerData.putInteger("life", life);
    }
    
    public int getLife(){
        return playerData.getInteger("life",100);
    }
    
    public Map<String, ?> getPlayerData(){
        return playerData.get();
    }
    
    /* Turrets */
    
    public void deactivateTurret(int id){
        turretsData.putBoolean("turret_"+String.valueOf(id), false);
    }
    
    public boolean isTurretActive(int id){
        return turretsData.getBoolean("turret_"+String.valueOf(id),true);
    }
    
    
    
    public void save(){
        gameData.flush();
        playerData.flush();
        turretsData.flush();
    }
    
    public void save(float posX, float posY, int life) {
        gameData.putFloat("player_xPosition", posX);
        gameData.putFloat("player_YPosition", posY);  
        gameData.putString("previousMapName", gameData.getString("currentMapName"));
        gameData.putBoolean("newGame", false);

        
        playerData.putInteger("life", life);
        playerData.putString("lastSkill", lastSkill);
        
        save();
    }
    
    public void reset(){
        gameData.putFloat("player_xPosition", initialPosition.x);
        gameData.putFloat("player_YPosition", initialPosition.y);
        gameData.putString("previousMapName", "village");
        gameData.putString("currentMapName", "village");
        gameData.putString("spawnName", "initial_spawn");
        gameData.putBoolean("newGame", true);
        
        playerData.putInteger("life", 100);
        playerData.putString("lastSkill", "none");
        for(String s : playerData.get().keySet()){
            if(!(s.equals("life") || s.equals("lastSkill"))){
                playerData.putBoolean(s, false);
            }
            
        }
        
        
        for(String s : turretsData.get().keySet()){
            turretsData.putBoolean(s, false);
        }
      
        save();
    }
    
    public void test(){
        for(String s : playerData.get().keySet()){
            System.err.println(s);
        }
    }
            
}
