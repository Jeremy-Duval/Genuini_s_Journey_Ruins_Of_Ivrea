/**
 *
 * @author Adrien
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import genuini.entities.Genuini;
import genuini.entities.Spring;
import genuini.entities.Sprites;
import genuini.game.BoundedCamera;
import genuini.game.PreferencesManager;
import genuini.world.ContactHandler;
import static genuini.world.PhysicsVariables.PPM;
import genuini.game.ScreenEnum;
import genuini.game.ScreenManager;
import genuini.game.TextManager;
import genuini.main.MainGame;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;
import static genuini.screens.AbstractScreen.arduinoInstance;
import static genuini.screens.AbstractScreen.connected;
import genuini.world.WorldManager;

public class GameScreen extends AbstractScreen{

    
    private final boolean debug = false;

    private BoundedCamera b2dCam;
    private Box2DDebugRenderer b2dr;
    private final BoundedCamera cam;

    private final Genuini genuini;

    private final World world;

    private final ContactHandler contactManager;

    private TiledMap map;
    private OrthogonalTiledMapRenderer tmr;

    private TextButton spellBookScreenButton;
    private TextButton menuButton;
    
    private Table table;
    private Label lifePointsLabel;
    
    private final WorldManager worldManager;
    
    public GameScreen() {
        super();
        if(!MainGame.contentManager.getMusic("gameMusic").isPlaying()){
            //MainGame.contentManager.getMusic("gameMusic").play();
        }

        
        world = new World(new Vector2(0, -9.81f), true); //Create world, any inactive bodies are asleep (not calculated)
        contactManager = new ContactHandler();
        world.setContactListener(contactManager);//

        cam = new BoundedCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);

        
        //set the Text batch
        TextManager.SetSpriteBatch(batch);
        
        
        worldManager = new WorldManager(this);


        
        
        /* DEBUG */
        if (debug) {
            b2dr = new Box2DDebugRenderer();
            // set up box2d cam
            b2dCam = new BoundedCamera();
            b2dCam.setToOrtho(false, MainGame.V_WIDTH / PPM, MainGame.V_HEIGHT / PPM);
            b2dCam.setBounds(0, (worldManager.getTileMapWidth() * worldManager.getTileSize()) / PPM, 0, (worldManager.getTileMapHeight() * worldManager.getTileSize()) / PPM);
        }

        //create player
        genuini = new Genuini(this);
        genuini.setLife(prefs.getLife());
        
        
        
        
        cam.setBounds(0, worldManager.getTileMapWidth() * worldManager.getTileSize(), 0, worldManager.getTileMapHeight() * worldManager.getTileSize());
        
        if (connected) {
            arduinoInstance.write("game;" + String.valueOf(genuini.getLife()));
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.setPositionX(genuini.getPosition().x);
                prefs.setPositionY(genuini.getPosition().y);
                prefs.setLife(genuini.getLife());
                prefs.save();
                
                MainGame.contentManager.getMusic("gameMusic").pause();
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
            }
        });

        spellBookScreenButton.addListener(new ClickListener() { //to know if there is a event on this button
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(connected){
                    arduinoInstance.write("game;" + String.valueOf(genuini.getLife()));
                }
                prefs.setPositionX(genuini.getPosition().x);
                prefs.setPositionY(genuini.getPosition().y);
                prefs.setLife(genuini.getLife());
                prefs.save();
                ScreenManager.getInstance().showScreen(ScreenEnum.SPELLBOOK);
            }
        });

    }

    @Override
    public void buildStage() {
        super.buildStage();
        menuButton = new TextButton("Menu", skinManager.createButtonSkin(((int) (worldManager.getTileSize() * 1.6f)), (int)worldManager.getTileSize() / 2));
        menuButton.setPosition(V_WIDTH - worldManager.getTileSize() * 1.6f, worldManager.getTileSize() * 3);

        spellBookScreenButton = new TextButton("Spellbook", skinManager.createBookButtonSkin((int)(worldManager.getTileSize() * 1.6f), (int) worldManager.getTileSize() / 2));
        spellBookScreenButton.setPosition(V_WIDTH - worldManager.getTileSize() - 20 * 1f, worldManager.getTileSize() * 1.8f);
        spellBookScreenButton.setSize(worldManager.getTileSize(), worldManager.getTileSize());
        

        if(!prefs.getBook()){
            spellBookScreenButton.setVisible(false);
        }
        stage.addActor(menuButton);
        stage.addActor(spellBookScreenButton);

        /* HUD creation */
        table = new Table();
        stage.addActor(table);
        table.setSize(V_WIDTH - 1, V_HEIGHT / 8);
        table.setPosition(1, (7 * V_HEIGHT / 8) - 1);
        table.right();
        // table.align(Align.right | Align.bottom);
        if (debug) {
            table.debug();// Enables debug lines for tables.
        }

        Label lifeLabel = new Label("Life :", skinManager.whiteTextSkin((int)(worldManager.getTileSize() * 1.6f), (int) worldManager.getTileSize() / 2), "default", Color.WHITE);
        table.add(lifeLabel).width(70);
        lifePointsLabel = new Label(String.valueOf(genuini.getLife()), skinManager.whiteTextSkin((int)(worldManager.getTileSize() * 1.6f), (int) worldManager.getTileSize() / 2), "default", Color.WHITE);
        table.add(lifePointsLabel).width(80);
        // Add widgets to the table here.
    }

    public void update(float delta){
        world.step(MainGame.STEP, 8, 3);
        if(!world.isLocked()){
        handleInput();
        handleContact();
        handleArea();
        
        genuini.update(delta);
        for(Sprites sprite : worldManager.getSprites()){
            sprite.update(delta);
        }
        
        }
        
        float player_pos_x=prefs.getPositionX();
        float player_pos_y=prefs.getPositionY();
        if(genuini.getState()!=Genuini.State.DEAD){
            player_pos_x = genuini.getPosition().x;
            player_pos_y = genuini.getPosition().y;
        }
        
        // camera follow player
        cam.setPosition(player_pos_x * PPM + MainGame.V_WIDTH / 4, player_pos_y * PPM);
        cam.update();
        
        
        //To write on screen
        if (debug) {
            b2dCam.setPosition(player_pos_x + MainGame.V_WIDTH / 4 / PPM, player_pos_y);
            b2dCam.update();
        }
    }
    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        batch.setProjectionMatrix(cam.combined);
        
        
        destroyBodies();

        //draw tiled map
        tmr.setView(cam);
        tmr.render();
        
        
        batch.begin();
        //draw player
        genuini.draw(batch);

        for(Sprites sprite : worldManager.getSprites()){
            sprite.draw(batch);
        }
        /*draw fireballs
        if(fireballs.size>0){
            for(Fireball f: fireballs){
                f.render(batch);
            }
        }*/
        
        batch.end();
        //genuini.draw(batch,delta);
        //To write on screen
        if (debug) {
            b2dr.render(world, b2dCam.combined);
        }

        stage.act(delta);
        stage.draw();
        
        
    }




    public void playerBounce(float impulse) {
        genuini.getBody().applyLinearImpulse(0, impulse / PPM, 0, 0, true);
        /*
        genuini.updateTexture(true);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                genuini.updateTexture(false);
            }
        },
                800
        );*/
    }


    private void targetPlayer(Body body, float speed) { 
        body.applyLinearImpulse((genuini.getPosition().x-body.getPosition().x)*speed/PPM, (genuini.getPosition().y-body.getPosition().y)*speed/PPM, 0, 0, true);
    }

    
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.Q) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            genuini.walk(Genuini.Direction.LEFT);
        }

        if (Gdx.input.isKeyPressed(Keys.D) || (Gdx.input.isKeyPressed(Keys.RIGHT))) {
            genuini.walk(Genuini.Direction.RIGHT);
        }

        if ((Gdx.input.isKeyPressed(Keys.Z) || (Gdx.input.isKeyPressed(Keys.UP))) && contactManager.playerCanJump()) {
            genuini.jump();
        }
        
        /*
        if ((Gdx.input.isKeyPressed(Keys.F))) {
            for(Turret turret : turrets){
                if(turret.isActive()){
                  turret.deactivate();
                }else{
                    turret.activate();
                }
               
            }
        }*/
        
        if ((Gdx.input.isKeyJustPressed(Keys.G))) {
            if(prefs.getChallenge()){
                if(connected){
                    arduinoInstance.write("game;" + String.valueOf(genuini.getLife()));
                }
               if(world.getGravity().y<0){
                  world.setGravity(new Vector2(0,9.81f));
                }else{
                  world.setGravity(new Vector2(0,-9.81f));
                } 
            }  
        }

    }

    public void handleContact() {

        if (contactManager.isBouncy() && contactManager.isDangerous()) {
            playerBounce(150f);
        } else if (contactManager.isBouncy()) {
            playerBounce(300f);
        }
        if (contactManager.isDangerous()) {
            genuini.changeLife(-5);
            if (connected) {
                arduinoInstance.write("game;" + String.valueOf(genuini.getLife())); //quand vie change
            }
            lifePointsLabel.setText(String.valueOf(genuini.getLife()));
            if (genuini.getLife() <= 0 && genuini.getState()!=Genuini.State.DEAD) {
                genuini.die();
            }
        }
        if(contactManager.bookActive() && !spellBookScreenButton.isVisible()){
            spellBookScreenButton.setVisible(true);
            prefs.setBook(true);
            if(connected){
                    arduinoInstance.write("book;");
            }
        }
        if(contactManager.hasWon()){
            prefs.reset();
            prefs.save();
            if(connected){
                arduinoInstance.write("victory;");
            }
            MainGame.contentManager.getMusic("gameMusic").pause();
            ScreenManager.getInstance().showScreen(ScreenEnum.VICTORY);
        }
    }
    
    public void handleArea(){
        if(contactManager.isBouncy()){
            for(Sprites sprite : worldManager.getSprites()){
                if((getDistanceFromPlayer(sprite)<0.8f)&&(sprite instanceof Spring)){
                   ((Spring)sprite).activate();    
                }
            }
        
        }
        /*
        for(Turret turret : turrets){      
            if(!turret.hasFireball() && turret.isActive()){   
                float distance_player_turret =(float) Math.sqrt(Math.pow(genuini.getPosition().x-turret.getPos().x,2) + (Math.pow(genuini.getPosition().y-turret.getPos().y,2)));
                if(distance_player_turret < 5){
                    Fireball fireball = createFireball(turret);
                    fireballs.add(fireball);
                    turret.setFireball(fireball);
                    targetPlayer(fireball.getBody(), 20);
                }
            }    
        }*/
        
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        map.dispose();
    }

    

    private void destroyBodies() {
        Array<Body> bodiesToDestroy = contactManager.getBodies();
        for(int i = 0; i < bodiesToDestroy.size; i++) {
            Body b = bodiesToDestroy.get(i);
            bodiesToDestroy.removeIndex(i);
            world.destroyBody(b);
        }
        bodiesToDestroy.clear();
    }

    
    public TiledMap getMap(){
        return map;
    }
    
    public World getWorld(){
        return world;
    }

    public OrthogonalTiledMapRenderer getTMR() {
        return tmr;
    }

    public void setMap(TiledMap map) {
        this.map = map ;
    }

    public void setTMR(OrthogonalTiledMapRenderer orthogonalTiledMapRenderer) {
        this.tmr=orthogonalTiledMapRenderer;
    }
    
    public PreferencesManager getPreferences(){
        return prefs;
    }
    
    public ContactHandler getContactManager(){
        return contactManager;
    }
    
    public float getDistanceFromPlayer(Body body){
        //return (float) (Math.sqrt(Math.pow(body.getPosition().x+,2) + (Math.pow(genuini.getBody().getWorldCenter().y-body.getWorldCenter().y,2))));
        return 1f;
    }
    
    public float getDistanceFromPlayer(Sprites sprite){
        return (float) (Math.sqrt(Math.pow(sprite.getBody().getPosition().x+sprite.getSprite().getWidth()/2/PPM-genuini.getPosition().x,2) + (Math.pow(sprite.getBody().getPosition().y+sprite.getSprite().getHeight()/2/PPM-genuini.getPosition().y,2))));
        
    }
    
}
