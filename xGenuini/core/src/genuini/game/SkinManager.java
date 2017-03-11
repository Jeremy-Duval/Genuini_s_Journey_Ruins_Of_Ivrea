/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 *
 * @author Adrien
 */
public class SkinManager {

    private Skin skin;
    private final BitmapFont fontBlack;
    private final BitmapFont fontWhite;
    private final Texture textureBookButton;

    public SkinManager() {
        textureBookButton = new Texture("img/book/redbook.png");
        //define the font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/grundchift.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30; //set the font size: 12px
        parameter.color = Color.BLACK; //set the color size
        fontBlack = generator.generateFont(parameter); //set the font size: 12px
        parameter.size = 24;
        parameter.color = Color.WHITE;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        fontWhite = generator.generateFont(parameter);
        generator.dispose(); // free memory space
        skin = new Skin();
        skin.add("default", fontBlack);
    }
    
    /**
     * Create a white button by default
     * @param width : text's width
     * @param height : text's height
     * @return 
     */
    public Skin createButtonSkin(int width, int height) {
        //text's creation 
        this.textSkin(Color.WHITE, false, width, height);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.WHITE);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }
    
    /**
     * Create a button with parameters and the dark backgroud by default 
     * @param width : text's width
     * @param height : text's height
     * @param color : text's color
     * @param dark : 1 if the color is dark ; 0 if it's light
     * @return 
     */
    public Skin createButtonSkin(int width, int height, Color color, boolean dark) {
        //text's creation 
        this.textSkin(color, dark, width, height);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.WHITE);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }
    
    /**
     * Create a button with parameters 
     * @param width : text's width
     * @param height : text's height
     * @param text_color : text's color
     * @param dark : 1 if the color is dark ; 0 if it's light
     * @param up_color : button's color when it is up
     * @param down_color : button's color when it is down
     * @param checked_color : button's color when it is checked
     * @param over_color : button's color when it is over
     * @return 
     */
    public Skin createButtonSkin(int width, int height, Color text_color, boolean dark,
            Color up_color, Color down_color, Color checked_color, Color over_color) {
        //text's creation 
        this.textSkin(text_color, dark, width, height);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", up_color);
        textButtonStyle.down = skin.newDrawable("background", down_color);
        textButtonStyle.checked = skin.newDrawable("background", checked_color);
        textButtonStyle.over = skin.newDrawable("background", over_color);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }
    
    /**
     * Create a text with parameters 
     * @param text_color : text's color
     * @param width : text's width
     * @param height : text's height 
     * @param dark : 1 if the color is dark ; 0 if it's light
     * @param background_color : the button's backgroud color
     * @param focusedBackground_color : the button's focused backgroud color
     * @param cursor_color : the button's cursor color
     * @param cursor_width : : the button's cursor width color
     * @return 
     */
     /*public Skin ButtonSkin(Color text_color, boolean dark, float width, float height, 
             Color background_color, Color focusedBackground_color, Color cursor_color, float cursor_width){
         
     //text's creation 
        this.textSkin(text_color, dark, width, height);
        
     return skin;
     }*/

    /**
     * Create a skin whith the parameter color
     *
     * @param color : text's color
     * @param dark : 1 if the color is dark ; 0 if it's light
     * @param width : text's width
     * @param height : text's height
     * @return skin : a Skin type
     */
    public Skin textSkin(Color color, boolean dark, float width, float height) {
        if (dark) {
            skin.add("default", fontBlack);
        } else {
            skin.add("default", fontWhite);
        }

        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGB888);
        pixmap.setColor(color);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        return skin;
    }

    
     
     
     
    public Skin whiteTextSkin(float width, float height) {
        //Create a font
        skin.add("default", fontWhite);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));

        //Create a button style
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.newDrawable("background", Color.CLEAR);
        textFieldStyle.focusedBackground = skin.newDrawable("background", Color.CLEAR);
        textFieldStyle.cursor = skin.newDrawable("background", Color.DARK_GRAY);
        textFieldStyle.cursor.setMinWidth(1f);
        textFieldStyle.font = skin.getFont("default");
        skin.add("default", textFieldStyle);
        return skin;
    }

    public void dispose() {
        skin.dispose();
    }
}
