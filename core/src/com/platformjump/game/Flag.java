package com.platformjump.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Flag extends BaseActor {
    public Flag(float x, float y, Stage s) {
        super(x, y, s);
        loadAnimationFromSheet("items/flag.png",1,2,0.2f,true);

    }
}
