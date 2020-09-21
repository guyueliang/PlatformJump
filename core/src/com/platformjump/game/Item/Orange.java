package com.platformjump.game.Item;

import com.platformjump.game.BaseFramework.BaseGameStage;

public class Orange extends Fruit{
    public Orange(float x, float y, BaseGameStage s) {
        super(x, y, s);
        loadAnimationFromSheet("Fruits/Pineapple.png",1,17,0.1f,true);
    }
}
