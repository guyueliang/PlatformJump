package com.platformjump.game.BaseFramework;

import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.jbump.World;
import com.platformjump.game.Item.TilemapActor;
import com.platformjump.game.Player.Koala;
import com.platformjump.game.platformjump;

import javax.swing.text.View;
import java.util.ArrayList;

public class BaseGameStage extends BaseStage{

    protected Koala jack;


    protected TilemapActor tma;
    protected ArrayList<Class<? extends BaseActor>> objClass;

    public BaseGameStage(platformjump mainGame, Viewport v) {
        super(mainGame,v);
        System.out.println("BaseGameStage constructor()");
        //由于GameStage类中也有init()方法，在调用的时候，会用GameStage类中的init()方法覆盖该类中的init()方法
        init();
    }

    public void init(){
        //super.init();
        System.out.println("BaseGameStage init()\n");
        for(BaseActor actor : getMainGame().entities){
            if(actor != null){
                getMainGame().world.remove(actor.item);
            }
        }
        getMainGame().entities.clear();


        //tma = new TilemapActor("myMap.tmx",this);

        objClass = new ArrayList<Class<? extends BaseActor>>();
    }




    public Koala getJack() {
        return jack;
    }
}
