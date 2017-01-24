/**
 *
 * @author Adrien
 */
package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import genuini.entities.Fireball;
import genuini.entities.Player;
import genuini.entities.Turret;
import genuini.handlers.BoundedCamera;
import genuini.handlers.ContactHandler;
import static genuini.handlers.PhysicsVariables.PPM;
import static genuini.handlers.PhysicsVariables.BIT_PLAYER;
import static genuini.handlers.PhysicsVariables.BIT_FIREBALL;
import static genuini.handlers.PhysicsVariables.BIT_TERRAIN;
import static genuini.handlers.PhysicsVariables.BIT_TURRET;

import genuini.handlers.ScreenEnum;
import genuini.handlers.ScreenManager;
import genuini.handlers.TextManager;
import genuini.main.MainGame;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;
import static genuini.screens.AbstractScreen.arduinoInstance;
import static genuini.screens.AbstractScreen.connected;

public class GameScreen extends AbstractScreen{

    
    private final boolean debug = false;

    private BoundedCamera b2dCam;
    private Box2DDebugRenderer b2dr;

    private final BoundedCamera cam;

    private Player player;

    private final World world;

    private final ContactHandler contactManager;

    private TiledMap map;
    private OrthogonalTiledMapRenderer tmr;
    private int tileMapWidth;
    private int tileMapHeight;
    private float tileSize;

    private TextButton spellBookScreenButton;
    private TextButton menuButton;


    private Table table;
    private Label lifePointsLabel;
    
    //Fire turret
    private Array<Turret> turrets;
    private Array<Fireball> fireballs;

    

    public GameScreen() {
        super();
        if(!MainGame.contentManager.getMusic("gameMusic").isPlaying()){
            MainGame.contentManager.getMusic("gameMusic").play();
        }

        
        world = new World(new Vector2(0, -9.81f), true); //Create world, any inactive bodies are asleep (not calculated)
        contactManager = new ContactHandler();
        world.setContactListener(contactManager);//

        cam = new BoundedCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        
        //set the Text batch
        TextManager.SetSpriteBatch(batch);
        
        //turrets
        turrets = new Array<Turret>();
        fireballs = new Array<Fireball>();
        
        //create tiles
        createTiles();

        super.createButtonSkin(tileSize * 1.6f, tileSize / 2);
        super.createBookButtonSkin(tileSize * 1.6f, tileSize / 2);
        super.createTextSkin();
        
        /* DEBUG */
        if (debug) {
            b2dr = new Box2DDebugRenderer();
            // set up box2d cam
            b2dCam = new BoundedCamera();
            b2dCam.setToOrtho(false, MainGame.V_WIDTH / PPM, MainGame.V_HEIGHT / PPM);
            b2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0, (tileMapHeight * tileSize) / PPM);
        }

        //create player
        createPlayer();
        cam.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);
        
        if (connected) {
            arduinoInstance.write("game;" + String.valueOf(player.getLife()));
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.setPositionX(player.getPosition().x);
                prefs.setPositionY(player.getPosition().y);
                prefs.setLife(player.getLife());
                prefs.save();
                
                MainGame.contentManager.getMusic("gameMusic").pause();
                ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
            }
        });

        spellBookScreenButton.addListener(new ClickListener() { //to know if there is a event on this button
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(connected){
                    arduinoInstance.write("game;" + String.valueOf(player.getLife()));
                }
                prefs.setPositionX(player.getPosition().x);
                prefs.setPositionY(player.getPosition().y);
                prefs.setLife(player.getLife());
                prefs.save();
                ScreenManager.getInstance().showScreen(ScreenEnum.SPELLBOOK);
            }
        });

    }

    @Override
    public void buildStage() {
        menuButton = new TextButton("Menu", skin);
        menuButton.setPosition(V_WIDTH - tileSize * 1.6f, tileSize * 3);

        spellBookScreenButton = new TextButton("Grimoire", bookButtonSkin);
        spellBookScreenButton.setPosition(V_WIDTH - tileSize - 20 * 1f, tileSize * 1.8f);
        spellBookScreenButton.setSize(tileSize, tileSize);
        

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

        Label lifeLabel = new Label("Life :", bookButtonSkin, "default", Color.WHITE);
        table.add(lifeLabel).width(70);
        lifePointsLabel = new Label(String.valueOf(player.getLife()), bookButtonSkin, "default", Color.WHITE);
        table.add(lifePointsLabel).width(80);
        // Add widgets to the table here.
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.setProjectionMatrix(cam.combined);
        world.step(delta, 7, 3);
        
        destroyBodies();
              
        
        handleInput();
        handleContact();
        handleArea();
        float player_pos_x=prefs.getPositionX();
        float player_pos_y=prefs.getPositionY();
        if(player.getStatus()!=0){
            player_pos_x = player.getPosition().x;
            player_pos_y = player.getPosition().y;
        }
         

        // camera follow player
        cam.setPosition(player_pos_x * PPM + MainGame.V_WIDTH / 4, player_pos_y * PPM);
        cam.update();

        //draw tiled map
        tmr.setView(cam);
        tmr.render();

        //draw player
        player.render(batch);
        
        //draw fireballs
        if(fireballs.size>0){
            for(Fireball f: fireballs){
                f.render(batch);
            }
        }
        
        
        //To write on screen
        if (debug) {
            b2dCam.setPosition(player_pos_x + MainGame.V_WIDTH / 4 / PPM, player_pos_y);
            b2dCam.update();
            b2dr.render(world, b2dCam.combined);
        }

        stage.act(delta);
        stage.draw();
        
        
    }

    private void playerDeath() {
        player.setStatus(0);
        prefs.reset();
        prefs.save();
        if (connected) {
            arduinoInstance.write("death;");
        }
        MainGame.contentManager.getMusic("gameMusic").stop();
        ScreenManager.getInstance().showScreen(ScreenEnum.DEATH);
    }

    /**
     * Apply upward force to player body.
     */
    private void playerJump() {
        player.getBody().applyLinearImpulse(0, 160 / PPM, 0, 0, true);
        player.updateTexture(true);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                player.updateTexture(false);
            }
        },
                600
        );
    }

    public void playerBounce(float impulse) {
        player.getBody().applyLinearImpulse(0, impulse / PPM, 0, 0, true);

        player.updateTexture(true);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
            @Override
            public void run() {
                player.updateTexture(false);
            }
        },
                800
        );
    }

    private void playerMoveLeft() {
        player.getBody().applyLinearImpulse(-5 / PPM, 0, 0, 0, true);
        player.walkLeft();
    }

    private void playerMoveRight() {
        player.getBody().applyLinearImpulse(5 / PPM, 0, 0, 0, true);
        player.walkRight();
    }
    
    private void targetPlayer(Body body, float speed) { 
        body.applyLinearImpulse((player.getPosition().x-body.getPosition().x)*speed/PPM, (player.getPosition().y-body.getPosition().y)*speed/PPM, 0, 0, true);
    }

    
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.Q) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            playerMoveLeft();
        }

        if (Gdx.input.isKeyPressed(Keys.D) || (Gdx.input.isKeyPressed(Keys.RIGHT))) {
            playerMoveRight();
        }

        if ((Gdx.input.isKeyPressed(Keys.Z) || (Gdx.input.isKeyPressed(Keys.UP))) && contactManager.playerCanJump()) {
                playerJump();
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
                    arduinoInstance.write("game;" + String.valueOf(player.getLife()));
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
            playerBounce(200f);
        } else if (contactManager.isBouncy()) {
            playerBounce(480f);
        }
        if (contactManager.isDangerous()) {
            player.changeLife(-5);
            if (connected) {
                arduinoInstance.write("game;" + String.valueOf(player.getLife())); //quand vie change
            }
            lifePointsLabel.setText(String.valueOf(player.getLife()));
            if (player.getLife() <= 0 && player.getStatus() != 0) {
                playerDeath();
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
            MainGame.contentManager.getMusic("gameMusic").pause();
            ScreenManager.getInstance().showScreen(ScreenEnum.VICTORY);
        }
    }
    
    public void handleArea(){
        for(Turret turret : turrets){      
            if(!turret.hasFireball() && turret.isActive()){   
                float distance_player_turret =(float) Math.sqrt(Math.pow(player.getPosition().x-turret.getPos().x,2) + (Math.pow(player.getPosition().y-turret.getPos().y,2)));
                if(distance_player_turret < 5){
                    Fireball fireball = createFireball(turret);
                    fireballs.add(fireball);
                    turret.setFireball(fireball);
                    targetPlayer(fireball.getBody(), 20);
                }
            }    
        }
        
    }

    @Override
    public void dispose() {
        super.dispose();
        //font.dispose();
        world.dispose();
        map.dispose();
    }

    private void createPlayer() {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        bdef.position.set(prefs.getPositionX(), prefs.getPositionY());
        bdef.type = BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        Body body = world.createBody(bdef);
        fdef.friction=2f;
        fdef.shape = shape;
        fdef.filter.categoryBits = BIT_PLAYER;
        fdef.filter.maskBits = BIT_TERRAIN | BIT_TURRET | BIT_FIREBALL;
        float bodyWidth = 22f/PPM;
        float bodyHeight = 44f/PPM;
        float feetWidth = 14f/PPM;
        float feetHeight = 14f/PPM;
        
        
        //Create player shape
        Vector2[] playerShapeVertices = new Vector2[6];
        playerShapeVertices[0] = new Vector2(-feetWidth, -bodyHeight);
        playerShapeVertices[1] = new Vector2(feetWidth, -bodyHeight);
        playerShapeVertices[2] = new Vector2(bodyWidth, -bodyHeight + feetHeight);
        playerShapeVertices[3] = new Vector2(bodyWidth, bodyHeight);
        playerShapeVertices[4] = new Vector2(-bodyWidth, bodyHeight);
        playerShapeVertices[5] = new Vector2(-bodyWidth, -bodyHeight + feetHeight);
        shape.set(playerShapeVertices);
        //shape.setAsBox(bodyWidth, bodyHeight);
        body.createFixture(fdef).setUserData("player");

        //create foot sensor
        shape.setAsBox(feetWidth, feetHeight / 2, new Vector2(0, -bodyHeight), 0);
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");

        //Create player entity
        player = new Player(body);
        player.setLife(prefs.getLife());
    }
    
    public Fireball createFireball(Turret turret){
        BodyDef bdef = new BodyDef();
        bdef.type = BodyType.DynamicBody;
        bdef.position.set(turret.getPos().x,turret.getPos().y);
        
        Body body = world.createBody(bdef);
        
        CircleShape circle = new CircleShape();
        circle.setRadius(0.15f);
        FixtureDef fd = new FixtureDef();
        fd.shape = circle;
        fd.density = 1f; 
        fd.friction = 1.4f;
        fd.restitution = 0.6f; // Make it bounce a little bit
        fd.filter.categoryBits = BIT_FIREBALL;
        fd.filter.maskBits = BIT_PLAYER | BIT_TERRAIN | BIT_PLAYER;
        body.createFixture(fd).setUserData("fireball");
        return new Fireball(body,turret);
    }


    private void createTiles() {

        // TmxMapLoader.Parameters
        Parameters params = new Parameters();
        params.textureMinFilter = TextureFilter.Linear;
        params.textureMagFilter = TextureFilter.Nearest;

        //Load map
        map = new TmxMapLoader().load("maps/test.tmx", params);
        // Retrieve map properties
        MapProperties properties = map.getProperties();

        tmr = new OrthogonalTiledMapRenderer(map);

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("terrain");
        BodyDef bdef = new BodyDef();
        tileMapWidth = properties.get("width", Integer.class);
        tileMapHeight = properties.get("height", Integer.class);
        tileSize = layer.getTileHeight();
        //go through all the cells in layer
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                // get cell
                Cell cell = layer.getCell(col, row);

                // check that there is a cell
                if (cell == null) {
                    continue;
                }
                if (cell.getTile() == null) {
                    continue;
                }
                // create body from cell
                bdef.type = BodyType.StaticBody;
                bdef.position.set((col + 0.5f) * tileSize / PPM, (row + 0.5f) * tileSize / PPM); //centered at center

                ChainShape cs = new ChainShape();
                FixtureDef fd = new FixtureDef();

                float box2dTileSize = tileSize / PPM;
                if (cell.getTile().getProperties().get("slide_left") != null) {
                    //to link the cell edges
                    Vector2[] v = new Vector2[4];
                    v[0] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    v[1] = new Vector2(box2dTileSize / 2, box2dTileSize / 2);//top right corner
                    v[2] = new Vector2(box2dTileSize / 2, -box2dTileSize / 2);//bottom right corner
                    v[3] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0.8f;
                    fd.shape = cs;
                    fd.isSensor=false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
                    world.createBody(bdef).createFixture(fd);
                } else if (cell.getTile().getProperties().get("slide_right") != null) {
                    //to link the cell edges
                    Vector2[] v = new Vector2[4];
                    v[0] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    v[1] = new Vector2(-box2dTileSize / 2, box2dTileSize / 2);//top left corner
                    v[2] = new Vector2(box2dTileSize / 2, -box2dTileSize / 2);//bottom right corner
                    v[3] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0.8f;
                    fd.shape = cs;
                    fd.isSensor = false;
                    fd.isSensor=false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
                    world.createBody(bdef).createFixture(fd);
                } else if (cell.getTile().getProperties().get("bounce") != null) {
                    //to link the cell edges
                    Vector2[] v = new Vector2[5];
                    v[0] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    v[1] = new Vector2(-box2dTileSize / 2, box2dTileSize / 9);//top left corner
                    v[2] = new Vector2(box2dTileSize / 2, box2dTileSize / 9);//top right corner
                    v[3] = new Vector2(box2dTileSize / 2, -box2dTileSize / 2);//bottom right corner
                    v[4] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0;
                    fd.shape = cs;
                    fd.isSensor=false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER;
                    world.createBody(bdef).createFixture(fd).setUserData("bounce");
                } else if (cell.getTile().getProperties().get("spike") != null) {
                    //to link the cell edges
                    Vector2[] v = new Vector2[5];
                    v[0] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    v[1] = new Vector2(-box2dTileSize / 2, -0.1f);//top left corner
                    v[2] = new Vector2(box2dTileSize / 2, -0.1f);//top right corner
                    v[3] = new Vector2(box2dTileSize / 2, -box2dTileSize / 2);//bottom right corner
                    v[4] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0;
                    fd.shape = cs;
                    fd.isSensor = false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER;
                    world.createBody(bdef).createFixture(fd).setUserData("spike");
                }else if(cell.getTile().getProperties().get("fire_turret")!=null){
                    //to link the cell edges
                    Vector2[] v = new Vector2[5];
                    v[0] = new Vector2(-box2dTileSize/2, -box2dTileSize/2);//bottom left corner
                    v[1] = new Vector2(-box2dTileSize/2, box2dTileSize/2);//top left corner
                    v[2] = new Vector2(box2dTileSize/2, box2dTileSize/2);//top right corner
                    v[3] = new Vector2(box2dTileSize/2 , -box2dTileSize/2);//bottom right corner
                    v[4] = new Vector2(-box2dTileSize/2, -box2dTileSize/2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0;
                    fd.shape = cs;
                    fd.isSensor=false;
                    fd.filter.categoryBits = BIT_TURRET;
                    fd.filter.maskBits = BIT_PLAYER | BIT_TERRAIN;
                    world.createBody(bdef).createFixture(fd).setUserData("fire_turret");
                    Vector2 turret_pos= new Vector2((col + 0.5f) * tileSize / PPM, (row + 0.5f) * tileSize / PPM);
                    createFireTurret(turret_pos);
                }else if(cell.getTile().getProperties().get("challengeBox")!=null){
                    //to link the cell edges
                    Vector2[] v = new Vector2[5];
                    v[0] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    v[1] = new Vector2(-box2dTileSize / 2, box2dTileSize / 2);//top left corner
                    v[2] = new Vector2(box2dTileSize / 2, box2dTileSize / 2);//top right corner
                    v[3] = new Vector2(box2dTileSize / 2, -box2dTileSize / 2);//bottom right corner
                    v[4] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0;
                    fd.shape = cs;
                    fd.isSensor=false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
                    world.createBody(bdef).createFixture(fd).setUserData("challengeBox");
                }else if(cell.getTile().getProperties().get("victory")!=null){
                    //to link the cell edges
                    Vector2[] v = new Vector2[5];
                    v[0] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    v[1] = new Vector2(-box2dTileSize / 2, box2dTileSize / 2);//top left corner
                    v[2] = new Vector2(box2dTileSize / 2, box2dTileSize / 2);//top right corner
                    v[3] = new Vector2(box2dTileSize / 2, -box2dTileSize / 2);//bottom right corner
                    v[4] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0;
                    fd.shape = cs;
                    fd.isSensor=false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
                    world.createBody(bdef).createFixture(fd).setUserData("victory");
                }else{
                    //to link the cell edges
                    Vector2[] v = new Vector2[5];
                    v[0] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    v[1] = new Vector2(-box2dTileSize / 2, box2dTileSize / 2);//top left corner
                    v[2] = new Vector2(box2dTileSize / 2, box2dTileSize / 2);//top right corner
                    v[3] = new Vector2(box2dTileSize / 2, -box2dTileSize / 2);//bottom right corner
                    v[4] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0;
                    fd.shape = cs;
                    fd.isSensor=false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
                    world.createBody(bdef).createFixture(fd);
                }
                cs.dispose();
            }
        }
    }

    private void destroyBodies() {
        Array<Body> bodies = contactManager.getBodies();
        for(int i = 0; i < bodies.size; i++) {
            Body b = bodies.get(i);
            bodies.removeIndex(i);
            world.destroyBody(b);
        }
        bodies.clear();
       if(fireballs.size>0){
            for(Fireball f : fireballs){
                if(!f.getBody().isActive()){
                    fireballs.removeValue(f, false);
                    f.getTurret().destroyFireball();
                }
            }
        }
    }

    private void createFireTurret(Vector2 turret_pos) {
        Turret turret = new Turret(turret_pos);
        turrets.add(turret);
    }
    
}
