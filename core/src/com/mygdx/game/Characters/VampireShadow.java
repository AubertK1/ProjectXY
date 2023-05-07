package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;

class VampireShadow extends Fighter{
    private final Vampire owner;
    private int nextVisibleFrame = 0;
    private float lastX;

    protected VampireShadow(Vampire owner) {
        super(owner.getX(), owner.getY(), owner.getWidth(), owner.getHeight(), false, true, null);
        this.owner = owner;

        //region animations = Vampire animations
        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_Idle_Sheet.png"), 2, 3, 36));

        runAnimation = animate(new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_Running_Sheet.png"), 2, 3, 36);

        //region neutral heavy
        nHeavyAnimation = animate(new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_Idle_Sheet.png"), 2, 3, 12);
        //endregion
        //endregion
    }

    @Override
    public void render(SpriteBatch batch) {
        //region combining update in render
        if(GameScreen.getFrame() >= nextVisibleFrame) setVisible(true);
        //endregion
        if(!getIsVisible()) return;

        boolean flip = getX() - lastX < 0;
        stateTime += Main.getFrameRate();
        modelFrame = currentAnimation.getKeyFrame(stateTime, true);

        float red = batch.getColor().r, green = batch.getColor().g, blue = batch.getColor().b;
        batch.setColor(batch.getColor().r, batch.getColor().g * .5f, batch.getColor().b * .5f, .5f);
        batch.draw(modelFrame, flip ? getX() + modelFrame.getRegionWidth() * getScale() : getX(), getY(),
                flip ? -modelFrame.getRegionWidth() * getScale() : modelFrame.getRegionWidth() * getScale(), modelFrame.getRegionHeight() * getScale());
        batch.setColor(red, green, blue, 1);

        lastX = getX();
    }

    public void selectAnimationFromNumber(int animationNum){
        switch (animationNum){
            case 0:
                currentAnimation = idleAnimation;
                break;
            case 1:
                currentAnimation = runAnimation;
                break;
            case 2:
                currentAnimation = jumpAnimation;
                break;
            case 3:
                currentAnimation = fallAnimation;
                break;
            case 4:
                currentAnimation = nLightAnimation;
                break;
            case 5:
                currentAnimation = sLightAnimation;
                break;
            case 6:
                currentAnimation = dLightAnimation;
                break;
            case 7:
                currentAnimation = nHeavyAnimation;
                break;
            case 8:
                currentAnimation = sHeavyAnimation;
                break;
            case 9:
                currentAnimation = dHeavyAnimation;
                break;
        }
    }
    public void beInvisible(int duration){
        setVisible(false);
        nextVisibleFrame = GameScreen.getFrame() + duration;
    }
}
