/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.handlers;

import genuini.screens.AbstractScreen;
import genuini.screens.GameScreen;
import genuini.screens.MainMenuScreen;
import genuini.screens.SpellBookScreen;

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
    },
    
    SPELLBOOK {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new SpellBookScreen() {};
        }
    };
 
    public abstract AbstractScreen getScreen(Object... params);
}