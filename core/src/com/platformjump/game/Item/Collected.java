package com.platformjump.game.Item;

import com.platformjump.game.BaseFramework.BaseGameActor;
import com.platformjump.game.BaseFramework.BaseGameStage;

public class Collected extends BaseGameActor {
    private float maxTime;
    public Collected(float x, float y, BaseGameStage s) {
        super(x, y, s);
        loadAnimationFromSheet("Fruits/Collected.png",1,6,0.1f,true);
        maxTime = .3f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(maxTime > 0){
            maxTime -= delta;
        }else if(maxTime <= 0){
            remove();
        }
    }
}
