package genuini.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import genuini.main.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
            cfg.title = Game.TITLE;
            cfg.width = Game.V_WIDTH * Game.SCALE;
            cfg.height = Game.V_HEIGHT * Game.SCALE;
            LwjglApplication lwjglApplication = new LwjglApplication(new Game(), cfg);
		
	}
}
