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
    public static final short BIT_POI = 64;
    public static final short BIT_MOB = 128;
    public static final short BIT_BOX = 256;
}
