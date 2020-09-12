package com.platformjump.game.Item;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dongbat.jbump.Item;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseStage;

public class Platform extends Solid{
    public Platform(float x, float y, BaseStage s) {
        super(x, y, 32, 16, s);
        loadTexture("items/platform.png");

        setEnabled(false);
        bboxWidth = 32;
        bboxHeight = 16;
        item = new Item<BaseActor>(this);

    }
}
