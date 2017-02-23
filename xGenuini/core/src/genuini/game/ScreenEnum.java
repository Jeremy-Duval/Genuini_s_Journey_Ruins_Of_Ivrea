/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import genuini.screens.AbstractScreen;
import genuini.screens.GameScreen;
import genuini.screens.DeathScreen;
import genuini.screens.MainMenuScreen;
import genuini.screens.SpellBookScreen;
import genuini.screens.VictoryScreen;

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
    },
    
    DEATH {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new DeathScreen() {};
        }
    },
    
    VICTORY {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new VictoryScreen() {};
        }
    };
 
    public abstract AbstractScreen getScreen(Object... params);
}