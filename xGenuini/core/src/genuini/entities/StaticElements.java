/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.PPM;

/**
 *
 * @author Adrien
 */
public abstract class StaticElements extends Sprites{
    private final int ID;
    final Vector2 position; 
    final Filter filter;
    
    public StaticElements(GameScreen screen, Body body, int ID) {
        super(screen);
        this.body=body;
        this.ID=ID;
        position = new Vector2(body.getPosition().x * PPM , body.getPosition().y * PPM);
        filter = new Filter();
    }

    @Override
    public void update(float delta){};
    
    public abstract void createFilter();
    
    public int getID() {
        return ID;
    }
    
}
