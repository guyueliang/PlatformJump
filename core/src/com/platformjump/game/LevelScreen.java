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
    ArrayList<Color> keyList;
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

        /**
        //创建solid类型的障碍物
        try {
            createObj(Solid.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/

        /**
        for(MapObject obj: tma.getRectangleList("Solid")){
            MapProperties props = obj.getProperties();
           Solid solid =  new Solid((float)props.get("x"),(float)props.get("y"),
                    (float)props.get("width"),(float)props.get("height"),
                    mainStage
                    );
           entities.add(solid);
           world.add(solid.item,solid.getX()+solid.bboxX,solid.getY()+solid.bboxY, solid.bboxWidth, solid.bboxHeight);
        }*/

        /**
        //创建主角
        try {
            createObj(Koala.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/
        /**
        MapObject startPoint = tma.getRectangleList("Koala").get(0);
        MapProperties startProps = startPoint.getProperties();
        jack = new Koala((float)startProps.get("x"),(float)startProps.get("y"),mainStage);
        entities.add(jack);
        world.add(jack.item,jack.getX()+jack.bboxX,jack.getY()+jack.bboxY, jack.bboxWidth, jack.bboxHeight);
         */


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



        /**
        //创建flag物体
        try {
            createObj(Flag.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/
        /**
        for(MapObject obj: tma.getTitleList("Flag")){
            MapProperties props = obj.getProperties();
            Flag flag = new Flag((float)props.get("x"),(float)props.get("y"),mainStage);

            entities.add(flag);
            world.add(flag.item,flag.getX()+flag.bboxX,flag.getY()+flag.bboxY, flag.bboxWidth, flag.bboxHeight);
        }*/

        /**
        //创建金币
        try {
            createObj(Coin.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/
        /**
        //创建金币
        for(MapObject obj: tma.getTitleList("Coin")){
            MapProperties props = obj.getProperties();
            Coin coin = new Coin((float)props.get("x"),(float)props.get("y"),mainStage);

            entities.add(coin);
            world.add(coin.item,coin.getX()+coin.bboxX,coin.getY()+coin.bboxY, coin.bboxWidth, coin.bboxHeight);

        }*/

        /**
        //创建时间计时器
        try {
            createObj(Timer.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/
        /**
        for(MapObject obj: tma.getTitleList("Timer")){
            MapProperties props = obj.getProperties();
           Timer timer = new Timer((float)props.get("x"),(float)props.get("y"),mainStage);

            entities.add(timer);
            world.add(timer.item,timer.getX()+timer.bboxX,timer.getY()+timer.bboxY, timer.bboxWidth, timer.bboxHeight);
        }*/

        /**
        //创建springboard物体并添加到world中
        try {
            createObj(Springboard.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/
        /**
        for(MapObject obj: tma.getTitleList("Springboard")){
            MapProperties props = obj.getProperties();
           Springboard springboard =  new Springboard((float)props.get("x"),(float)props.get("y"),mainStage);

           entities.add(springboard);
           world.add(springboard.item,springboard.getX()+springboard.bboxX,springboard.getY()+springboard.bboxY, springboard.bboxWidth, springboard.bboxHeight);
        }*/
        jack.toFront();

        /**
        //创建platform物体
        try {
            createObj(Platform.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/
        /**
        for(MapObject obj : tma.getTitleList("Platform")){
            MapProperties props = obj.getProperties();
            Platform platform = new Platform((float)props.get("x"),(float)props.get("y"),mainStage);

            entities.add(platform);
            world.add(platform.item,platform.getX()+platform.bboxX,platform.getY()+platform.bboxY, platform.bboxWidth, platform.bboxHeight);

        }*/

        keyList = new ArrayList<Color>();

        /**
        //创建key物体
        try {
            createObj(Key.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/
        /**
        for(MapObject obj: tma.getTitleList("Key")){
            MapProperties props = obj.getProperties();
            Key key = new Key((float)props.get("x"),(float)props.get("y"),mainStage);
            String color = (String)props.get("color");
            if(color.equals("red"))
                key.setColor(Color.RED);
            else
                key.setColor(Color.WHITE);

            entities.add(key);
            world.add(key.item,key.getX()+key.bboxX,key.getY()+key.bboxY,key.bboxWidth,key.bboxHeight);
        }*/

        /**
        //创建lock物体
        try {
            createObj(Lock.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }*/
        /**
        for(MapObject obj: tma.getTitleList("Lock")){
            MapProperties props = obj.getProperties();
            Lock lock = new Lock((float)props.get("x"),(float)props.get("y"),mainStage);
            String color = (String)props.get("color");
            if(color.equals("red"))
                lock.setColor(Color.RED);
            else
                lock.setColor(Color.WHITE);

            entities.add(lock);
            world.add(lock.item,lock.getX()+lock.bboxX,lock.getY()+lock.bboxY,lock.bboxWidth,lock.bboxHeight);
        }*/

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
        /**
        for(BaseActor flag: BaseActor.getList(mainStage,"com.platformjump.game.Flag")){
            if(jack.overlaps(flag)){
                messageLabel.setText("You Win!");
                messageLabel.setColor(Color.LIME);
                messageLabel.setVisible(true);
                jack.remove();
                gameOver = true;
            }
        }*/

        timeLabel.setText("Time: " + (int)jack.getGameLeftTime());
       // time -=dt;
       // timeLabel.setText("Time: " + (int)time);
        /**
        for(BaseActor timer: BaseActor.getList(mainStage,"com.platformjump.game.Timer")){
            if(jack.overlaps(timer)){
                time += 20;
                timer.remove();
            }
        }*/

        if(jack.getGameLeftTime() <= 0){
            messageLabel.setText("Time Up- Game Over");
            messageLabel.setColor(Color.RED);
            messageLabel.setVisible(true);
            //jack.remove();
            gameOver = true;
        }
        /**
        if(time <= 0){
            messageLabel.setText("Time Up- Game Over");
            messageLabel.setColor(Color.RED);
            messageLabel.setVisible(true);
            jack.remove();
            gameOver = true;
        }*/

        /**
        for(BaseActor springboard: BaseActor.getList(mainStage,"com.platformjump.game.Springboard")){
            if(jack.overlaps(springboard) && jack.isFalling()){
                jack.spring();
            }
        }*/

        /**
        for(BaseActor coin: BaseActor.getList(mainStage,"com.platformjump.game.Coin")){
            if(jack.overlaps(coin)){
                coins++;
                coinLabel.setText("Coins: " + coins);
                coin.remove();
            }
        }*/
        coinLabel.setText("Coins: " + jack.getCoin());


      //  for(BaseActor key: BaseActor.getList(mainStage,"com.platformjump.game.Key")){
           // if(jack.overlaps(key)){
            //    Color keyColor = key.getColor();
            //    key.remove();
                /**
                BaseActor keyIcon = new BaseActor(0,0,uiStage);
                keyIcon.loadTexture("key-icon.png");
                keyIcon.setColor(keyColor);
                keyTable.add(keyIcon);*/
              //  keyIcon.setColor(keyColor);
              //  keyIcon.setVisible(true);
               // keyList.add(keyColor);
        //    }
      //  }



        if(jack.isHasKey()){
            keyIcon.setColor(jack.getKeyColor());
            keyIcon.setVisible(true);
        }




        //处理actor与solid类型的物体的碰撞
        //Platform物体也在此可以获得，因为Platform是Solid物体的子类
     //   for(BaseActor actor: BaseActor.getList(mainStage,"com.platformjump.game.Solid")){
         //   Solid solid = (Solid) actor;



            //判断solid是platform物体的实例
         //   if(solid instanceof Platform){
                /**
                //jack跳跃并且与platform重叠，则platform可以穿越，就是不进行碰撞
                if(jack.isJumping() && jack.overlaps(solid)){
                    solid.setEnabled(false);
                }*/

                /**
                //jack跳跃，并且没有与platform重叠，则platform恢复碰撞功能
                if(jack.isJumping() && !jack.overlaps(solid)){
                    solid.setEnabled(true);
                }

                //jack在落下，并且没有与plaform重叠，则platform恢复碰撞功能
                if(jack.isFalling() && !jack.overlaps(solid) ){
                    solid.setEnabled(true);
                }*/
          //  }

            /**
            if(solid instanceof Lock && jack.overlaps(solid)){
                Color lockColor = solid.getColor();
                if(keyList.contains(lockColor)){
                    solid.setEnabled(false);
                    solid.addAction(Actions.fadeOut(0.5f));
                    solid.addAction(Actions.after(Actions.removeActor()));
                }
            }*/


            /**
            Response.Result result = world.move(jack.item, jack.getX()+jack.bboxX,jack.getY()+jack.bboxY,Koala.PLAYER_COLLISION_FILTER);
            for(int i = 0; i < result.projectedCollisions.size();i++){
                Collision collision = result.projectedCollisions.get(i);
                if(collision.other.userData instanceof Solid){
                    Gdx.app.log(TAG,"collison.normal= " +collision.normal);
                    if(collision.normal.x != 0){
                        //hit a wall

                        jack.velocityVec.x = 0;
                    }

                    if(collision.normal.y != 0){
                        //hit ceiling or floor
                        jack.velocityVec.y = 0;
                    }
                }
            }*/

            /**
            if(jack.overlaps(solid) && solid.isEnabled()){
                Vector2 offset = jack.preventOverlap(solid);
                //如何绘制出碰撞向量,以及多边形
                if(offset != null){
                    //Gdx.app.log(TAG,"offset= " +offset);
                    //在x方向碰撞，则x方向上的速度设置为0
                    if(Math.abs(offset.x) > Math.abs(offset.y)){
                       // Gdx.app.log(TAG,"offset= " +offset);
                        //Gdx.app.log(TAG,"error: it is y direction collison, but it shows x direction collision!!!");
                        jack.velocityVec.x = 0;
                    }else {
                        jack.velocityVec.y = 0;
                    }

                }
            }*/
      //  }

    }


    @Override
    public boolean keyDown(int keycode) {

        if(gameOver)
            return false;

        /**
        //判断是否按下空格键和DOWN键
        if(keycode == Input.Keys.SPACE){
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                for(BaseActor actor : BaseActor.getList(mainStage,"com.platformjump.game.Platform")){
                    Platform platform = (Platform)actor;
                    if(jack.belowOverlaps(platform)){
                        platform.setEnabled(false);
                    }
                }
            }else if(jack.isOnSolid()){
                jack.jump();
            }

        }
        return false;*/
        return false;
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
