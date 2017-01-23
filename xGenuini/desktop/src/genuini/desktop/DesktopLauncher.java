package genuini.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import genuini.main.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.vSyncEnabled = true;
            config.title = MainGame.TITLE;
            config.width = MainGame.V_WIDTH * MainGame.SCALE;
            config.height = MainGame.V_HEIGHT * MainGame.SCALE;
            LwjglApplication lwjglApplication = new LwjglApplication(new MainGame(), config);
		
	}
}
