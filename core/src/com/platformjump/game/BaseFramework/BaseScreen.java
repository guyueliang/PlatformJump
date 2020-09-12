package com.platformjump.game.BaseFramework;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.platformjump.game.GameStage;
import com.platformjump.game.LevelScreen;
import com.platformjump.game.UIStage;
import com.platformjump.game.platformjump;

import java.lang.reflect.InvocationTargetException;

public abstract class BaseScreen implements Screen, InputProcessor {

    public  static final String TAG = BaseScreen.class.getSimpleName();
    protected GameStage mainStage;
    protected UIStage uiStage;
   // protected Table uiTable;
    platformjump mainGame;
    private int count = 0;

    public BaseScreen(platformjump mainGame){
        this.mainGame = mainGame;
        mainStage = new GameStage(this.mainGame);
        uiStage = new UIStage(this.mainGame);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        count++;


        /**
        uiTable = new Table();
        //将table扩展到stage的尺寸
        uiTable.setFillParent(true);
        UIStage.addActor(uiTable);*/

        initialize();
        print();
    }
    private void print() {Gdx.app.log(TAG,"count= " + count);}


    public abstract void initialize();
    public abstract void update(float dt);

    public void render(float dt){
        uiStage.act();
        mainStage.act();

        update(dt);

        //clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw the graphics
        mainStage.draw();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    //当screen被显示的时候要处理的事情
    @Override
    public void show() {
        //添加输入事件处理器
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(mainStage);

    }

    //当screen被隐藏的时候要处理的事情
    @Override
    public void hide() {
        //去除输入处理器
        InputMultiplexer im = (InputMultiplexer) Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(mainStage);


    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}

