package com.platformjump.game.Item;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.platformjump.game.BaseFramework.BaseActor;

import java.util.ArrayList;
import java.util.Iterator;

public class TilemapActor extends Actor {

    //窗口尺寸
    private static int windowWidth = 800;
    private static int windowHeight = 600;
    private float moveVec = 100f;
    private float offerX;

    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;
    private int[] backgroundLayers = {0,1,2,3,4,5};
    private int[] tileLayers = {6};

    public TilemapActor(String filename, Stage theStage){
        tiledMap = new TmxMapLoader().load(filename);

        //获取每个图块的尺寸
        int tileWidth = (int)tiledMap.getProperties().get("tilewidth");
        int tileHeight = (int) tiledMap.getProperties().get("tileheight");

        //获取行和列的总的图块数
        int numTileHorizontal = (int)tiledMap.getProperties().get("width");
        int numTileVertical = (int)tiledMap.getProperties().get("height");

        //计算地图的宽和高
        int mapWidth = tileWidth*numTileHorizontal;
        int mapHeight = tileHeight*numTileVertical;

        //设置世界的边界
        BaseActor.setWorldBounds(mapWidth,mapHeight);

        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap,1.0f);
        tiledMapRenderer.setBlending(true);
        tiledCamera = new OrthographicCamera();

        /**
        Viewport v = getStage().getViewport();
        int w = v.getScreenWidth();
        int h = v.getScreenHeight();
        tiledCamera.viewportWidth = w;
        tiledCamera.viewportHeight = h;

        tiledCamera.setToOrtho(false,w,h);
        tiledCamera.update();*/

        theStage.addActor(this);

        Viewport v = getStage().getViewport();
        int w = v.getScreenWidth();
        int h = v.getScreenHeight();
        tiledCamera.viewportWidth = w;
        tiledCamera.viewportHeight = h;

        tiledCamera.setToOrtho(false,w,h);
        tiledCamera.update();


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Viewport v = getStage().getViewport();
        int w = v.getScreenWidth();
        int h = v.getScreenHeight();
        tiledCamera.viewportWidth = w;
        tiledCamera.viewportHeight = h;
        tiledCamera.update();

        offerX += delta*moveVec;
        offerX %= BaseActor.getWorldBounds().width;
        if(offerX > 0){
            offerX -= BaseActor.getWorldBounds().width;
        }
        /**
        offerX %= getStage().getCamera().viewportWidth;
        if(offerX > 0){
            offerX -= getStage().getCamera().viewportWidth;
        }*/

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //调整tiledmap camera与main camera同步
        Camera mainCamera = getStage().getCamera();
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;

        tiledCamera.update();

        tiledMapRenderer.setView(tiledCamera);

        //tiledMapRenderer使用和stage一样的camera就可以解决缩放窗口导致的问题了，就是两者一定要同步才行~~
        tiledMapRenderer.setView((OrthographicCamera) mainCamera);

        //背景层移动，需要改变view
        float tmpx = mainCamera.position.x;
        mainCamera.position.x += offerX;
        mainCamera.update();
        tiledMapRenderer.setView((OrthographicCamera) mainCamera);
        batch.end();
        tiledMapRenderer.render(backgroundLayers);

        if(Math.abs(offerX) > 0.001F){
           // mainCamera.position.x = tmpx + mainCamera.viewportWidth + offerX;
            mainCamera.position.x = tmpx + BaseActor.getWorldBounds().width + offerX;
            mainCamera.update();
            tiledMapRenderer.setView((OrthographicCamera) mainCamera);
            tiledMapRenderer.render(backgroundLayers);
        }

        //tile层不需要移动，所以view需要恢复之前的值
        mainCamera.position.x = tmpx;
        mainCamera.update();
        tiledMapRenderer.setView((OrthographicCamera) mainCamera);
        tiledMapRenderer.render(tileLayers);

        /**
        mainCamera.translate(-9,0,0);
        mainCamera.update();
        tiledMapRenderer.setView((OrthographicCamera) mainCamera);
        tiledMapRenderer.render();*/
        batch.begin();
    }

    //获取地图中有某个属性的对象
    public ArrayList<MapObject> getRectangleList(String propertyName){
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        //获取每一个图层
        for(MapLayer layer: tiledMap.getLayers()){
            //获取每一个图层中的对象
            for(MapObject obj: layer.getObjects()){

                //判断该对象是否是矩形对象
                if(!(obj instanceof RectangleMapObject))
                    continue;

                //获取每个对象的属性
                MapProperties props = obj.getProperties();

                //如果该对象的属性中有name这个属性，并且其属性值为propertyName，则添加到list中
                if(props.containsKey("name") && props.get("name").equals(propertyName)){
                    list.add(obj);
                }

            }
        }

        return list;
    }

    public ArrayList<MapObject> getTitleList(String propertyName){
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        for(MapLayer layer: tiledMap.getLayers()){
            for(MapObject obj : layer.getObjects()){

                //判断是否是块对象
                if(!(obj instanceof TiledMapTileMapObject))
                    continue;

                //获取图块实例化后的属性
                MapProperties props = obj.getProperties();

                //默认的MapProperties 存在Tile object
                //而实例化的MapProperties属性存储在MapObject
                //就是说，每个图块都有默认的属性和值，但是在地图中其默认的属性和值可能会发生改变，
                // 就是实例化的属性和值会保存在不同的位置
                TiledMapTileMapObject tmtmo = (TiledMapTileMapObject)obj;
                TiledMapTile t = tmtmo.getTile();
                //获取图块默认的属性
                MapProperties defaultProps = t.getProperties();

                if(defaultProps.containsKey("name") &&
                        defaultProps.get("name").equals(propertyName)){
                    list.add(obj);
                }

                //get list of default property keys
                Iterator<String> propertyKeys = defaultProps.getKeys();

                //iterator over keys;copy default values into props if needed
                while (propertyKeys.hasNext()){
                    String key = propertyKeys.next();

                    //check if value already exits; if not, create property with
                    //default value
                    if(props.containsKey(key)){
                        continue;
                    }else{
                        Object value = defaultProps.get(key);
                        props.put(key,value);
                    }
                }

            }
        }

        return list;
    }


}

