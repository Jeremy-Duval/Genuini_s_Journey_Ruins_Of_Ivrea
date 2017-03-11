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
    
    public SkinManager(){
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
        skin.add("default",fontBlack);
    }
    
    
    
    public Skin createBookButtonSkin(int width, int height) {
        //Create a font

        skin.add("default", fontWhite);

        skin.add("textureBookButton", textureBookButton);

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("textureBookButton", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("textureBookButton", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("textureBookButton", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("textureBookButton", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        return skin;
    }
    
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
     * Create a skin whith the parameter color
     * @param color : text's color
     * @param dark  : 1 if the color is dark ; 0 if it's light
     * @param width : text's width 
     * @param height : text's height
     * @return skin : a Skin type
     */
    public Skin textSkin(Color color, boolean dark, float width, float height){
        if(dark){
            skin.add("default", fontBlack);
        } else {
            skin.add("default", fontWhite);
        }
        
        Pixmap pixmap = new Pixmap((int) width, (int) height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background", new Texture(pixmap));
        
        return skin;
    }

    public Skin blackTextSkin() {
        //Create a font
        skin.add("default", fontBlack);
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
    
    public void dispose(){
        skin.dispose();
    }
}
