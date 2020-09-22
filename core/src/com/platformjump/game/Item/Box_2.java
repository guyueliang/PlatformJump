package com.platformjump.game.Item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGameActor;
import com.platformjump.game.BaseFramework.BaseGameStage;
import com.platformjump.game.Player.Koala;
import com.platformjump.game.Utils.Fruits;

import java.util.Random;

public class Box_2 extends BaseGameActor {

    private Animation Idle;
    private Animation Hit;

    public boolean contactWithPlayer = false;
    public int hitCount;
    public float hitTimer;
    private Random random;
    private int rNum;
    private  static final int generateFruitNum = 5;
    private float maxShowTime = 0.4f;
    private float timeCounter = 0.0f;

    public final Box_2_CollisionFilter BOX_2_COLLISION_FILTER = new Box_2_CollisionFilter();

    public Box_2(float x, float y, BaseGameStage s) {
        super(x, y, s);

        Idle = loadTexture("Boxes/Box2/Idle.png");
        Hit = loadAnimationFromSheet("Boxes/Box2/Hit.png",1,4,0.08f,true);

        hitCount = 0;
        setAnimation(Idle);

        //包围盒的起始坐标与Player的坐标x和y方向的距离
        bboxX = 0;
        bboxY = 0;
        //包围盒的宽度和高度
        bboxWidth = 28;
        bboxHeight = 24;
        item = new Item<BaseActor>(this);

        random = new Random();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(contactWithPlayer ){
            setAnimation(Hit);
            timeCounter += delta;

            //player每次与box碰撞，box会随机生成一种水果
            rNum = random.nextInt(7);
            Fruits fr = Fruits.getByValue(rNum);
            switch (fr){
                case APPLE:{
                    Apple apple_ = new Apple(getX()+getWidth()/2+20,getY()+getHeight()/2+20,baseGameStage);
                    baseGameStage.getMainGame().getWorld().add(apple_.item, apple_.getX()+bboxX, apple_.getY()+bboxY, apple_.bboxWidth, apple_.bboxHeight);
                    baseGameStage.getMainGame().getEntities().add(apple_);
                    break;
                }

                case BANANA:{
                    Banana banana = new Banana(getX()+getWidth()/2-10,getY()+getHeight()/2-10,baseGameStage);
                    baseGameStage.getMainGame().getWorld().add(banana.item, banana.getX()+bboxX, banana.getY()+bboxY, banana.bboxWidth, banana.bboxHeight);
                    baseGameStage.getMainGame().getEntities().add(banana);
                    break;

                }

                case CHERRIES:{
                    Cherries cherries = new Cherries(getX()+getWidth()/2+5,getY()+getHeight()/2-5,baseGameStage);
                    baseGameStage.getMainGame().getWorld().add(cherries.item, cherries.getX()+bboxX, cherries.getY()+bboxY, cherries.bboxWidth, cherries.bboxHeight);
                    baseGameStage.getMainGame().getEntities().add(cherries);
                    break;

                }

                case KIWI:{
                    Kiwi kiwi = new Kiwi(getX()+getWidth()/2+5,getY()+getHeight()/2-5,baseGameStage);
                    baseGameStage.getMainGame().getWorld().add(kiwi.item, kiwi.getX()+bboxX, kiwi.getY()+bboxY, kiwi.bboxWidth, kiwi.bboxHeight);
                    baseGameStage.getMainGame().getEntities().add(kiwi);
                    break;

                }

                case MELON:{
                    Melon melon = new Melon(getX()+getWidth()/2+10,getY()+getHeight()/2+10,baseGameStage);
                    baseGameStage.getMainGame().getWorld().add(melon.item, melon.getX()+bboxX, melon.getY()+bboxY, melon.bboxWidth, melon.bboxHeight);
                    baseGameStage.getMainGame().getEntities().add(melon);
                    break;

                }

                case ORANGE:{
                    Orange orange = new Orange(getX()+getWidth()/2+5,getY()+getHeight()/2-5,baseGameStage);
                    baseGameStage.getMainGame().getWorld().add(orange.item, orange.getX()+bboxX, orange.getY()+bboxY, orange.bboxWidth, orange.bboxHeight);
                    baseGameStage.getMainGame().getEntities().add(orange);
                    break;

                }

                case STRAWBERRY:{
                    Strawberry strawberry = new Strawberry(getX()+getWidth()/2+5,getY()+getHeight()/2-5,baseGameStage);
                    baseGameStage.getMainGame().getWorld().add(strawberry.item, strawberry.getX()+bboxX, strawberry.getY()+bboxY, strawberry.bboxWidth, strawberry.bboxHeight);
                    baseGameStage.getMainGame().getEntities().add(strawberry);
                    break;

                }
            }
        }

        if(timeCounter > 0 && timeCounter <= maxShowTime){
            timeCounter += delta;
        }

        if(timeCounter > maxShowTime){
            setAnimation(Idle);
            timeCounter = 0;
        }

        //生成碎片
        if(hitCount == 5 ){
            Box2_Breaks box2_breaks = new Box2_Breaks(getX(),getY(),baseGameStage);
            Box2_Breaks box2_breaks1 = new Box2_Breaks(getX()+10,getY()-5,baseGameStage);
            Box2_Breaks box2_breaks2 = new Box2_Breaks(getX()-10,getY()+5,baseGameStage);
            Box2_Breaks box2_breaks3 = new Box2_Breaks(getX()+5,getY()+10,baseGameStage);

            baseGameStage.getMainGame().getWorld().add(box2_breaks.item, box2_breaks.getX()+bboxX, box2_breaks.getY()+bboxY, box2_breaks.bboxWidth, box2_breaks.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(box2_breaks);

            baseGameStage.getMainGame().getWorld().add(box2_breaks1.item, box2_breaks1.getX()+bboxX, box2_breaks1.getY()+bboxY, box2_breaks1.bboxWidth, box2_breaks1.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(box2_breaks1);

            baseGameStage.getMainGame().getWorld().add(box2_breaks2.item, box2_breaks2.getX()+bboxX, box2_breaks2.getY()+bboxY, box2_breaks2.bboxWidth, box2_breaks2.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(box2_breaks2);

            baseGameStage.getMainGame().getWorld().add(box2_breaks3.item, box2_breaks3.getX()+bboxX, box2_breaks3.getY()+bboxY, box2_breaks3.bboxWidth, box2_breaks3.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(box2_breaks3);

            //从world和entities以及stage去除该对象
            baseGameStage.getMainGame().getWorld().remove(this.item);
            baseGameStage.getMainGame().getEntities().removeValue(this,true);
            remove();
        }

        boolean flag = baseGameStage.getMainGame().getWorld().hasItem(this.item);

        if(flag) {
            Response.Result result = baseGameStage.getMainGame().getWorld().move(item, getX() + bboxX, getY() + bboxY, BOX_2_COLLISION_FILTER);

            if (result.projectedCollisions.size() <= 0) {
                contactWithPlayer = false;
            }

        }


    }



    //由于Box2每帧都需要获取自身是否需要更新帧动画，所以需要添加碰撞过滤器，判断是否与player碰撞
    private  class Box_2_CollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {

            if (other.userData instanceof Koala){
                return Response.slide;
            }

            return null;
        }
    }
}
