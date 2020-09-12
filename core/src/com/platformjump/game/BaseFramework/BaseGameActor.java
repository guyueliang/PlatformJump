package com.platformjump.game.BaseFramework;

import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.World;

public class BaseGameActor extends BaseActor{

    protected SnapshotArray<BaseActor> entities;
    protected World<BaseActor> world;
    protected BaseGameStage baseGameStage;

    public BaseGameActor(float x, float y, BaseGameStage s) {
        super(x, y, s);
        baseGameStage = s;

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        /**
        entities = baseGameStage.getEntities();
        world = baseGameStage.getWorld();*/
    }


     public SnapshotArray<BaseActor> getEntities() {
     return entities;
     }

     public void setEntities(SnapshotArray<BaseActor> entities) {
     this.entities = entities;
     }

     public World<BaseActor> getWorld() {
     return world;
     }

     public void setWorld(World<BaseActor> world) {
     this.world = world;
     }
}
