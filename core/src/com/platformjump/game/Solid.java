package com.platformjump.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.Rect;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Solid extends BaseActor{
    private boolean enabled;

    protected TextureRegion region;
    protected ShapeDrawer drawer;

    public Solid(float x, float y,float width,float height, Stage s) {
        super(x, y, s);
        //loadTexture("Solid.png");
        setSize(width,height);
        setBoundaryRectangle();
        //setBoundaryPolygon(6);
        enabled = true;

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

    public void setEnabled(boolean b){
        enabled = b;
    }

    public boolean isEnabled(){
        return enabled;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // 使用shapedrawer绘制图形
        if (region != null && drawer != null) {
            drawer.setColor(1, 0, 0, 1);
            Rect rect = LevelScreen.world.getRect(item);
            if(rect !=null)
                drawer.rectangle(rect.x, rect.y, rect.w, rect.h);
        }
    }
}
