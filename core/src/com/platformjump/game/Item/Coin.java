package com.platformjump.game.Item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseStage;
import com.platformjump.game.GameStage;
import com.platformjump.game.LevelScreen;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Coin extends BaseActor {

    protected TextureRegion region;
    protected ShapeDrawer drawer;

    public Coin(float x, float y, BaseStage s) {
        super(x, y, s);
        loadAnimationFromSheet("items/coin.png",1,6,0.1f,true);

        bboxWidth = 32;
        bboxHeight = 32;
        item = new Item<BaseActor>(this);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);

        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        region = new TextureRegion(texture, 0, 0, 1, 1);
        drawer = new ShapeDrawer(s.getBatch(),region);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        /**
        // 使用shapedrawer绘制图形
        if (region != null && drawer != null) {
            drawer.setColor(1, 0, 0, 1);
            Rect rect = GameStage.world.getRect(item);
            if(rect !=null)
                drawer.rectangle(rect.x, rect.y, rect.w, rect.h);
        }*/
    }
}
