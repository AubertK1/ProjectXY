package com.mygdx.game;

public class HitData {
    public int damage = 0;
    public int fortDamage = 0;
    public float knockbackMultiplier = 1;
    public int direction = Object.TOPCOLLISION;

    public int hitStunDuration = 0;

    //region shortcuts
    public static int IGNORE = -20000;  //if you are using a set function and don't want to change a certain variable
    public static int CLEAR = 0; //if you want to reset the value of a variable back to 0;
    //endregion

    public HitData() {
    }

    public HitData set(int damage, int fortDamage, float knockbackMultiplier, int direction, int hitStun){
        if(damage != IGNORE) this.damage = damage;
        if(fortDamage != IGNORE) this.fortDamage = fortDamage;
        if(knockbackMultiplier != IGNORE) this.knockbackMultiplier = knockbackMultiplier;
        if(direction != IGNORE) this.direction = direction;
        if(hitStun != IGNORE) this.hitStunDuration = hitStun;

        return this;
    }
    public HitData clear(){
        damage = 0;
        fortDamage = 0;
        knockbackMultiplier = 0;
        direction = 0;
        hitStunDuration = 0;

        return this;
    }
}
