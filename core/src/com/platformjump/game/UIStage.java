package com.platformjump.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.platformjump.game.BaseFramework.BaseActor;
import com.platformjump.game.BaseFramework.BaseGame;
import com.platformjump.game.BaseFramework.BaseStage;

public class UIStage extends BaseStage {

    private boolean gameOver;

    //win, isHasKey,coins,以及keyColor需要从player中得到
    private boolean win;
    private boolean isHasKey;
    private int coins;
    private Color keyColor;

    private float time;
    private Label coinLabel;
    private Table keyTable;
    private Label timeLabel;
    private Label messageLabel;
    private BaseActor keyIcon;
    private Table uiTable;


    public  static final String TAG = UIStage.class.getSimpleName();

    public UIStage(platformjump mainGame){
        super(mainGame);
        init();
    }

    private void init(){
        //ui设置
        gameOver = false;
        win = false;
        isHasKey = false;
        coins = 0;
        time = 300;

        keyColor = new Color(1,1,1,1);
        keyTable = new Table();
        uiTable = new Table();

        //将table扩展到stage的尺寸
        uiTable.setFillParent(true);

        //将uiTable添加到舞台中
        addActor(uiTable);

        coinLabel = new Label("Coins: " +coins, BaseGame.labelStyle);
        coinLabel.setColor(Color.GOLD);

        timeLabel = new Label("Time: " + (int)time,BaseGame.labelStyle);
        timeLabel.setColor(Color.LIGHT_GRAY);
        messageLabel = new Label("Message",BaseGame.labelStyle);
        messageLabel.setVisible(false);


        uiTable.pad(20);//上下左右各填充20个像素
        uiTable.add(coinLabel);
        uiTable.add(keyTable).expandX();

        //FIXME 这里可能会有问题，keyIcon只需要添加到keyTable中，而不需要再次添加到这个舞台中
        keyIcon = new BaseActor(0,0,this);
        keyIcon.loadTexture("key-icon.png");
        keyIcon.setVisible(false);
        keyTable.add(keyIcon);

        uiTable.add(timeLabel);
        uiTable.row();
        uiTable.add(messageLabel).colspan(3).expandY();

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(win){
            messageLabel.setText("You Win!");
            messageLabel.setColor(Color.LIME);
            messageLabel.setVisible(true);
            gameOver = true;
        }

        if(!gameOver) {
            //time -= delta;

            if (time <= 0) {
                messageLabel.setText("Time Up- Game Over");
                messageLabel.setColor(Color.RED);
                messageLabel.setVisible(true);
                gameOver = true;
            }

            timeLabel.setText("Time: " + (int) time);
            coinLabel.setText("Coins: " + coins);

            if (isHasKey) {
                keyIcon.setColor(keyColor);
                keyIcon.setVisible(true);
            }
        }
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isHasKey() {
        return isHasKey;
    }

    public void setHasKey(boolean hasKey) {
        isHasKey = hasKey;
    }

    public Color getKeyColor() {
        return keyColor;
    }

    public void setKeyColor(Color keyColor) {
        this.keyColor = keyColor;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }


}
