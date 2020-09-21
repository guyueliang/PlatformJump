package com.platformjump.game.Item;

import com.platformjump.game.BaseFramework.BaseGameStage;

public class Strawberry extends Fruit{
    public Strawberry(float x, float y, BaseGameStage s) {
        super(x, y, s);
        loadAnimationFromSheet("Fruits/Strawberry.png",1,17,0.1f,true);
    }
}
