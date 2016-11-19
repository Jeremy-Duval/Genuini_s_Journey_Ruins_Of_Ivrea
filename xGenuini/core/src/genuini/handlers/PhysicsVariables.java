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
public class PhysicsVariables {
    // pixels per meter
	public static final float PPM = 100;
	
    // collision bit filters
    public static final short BIT_PLAYER = 2;
    public static final short BIT_RED_BLOCK = 4;
    public static final short BIT_GREEN_BLOCK = 8;
    public static final short BIT_BLUE_BLOCK = 16;
    public static final short BIT_CRYSTAL = 32;
    public static final short BIT_SPIKE = 64;

    public static final short BIT_TOP_BLOCK = 2;
    public static final short BIT_BOTTOM_BLOCK = 4;
    public static final short BIT_TOP_PLATFORM = 8;
    public static final short BIT_BOTTOM_PLATFORM = 16;
}
