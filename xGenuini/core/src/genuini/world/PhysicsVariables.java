/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.world;

/**
 *
 * @author Adrien
 */
public class PhysicsVariables {
    // pixels per meter
    public static final float PPM = 100;
    
    //acceleration due to earth's gravitational field
    public static final float GRAVITY = -9.81f;
	
    // collision bit filters
    public static final short BIT_OBJECT = 2;
    public static final short BIT_PLAYER = 4;
    public static final short BIT_TERRAIN = 8;
    public static final short BIT_FIREBALL = 16;
    public static final short BIT_TURRET = 32;
}
