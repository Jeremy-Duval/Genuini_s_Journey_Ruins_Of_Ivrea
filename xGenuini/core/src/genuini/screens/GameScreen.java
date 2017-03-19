/**
 *
 * @author Adrien
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import genuini.entities.AccessPoint;
import genuini.entities.Genuini;
import genuini.entities.LivingBeings;
import genuini.entities.MobSpawnPoint;
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
import static genuini.world.PhysicsVariables.GRAVITY;
import genuini.world.ScenarioVariables;
import genuini.world.WorldManager;

public class GameScreen extends AbstractScreen {

    private final boolean debug = false;

    private BoundedCamera b2dCam;
    private Box2DDebugRenderer b2dr;
    private final BoundedCamera cam;

    private final Genuini genuini;

    private final World world;

    private final ContactHandler contactManager;

    private TiledMap map;
    private OrthogonalTiledMapRenderer tmr;


    private TextButton menuButton;

    private Table table;
    private Label lifePointsLabel;

    private final WorldManager worldManager;
    //private final String mapName;
    private boolean changeScreen;
    

    private final TextManager textManager;

    public GameScreen() {
        super();
        if (!MainGame.contentManager.getMusic("gameMusic").isPlaying()) {
            //MainGame.contentManager.getMusic("gameMusic").play();
        }
        
        
        
        world = new World(new Vector2(0, GRAVITY), true); //Create world, any inactive bodies are asleep (not calculated)
        contactManager = new ContactHandler();
        world.setContactListener(contactManager);//

        cam = new BoundedCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);

        worldManager = new WorldManager(this, prefs.getCurrentMapName());
        
                //prefs.setInitialPosition(accessPoint.getPosition());
        if (prefs.getPreviousMapName().equals(prefs.getCurrentMapName())) {
            prefs.setPositionX(prefs.getPositionX());
            prefs.setPositionY(prefs.getPositionY());
        } else {
            for (AccessPoint accessPoint : worldManager.getAccessPoints()) {
                if (accessPoint.getName().equals(prefs.getSpawnName())) {
                    prefs.setPositionX(accessPoint.getPosition().x);
                    prefs.setPositionY(accessPoint.getPosition().y);
                    break;
                }
            }
        }
       

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
        
        changeScreen=false;
        
        textManager = new TextManager(this);
        textManager.setSize(30);
        textManager.setColor(Color.FIREBRICK);
        
        if(prefs.getNewGame()){
            textManager.playTutorial();
        }
        if(prefs.getProgression()==ScenarioVariables.GRAVITY){
            textManager.displayText("Press G to change the direction of the gravitional field",5000);
        }
        //textManager.playTutorial();
        
        //arduinoInstance.serialEventString(new SerialPortEvent());
        
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.save(genuini.getPosition().x, genuini.getPosition().y, genuini.getLife());

                MainGame.contentManager.getMusic("gameMusic").pause();
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
            }
        });


    }

    @Override
    public void buildStage() {
        super.buildStage();
        menuButton = new TextButton("Menu", skinManager.createButtonSkin(((int) (worldManager.getTileSize() * 1.6f)), (int) worldManager.getTileSize() / 2));
        menuButton.setPosition(V_WIDTH - worldManager.getTileSize() * 1.6f, worldManager.getTileSize() * 3);

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

        Label lifeLabel = new Label("Life :", skinManager.textFieldSkin((int) (worldManager.getTileSize() * 1.6f), (int) worldManager.getTileSize() / 2, Color.WHITE, false, Color.CLEAR, Color.CLEAR, Color.DARK_GRAY, 1f), "default", Color.WHITE);
        table.add(lifeLabel).width(70);
        lifePointsLabel = new Label(String.valueOf(genuini.getLife()), skinManager.textFieldSkin((int) (worldManager.getTileSize() * 1.6f), (int) worldManager.getTileSize() / 2, Color.WHITE, false, Color.CLEAR, Color.CLEAR, Color.DARK_GRAY, 1f), "default", Color.WHITE);
        table.add(lifePointsLabel).width(80);
        // Add widgets to the table here.
    }

    public void update(float delta) {
        //World step
        world.step(MainGame.STEP, 8, 3);

        //Check if world is not stepping
        if (!world.isLocked()) {
            
            //Update player & sprites
            genuini.update(delta);
            for (Sprites sprite : worldManager.getSprites()) {
                sprite.update(delta);
            }
            for(MobSpawnPoint mobSpawnPoint : worldManager.getMobSpawnPoints()){
                mobSpawnPoint.update(delta);
            }
            if(textManager.isActive()){
                textManager.update(delta);
            }
            
            worldManager.handleInput();
            worldManager.handleContact();
            worldManager.handleArea();
            
        }
        worldManager.destroyBodies();
        
        
        //Get player position for camera
        float player_pos_x = prefs.getPositionX();
        float player_pos_y = prefs.getPositionY();
        if (genuini.getState() != LivingBeings.State.DEAD) {
            player_pos_x = genuini.getPosition().x;
            player_pos_y = genuini.getPosition().y;
        }

        //Camera follows player
        cam.setPosition(player_pos_x * PPM + MainGame.V_WIDTH / 4, player_pos_y * PPM);
        cam.update();

        //Update of box2d debug camera
        if (debug) {
            b2dCam.setPosition(player_pos_x + MainGame.V_WIDTH / 4 / PPM, player_pos_y);
            b2dCam.update();
        }
        
        if (changeScreen) {
            ScreenManager.getInstance().showScreen(ScreenEnum.LOAD);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        batch.setProjectionMatrix(cam.combined);

        //Render tiled map
        tmr.setView(cam);
        tmr.render();

        /**
         * ** Beggining of drawing area ***
         */
        batch.begin();

        //draw player
        genuini.draw(batch);

        //draw sprites
        for (Sprites sprite : worldManager.getSprites()) {
            sprite.draw(batch);
        }
        
        //draw mobs
        for(MobSpawnPoint mobSpawnPoint : worldManager.getMobSpawnPoints()){
                mobSpawnPoint.draw(batch);
        }
        
        if(textManager.isActive()){
          textManager.draw(batch);       
        }
        
        batch.end();
        /**
         * ** End of drawing area ***
         */

        //Box2d debug rendering
        if (debug) {
            b2dr.render(world, b2dCam.combined);
        }

        //Stage rendering
        stage.act(delta);
        stage.draw();

    }


    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        map.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public OrthogonalTiledMapRenderer getTMR() {
        return tmr;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public void setTMR(OrthogonalTiledMapRenderer orthogonalTiledMapRenderer) {
        this.tmr = orthogonalTiledMapRenderer;
    }

    public PreferencesManager getPreferences() {
        return prefs;
    }

    public ContactHandler getContactManager() {
        return contactManager;
    }

    public float getDistanceFromPlayer(Body body) {
        //return (float) (Math.sqrt(Math.pow(body.getPosition().x+,2) + (Math.pow(genuini.getBody().getWorldCenter().y-body.getWorldCenter().y,2))));
        return 1f;
    }

    public float getDistanceFromPlayer(Sprites sprite) {
        if(genuini.getLife()>0){
            return (float) (Math.sqrt(Math.pow(sprite.getBody().getPosition().x + sprite.getSprite().getWidth() / 2 / PPM - genuini.getPosition().x, 2) + (Math.pow(sprite.getBody().getPosition().y + sprite.getSprite().getHeight() / 2 / PPM - genuini.getPosition().y, 2))));
        }else{
            return 0;
        }  
    }

    public Genuini getGenuini() {
        return genuini;
    }
    
    public void setLifeText(){
        lifePointsLabel.setText(String.valueOf(genuini.getLife()));
    }

    public void changeScreen() {
        changeScreen=true;
    }
    

}
