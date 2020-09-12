package com.platformjump.game.Player;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.*;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGameActor;
import com.platformjump.game.BaseFramework.BaseGameStage;
import com.platformjump.game.BaseFramework.BaseStage;
import com.platformjump.game.GameStage;
import com.platformjump.game.Item.*;
import com.platformjump.game.LevelScreen;
import com.platformjump.game.Utils.Utils;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
//import sun.security.krb5.internal.APOptions;

public class Koala extends BaseGameActor {
    public static final String TAG = Koala.class.getSimpleName();
    private Animation stand;
    private Animation walk;

    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;

    //跳跃的最大时间
    public static final float JUMP_MAX_TIME = .25f;
    private float jumpTime;
    private boolean jumping;

    //重力加速度
    public static final float GRAVITY = 3000f;


    private Animation jump;
    private float jumpSpeed;
    private BaseActor belowSensor;

    //private BaseActor polygon_test;
  //  private Pixmap pixmap;
   // private Texture test;
    //private Texture texture;

    protected  TextureRegion region;
    protected ShapeDrawer drawer;
    protected float[] polygon_vertices;

    public static final PlayerCollisionFilter PLAYER_COLLISION_FILTER = new PlayerCollisionFilter();

    public static final Collisions tempCollisions = new Collisions();


    private boolean onPlatform;
    private boolean downPlatform;

    private int coin;
    private boolean hasKey;
    private Color keyColor;
    ArrayList<Color> keyList;
    private boolean isWin;
    private  float gameLeftTime;

    public Koala(float x, float y, BaseGameStage s) {
        super(x, y, s);
        stand = loadTexture("koala/stand.png");
        String[] walkFileNames = {
                "koala/walk-1.png","koala/walk-2.png",
                "koala/walk-3.png","koala/walk-2.png"
        };

        //包围盒的起始坐标与Player的坐标x和y方向的距离
        bboxX = 0;
        bboxY = 0;
        //包围盒的宽度和高度
        bboxWidth = 36;
        bboxHeight = 52;
        item = new Item<BaseActor>(this);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        walk = loadAnimationFromFiles(walkFileNames,0.2f,true);

        jump = loadTexture("koala/jump.png");

        jumpSpeed = 400;
        //setBoundaryRectangle();
        setBoundaryPolygon(10);
        belowSensor = new BaseActor(0,0,s);
        belowSensor.loadTexture("white.png");
        belowSensor.setSize(this.getWidth()-8,8);
        belowSensor.setBoundaryRectangle();
        belowSensor.setVisible(false);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);

        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        region = new TextureRegion(texture, 0, 0, 1, 1);
        drawer = new ShapeDrawer(s.getBatch(),region);



        maxHorizontalSpeed = 300;
        walkAcceleration = 600;
        walkDeceleration = 200;
        gravity = 500;
        maxVerticalSpeed = 600;
        gravityY = -GRAVITY;
        accelerationVec.x = walkAcceleration;
        onPlatform = false;
        downPlatform = false;

        coin = 0;
        hasKey = false;
        keyList = new ArrayList<Color>();
        keyColor = new Color(0,1,0,1);
        gameLeftTime = 40;

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        //减少游戏时间
        gameLeftTime -= delta;

        velocityVec.x = Utils.approach(velocityVec.x, 0, walkDeceleration*delta);

        boolean left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean upJustPressed = Gdx.input.isKeyJustPressed(Input.Keys.UP);

        //根据玩家的输入，设置水平方向的速度
        if(right){
            setAnimation(walk);
            setScaleX(1);
            velocityVec.x = Utils.approach(velocityVec.x,maxHorizontalSpeed,accelerationVec.x*delta);
        }else if(left){
            setAnimation(walk);
            setScaleX(-1);
            velocityVec.x = Utils.approach(velocityVec.x,-maxHorizontalSpeed,accelerationVec.x*delta);
        }else{
            setAnimation(stand);
            velocityVec.x = Utils.approach(velocityVec.x, 0,accelerationVec.x*delta);
        }

        if(!up)
            jumping = false;
        if(upJustPressed){
            //判断是否在地面
            world.project(item,getX()+bboxX,getY()+bboxY,bboxWidth,bboxHeight,
                    getX()+bboxX,getY()+bboxY-.1f,PLAYER_COLLISION_FILTER,tempCollisions);
            if(tempCollisions.size() > 0){
                jumping = true;

            }

        }

        //当按UP键时，持续向上加速
        if(up && jumping && jumpTime < JUMP_MAX_TIME){
            velocityVec.y = maxVerticalSpeed;
            jumpTime += delta;
        }

        //根据玩家的输入更新位置
        velocityVec.x += delta*gravityX;
        velocityVec.y += delta*gravityY;
        moveBy(velocityVec.x*delta,velocityVec.y*delta);

        downPlatform = Gdx.input.isKeyPressed(Input.Keys.DOWN);


        //TODO 使player不与特定关卡的world和entitines变量相关，这样可以去耦合，使player游戏逻辑更加通用
        //处理碰撞
        boolean inAir = true;
        Response.Result result = world.move(item, getX()+bboxX,getY()+bboxY,PLAYER_COLLISION_FILTER);
        for(int i = 0; i < result.projectedCollisions.size();i++){
            Collision collision = result.projectedCollisions.get(i);

            if(collision.other.userData instanceof Solid){
                Solid solid = (Solid)collision.other.userData;
                //Gdx.app.log(TAG,"collison.normal= " +collision.normal);

                if(solid.isEnabled()){
                    if(collision.normal.x != 0){
                        //hit a wall
                        velocityVec.x = 0;
                    }

                    if(collision.normal.y != 0){
                        //hit ceiling or floor
                        velocityVec.y = 0;
                        jumpTime = JUMP_MAX_TIME;

                        if(collision.normal.y == 1){
                            //碰撞到地板
                            jumpTime = 0f;
                            jumping = false;
                            inAir = false;

                            if(solid instanceof Platform){
                                onPlatform = true;
                            }
                        }
                    }
                }else if(solid instanceof Lock){
                    solid.addAction(Actions.fadeOut(0.5f));
                    solid.addAction(Actions.after(Actions.removeActor()));
                    entities.removeValue(solid,true);
                    world.remove(collision.other);
                    solid.remove();
                }
            }else if(collision.other.userData instanceof Springboard){
                //如果是冲刺板
                if(isFalling())
                    spring();
            }else if(collision.other.userData instanceof Coin){
                //如果是金币
                Coin coin = (Coin)collision.other.userData;
                addCoin(1);
                entities.removeValue(coin,true);
                world.remove(collision.other);
                coin.remove();
            }else if(collision.other.userData instanceof Key){
                //如果是钥匙
                Key key = (Key)collision.other.userData;
                setHasKey(true);
                keyList.add(key.getColor());
                setKeyColor(key.getColor());

                entities.removeValue(key,true);
                world.remove(collision.other);
                key.remove();
            }else if(collision.other.userData instanceof Flag){
                setWin(true);

            }else if(collision.other.userData instanceof Timer){
                Timer timer = (Timer) collision.other.userData;
                gameLeftTime += 20;
                entities.removeValue(timer,false);
                world.remove(collision.other);
                timer.remove();

            }


        }

        //根据碰撞，更新位置
        Rect rect = world.getRect(item);
        if(rect != null){
            setX(rect.x-bboxX);
            setY(rect.y-bboxY);
        }

        //根据碰撞设置动画
        if(inAir)
            setAnimation(jump);

        alignCamera();
        boundToWorld();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        /**
        // 使用shapedrawer绘制图形
        if( region != null && drawer != null){
            drawer.setColor(1,0,0,1);
            Rect rect = GameStage.world.getRect(item);
            drawer.rectangle(rect.x, rect.y, rect.w, rect.h);
            //drawer.line(0,0,300,300);
            //drawer.setColor(Color.BROWN);
           // drawer.line(350,250,490,780);
            //drawer.polygon(polygon_vertices);
        }*/
    }

    //判断koala是否站在solid物体上,不仅仅需要判断两者是否相碰撞，还需要判断belowsensor 物体是否在solid 类型物体之上，否则会出错
    public boolean belowOverlaps(BaseActor actor){
        return belowSensor.overlaps(actor);
    }


    public boolean isOnSolid(){
        /**
        //遍历所有的solid物体
        for(BaseActor actor: BaseActor.getList(getStage(),"com.platformjump.game.Item.Solid")){
            Solid solid = (Solid)actor;
            //这里多出一个判断，增加正确的可靠性
            if(belowOverlaps(solid) && solid.isEnabled() && (belowSensor.getY()-solid.getY())>=(solid.getHeight()-10)){
                return true;
            }
        }*/

        Response.Result result = world.move(item, getX() + bboxX, getY() + bboxY, PLAYER_COLLISION_FILTER);
        for (int i = 0; i < result.projectedCollisions.size(); i++){
            Collision collision = result.projectedCollisions.get(i);
            if (collision.other.userData instanceof Solid){
                if (collision.normal.y != 0){
                    if (collision.normal.y == 1)
                        return true;

                }

            }
        }

        return false;
    }

    public void jump(){
        velocityVec.y = jumpSpeed;
    }

    //判断是否在下降
    public boolean isFalling(){
        return (velocityVec.y < 0);
    }

    //弹跳冲刺
    public void spring(){
        velocityVec.y = 3.5f*jumpSpeed;
    }

    //判断是否正在向上跳跃
    public boolean isJumping(){
        return (velocityVec.y > 0);
    }

    public int getCoin(){
        return coin;
    }

    public void addCoin(int coin){
        this.coin += coin;
    }

    public boolean isHasKey(){
        return hasKey;
    }

    public void setHasKey(boolean hasKey){
        this.hasKey = hasKey;
    }

    public Color getKeyColor() {
        return keyColor;
    }

    public void setKeyColor(Color keyColor) {
        this.keyColor = keyColor;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public float getGameLeftTime() {
        return gameLeftTime;
    }

    public void setGameLeftTime(float gameLeftTime) {
        this.gameLeftTime = gameLeftTime;
    }

    /**
     * Slide on blocks, detect collisions with enemies
     */
    public static class PlayerCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {

            if (other.userData instanceof Solid ){
                Solid solid = (Solid)other.userData;

                //如果solid是platform类型的物体，则需要考虑koala是否正在跳跃还是降落
                //根据koala player的状态来判断是否使能或关闭solid的碰撞
                if(solid instanceof Platform){
                    Koala koala = (Koala)item.userData;
                    if(koala.isJumping()){
                        solid.setEnabled(false);
                    }else if(koala.isFalling() && !koala.downPlatform){
                        solid.setEnabled(true);
                    }else if(koala.onPlatform && koala.downPlatform && koala.isFalling()){
                        solid.setEnabled(false);
                    }

                    if(solid.isEnabled()){
                        return Response.slide;
                    }else
                        return Response.cross;
                }else if(solid instanceof Lock){
                    //如果是lock的话
                    Lock lock = (Lock)other.userData;
                    Color color = lock.getColor();
                    Koala koala = (Koala)item.userData;
                   if(koala.keyList.contains(color)){
                       lock.setEnabled(false);
                       return  Response.cross;
                   }

                }

                return Response.slide;
            } else if(other.userData instanceof Springboard){
                return Response.cross;
            }else if(other.userData instanceof Coin){
                return Response.cross;
            }else if(other.userData instanceof Key){
                return Response.cross;
            }else if(other.userData instanceof Flag){
                return Response.cross;
            }else if(other.userData instanceof Timer){
                return Response.cross;
            }

            return null;
        }
    }

}
