package com.platformjump.game.Item;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.platformjump.game.BaseFramework.BaseGameStage;

public class Box2_Breaks extends Box_Breaks{
    public Box2_Breaks(float x, float y, BaseGameStage s) {
        super(x, y, s);

        boxBreaks = loadAnimationFromSheet("Boxes/Box2/Break.png",1,4,0.1f,true);
        Object[] textureRegions = boxBreaks.getKeyFrames();

        int len = textureRegions.length;

        //根据随机数来决定显示哪一帧图形
        rNum = random.nextInt(len);
        textureRegion1 = (TextureRegion) textureRegions[rNum];
    }
}
