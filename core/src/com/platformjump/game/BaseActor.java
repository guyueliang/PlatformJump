package com.platformjump.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.jbump.Item;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.Iterator;

public class BaseActor extends Group {

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;

    //actor速度，通过一个二维向量表示
    protected Vector2 velocityVec;

    //actor加速度
    protected Vector2 accelerationVec;
    private float acceleration;

    //actor最大速度
    private float maxSpeed;

    //actor减速度
    private float deceleration;

    //actor的多边形边界
    protected Polygon boundaryPolygon;

    //actor所在世界的边界
    private static Rectangle worldBounds;

    //与碰撞相关，即碰撞包围盒
    public float bboxX;
    public float bboxY;
    public float bboxWidth;
    public float bboxHeight;

    public float gravityX;
    public float gravityY;

    //与jbump相关，用于碰撞
    public Item<BaseActor> item;


    //绘制pixmap类型的图形
    //protected Texture tt;



    public BaseActor(float x, float y, Stage s){
        super();
        setPosition(x,y);
        s.addActor(this);

        animation = null;
        elapsedTime = 0;
        animationPaused = false;

        //初始化actor速度
        velocityVec = new Vector2(0,0);

        //初始化actor加速度
        accelerationVec = new Vector2(0,0);
        acceleration = 0;

        //初始化最大速度
        maxSpeed = 1000;

        deceleration = 0;

        //Pixmap pixmap = new Pixmap(256, 256, Pixmap.Format.RGBA8888);
       // tt = new Texture(pixmap);

        //* test shapedrawer functionality



    }

    public void setAnimation(Animation<TextureRegion> anim){
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize(w,h);
        setOrigin(w/2,h/2);

        if(boundaryPolygon == null){
            setBoundaryRectangle();
        }
    }

    public void setAnimationPaused(boolean pause){
        animationPaused = pause;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(!animationPaused){
            elapsedTime += delta;
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        Color c = getColor();
        batch.setColor(c.r,c.g,c.b,c.a);

        if(animation != null && isVisible()){
            batch.draw(
                    animation.getKeyFrame(elapsedTime),getX(),getY(),getOriginX(),getOriginY(),
                    getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation()
            );

        }

        //绘制pixmap类型的图形
        //batch.draw(tt,getX(),getY());



        super.draw(batch, parentAlpha);
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames,
                                                           float frameDuration,boolean loop){

        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        for(int n = 0; n < fileCount; n++){
            String fileName = fileNames[n];
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));

        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration,textureArray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }else{
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if(animation == null){
            setAnimation(anim);
        }
        return anim;

    }

    public Animation<TextureRegion> loadAnimationFromSheet(String filename,int rows, int cols,
                                                           float frameDuration,boolean loop){
        Texture texture = new Texture(Gdx.files.internal(filename));
        texture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
        int frameWidth = texture.getWidth()/cols;
        int frameHeight = texture.getHeight()/rows;

        TextureRegion[][] temp = TextureRegion.split(texture,frameWidth,frameHeight);

        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                textureArray.add(temp[r][c]);
            }
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration,textureArray);

        if(loop){
            anim.setPlayMode(Animation.PlayMode.LOOP);
        }else{
            anim.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if(animation == null){
            setAnimation(anim);
        }
        return anim;


    }

    //for a still image，just a image
    public  Animation<TextureRegion> loadTexture(String filename){
        String[] filenames = new String[1];
        filenames[0] = filename;
        return loadAnimationFromFiles(filenames,1,true);
    }

    public boolean isAnimationFinished(){
        return animation.isAnimationFinished(elapsedTime);
    }

    //设置actor的速度
    public void setSpeed(float speed){

        //如果当前actor的速度为0，则默认的angle为0
        if(velocityVec.len() == 0){
            //只在x轴方向上有速度
            velocityVec.set(speed,0);
        }else{
            velocityVec.setLength(speed);
        }
    }

    public float getSpeed(){
        return velocityVec.len();
    }

    //设置运动的角度
    public void setMotionAngle(float angle){
        velocityVec.setAngle(angle);
    }

    public float getMotionAngle(){
        return velocityVec.angle();
    }

    //判断actor 是否在运动
    public boolean isMoving(){
        return (getSpeed() > 0);
    }

    //设置actor加速度
    public void setAcceleration(float acc){
        acceleration = acc;
    }

    //设置加速度的角度，同时设置加速度的值
    //acceleration的值是不改变的，改变的是accelerationVec这个向量
    public void accelerationAtAngle(float angle){
        accelerationVec.add(new Vector2(acceleration,0).setAngle(angle));
    }

    //向actor所面对的方向进行加速
    public void accelerateForward(){
        accelerationAtAngle(getRotation());
    }

    public void setMaxSpeed(float ms){
        maxSpeed = ms;
    }

    public void setDeceleration(float dec){
        deceleration = dec;
    }

    public void applyPhysics(float dt){
        //应用加速度
        //add方法是表示两个vector相加
        velocityVec.add(accelerationVec.x*dt,accelerationVec.y*dt);

        float speed = getSpeed();

        //当没有在加速的时候，就需要减速
        if(accelerationVec.len() == 0){
            speed -= deceleration*dt;
        }

        //使速度保持在特定范围内
        speed = MathUtils.clamp(speed,0,maxSpeed);

        //更新速度
        setSpeed(speed);

        //应用速度，更新actor位置
        moveBy(velocityVec.x*dt, velocityVec.y*dt);

        //加速度清零
        accelerationVec.set(0,0);
    }

    //设置矩形边界
    public void setBoundaryRectangle(){
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0,w,0,w,h,0,h};
        boundaryPolygon = new Polygon(vertices);
    }

    //设置多边形边界
    public void setBoundaryPolygon(int numSides){
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2*numSides];
        for(int i = 0; i < numSides; i++){
            float angle = i*6.28f / numSides;
            //x轴坐标
            vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;

            //y轴坐标
            vertices[2*i+1] = h/2 * MathUtils.sin(angle) + h/2;
        }

        boundaryPolygon = new Polygon(vertices);
    }

    //获取多边形边界
    public Polygon getBoundaryPolygon(){
        boundaryPolygon.setPosition(getX(),getY());
        boundaryPolygon.setOrigin(getOriginX(),getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(),getScaleY());
        return boundaryPolygon;
    }

    //判断两个actor是否重叠
    public boolean overlaps(BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        //先对矩形边界进行测试
        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))
            return false;

        return Intersector.overlapConvexPolygons(poly1,poly2);
    }

    //把actor放在某个位置，以该位置为中心
    public void centerAtPosition(float x, float y){
        setPosition(x-getWidth()/2,y-getHeight()/2);
    }

    //把actor放在其他actor的中心
    public void centerAtActor(BaseActor other){
        centerAtPosition(other.getX()+ other.getWidth()/2, other.getY()+ other.getHeight()/2 );
    }

    //改变actor的透明度
    public void setOpacity(float opacity){
        this.getColor().a = opacity;
    }

    //防止与障碍物actor相重叠
    public Vector2 preventOverlap(BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        //初步测试
        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return null;
        }

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1,poly2,mtv);

        if(!polygonOverlap){
            return null;
        }

        this.moveBy(mtv.normal.x*mtv.depth,mtv.normal.y* mtv.depth);
        return mtv.normal;
    }

    //获取某个类的实例对象的list
    public static ArrayList<BaseActor> getList(Stage stage, String classname){
        ArrayList<BaseActor> list = new ArrayList<BaseActor>();

        Class theClass = null;
        try{
            theClass = Class.forName(classname);
        }catch(Exception error){
            error.printStackTrace();
        }

        for(Actor a : stage.getActors()){
            if(theClass.isInstance(a)){
                list.add((BaseActor)a);
            }
        }
        return list;

    }

    //获取某个类实例对象的数量
    public static int count(Stage stage, String className){
        return getList(stage,className).size();
    }

    //设置边界
    public static void setWorldBounds(float width,float height){
        worldBounds = new Rectangle(0,0,width,height);
    }

    //设置边界
    public static void setWorldBounds(BaseActor ba){
        setWorldBounds(ba.getWidth(),ba.getHeight());
    }

    //将actor的x和y坐标限制在所设定的范围内
    public void boundToWorld(){
        if(getX() < 0){
            setX(0);
        }

        if(getX()+getWidth() > worldBounds.width){
            setX(worldBounds.width-getWidth());
        }

        if(getY() < 0){
            setY(0);
        }

        if(getY() + getHeight() > worldBounds.height){
            setY(worldBounds.height-getHeight());
        }
    }

    public void wrapAroundWorld(){
        //如果actor完全消失在游戏左边界，则在右边界出现
        if(getX()+getWidth() < 0){
            setX(worldBounds.width);
        }

        if(getX() > worldBounds.getWidth()){
            setX(-getWidth());
        }

        if(getY()+getHeight() < 0){
            setY(worldBounds.height);
        }

        if(getY() > worldBounds.getHeight()){
            setY(-getHeight());
        }
    }

    public void alignCamera(){
        Camera cam = this.getStage().getCamera();
        Viewport v = this.getStage().getViewport();

        //将camera以actor为中心
        cam.position.set(this.getX()+this.getOriginX(),this.getY()+this.getOriginY(),0);

        //将camera的坐标限制在所设置的边界范围内
        cam.position.x = MathUtils.clamp(cam.position.x,
                cam.viewportWidth/2,worldBounds.width-cam.viewportWidth/2);

        cam.position.y = MathUtils.clamp(cam.position.y,
                cam.viewportHeight/2,worldBounds.height-cam.viewportHeight/2);

        cam.update();
    }

    //判断两个actor的距离是否在distance内
    public boolean isWithinDistance(float distance,BaseActor other){
        Polygon poly1 = this.getBoundaryPolygon();
        float scaleX = (this.getWidth()+2*distance)/this.getWidth();
        float scaleY = (this.getHeight()+2*distance)/this.getHeight();

        poly1.setScale(scaleX,scaleY);

        Polygon poly2 = other.getBoundaryPolygon();

        //初步测试
        if(!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle())){
            return false;
        }

        return Intersector.overlapConvexPolygons(poly1,poly2);
    }

    //获取游戏世界边界
    public static Rectangle getWorldBounds(){
        return worldBounds;
    }



}

