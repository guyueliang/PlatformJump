package com.platformjump.game.Item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformjump.game.BaseFramework.BaseGameStage;

import java.util.Random;

public class Box1_Breaks extends Box_Breaks{
    public Box1_Breaks(float x, float y, BaseGameStage s) {
        super(x, y, s);
        boxBreaks = loadAnimationFromSheet("Boxes/Box1/Break.png",1,4,0.1f,true);
        Object[] textureRegions = boxBreaks.getKeyFrames();

        int len = textureRegions.length;

        //根据随机数来决定显示哪一帧图形
        rNum = random.nextInt(len);
        textureRegion1 = (TextureRegion) textureRegions[rNum];
    }
}
