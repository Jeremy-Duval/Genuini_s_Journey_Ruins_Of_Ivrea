package genuini.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import genuini.main.MainGame;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;

/**
 * Class used to create a button
 *
 * @author Adrien Techer
 */
public class Button extends StaticElements {

    private final Texture buttonTexture;
    private final Texture buttonPressedTexture;
    private boolean pressed;
    private final String linkedObjectType;
    private final int linkedObjectID;

    /**
     * Button class constructor
     *
     * @param screen the screen to which the sprite belongs
     * @param body the sprite's body
     * @param ID the ID of the map object
     * @param linkedObjectID the ID of the linked map object
     * @param linkedObjectType the object type/class of the linked map object
     */
    public Button(GameScreen screen, Body body, int ID, int linkedObjectID, String linkedObjectType) {
        super(screen, body, ID);
        this.linkedObjectType = linkedObjectType;
        buttonTexture = MainGame.contentManager.getTexture("buttonRed");
        buttonPressedTexture = MainGame.contentManager.getTexture("buttonRed_pressed");

        sprite = new Sprite(buttonTexture);
        sprite.setPosition(position.x, position.y);
        this.linkedObjectID = linkedObjectID;
        pressed = false;
        createFilter();
    }

    /**
     * Changes the sprite's texture and the button's status to pressed
     */
    public void press() {
        sprite.setRegion(buttonPressedTexture);
        pressed = true;
    }

    @Override
    /**
     * @inheritDoc
     */
    public final void createFilter() {
        filter.categoryBits = BIT_TERRAIN;
        filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
        body.getFixtureList().first().setFilterData(filter);
        body.getFixtureList().first().setUserData("button");
    }

    /**
     *
     * @return true if button is pressed, false otherwise
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     *
     * @return the linked object type
     */
    public String getLinkedObjectType() {
        return linkedObjectType;
    }

    /**
     *
     * @return the linked object ID
     */
    public int getLinkedObjectID() {
        return linkedObjectID;
    }

}
