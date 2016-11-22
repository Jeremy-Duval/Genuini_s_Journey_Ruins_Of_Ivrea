/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.handlers;

/**
 *
 * @author Adrien
 */
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;


public class InputProcessor extends InputAdapter {
	
    @Override
    public boolean mouseMoved(int x, int y) {
            Input.x = x;
            Input.y = y;
            return true;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
            Input.x = x;
            Input.y = y;
            Input.down = true;
            return true;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
            Input.x = x;
            Input.y = y;
            Input.down = true;
            return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
            Input.x = x;
            Input.y = y;
            Input.down = false;
            return true;
    }

    @Override
    public boolean keyDown(int k) {
            if(k == Keys.Z) Input.setKey(Input.UP, true);
            if(k == Keys.Q) Input.setKey(Input.LEFT, true);
            if(k == Keys.D) Input.setKey(Input.RIGHT, true);
            if(k == Keys.SPACE) Input.setKey(Input.SPACE, true); 
            return true;
    }

    @Override
    public boolean keyUp(int k) {
            if(k == Keys.Z) Input.setKey(Input.UP, false);
            if(k == Keys.Q) Input.setKey(Input.LEFT, false);
            if(k == Keys.D) Input.setKey(Input.RIGHT, false);
            if(k == Keys.SPACE) Input.setKey(Input.SPACE, false); 
            return true;
    }
    
}
