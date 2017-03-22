/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import genuini.screens.AbstractScreen;
import genuini.screens.GameScreen;
import genuini.screens.DeathMenu;
import genuini.screens.LoadingScreen;
import genuini.screens.MainMenu;
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
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen() {};
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
    },
    
    LOAD {
        @Override
        public AbstractScreen getScreen(Object... params) {
            return new LoadingScreen() {};
        }
    };

 
    public abstract AbstractScreen getScreen(Object... params);
}