package com.platformjump.game.Item;

import com.platformjump.game.BaseFramework.BaseGameStage;

public class Cherries extends Fruit{
    public Cherries(float x, float y, BaseGameStage s) {
        super(x, y, s);
        loadAnimationFromSheet("Fruits/Cherries.png",1,17,0.1f,true);
    }
}
