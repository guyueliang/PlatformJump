package com.platformjump.game.BaseFramework;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.dongbat.jbump.World;


public abstract class BaseGame extends Game {

    public static final String FONT_CH = "金鱼";
    public static Label.LabelStyle labelStyle;
    public static TextButton.TextButtonStyle textButtonStyle;

    private static BaseGame game;

    protected SnapshotArray<BaseActor> entities;
    protected World<BaseActor> world;
    private  float TILE_DIMENSION = 64f;

    //好奇怪的用法
    //似乎是单例模式

    public BaseGame(){

        game = this;

        //Gdx.app.setLogLevel(Application.LOG_DEBUG);
        //Gdx.app.log("BaseGame class constructor", "BaseGame() constructor");
    }

    public static void setActiveScreen(BaseScreen s){
        game.setScreen(s);
    }

    @Override
    public void create() {
        InputMultiplexer im = new InputMultiplexer();
        Gdx.input.setInputProcessor(im);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();

        textButtonStyle = new TextButton.TextButtonStyle();
        Texture buttonTex = new Texture(Gdx.files.internal("button.png"));

        //使用9宫格图片
        NinePatch buttonPatch = new NinePatch(buttonTex,24,24,24,24);
        textButtonStyle.up = new NinePatchDrawable(buttonPatch);


        //使用ttf字体库
        FreeTypeFontGenerator fontGenerator =
                new FreeTypeFontGenerator(Gdx.files.internal("OpenSans.ttf"));

        //定义字体参数
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 25;
        fontParameter.color = Color.WHITE;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.BLACK;
        fontParameter.borderStraight = true;
        fontParameter.minFilter = Texture.TextureFilter.Linear;
        fontParameter.magFilter = Texture.TextureFilter.Linear;
        //可以从文本文件中读取相应的文字
        //FileHandle fileHandle = Gdx.files.internal("word.txt");
        //String word = fileHandle.readString();
        //fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + word;
        fontParameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS + "已经全部的啦我想成为最好的收集者东西部海湾开始退出剩余星星: ";



        //生成自定义的字体
        BitmapFont customFont = fontGenerator.generateFont(fontParameter);
        labelStyle.font = customFont;

        textButtonStyle.font = customFont;
        textButtonStyle.fontColor = Color.GRAY;


        entities = new SnapshotArray<>();
        world = new World<>(TILE_DIMENSION);


    }

    public SnapshotArray<BaseActor> getEntities() {
        return entities;
    }

    public World<BaseActor> getWorld() {
        return world;
    }

}

