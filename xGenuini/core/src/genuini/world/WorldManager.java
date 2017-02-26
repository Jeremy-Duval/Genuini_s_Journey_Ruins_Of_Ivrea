/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genuini.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import genuini.entities.AccessPoint;
import genuini.entities.Button;
import genuini.entities.Spring;
import genuini.entities.Sprites;
import genuini.entities.Turret;
import genuini.screens.GameScreen;
import static genuini.world.PhysicsVariables.BIT_ACCESSPOINT;
import static genuini.world.PhysicsVariables.BIT_FIREBALL;
import static genuini.world.PhysicsVariables.BIT_PLAYER;
import static genuini.world.PhysicsVariables.BIT_TERRAIN;
import static genuini.world.PhysicsVariables.BIT_TURRET;
import static genuini.world.PhysicsVariables.PPM;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Adrien Techer
 */
public class WorldManager {

    private int tileMapWidth;
    private int tileMapHeight;
    private int tileSize;
    TiledMapTileLayer terrainLayer;
    Array<Sprites> sprites;
    Array<Turret> turrets;
    Array<Spring> springs;
    Array<Button> buttons;
    Array<AccessPoint> accessPoints;
    MapLayer objectLayer;
    GameScreen screen;
    private MapLayer accessPointsLayer;

    public WorldManager(GameScreen screen, String mapName) {
        this.screen = screen;
        loadMap(screen, mapName);
        createTerrainLayers(screen.getWorld());
        createObjectsLayer(screen.getWorld());
        createAccessPoints();
    }

    private void loadMap(GameScreen screen, String mapName) {
        // TmxMapLoader.Parameters
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = Texture.TextureFilter.Linear;
        params.textureMagFilter = Texture.TextureFilter.Nearest;

        //Load map
        String mapPath = "maps/" + mapName + ".tmx";
        TiledMap map = new TmxMapLoader().load(mapPath, params);
        screen.setMap(map);
        // Retrieve map properties
        MapProperties properties = map.getProperties();

        screen.setTMR(new OrthogonalTiledMapRenderer(map));

        terrainLayer = (TiledMapTileLayer) map.getLayers().get("terrain");
        objectLayer = map.getLayers().get("objects");
        accessPointsLayer = map.getLayers().get("accessPoints");
        tileMapWidth = properties.get("width", Integer.class);
        tileMapHeight = properties.get("height", Integer.class);
        tileSize = (int) terrainLayer.getTileHeight();
    }

    public int getTileMapWidth() {
        return tileMapWidth;
    }

    public int getTileMapHeight() {
        return tileMapHeight;
    }

    public int getTileSize() {
        return tileSize;
    }

    private void createTerrainLayers(World world) {
        BodyDef bdef = new BodyDef();
        //go through all the cells in terrainLayer
        for (int row = 0; row < terrainLayer.getHeight(); row++) {
            for (int col = 0; col < terrainLayer.getWidth(); col++) {
                // get cell
                TiledMapTileLayer.Cell cell = terrainLayer.getCell(col, row);

                // check that there is a cell
                if (cell == null) {
                    continue;
                }
                if (cell.getTile() == null) {
                    continue;
                }
                // create body from cell
                bdef.type = BodyDef.BodyType.StaticBody;
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
                    fd.isSensor = false;
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
                    fd.isSensor = false;
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
                    fd.isSensor = false;
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
                } else if (cell.getTile().getProperties().get("challengeBox") != null) {
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
                    fd.isSensor = false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
                    world.createBody(bdef).createFixture(fd).setUserData("challengeBox");
                } else if (cell.getTile().getProperties().get("victory") != null) {
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
                    fd.isSensor = false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
                    world.createBody(bdef).createFixture(fd).setUserData("victory");
                } else {
                    //to link the cell edges
                    Vector2[] v = new Vector2[5];
                    v[0] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    v[1] = new Vector2(-box2dTileSize / 2, box2dTileSize / 2);//top left corner
                    v[2] = new Vector2(box2dTileSize / 2, box2dTileSize / 2);//top right corner
                    v[3] = new Vector2(box2dTileSize / 2, -box2dTileSize / 2);//bottom right corner
                    v[4] = new Vector2(-box2dTileSize / 2, -box2dTileSize / 2);//bottom left corner
                    cs.createChain(v);
                    fd.friction = 0.05f;
                    fd.shape = cs;
                    fd.isSensor = false;
                    fd.filter.categoryBits = BIT_TERRAIN;
                    fd.filter.maskBits = BIT_PLAYER | BIT_FIREBALL;
                    world.createBody(bdef).createFixture(fd);
                }
                cs.dispose();
            }
        }
    }

    private void createObjectsLayer(World world) {
        MapObjects objects = objectLayer.getObjects();

        sprites = new Array<Sprites>();
        turrets = new Array<Turret>();
        springs = new Array<Spring>();
        buttons = new Array<Button>();

        for (MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject) object);
            } else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject) object);
            } else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject) object);
            } else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject) object);
            } else {
                continue;
            }
            float x = new Float(object.getProperties().get("x").toString());
            float y = new Float(object.getProperties().get("y").toString());
            BodyDef bd = new BodyDef();
            bd.type = BodyType.StaticBody;
            bd.position.x = x / PPM;
            bd.position.y = y / PPM;
            Body body = world.createBody(bd);
            body.createFixture(shape, 1);
            shape.dispose();
            createObject(object, body);
        }

    }

    private void createObject(MapObject object, Body body) {
        if (object.getProperties().containsKey("turret")) {
            Turret t = new Turret(screen, body, Integer.valueOf(object.getProperties().get("id").toString()), object.getProperties().get("turret").toString());
            sprites.add(t);
            turrets.add(t);
        } else if (object.getProperties().containsKey("spring")) {
            Spring s = new Spring(screen, body, Integer.valueOf(object.getProperties().get("id").toString()));
            sprites.add(s);
            springs.add(s);
        } else if (object.getProperties().containsKey("button")) {
            Button b = new Button(screen, body, Integer.valueOf(object.getProperties().get("id").toString()), Integer.valueOf(object.getProperties().get("linkedObjectID").toString()), object.getProperties().get("button").toString());
            sprites.add(b);
            buttons.add(b);
        }
    }

    private PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.width * 0.5f) / PPM,
                (rectangle.height * 0.5f) / PPM);
        polygon.setAsBox(rectangle.width * 0.5f / PPM,
                rectangle.height * 0.5f / PPM,
                size,
                0.0f);

        return polygon;
    }

    private CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / PPM);
        circleShape.setPosition(new Vector2(circle.x / PPM, circle.y / PPM));
        return circleShape;
    }

    private PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            //System.out.println(vertices[i]);
            worldVertices[i] = vertices[i] / PPM;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / PPM;
            worldVertices[i].y = vertices[i * 2 + 1] / PPM;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

    private void createAccessPoints() {

        accessPoints = new Array<AccessPoint>();

        MapObjects accessPointsObjects = accessPointsLayer.getObjects();
        for (MapObject accessPoint : accessPointsObjects) {
            if (accessPoint instanceof TextureMapObject) {
                continue;
            }

            //Vector2 position = new Vector2(new Float(accessPoint.getProperties().get("x").toString())/PPM,new Float(accessPoint.getProperties().get("y").toString())/PPM);
            Shape shape = getRectangle((RectangleMapObject) accessPoint);
            float x = new Float(accessPoint.getProperties().get("x").toString());
            float y = new Float(accessPoint.getProperties().get("y").toString());
            BodyDef bd = new BodyDef();
            bd.type = BodyType.StaticBody;
            bd.position.x = x / PPM;
            bd.position.y = y / PPM;
            Body body = screen.getWorld().createBody(bd);
            body.createFixture(shape, 1);
            shape.dispose();

            boolean isSpawn = false;
            String name = accessPoint.getProperties().get("name").toString();
            if (name.equals("spawn")) {
                isSpawn = true;
            }
            AccessPoint ac = new AccessPoint(screen, body, Integer.valueOf(accessPoint.getProperties().get("id").toString()), isSpawn, name, accessPoint.getProperties().get("linkedMapName").toString(), accessPoint.getProperties().get("linkedAccessPointName").toString());
            accessPoints.add(ac);
        }
    }

    public Array<Sprites> getSprites() {
        return sprites;
    }

    public Array<Turret> getTurrets() {
        return turrets;
    }

    public Array<Spring> getSprings() {
        return springs;
    }

    public Array<Button> getButtons() {
        return buttons;
    }

    public Array<AccessPoint> getAccessPoints() {
        return accessPoints;
    }

}
