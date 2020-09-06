package com.platformjump.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Solid extends BaseActor{
    private boolean enabled;

    public Solid(float x, float y,float width,float height, Stage s) {
        super(x, y, s);
        //loadTexture("Solid.png");
        setSize(width,height);
        setBoundaryRectangle();
        //setBoundaryPolygon(6);
        enabled = true;

    }

    public void setEnabled(boolean b){
        enabled = b;
    }

    public boolean isEnabled(){
        return enabled;
    }
}
