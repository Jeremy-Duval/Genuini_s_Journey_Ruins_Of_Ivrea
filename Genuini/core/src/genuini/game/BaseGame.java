/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import com.badlogic.gdx.Game;
import genuini.game.screens.PlayScreen;

/**
 *
 * @author Adrien
 */
public class BaseGame extends Game{

    @Override
    public void create() {
        setScreen(new PlayScreen());
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }
    
    @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }
    
    @Override
    public void pause(){
        super.pause();
        
    }
    
    
}
