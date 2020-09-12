package com.platformjump.game.BaseFramework;

import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.World;
import com.platformjump.game.Item.TilemapActor;
import com.platformjump.game.Player.Koala;
import com.platformjump.game.platformjump;

import java.util.ArrayList;

public class BaseGameStage extends BaseStage{

    protected Koala jack;
    protected SnapshotArray<BaseActor> entities;
    protected World<BaseActor> world;
    private  float TILE_DIMENSION = 64f;

    protected TilemapActor tma;
    protected ArrayList<Class<? extends BaseActor>> objClass;

    public BaseGameStage(platformjump mainGame) {
        super(mainGame);
    }

    @Override
    public void init(){
        //super.init();
        entities = new SnapshotArray<>();
        world = new World<>(TILE_DIMENSION);

        //tma = new TilemapActor("myMap.tmx",this);

        objClass = new ArrayList<Class<? extends BaseActor>>();
    }


    public SnapshotArray<BaseActor> getEntities() {
        return entities;
    }

    public World<BaseActor> getWorld() {
        return world;
    }

    public Koala getJack() {
        return jack;
    }
}
