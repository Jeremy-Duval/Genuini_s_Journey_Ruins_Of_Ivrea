/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import com.badlogic.gdx.Gdx;

/**
 *
 * @author Adrien
 */
public class Time {
    public static double time = 1.0d;
    
    private static int defaultFPS = 60;
    
    public static void Update(){
        int actualFPS = Gdx.graphics.getFramesPerSecond();
        actualFPS = (actualFPS == 0) ? 3000 : actualFPS;
        time = (double) defaultFPS /actualFPS;
    }
}
