package com.platformjump.game;

public class platformjump extends BaseGame{

    public void create(){
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
