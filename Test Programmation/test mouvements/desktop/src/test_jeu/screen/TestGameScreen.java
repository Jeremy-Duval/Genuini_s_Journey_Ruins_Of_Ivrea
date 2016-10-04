/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_jeu.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import static java.lang.Thread.sleep;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.games.input.Component;
import test_jeu.Test_Jeu;

/**
 * Classe servant à definir notre ecran. Implemente la classe Screen de LibGDX
 *
 * @since 1.0
 * @author jeremy
 */
public class TestGameScreen implements Screen {

    private SpriteBatch batch;

    /*Perso*/
    private Texture texture_perso;
    private Map<String, Texture> textures_perso = new TreeMap();
    private Vector2 perso_pos;
    boolean pied = true;

    private enum Direction {

        debut,
        gauche,
        droit,
        stop_gauche,
        stop_droit,
    }

    Direction direction = Direction.debut;
    
    
    private int vitesse_horizontale = 10;
    private int vitesse_verticale = 5;
    private int hauteur_saut = 25;
    boolean phase_montante = true;
    private Vector2 perso_pos_prec;
    private boolean saut_active = false;

    /*fond*/
    private Texture texture_fond;

    /**
     * Constructeur de l'ecran.
     *
     * @param application : Test_Jeu
     * @since 1.0
     * @author jeremy
     */
    public TestGameScreen(Test_Jeu application) {
        batch = new SpriteBatch();
        texture_fond = new Texture("img/landscape.png");
        texture_perso = new Texture("img/gd.jpg");
        textures_perso.put("haut", new Texture("img/gd.jpg"));
        textures_perso.put("droit", new Texture("img/gpd.jpg"));
        textures_perso.put("droit_droit", new Texture("img/gpdd.jpg"));
        textures_perso.put("droit_gauche", new Texture("img/gpdg.jpg"));
        textures_perso.put("gauche", new Texture("img/gpg.jpg"));
        textures_perso.put("gauche_droit", new Texture("img/gpgd.jpg"));
        textures_perso.put("gauche_gauche", new Texture("img/gpgg.png"));
        perso_pos = new Vector2(100, 100);
        perso_pos_prec = new Vector2(100, 100);
    }

    /**
     * methode override servant à afficher l'ecran
     *
     * @since 1.0
     * @author jeremy
     */
    @Override
    public void show() {

    }

    /**
     * methode override servant au rendu de l'ecran
     *
     * @since 1.0
     * @author jeremy
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float) 0.5, 1, 0, 1);//couleur de fond
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);//netoie l'écran en recoloriant

        skin();

        batch.begin();//début la zone de dessin

        //dessine le fond
        batch.draw(texture_fond, 0, 0, 1024, 624);

        //batch.draw(texture_perso, 100, 100, 64, 64);//dessine le perso à la position 100,100 de taille 64x64
        batch.draw(texture_perso, perso_pos.x, perso_pos.y);

        batch.end();//termine la zone de dessin
        processInput();
        if(saut_active){
            saut();
        }
        
        try {
            sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestGameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gère les skins à afficher.
     *
     * @since 1.0
     * @author jeremy
     */
    private void skin() {

        if (direction == Direction.debut) {
            texture_perso = textures_perso.get("haut");
        } else if (direction == Direction.gauche) {
            if (pied) {
                texture_perso = textures_perso.get("gauche_gauche");
            } else {
                texture_perso = textures_perso.get("gauche_droit");
            }
            pied = !pied;
        } else if (direction == Direction.droit) {
            if (pied) {
                texture_perso = textures_perso.get("droit_gauche");
            } else {
                texture_perso = textures_perso.get("droit_droit");
            }
            pied = !pied;
        } else if (direction == Direction.stop_droit) {
            texture_perso = textures_perso.get("droit");
        } else if (direction == Direction.stop_gauche) {
            texture_perso = textures_perso.get("gauche");
        } else {
            System.out.println("erreur");
        }
    }

    /**
     * Gère les évènements (claviers, souris...)
     *
     * @since 1.0
     * @author jeremy
     */
    private void processInput(){

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            direction = Direction.droit;
            perso_pos.x = perso_pos.x + 10;
            //tester si saut (apppui de 2 touches en même temps)
            
            
        } else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            direction = Direction.gauche;
            perso_pos.x = perso_pos.x - 10;
            //tester si saut (apppui de 2 touches en même temps)
            
            
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {
            //mettre deux état : saut droit, saut gauche : direction = Direction.?;
            saut_active = true;
        } else {//si on s'arrête
            if (direction == Direction.gauche) {
                direction = Direction.stop_gauche;
            } else if (direction == Direction.droit) {
                direction = Direction.stop_droit;
            }
        }
    }
    
    /**
     * Effectue les saut du perso
     *
     * @since 1.0
     * @author jeremy
     */
    private void saut(){
        
        if(perso_pos.y >99){//à modif (hitbox sol)
            if(phase_montante){
                if(direction == Direction.droit){//à changer avec saut droit
                    perso_pos.x = perso_pos.x + vitesse_horizontale;
                } else if(direction == Direction.gauche){
                    perso_pos.x = perso_pos.x - vitesse_horizontale;
                } else {
                    //on reste sur place
                }
                perso_pos.y = perso_pos.y + vitesse_verticale;
                if(perso_pos.y >= hauteur_saut+perso_pos_prec.y){
                    phase_montante = !phase_montante;
                }
            } else {
                if(direction == Direction.droit){//à changer avec saut droit
                    perso_pos.x = perso_pos.x + vitesse_horizontale;
                } else if(direction == Direction.gauche){
                    perso_pos.x = perso_pos.x - vitesse_horizontale;
                } else {
                    //on reste sur place
                }
                perso_pos.y = perso_pos.y - vitesse_verticale;
            }
        } else {
            phase_montante = true;
            
            //tant que pas de gestion de hitbox
            perso_pos.y = 100;
            
            perso_pos_prec.x = perso_pos.x;
            perso_pos_prec.y = perso_pos.y;
            saut_active = false;
        }
    }

    /**
     * methode override servant au redimentionnement l'ecran
     *
     * @since 1.0
     * @author jeremy
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * methode override servant à mettre en pause l'ecran
     *
     * @since 1.0
     * @author jeremy
     */
    @Override
    public void pause() {

    }

    /**
     * methode override servant à mettre fin à une pause
     *
     * @since 1.0
     * @author jeremy
     */
    @Override
    public void resume() {

    }

    /**
     * methode override servant à cacher l'ecran
     *
     * @since 1.0
     * @author jeremy
     */
    @Override
    public void hide() {

    }

    /**
     * methode override servant à quitter l'ecran
     *
     * @since 1.0
     * @author jeremy
     */
    @Override
    public void dispose() {

    }

}
