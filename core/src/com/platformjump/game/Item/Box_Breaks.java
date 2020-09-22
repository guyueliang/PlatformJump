package com.platformjump.game.Item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.dongbat.jbump.*;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGameActor;
import com.platformjump.game.BaseFramework.BaseGameStage;
import com.platformjump.game.Utils.Utils;

import java.util.Random;

public class Box_Breaks extends BaseGameActor {

    protected Animation boxBreaks;
    protected int rNum;
    private int rNum2;
    protected Random random;
    protected TextureRegion textureRegion1;

    private Vector2 vec;
    private boolean inAir;

    private float horizontalDeceleration;
    // private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;
    public static final Box_BreaksCollisionFilter BOX_BREAKS_COLLISION_FILTER = new Box_BreaksCollisionFilter();
    public static final Collisions tempCollisions = new Collisions();

    private boolean hitSolid = false;
    private int hitCount = 0;
    private float survieTime = 3.0f;

    Action action5;


    public Box_Breaks(float x, float y, BaseGameStage s) {
        super(x, y, s);
        //这一部分放到子类中完成
        /**
        boxBreaks = loadAnimationFromSheet("Boxes/Box1/Break.png",1,4,0.1f,true);
        Object[] textureRegions = boxBreaks.getKeyFrames();

        int len = textureRegions.length;

        //根据随机数来决定显示哪一帧图形

        rNum = random.nextInt(len);
        textureRegion1 = (TextureRegion) textureRegions[rNum];*/

        bboxX = 10;
        bboxY = 8;
        //包围盒的宽度和高度
        bboxWidth = 10;
        bboxHeight = 11;
        item = new Item<BaseActor>(this);



        random = new Random();
        rNum2 = random.nextInt(2);
        int rNum3 = random.nextInt(60) + 50;
        int rNum4 = random.nextInt(80) + 60;
        vec = new Vector2();
        if(rNum2 == 1){
            rNum3 *= -1;
        }
        vec.set(rNum3,-rNum4);
        /**
         if(rNum2 == 0)
         vec.set(100,-150);
         else if(rNum2 == 1){
         vec.set(-100,-150);
         }*/

        horizontalDeceleration = 200;
        //maxHorizontalSpeed = 300;

        gravity = -200;
        maxVerticalSpeed = -600;

        inAir = true;


    }

    @Override
    public void act(float delta) {
        super.act(delta);

        boolean flag = baseGameStage.getMainGame().getWorld().hasItem(this.item);

        if(flag){
            baseGameStage.getMainGame().getWorld().project(item,getX()+bboxX,getY()+bboxY,bboxWidth,bboxHeight,
                    getX()+bboxX,getY()+bboxY-.1f,BOX_BREAKS_COLLISION_FILTER,tempCollisions);
            if(tempCollisions.size() == 0){
                inAir = true;
            }else{
                hitSolid = true;
                ++hitCount;
            }

            if(hitSolid && hitCount == 1){
                Action action = Actions.fadeOut(.2f);
                //Action action1 = Actions.delay(.2f);
                Action action2 = Actions.fadeIn(.2f);
                //Action action3 = Actions.alpha(0.2f,1.0f);
                Action action4 = Actions.sequence(action,action2);
                action5 = Actions.forever(action4);
                addAction(action5);
                survieTime -= delta;
            }else if(hitCount > 0){
                survieTime -= delta;

            }

            if(survieTime <= 0) {
                removeAction(action5);

                baseGameStage.getMainGame().getEntities().removeValue(this,true);
                baseGameStage.getMainGame().getWorld().remove(item);
                remove();
            }


        }

        flag = baseGameStage.getMainGame().getWorld().hasItem(this.item);

        vec.x = Utils.approach(vec.x, 0, horizontalDeceleration*delta);

        if(inAir){
            vec.y = Utils.approach(vec.y,maxVerticalSpeed,gravity*delta);
            vec.y += gravity*delta;

        }


        moveBy(vec.x*delta,vec.y*delta);
        boundToWorld();

        // System.out.println("hello Apple");


        if(flag) {
            Response.Result result = baseGameStage.getMainGame().getWorld().move(item, getX() + bboxX, getY() + bboxY, BOX_BREAKS_COLLISION_FILTER);
            for (int i = 0; i < result.projectedCollisions.size(); i++) {
                Collision collision = result.projectedCollisions.get(i);

                if (collision.other.userData instanceof Solid) {

                    // ++hitCount;
                    if (collision.normal.x != 0) {
                        vec.x = 0;
                        //hitSolid = true;
                    }

                    if (collision.normal.y != 0) {
                        vec.y = 0;
                        inAir = false;
                        //hitSolid = true;
                    }

                    //hitSolid = false;
                }

            }

            //根据碰撞，更新位置
            Rect rect = baseGameStage.getMainGame().getWorld().getRect(item);
            if(rect != null){
                setX(rect.x-bboxX);
                setY(rect.y-bboxY);
            }
        }



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color c = getColor();
        batch.setColor(c.r,c.g,c.b,c.a);

        if(textureRegion1 != null && isVisible()){
            batch.draw(
                    textureRegion1,getX(),getY(),getOriginX(),getOriginY(),
                    getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation()
            );

        }

        //使父类不再绘制其animation
        setVisible(false);
        super.draw(batch, parentAlpha);
        setVisible(true);
    }


    /**
     * Slide on blocks, detect collisions with enemies
     */
    public static class Box_BreaksCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {

            if (other.userData instanceof Solid ){
                return Response.slide;
            }

            return null;
        }
    }
}

