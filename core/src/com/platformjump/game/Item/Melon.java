package com.platformjump.game.Item;

import com.platformjump.game.BaseFramework.BaseGameStage;

public class Melon extends Fruit{
    public Melon(float x, float y, BaseGameStage s) {
        super(x, y, s);
        loadAnimationFromSheet("Fruits/Melon.png",1,17,0.1f,true);
    }
}
