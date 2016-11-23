/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.screens;

import com.badlogic.gdx.Game;

/**
 *
 * @author Valentin
 */
public class Start extends Game{

    @Override
    public void create() {
        setScreen(new Menu(this));
    }
    
    @Override
	public void render () {
		super.render();
                
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
