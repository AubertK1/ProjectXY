package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;

class VampireShadow extends Fighter{
    private final Vampire owner;
    private int nextVisibleFrame = 0;
    protected VampireShadow(Vampire owner) {
        super(owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight(), false, true, null);
        this.owner = owner;

        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_Idle_Sheet.png"), 2, 3, 36));
    }

    @Override
    public void render(SpriteBatch batch) {
        //region combining update in render
        if(GameScreen.getFrame() >= nextVisibleFrame) setVisible(true);
        //endregion
        if(!getIsVisible()) return;

        boolean flip = !isFacingRight;
        stateTime += Main.getFrameRate();
        modelFrame = idleAnimation.getKeyFrame(stateTime, true);

        float red = batch.getColor().r, green = batch.getColor().g, blue = batch.getColor().b;
        batch.setColor(batch.getColor().r, batch.getColor().g * .5f, batch.getColor().b * .5f, .5f);
        batch.draw(modelFrame, flip ? getX() + modelFrame.getRegionWidth() * getScale() : getX(), getY(),
                flip ? -modelFrame.getRegionWidth() * getScale() : modelFrame.getRegionWidth() * getScale(), modelFrame.getRegionHeight() * getScale());
        batch.setColor(red, green, blue, 1);
    }

    public void beInvisible(int duration){
        setVisible(false);
        nextVisibleFrame = GameScreen.getFrame() + duration;
    }
}
