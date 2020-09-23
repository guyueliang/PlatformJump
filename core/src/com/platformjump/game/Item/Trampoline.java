package com.platformjump.game.Item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Response;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGameActor;
import com.platformjump.game.BaseFramework.BaseGameStage;
import com.platformjump.game.Player.Koala;

import java.util.Random;

public class Trampoline extends BaseGameActor {

    private Animation Idle;
    private Animation Jump;

    public boolean contactWithPlayer = false;

    private float maxShowTime = 0.8f;
    private float timeCounter = 0.0f;

    public final Trampoline.Trampoline_CollisionFilter TRAMPOLINE_COLLISION_FILTER = new Trampoline.Trampoline_CollisionFilter();

    public Trampoline(float x, float y, BaseGameStage s) {
        super(x, y, s);

        Idle = loadTexture("Traps/Trampoline/Idle.png");
        Jump = loadAnimationFromSheet("Traps/Trampoline/Jump.png",1,8,0.08f,true);

        setAnimation(Idle);

        //包围盒的起始坐标与Player的坐标x和y方向的距离
        bboxX = 0;
        bboxY = 0;
        //包围盒的宽度和高度
        bboxWidth = 28;
        bboxHeight = 28;
        item = new Item<BaseActor>(this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(contactWithPlayer ) {
            setAnimation(Jump);
            timeCounter += delta;
        }

        if(timeCounter > 0 && timeCounter <= maxShowTime){
            timeCounter += delta;
        }

        if(timeCounter > maxShowTime){
            setAnimation(Idle);
            timeCounter = 0;
        }

        boolean flag = baseGameStage.getMainGame().getWorld().hasItem(this.item);

        if(flag) {
            //判断是否与player碰撞
            Response.Result result = baseGameStage.getMainGame().getWorld().move(item, getX() + bboxX, getY() + bboxY, TRAMPOLINE_COLLISION_FILTER);

            if (result.projectedCollisions.size() <= 0) {
                contactWithPlayer = false;
            }

        }
    }

    //由于Box2每帧都需要获取自身是否需要更新帧动画，所以需要添加碰撞过滤器，判断是否与player碰撞
    private  class Trampoline_CollisionFilter implements CollisionFilter {
        @Override
        public Response filter(Item item, Item other) {

            if (other.userData instanceof Koala){
                return Response.slide;
            }

            return null;
        }
    }
}
