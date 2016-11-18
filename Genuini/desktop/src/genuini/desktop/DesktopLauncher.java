package genuini.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import genuini.game.BaseGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "Genuini";
                config.useGL30 = true;
                config.width = 1280;
                config.height = 720;
                config.backgroundFPS = config.backgroundFPS = 60;
                LwjglApplication lwjglApplication = new LwjglApplication(new BaseGame(), config);
	}
}
