package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

public class DualAnimation extends Animation<TextureRegion> {
    private Rectangle[] hitboxes;
    private Point[] focalPoints;

    public DualAnimation(float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
        hitboxes = new Rectangle[keyFrames.length];
        focalPoints = new Point[keyFrames.length];
        for (int i = 0; i < hitboxes.length; i++) {
            if(hitboxes[i] == null) hitboxes[i] = new Rectangle(0,0,0,0);
            else hitboxes[i].set(0,0,0,0);
        }
        for (int i = 0; i < focalPoints.length; i++) {
            if(focalPoints[i] == null) focalPoints[i] = new Point(-1,-1);
            else focalPoints[i].setLocation(-1, -1);
        }
    }

    public void setHitboxes(Rectangle... hitboxOffsets){
        for (int i = 0; i < hitboxes.length; i++) {
            if(i < hitboxOffsets.length) hitboxes[i].set(hitboxOffsets[i]);
            else hitboxes[i].set(hitboxOffsets[hitboxOffsets.length-1]);
        }
    }

    public void setFocalPoints(Point... focalPointOffsets){
        for (int i = 0; i < focalPoints.length; i++) {
            if(i < focalPointOffsets.length) focalPoints[i].setLocation(focalPointOffsets[i]);
            else focalPoints[i].setLocation(-1, -1);
        }
    }

    public Rectangle getKeyHitBox(float stateTime){
        setPlayMode(PlayMode.LOOP);
        int frameNumber = this.getKeyFrameIndex(stateTime);
        return this.hitboxes[frameNumber];
    }

    public Point getKeyFocalPoint(float stateTime){
        setPlayMode(PlayMode.LOOP);
        int frameNumber = this.getKeyFrameIndex(stateTime);
        return this.focalPoints[frameNumber];
    }

    public Point[] getFocalPoints(){
        return this.focalPoints;
    }
}
