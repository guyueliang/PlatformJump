package com.platformjump.game.BaseFramework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.platformjump.game.platformjump;

public class BaseStage extends Stage {
    //舞台是否可见（是否更新和绘制)
    private boolean visible;
    private platformjump mainGame;

    public BaseStage(platformjump mainGame){
        visible = true;
        this.mainGame = mainGame;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public platformjump getMainGame() {
        return mainGame;
    }

    public void setMainGame(platformjump mainGame) {
        this.mainGame = mainGame;
    }
}
