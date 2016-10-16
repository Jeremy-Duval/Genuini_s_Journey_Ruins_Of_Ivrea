package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import test_jeu.Test_Jeu;

/**
 * Classe de depart de la version bureau d'un projet libGDX
 * @author jeremy
 * @version 1.0
 */

public class DesktopLauncher {
    /**
     * Classe main contenant l'initialisation de la fenetre
     * @param arg 
     * @author jeremy
     * @version 1.0
     */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.width = 1024;
                config.height = 624;
                config.title = "Test de mouvements";
                //new LwjglApplication(new MyGdxGame(), config);
		new LwjglApplication(new Test_Jeu(), config);
	}
}
