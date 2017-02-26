/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import genuini.screens.AbstractScreen;
import genuini.screens.GameScreen;
import genuini.screens.DeathMenu;
import genuini.screens.MainMenu;
import genuini.screens.SpellBookMenu;
import genuini.screens.VictoryMenu;

/**
 *
 * @author Adrien Techer
 */
public enum ScreenEnum {
 
    MAIN_MENU {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new MainMenu();
        }
    },
    
    GAME {
        @Override
        public GameScreen getScreen(Object... params) {
            return new GameScreen() {};
        }
    },
    
    SPELLBOOK {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new SpellBookMenu() {};
        }
    },
    
    DEATH {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new DeathMenu() {};
        }
    },
    
    VICTORY {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new VictoryMenu() {};
        }
    };
 
    public abstract AbstractScreen getScreen(Object... params);
}