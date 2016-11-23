/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

/**
 *
 * @author Adrien
 */
public enum ScreenEnum {
 
    MAIN_MENU {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new MainMenuScreen();
        }
    },
    
    GAME {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen() {};
        }
    };
 
    public abstract AbstractScreen getScreen(Object... params);
}