package com.platformjump.game.Item;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.*;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGameActor;
import com.platformjump.game.BaseFramework.BaseGameStage;
import com.platformjump.game.Utils.Utils;

import java.util.Random;

public class Fruit extends BaseGameActor {

    private Vector2 vec;
    private boolean inAir;
    private int rNum;
    private Random random;

    private float horizontalDeceleration;
    // private float maxHorizontalSpeed;
    private float gravity;
    private float maxVerticalSpeed;
    private  Fruit.FruitCollisionFilter FRUIT_COLLISION_FILTER = new Fruit.FruitCollisionFilter();
    public static final Collisions tempCollisions = new Collisions();

    public Fruit(float x, float y, BaseGameStage s) {
        super(x, y, s);

        //包围盒的起始坐标与actor的坐标x和y方向的距离
        bboxX = 10;
        bboxY = 10;
        //包围盒的宽度和高度
        bboxWidth = 20;
        bboxHeight = 20;
        item = new Item<BaseActor>(this);

        random = new Random();
        rNum = random.nextInt(150)+150;
        int rNum2 = random.nextInt(2);
        if(rNum2 == 1){
            rNum *= -1;
        }
        vec = new Vector2();
        vec.set(rNum,-150);
        /**
        if(rNum == 0)
            vec.set(300,-150);
        else if(rNum == 1){
            vec.set(-300,-150);
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
                    getX()+bboxX,getY()+bboxY-.1f,FRUIT_COLLISION_FILTER,tempCollisions);
            if(tempCollisions.size() == 0){
                inAir = true;
            }

        }

        vec.x = Utils.approach(vec.x, 0, horizontalDeceleration*delta);

        if(inAir){
            vec.y = Utils.approach(vec.y,maxVerticalSpeed,gravity*delta);
            vec.y += gravity*delta;

        }


        moveBy(vec.x*delta,vec.y*delta);
        boundToWorld();

        // System.out.println("hello Apple");


        if(flag) {
            Response.Result result = baseGameStage.getMainGame().getWorld().move(item, getX() + bboxX, getY() + bboxY, FRUIT_COLLISION_FILTER);
            for (int i = 0; i < result.projectedCollisions.size(); i++) {
                Collision collision = result.projectedCollisions.get(i);

                if (collision.other.userData instanceof Solid) {
                    if (collision.normal.x != 0) {
                        vec.x = 0;
                    }

                    if (collision.normal.y != 0) {
                        vec.y = 0;
                        inAir = false;
                    }
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



    /**
     * Slide on blocks, detect collisions with enemies
     */
    private  class FruitCollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {

            if (other.userData instanceof Solid ){
                return Response.slide;
            }

            return null;
        }
    }
}
