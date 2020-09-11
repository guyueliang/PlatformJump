package com.platformjump.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class LevelScreen extends BaseScreen{
    public  static final String TAG = LevelScreen.class.getSimpleName();

    Koala jack;
    boolean gameOver;
    int coins;
    float time;
    Label coinLabel;
    Table keyTable;
    Label timeLabel;
    Label messageLabel;
   // ArrayList<Color> keyList;
    BaseActor keyIcon;

    public static SnapshotArray<BaseActor> entities;
    public static World<BaseActor> world;
    public static float TILE_DIMENSION = 64f;

    private  TilemapActor tma;
    private ArrayList<Class<? extends BaseActor>> objClass;

    @Override
    public void initialize() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        entities = new SnapshotArray<>();
        world = new World<>(TILE_DIMENSION);

        tma = new TilemapActor("myMap.tmx",mainStage);

        objClass = new ArrayList<Class<? extends BaseActor>>();
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


        //ui设置
        gameOver = false;
        coins = 0;
        time = 300;
        coinLabel = new Label("Coins: " +coins, BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);
        keyTable = new Table();
        timeLabel = new Label("Time: " + (int)time,BaseGame.labelStyle);
        timeLabel.setColor(Color.LIGHT_GRAY);
        messageLabel = new Label("Message",BaseGame.labelStyle);
        messageLabel.setVisible(false);

        uiTable.pad(20);//上下左右各填充20个像素
        uiTable.add(coinLabel);
        uiTable.add(keyTable).expandX();

        keyIcon = new BaseActor(0,0,uiStage);
        keyIcon.loadTexture("key-icon.png");
        //keyIcon.setColor(keyColor);
        keyIcon.setVisible(false);
        keyTable.add(keyIcon);

        uiTable.add(timeLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();




        jack.toFront();


        //keyList = new ArrayList<Color>();




    }

    @Override
    public void update(float dt) {
        //判断游戏是否结束
        if(gameOver)
            return;

        if(jack.isWin()){
            messageLabel.setText("You Win!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setVisible(true);
            gameOver = true;
        }

        timeLabel.setText("Time: " + (int)jack.getGameLeftTime());

        if(jack.getGameLeftTime() <= 0){
            messageLabel.setText("Time Up- Game Over");
            messageLabel.setColor(Color.RED);
            messageLabel.setVisible(true);
            //jack.remove();
            gameOver = true;
        }

        coinLabel.setText("Coins: " + jack.getCoin());

        if(jack.isHasKey()){
            keyIcon.setColor(jack.getKeyColor());
            keyIcon.setVisible(true);
        }


    }


    //根据传入类的类型进行创建相对应的物体
    public void createObj(Class cls) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        BaseActor actor;
        Class[] paramTypes;
        Object[] params;
        ArrayList<MapObject> mapObj;
        boolean isSolid = false;

         //注意float.class而不是Float.class
        String s = cls.getSimpleName();
        if(s.equals("Solid")){
            isSolid = true;
            paramTypes = new Class[]{float.class, float.class, float.class, float.class, Stage.class};
            //Solid物体是用矩形框表示的，并且不可见
            mapObj = tma.getRectangleList(s);
        }else if(s.equals("Koala")){

            paramTypes = new Class[]{float.class, float.class, Stage.class};
            mapObj = tma.getRectangleList(s);

        }else {
            paramTypes = new Class[]{float.class, float.class, Stage.class};
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
                params = new Object[]{x,y,width,height,mainStage};
            }else{
                params = new Object[]{x, y, mainStage};
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
                Gdx.app.log(TAG,"creatObj() has created a Timer obj");
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
}
