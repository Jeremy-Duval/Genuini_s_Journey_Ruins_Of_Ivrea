/**
 *
 * @author Adrien
 */

package genuini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import genuini.entities.Player;
import genuini.handlers.BoundedCamera;
import genuini.handlers.ContactHandler;
import static genuini.handlers.PhysicsVariables.PPM;
import genuini.handlers.TextManager;
import genuini.main.MainGame;
import static genuini.main.MainGame.V_HEIGHT;
import static genuini.main.MainGame.V_WIDTH;



public class GameScreen extends AbstractScreen{
    private boolean debug = false;
    private BitmapFont font = new BitmapFont();
    
    private BoundedCamera b2dCam;
    private Player player;
    
    private final World world;
    private Box2DDebugRenderer b2dr;
    private ContactHandler contactManager;
    
    private TiledMap map;
    private int tileMapWidth;
    private int tileMapHeight;
    private float tileSize;
    private TiledMapRenderer tmr;
    
    private SpriteBatch spriteBatch;
    private BoundedCamera cam;
    
    public GameScreen() {
        super();
        
        world = new World(new Vector2(0, -9.81f), true); //Create world, any inactive bodies are asleep (not calculated)
        contactManager = new ContactHandler();
        world.setContactListener(contactManager);//
        
        /* DEBUG */
        b2dr = new Box2DDebugRenderer();
        
        
        cam = new BoundedCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        spriteBatch=new SpriteBatch();
        
        //set the Text batch
        TextManager.SetSpriteBatch(spriteBatch);
        
        //create tiles
        createTiles();
        
        // set up box2d cam
        b2dCam = new BoundedCamera();
        b2dCam.setToOrtho(false, MainGame.V_WIDTH / PPM, MainGame.V_HEIGHT / PPM);
        b2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0, (tileMapHeight * tileSize) / PPM);
        
        
        
        //create player
        createPlayer();
        cam.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);
        
    }
    
    
    @Override
    public void show(){
        
    }
    
    


    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(cam.combined);
        world.step(dt, 6, 2);
        
        handleInput();
        // camera follow player
        cam.setPosition(player.getPosition().x * PPM + MainGame.V_WIDTH / 4, MainGame.V_HEIGHT / 2);
        cam.update();
        
        //draw tiled map
        tmr.setView(cam);
        tmr.render();

        
        //draw player
        
        player.render(spriteBatch);
        //To write on screen
        spriteBatch.begin();
        //spriteBatch.draw(background, 0,0,MainGame.V_WIDTH, MainGame.V_WIDTH);
        //TextManager.Draw("FPS: ",cam);
        spriteBatch.end();
        
        
        if(debug) {
                b2dCam.setPosition(player.getPosition().x + MainGame.V_WIDTH / 4 / PPM, MainGame.V_HEIGHT / 2 / PPM);
                b2dCam.update();
                b2dr.render(world, b2dCam.combined);
        }
    }

    
    @Override
    public void buildStage() {
        
    }
    /**
     * Apply upward force to player body.
     */
    private void playerJump() {
        player.getBody().applyLinearImpulse(0, 320/PPM, 0, 0, true);
        player.updateTexture(true);
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                @Override
                public void run() {
                    player.updateTexture(false);
                }
            }, 
            500
        );
    }
    
    private void playerMoveLeft() {
        player.getBody().applyLinearImpulse(-7/PPM, 0, 0, 0, true);
        player.walkLeft();
    }
    
    private void playerMoveRight() {
        player.getBody().applyLinearImpulse(7/PPM, 0, 0, 0, true);
        player.walkRight();
    }
        
    public void handleInput() {
        if(Gdx.input.isKeyPressed(Keys.Q)){
            playerMoveLeft();
        }
        if(Gdx.input.isKeyPressed(Keys.D)){
            playerMoveRight();
        }
        if(Gdx.input.isKeyPressed(Keys.Z) && contactManager.playerCanJump()){
            playerJump();
        }
    }

    @Override
    public void dispose() {
    }
    
    
    private void createPlayer(){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        
        bdef.position.set(160 / PPM , 200 / PPM);
        bdef.type = BodyType.DynamicBody;
        bdef.linearDamping = 3f;
        Body body = world.createBody(bdef);
        fdef.friction=1.5f;
        fdef.shape = shape;
        //fdef.filter.categoryBits = PhysicsVariables.BIT_PLAYER;
        //fdef.filter.maskBits = PhysicsVariables.BIT_TERRAIN;
        float bodyWidth = 22f;
        float bodyHeight = 44f;
        shape.setAsBox(bodyWidth/PPM ,bodyHeight/PPM);
        body.createFixture(fdef).setUserData("player");
        
        
        //create foot sensor
        shape.setAsBox((bodyWidth-10)/PPM ,3/PPM, new Vector2(0, -bodyHeight /PPM),0);
        fdef.isSensor=true;
        body.createFixture(fdef).setUserData("foot");
        
        //Create player entity
        player = new Player(body);
        
    }
    
    private void createTiles(){
        //Load map
        map = new TmxMapLoader().load("maps/test.tmx");
        // Retrieve map properties
        MapProperties properties = map.getProperties();
        tmr = new OrthogonalTiledMapRenderer(map);
        
        
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("terrain");
        BodyDef bdef = new BodyDef();
        tileMapWidth = properties.get("width", Integer.class);
	tileMapHeight = properties.get("height", Integer.class);
        tileSize = layer.getTileHeight();
        //go through all the cells in layer
        for(int row=0; row < layer.getHeight(); row++){
            for(int col = 0; col < layer.getWidth(); col++){
                // get cell
                Cell cell = layer.getCell(col, row);

                // check that there is a cell
                if(cell == null) continue;
                if(cell.getTile() == null) continue;

                // create body from cell
                bdef.type = BodyType.StaticBody;
                bdef.position.set((col + 0.5f) * tileSize / PPM, (row + 0.5f) * tileSize / PPM); //centered at center
                
                
                ChainShape cs = new ChainShape();
                float padding = 2f;
                //to link the cell edges
                Vector2[] v = new Vector2[5];
                v[0] = new Vector2((-tileSize+padding) / 2 / PPM, (-tileSize+padding) / 2 / PPM);//bottom left corner
                v[1] = new Vector2((-tileSize+padding)  / 2 / PPM, (tileSize-padding) / 2 / PPM);//top left corner
                v[2] = new Vector2((tileSize-padding)  / 2 / PPM,(tileSize-padding)  / 2 / PPM);//top right corner
                v[3] = new Vector2((tileSize-padding) / 2 / PPM, (-tileSize+padding)  / 2 / PPM);//bottom right corner
                v[4] = new Vector2((-tileSize+padding) / 2 / PPM, (-tileSize+padding) / 2 / PPM);//bottom left corner
                cs.createChain(v);
                FixtureDef fd = new FixtureDef();
                fd.friction = 0;
                fd.shape = cs;
                fd.isSensor=false;
                //fd.filter.categoryBits = PhysicsVariables.BIT_TERRAIN;    
                //fd.filter.maskBits = PhysicsVariables.BIT_BALL | PhysicsVariables.BIT_PLAYER;
                world.createBody(bdef).createFixture(fd);
                cs.dispose();
            }
        }
    }

    
}
