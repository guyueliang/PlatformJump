package com.platformjump.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import space.earlygrey.shapedrawer.ShapeDrawer;
//import sun.security.krb5.internal.APOptions;

public class Koala extends BaseActor{
    public static final String TAG = Koala.class.getSimpleName();
    private Animation stand;
    private Animation walk;

    private float walkAcceleration;
    private float walkDeceleration;
    private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;

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


    public Koala(float x, float y, Stage s) {
        super(x, y, s);
        stand = loadTexture("koala/stand.png");
        String[] walkFileNames = {
                "koala/walk-1.png","koala/walk-2.png",
                "koala/walk-3.png","koala/walk-2.png"
        };

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
        belowSensor.setVisible(true);

       // polygon_test = new BaseActor(0,0,s);
       // polygon_test.loadTexture("polygon_test.png");

        //pixmap = new Pixmap(Gdx.files.internal("polygon_test.png"));
       // pixmap = new Pixmap(32, 56, Pixmap.Format.RGBA8888);
        //pixmap.setColor(0,0,0,1);
        //texture = new Texture(Gdx.files.internal("polygon_test.png"));
        //textureRegion = new TextureRegion(texture);
       // drawer = new ShapeDrawer(s.getBatch(),textureRegion);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);

        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        region = new TextureRegion(texture, 0, 0, 1, 1);
        drawer = new ShapeDrawer(s.getBatch(),region);


        maxHorizontalSpeed = 200;
        walkAcceleration = 300;
        walkDeceleration = 500;
        gravity = 500;
        maxVerticalSpeed = 700;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        //根据玩家的输入，设置水平方向的加速度
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            accelerationVec.add(-walkAcceleration,0);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            accelerationVec.add(walkAcceleration,0);
        }

        //设置垂直方向的加速度,垂直加速度是固定的
        accelerationVec.add(0,-gravity);

        //设置速度向量
        velocityVec.add(accelerationVec.x*delta,accelerationVec.y*delta);


        //如果没有按左右方向的行走按键
        if(!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            float decelerationAmount = walkDeceleration*delta;

            float walkDirection;

            //设置行走方向
            if(velocityVec.x > 0){
                walkDirection = 1;
            }else{
                walkDirection = -1;
            }

            float walkSpeed = Math.abs(velocityVec.x);

            walkSpeed -= decelerationAmount;

            if(walkSpeed < 0){
                walkSpeed = 0;
            }

            velocityVec.x = walkSpeed* walkDirection;
        }

        //限制速度向量
        velocityVec.x = MathUtils.clamp(velocityVec.x,-maxHorizontalSpeed,maxHorizontalSpeed);
        velocityVec.y = MathUtils.clamp(velocityVec.y,-maxVerticalSpeed,maxVerticalSpeed);

        //将速度向量添加到位置
        moveBy(velocityVec.x*delta,velocityVec.y*delta);
        accelerationVec.set(0,0);

        belowSensor.setPosition(getX()+4,getY()-8);
       // polygon_test.setPosition(getX(),getY());


        //绘制actor的碰撞形状，方便调试
        /**float[] vertices = boundaryPolygon.getTransformedVertices();
        int m =0;
        for(int k = 0; k<vertices.length/2; k++) {
            Gdx.app.log(TAG,"vertices[" + k +"]"+vertices[k]+","+vertices[k+1]);
            pixmap.drawPixel((int)vertices[m],(int)(getHeight()-vertices[m+1]));
            m = m+2;
        }*/
       // int len = vertices.length/2-1;
        //int j =0;
       // for(int i=0; i<len;i=i+1){
            //pixmap.drawLine((int)vertices[j],(int)vertices[j+1],(int)vertices[j+2],(int)vertices[j+3]);
           // j = j+2;

       // j=j-2;
       // pixmap.drawLine((int)vertices[j],(int)vertices[j+1],(int)vertices[0],(int)vertices[1]);
        //以actor的中心绘制圆，仅仅是测试
       // pixmap.drawCircle((int)(getWidth()/2),(int)(getHeight()/2),10);

       // Texture texture = new Texture(pixmap);

      //  tt = texture;

        polygon_vertices = this.getBoundaryPolygon().getTransformedVertices();

        alignCamera();
        boundToWorld();

        if(this.isOnSolid()){
            belowSensor.setColor(Color.GREEN);
            if(velocityVec.x == 0){
                setAnimation(stand);
            }else{
                setAnimation(walk);
            }
        }else{
            belowSensor.setColor(Color.RED);
            setAnimation(jump);
        }


        if(velocityVec.x > 0){
            setScaleX(1);
        }

        if(velocityVec.x < 0){
            setScaleX(-1);
        }




    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // 使用shapedrawer绘制图形
        if( region != null && drawer != null){
            drawer.setColor(1,0,0,1);
            //drawer.line(0,0,300,300);
            //drawer.setColor(Color.BROWN);
           // drawer.line(350,250,490,780);
            //drawer.polygon(polygon_vertices);
        }
    }

    //判断koala是否站在solid物体上,不仅仅需要判断两者是否相碰撞，还需要判断belowsensor 物体是否在solid 类型物体之上，否则会出错
    public boolean belowOverlaps(BaseActor actor){
        return belowSensor.overlaps(actor);
    }

    public boolean isOnSolid(){
        //遍历所有的solid物体
        for(BaseActor actor: BaseActor.getList(getStage(),"com.platformjump.game.Solid")){
            Solid solid = (Solid)actor;
            //这里多出一个判断，增加正确的可靠性
            if(belowOverlaps(solid) && solid.isEnabled() && (belowSensor.getY()-solid.getY())>=(solid.getHeight()-10)){
                return true;
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
        velocityVec.y = 1.5f*jumpSpeed;
    }

    //判断是否正在向上跳跃
    public boolean isJumping(){
        return (velocityVec.y > 0);
    }
}
