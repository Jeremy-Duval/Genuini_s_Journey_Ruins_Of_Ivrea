package genuini.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import genuini.main.GenuiniGame;
import genuini.screens.Menu;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
            cfg.title = GenuiniGame.TITLE;
            cfg.width = GenuiniGame.V_WIDTH * GenuiniGame.SCALE;
            cfg.height = GenuiniGame.V_HEIGHT * GenuiniGame.SCALE;
            LwjglApplication lwjglApplication = new LwjglApplication(new GenuiniGame(), cfg);
		
	}
}
