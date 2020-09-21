package com.platformjump.game.Item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.Item;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGameActor;
import com.platformjump.game.BaseFramework.BaseGameStage;


public class Box_1 extends BaseGameActor {
    private Animation Idle;
    private Animation Hit;
    private Animation Break;
    public int hitCount;
    public float hitTimer;


    public Box_1(float x, float y, BaseGameStage s) {
        super(x, y, s);
        Idle = loadTexture("Boxes/Box1/Idle.png");
        Hit = loadAnimationFromSheet("Boxes/Box1/Hit.png",1,3,0.1f,true);
        Break = loadAnimationFromSheet("Boxes/Box1/Break.png",1,4,0.1f,true);
        hitCount = 0;
        setAnimation(Idle);

        //包围盒的起始坐标与Player的坐标x和y方向的距离
        bboxX = 0;
        bboxY = 0;
        //包围盒的宽度和高度
        bboxWidth = 28;
        bboxHeight = 24;
        item = new Item<BaseActor>(this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (hitCount == 1){
            hitCount++;
            setAnimation(Hit);
            hitTimer += delta;
        }
        if(hitCount == 2){
            hitTimer += delta;
        }
        if(hitTimer >= 0.2f ){
            //setAnimation(Break);

            baseGameStage.getMainGame().getEntities().removeValue(this,true);
            baseGameStage.getMainGame().getWorld().remove(item);


            Cherries cherries = new Cherries(getX()+getWidth()/2+5,getY()+getHeight()/2-5,baseGameStage);
            baseGameStage.getMainGame().getWorld().add(cherries.item, cherries.getX()+bboxX, cherries.getY()+bboxY, cherries.bboxWidth, cherries.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(cherries);

            Orange orange = new Orange(getX()+getWidth()/2+5,getY()+getHeight()/2-5,baseGameStage);
            baseGameStage.getMainGame().getWorld().add(orange.item, orange.getX()+bboxX, orange.getY()+bboxY, orange.bboxWidth, orange.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(orange);

            Strawberry strawberry = new Strawberry(getX()+getWidth()/2+5,getY()+getHeight()/2-5,baseGameStage);
            baseGameStage.getMainGame().getWorld().add(strawberry.item, strawberry.getX()+bboxX, strawberry.getY()+bboxY, strawberry.bboxWidth, strawberry.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(strawberry);

            Kiwi kiwi = new Kiwi(getX()+getWidth()/2+5,getY()+getHeight()/2-5,baseGameStage);
            baseGameStage.getMainGame().getWorld().add(kiwi.item, kiwi.getX()+bboxX, kiwi.getY()+bboxY, kiwi.bboxWidth, kiwi.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(kiwi);

            Melon melon = new Melon(getX()+getWidth()/2+10,getY()+getHeight()/2+10,baseGameStage);
            baseGameStage.getMainGame().getWorld().add(melon.item, melon.getX()+bboxX, melon.getY()+bboxY, melon.bboxWidth, melon.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(melon);


            Apple apple_ = new Apple(getX()+getWidth()/2+20,getY()+getHeight()/2+20,baseGameStage);
            baseGameStage.getMainGame().getWorld().add(apple_.item, apple_.getX()+bboxX, apple_.getY()+bboxY, apple_.bboxWidth, apple_.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(apple_);


            Banana banana = new Banana(getX()+getWidth()/2-10,getY()+getHeight()/2-10,baseGameStage);
            baseGameStage.getMainGame().getWorld().add(banana.item, banana.getX()+bboxX, banana.getY()+bboxY, banana.bboxWidth, banana.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(banana);

            Box1_Breaks box1_breaks = new Box1_Breaks(getX(),getY(),baseGameStage);
            Box1_Breaks box1_breaks1 = new Box1_Breaks(getX()+10,getY()-5,baseGameStage);
            Box1_Breaks box1_breaks2 = new Box1_Breaks(getX()-10,getY()+5,baseGameStage);
            Box1_Breaks box1_breaks3 = new Box1_Breaks(getX()+5,getY()+10,baseGameStage);

            baseGameStage.getMainGame().getWorld().add(box1_breaks.item, box1_breaks.getX()+bboxX, box1_breaks.getY()+bboxY, box1_breaks.bboxWidth, box1_breaks.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(box1_breaks);

            baseGameStage.getMainGame().getWorld().add(box1_breaks1.item, box1_breaks1.getX()+bboxX, box1_breaks1.getY()+bboxY, box1_breaks1.bboxWidth, box1_breaks1.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(box1_breaks1);

            baseGameStage.getMainGame().getWorld().add(box1_breaks2.item, box1_breaks2.getX()+bboxX, box1_breaks2.getY()+bboxY, box1_breaks2.bboxWidth, box1_breaks2.bboxHeight);
           baseGameStage.getMainGame().getEntities().add(box1_breaks2);

           baseGameStage.getMainGame().getWorld().add(box1_breaks3.item, box1_breaks3.getX()+bboxX, box1_breaks3.getY()+bboxY, box1_breaks3.bboxWidth, box1_breaks3.bboxHeight);
           baseGameStage.getMainGame().getEntities().add(box1_breaks3);



            remove();
        }



       // if(hitCount == 2 && Hit.isAnimationFinished(6.1f)){
          //  setAnimation(Break);
      //  }



    }
}
