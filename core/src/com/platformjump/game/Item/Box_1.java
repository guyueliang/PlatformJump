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


            Apple apple = new Apple(getX()+getWidth()/2+20,getY()+getHeight()/2,baseGameStage);
            baseGameStage.getMainGame().getWorld().add(apple.item, apple.getX()+bboxX, apple.getY()+bboxY, apple.bboxWidth, apple.bboxHeight);
            baseGameStage.getMainGame().getEntities().add(apple);

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
