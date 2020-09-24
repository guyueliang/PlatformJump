package com.platformjump.game.Item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.dongbat.jbump.Item;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGameActor;
import com.platformjump.game.BaseFramework.BaseGameStage;

public class BubbleRegion extends BaseGameActor {
    Animation  Off;
    Animation On;

    ParticleEffect effect = new ParticleEffect();
    public BubbleRegion(float x, float y, BaseGameStage s) {
        super(x, y, s);
        Off = loadTexture("Traps/Fan/Off.png");
        On = loadAnimationFromSheet("Traps/Fan/On.png",1,4,0.1f,true);
        setAnimation(On);

        //包围盒的起始坐标与Player的坐标x和y方向的距离
        bboxX = 0;
        bboxY = 0;
        //包围盒的宽度和高度
        bboxWidth = 28;
        bboxHeight = 100;
        item = new Item<BaseActor>(this);

        //effect.load(Gdx.files.internal("Particles/bubleNote.p"),Gdx.files.internal("Particles"));
        effect.load(Gdx.files.internal("Particles/ParticleParkRain/ParticleParkRain.p"),Gdx.files.internal("Particles/ParticleParkRain"));
        effect.start();
        effect.setPosition(x,y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        effect.draw(batch);
    }
}
