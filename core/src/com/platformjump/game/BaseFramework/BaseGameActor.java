package com.platformjump.game.BaseFramework;

import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.World;

public class BaseGameActor extends BaseActor{
    protected BaseGameStage baseGameStage;

    public BaseGameActor(float x, float y, BaseGameStage s) {
        super(x, y, s);
        baseGameStage = s;

    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }


}
