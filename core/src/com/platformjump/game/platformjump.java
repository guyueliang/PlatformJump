package com.platformjump.game;

import com.platformjump.game.BaseFramework.BaseGame;

public class platformjump extends BaseGame {

    public void create(){
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
