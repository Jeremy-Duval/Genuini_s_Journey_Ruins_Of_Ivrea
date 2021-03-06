/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import com.badlogic.gdx.Screen;
import genuini.main.MainGame;
import genuini.screens.AbstractScreen;

/**
 *
 * @author Adrien
 */
public class ScreenManager{
 
    // Singleton: unique instance
    private static ScreenManager instance;
 
    // Reference to game
    private MainGame game;
    
 
    // Singleton: private constructor
    private ScreenManager() {
    }
 
    // Singleton: retrieve instance
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }
 
    // Initialization with the game class
    public void initialize(MainGame game) {
        this.game = game;
    }
 
    // Show in the game the screen which enum type is received
    public void showScreen(ScreenEnum screenEnum, Object... params) {
 
        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();
        
        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        // Show new screen
        AbstractScreen newScreen = screenEnum.getScreen(params);
        newScreen.buildStage();
        game.setScreen(newScreen);
    }

    
    

}