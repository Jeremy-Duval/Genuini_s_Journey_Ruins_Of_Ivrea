/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.world;

import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Adrien
 */
public class ScenarioVariables {
    public static final int DOUBLE_JUMP=1;
    public static final int TURRET=2;
    public static final int FIREBALL=3;
    public static final int GRAVITY=4;
    public static Map map = new HashMap();
    public void mapScenario(){
        Array st = new Array();
        st.add("gravity");
    }
}
