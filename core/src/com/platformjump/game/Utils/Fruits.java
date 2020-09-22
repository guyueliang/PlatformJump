package com.platformjump.game.Utils;

public enum Fruits {
    APPLE(0),
    BANANA(1),
    CHERRIES(2),
    KIWI(3),
    MELON(4),
    ORANGE(5),
    STRAWBERRY(6);

    private int num;

    private Fruits(int n) {
        num = n;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int n){
        num = n;
    }

    //根据value返回枚举类型，主要在switch中使用
    public static Fruits getByValue(int value){
        for(Fruits fruits : values()){
            if(fruits.getNum() == value){
                return fruits;
            }
        }
        return null;
    }
}
