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
public class Input {
    public static int x;
    public static int y;
    public static boolean down;
    public static boolean pdown;

    public static boolean[] keys;
    public static boolean[] pkeys;
    private static final int NUM_KEYS = 4;
    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int SPACE = 3;
    
    static {
            keys = new boolean[NUM_KEYS];
            pkeys = new boolean[NUM_KEYS];
    }

    public static void update() {
        pdown = down;
        for(int i = 0; i < NUM_KEYS; i++) {
                pkeys[i] = keys[i];
        }
    }

    public static boolean isDown() { return down; }
    public static boolean isPressed() { return down && !pdown; }
    public static boolean isReleased() { return !down && pdown; }

    public static void setKey(int i, boolean b) { keys[i] = b; }
    public static boolean isDown(int i) { return keys[i]; }
    public static boolean isPressed(int i) { return keys[i] && !pkeys[i]; }
}
