package com.platformjump.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.dongbat.jbump.World;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGame;
import com.platformjump.game.BaseFramework.BaseScreen;
import com.platformjump.game.Item.*;
import com.platformjump.game.Player.Koala;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

//TODO 需要重构代码，将UI逻辑和游戏逻辑分开，分别在不同的舞台中实现-------->已经实现
//TODO 需要为游戏添加更多的特性，如添加一些enemy,这就涉及到AI的问题；如何为游戏添加特效；如何实现背景屏幕移动；如何添加一些可以与玩家进行互动的道具；等等
public class LevelScreen extends BaseScreen {
    public  static final String TAG = LevelScreen.class.getSimpleName();
    protected TextureRegion region;
    protected ShapeDrawer drawer;
    private boolean debug = true;

    public LevelScreen(platformjump mainGame) {
        super(mainGame);
    }

    @Override
    public void initialize() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);

        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        region = new TextureRegion(texture, 0, 0, 1, 1);
        drawer = new ShapeDrawer(mainStage.getBatch(),region);

    }

    @Override
    public void update(float dt) {

        if(uiStage.isGameOver())
            return;

        //在这里主要处理player与UI相关的事情
        uiStage.setWin(mainStage.getJack().isWin());
        uiStage.setCoins(mainStage.getJack().getCoin());
        if(mainStage.getJack().isHasKey()){
            uiStage.setHasKey(mainStage.getJack().isHasKey());
            uiStage.setKeyColor(mainStage.getJack().getKeyColor());
        }
        //uiStage.setHasKey(GameStage.jack.isHasKey());
        uiStage.setTime(mainStage.getJack().getGameLeftTime());

    }


    @Override
    public void render(float dt) {
        super.render(dt);



        if(debug) {

            mainStage.getBatch().begin();
            for (BaseActor actor : mainGame.getEntities()) {
                Item item = actor.item;
                // 使用shapedrawer绘制图形
                if (region != null && drawer != null) {
                    drawer.setColor(1, 0, 0, 1);
                    Rect rect = mainGame.getWorld().getRect(item);
                    if (rect != null)
                        drawer.rectangle(rect.x, rect.y, rect.w, rect.h);
                }
            }
            mainStage.getBatch().end();
        }

    }


    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.F10){
            mainGame.setScreen(new LevelScreen(this.mainGame));

            return true;

        }

        return false;

    }
}
