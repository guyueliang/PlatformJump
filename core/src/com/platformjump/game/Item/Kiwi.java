package com.platformjump.game.Item;

import com.platformjump.game.BaseFramework.BaseGameStage;

public class Kiwi extends Fruit{
    public Kiwi(float x, float y, BaseGameStage s) {
        super(x, y, s);
        loadAnimationFromSheet("Fruits/Kiwi.png",1,17,0.1f,true);
    }
}
