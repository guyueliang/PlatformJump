package com.platformjump.game.BaseFramework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.jbump.World;
import com.platformjump.game.GameStage;
import com.platformjump.game.Item.TilemapActor;
import com.platformjump.game.Player.Koala;
import com.platformjump.game.platformjump;

import java.util.ArrayList;

public abstract class BaseStage extends Stage {
    //舞台是否可见（是否更新和绘制)
    protected boolean visible;
    protected platformjump mainGame;


    //public  static final String TAG = GameStage.class.getSimpleName();

    public BaseStage(platformjump mainGame){
        super();
        visible = true;
        this.mainGame = mainGame;
        //FIXME 这里有个问题就是关于init()函数调用的问题，在其子类中的构造函数中调用init()会导致的问题是？？？后续需要理清这个问题！！！
        //为什么这里加上init()就会出错，并且会调用GameStage类中的init()???
        //this.init();
        System.out.println("BaseStage constructor\n");
    }

    public BaseStage(platformjump mainGame, Viewport viewport){
        super(viewport);
        visible = true;
        this.mainGame = mainGame;
        //FIXME 这里有个问题就是关于init()函数调用的问题，在其子类中的构造函数中调用init()会导致的问题是？？？后续需要理清这个问题！！！
        //为什么这里加上init()就会出错，并且会调用GameStage类中的init()???
        //this.init();
        System.out.println("BaseStage constructor\n");
    }


    //public abstract void init();

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public platformjump getMainGame() {
        return mainGame;
    }

    public void setMainGame(platformjump mainGame) {
        this.mainGame = mainGame;
    }

}
