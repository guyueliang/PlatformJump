package com.platformjump.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.World;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGame;
import com.platformjump.game.BaseFramework.BaseGameStage;
import com.platformjump.game.BaseFramework.BaseStage;
import com.platformjump.game.Item.*;
import com.platformjump.game.Player.Koala;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GameStage extends BaseGameStage {

    /**
    public int test;
    private  Koala jack;
    private  SnapshotArray<BaseActor> entities;
    private  World<BaseActor> world;
    public static float TILE_DIMENSION = 64f;

    private TilemapActor tma;
    private ArrayList<Class<? extends BaseActor>> objClass;
     */

    public  static final String TAG = GameStage.class.getSimpleName();

    private int count = 0;
    public GameStage(platformjump mainGame) {
        super(mainGame);
        //这个init()可以去掉，因为在其父类BaseStage的构造函数中已经调用init()函数了
        init();


    }

    private void print() {Gdx.app.log(TAG,"count= " + count);}


    @Override
    public void init(){
        super.init();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        tma = new TilemapActor("myMap.tmx",this);

        count++;
        print();
        StackTraceElement[] s = new Exception().getStackTrace();
        System.out.println(s[1].getClassName() + s[1].getMethodName());
        /**
        entities = new SnapshotArray<>();
        world = new World<>(TILE_DIMENSION);

        tma = new TilemapActor("myMap.tmx",this);

        objClass = new ArrayList<Class<? extends BaseActor>>();
         */
        objClass.add(Solid.class);
        objClass.add(Koala.class);
        objClass.add(Flag.class);
        objClass.add(Coin.class);
        objClass.add(Timer.class);
        objClass.add(Springboard.class);
        objClass.add(Lock.class);
        objClass.add(Key.class);
        objClass.add(Platform.class);

        //创建不同类型的物体以及player
        for(Class cls : objClass){
            if(cls != null){
                try {
                    createObj(cls);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }

        jack.toFront();

        jack.setEntities(getEntities());
        jack.setWorld(getWorld());

        int count = world.countItems();

    }


    //根据传入类的类型进行创建相对应的物体
    public void createObj(Class cls) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        BaseActor actor;
        Class[] paramTypes;
        Object[] params;
        ArrayList<MapObject> mapObj;
        boolean isSolid = false;
        int countTimer = 0;

        //注意float.class而不是Float.class
        String s = cls.getSimpleName();
        if(s.equals("Solid")){
            isSolid = true;
            paramTypes = new Class[]{float.class, float.class, float.class, float.class, BaseStage.class};
            //Solid物体是用矩形框表示的，并且不可见
            mapObj = tma.getRectangleList(s);
        }else if(s.equals("Koala")){

            paramTypes = new Class[]{float.class, float.class, BaseGameStage.class};
            mapObj = tma.getRectangleList(s);

        }else {
            paramTypes = new Class[]{float.class, float.class, BaseStage.class};
            //其他类型的物体是用tile表示的
            mapObj = tma.getTitleList(s);
        }

        //先获取构造函数
        Constructor<BaseActor> con = cls.getConstructor(paramTypes);

        for(MapObject obj: mapObj){
            MapProperties props = obj.getProperties();
            float x = (float)props.get("x");
            float y = (float)props.get("y");

            //Solid类型物体多出2个参数

            if(isSolid){
                float width = (float)props.get("width");
                float height = (float)props.get("height");
                params = new Object[]{x,y,width,height,this};
            }else{
                params = new Object[]{x, y, this};
            }

            //根据构造函数创建新的对象
            actor = con.newInstance(params);

            if(actor instanceof Lock || actor instanceof Key){
                String color = (String)props.get("color");
                if(color.equals("red"))
                    actor.setColor(Color.RED);
                else
                    actor.setColor(Color.WHITE);

            }else if(actor instanceof Koala){
                jack = (Koala) actor;
            }

            entities.add(actor);
            world.add(actor.item,actor.getX()+actor.bboxX,actor.getY()+actor.bboxY, actor.bboxWidth, actor.bboxHeight);



            //for debug
            if(actor instanceof Timer){
                countTimer++;
                Gdx.app.log(TAG,"creatObj() has created a Timer obj");
                Gdx.app.log(TAG,"Timer count = " + countTimer);
            }else if(actor instanceof Coin){
                Gdx.app.log(TAG,"creatObj() has created a Coin obj");
            }else if(actor instanceof Springboard){
                Gdx.app.log(TAG,"creatObj() has created a Springboard obj");
            }else if(actor instanceof Lock){
                Gdx.app.log(TAG,"creatObj() has created a Lock obj");
            }else if(actor instanceof Key){
                Gdx.app.log(TAG,"creatObj() has created a Key obj");
            }else if(actor instanceof Flag){
                Gdx.app.log(TAG,"creatObj() has created a Flag obj");
            }else if(actor instanceof Platform){
                Gdx.app.log(TAG,"creatObj() has created a Platform obj");
            }else if(actor instanceof Solid){
                Gdx.app.log(TAG,"creatObj() has created a Solid obj");

            }else if(actor instanceof Koala){
                Gdx.app.log(TAG,"creatObj() has created a Koala obj");
            }

        }

    }

    /**
    public SnapshotArray<BaseActor> getEntities() {
        return entities;
    }

    public World<BaseActor> getWorld() {
        return world;
    }

    public Koala getJack() {
        return jack;
    }*/
}