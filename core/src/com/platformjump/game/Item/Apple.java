package com.platformjump.game.Item;

import com.platformjump.game.BaseFramework.BaseGameStage;

public class Apple extends Fruit{
    public Apple(float x, float y, BaseGameStage s) {
        super(x, y, s);
        loadAnimationFromSheet("Fruits/Apple.png",1,17,0.1f,true);
    }
}
