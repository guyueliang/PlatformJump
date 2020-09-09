package com.platformjump.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dongbat.jbump.Item;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Platform extends Solid{
    public Platform(float x, float y, Stage s) {
        super(x, y, 32, 16, s);
        loadTexture("items/platform.png");

        setEnabled(false);
        bboxWidth = 32;
        bboxHeight = 16;
        item = new Item<BaseActor>(this);

    }
}
