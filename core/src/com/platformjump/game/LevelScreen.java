package com.platformjump.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.Response;
import com.dongbat.jbump.World;

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

    public static SnapshotArray<BaseActor> entities;
    public static World<BaseActor> world;
    public static float TILE_DIMENSION = 64f;

    @Override
    public void initialize() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        entities = new SnapshotArray<>();
        world = new World<>(TILE_DIMENSION);

        TilemapActor tma = new TilemapActor("myMap.tmx",mainStage);


        //创建solid类型的障碍物
        for(MapObject obj: tma.getRectangleList("Solid")){
            MapProperties props = obj.getProperties();
           Solid solid =  new Solid((float)props.get("x"),(float)props.get("y"),
                    (float)props.get("width"),(float)props.get("height"),
                    mainStage
                    );
           entities.add(solid);
           world.add(solid.item,solid.getX()+solid.bboxX,solid.getY()+solid.bboxY, solid.bboxWidth, solid.bboxHeight);
        }

        //创建主角
        MapObject startPoint = tma.getRectangleList("Start").get(0);
        MapProperties startProps = startPoint.getProperties();
        jack = new Koala((float)startProps.get("x"),(float)startProps.get("y"),mainStage);
        entities.add(jack);
        world.add(jack.item,jack.getX()+jack.bboxX,jack.getY()+jack.bboxY, jack.bboxWidth, jack.bboxHeight);

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
        uiTable.add(timeLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();

        //创建flag物体
        for(MapObject obj: tma.getTitleList("Flag")){
            MapProperties props = obj.getProperties();
            new Flag((float)props.get("x"),(float)props.get("y"),mainStage);
        }

        //创建金币
        for(MapObject obj: tma.getTitleList("Coin")){
            MapProperties props = obj.getProperties();
            Coin coin = new Coin((float)props.get("x"),(float)props.get("y"),mainStage);

            entities.add(coin);
            world.add(coin.item,coin.getX()+coin.bboxX,coin.getY()+coin.bboxY, coin.bboxWidth, coin.bboxHeight);

        }

        //创建时间计时器
        for(MapObject obj: tma.getTitleList("Timer")){
            MapProperties props = obj.getProperties();
            new Timer((float)props.get("x"),(float)props.get("y"),mainStage);
        }

        //创建springboard物体并添加到world中
        for(MapObject obj: tma.getTitleList("Springboard")){
            MapProperties props = obj.getProperties();
           Springboard springboard =  new Springboard((float)props.get("x"),(float)props.get("y"),mainStage);

           entities.add(springboard);
           world.add(springboard.item,springboard.getX()+springboard.bboxX,springboard.getY()+springboard.bboxY, springboard.bboxWidth, springboard.bboxHeight);
        }
        jack.toFront();

        //创建platform物体
        for(MapObject obj : tma.getTitleList("Platform")){
            MapProperties props = obj.getProperties();
            Platform platform = new Platform((float)props.get("x"),(float)props.get("y"),mainStage);

            entities.add(platform);
            world.add(platform.item,platform.getX()+platform.bboxX,platform.getY()+platform.bboxY, platform.bboxWidth, platform.bboxHeight);

        }

        keyList = new ArrayList<Color>();

        //创建key物体
        for(MapObject obj: tma.getTitleList("Key")){
            MapProperties props = obj.getProperties();
            Key key = new Key((float)props.get("x"),(float)props.get("y"),mainStage);
            String color = (String)props.get("color");
            if(color.equals("red"))
                key.setColor(Color.RED);
            else
                key.setColor(Color.WHITE);
        }

        //创建lock物体
        for(MapObject obj: tma.getTitleList("Lock")){
            MapProperties props = obj.getProperties();
            Lock lock = new Lock((float)props.get("x"),(float)props.get("y"),mainStage);
            String color = (String)props.get("color");
            if(color.equals("red"))
                lock.setColor(Color.RED);
            else
                lock.setColor(Color.WHITE);
        }

    }

    @Override
    public void update(float dt) {
        //判断游戏是否结束
        if(gameOver)
            return;

        for(BaseActor flag: BaseActor.getList(mainStage,"com.platformjump.game.Flag")){
            if(jack.overlaps(flag)){
                messageLabel.setText("You Win!");
                messageLabel.setColor(Color.LIME);
                messageLabel.setVisible(true);
                jack.remove();
                gameOver = true;
            }
        }

        time -=dt;
        timeLabel.setText("Time: " + (int)time);
        for(BaseActor timer: BaseActor.getList(mainStage,"com.platformjump.game.Timer")){
            if(jack.overlaps(timer)){
                time += 20;
                timer.remove();
            }
        }
        if(time <= 0){
            messageLabel.setText("Time Up- Game Over");
            messageLabel.setColor(Color.RED);
            messageLabel.setVisible(true);
            jack.remove();
            gameOver = true;
        }

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

        for(BaseActor key: BaseActor.getList(mainStage,"com.platformjump.game.Key")){
            if(jack.overlaps(key)){
                Color keyColor = key.getColor();
                key.remove();
                BaseActor keyIcon = new BaseActor(0,0,uiStage);
                keyIcon.loadTexture("key-icon.png");
                keyIcon.setColor(keyColor);
                keyTable.add(keyIcon);
                keyList.add(keyColor);
            }
        }


        //处理actor与solid类型的物体的碰撞
        //Platform物体也在此可以获得，因为Platform是Solid物体的子类
        for(BaseActor actor: BaseActor.getList(mainStage,"com.platformjump.game.Solid")){
            Solid solid = (Solid) actor;



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

            if(solid instanceof Lock && jack.overlaps(solid)){
                Color lockColor = solid.getColor();
                if(keyList.contains(lockColor)){
                    solid.setEnabled(false);
                    solid.addAction(Actions.fadeOut(0.5f));
                    solid.addAction(Actions.after(Actions.removeActor()));
                }
            }


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
        }

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
}
