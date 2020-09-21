package com.platformjump.game.Item;

import com.platformjump.game.BaseFramework.BaseGameStage;

public class Banana extends Fruit{
    public Banana(float x, float y, BaseGameStage s) {
        super(x, y, s);
        loadAnimationFromSheet("Fruits/Bananas.png",1,17,0.1f,true);
    }
}
