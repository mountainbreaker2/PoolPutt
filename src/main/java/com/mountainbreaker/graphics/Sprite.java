package com.mountainbreaker.graphics;

import com.mountainbreaker.core.DynamicObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class Sprite extends DynamicObject implements Drawable{
    ///////////////////////////////////////////////////////////////////
    // Animation class
    public static class Animation {
        /////////////////////////////
        // Object data
        public String id;
        public int startFrame;
        public int endFrame;
        public float framerate;

        ////////////////////////////
        // Constructors
        public Animation(String id, int startFrame, int endFrame, float framerate) {
            this.id = id;
            this.startFrame = startFrame;
            this.endFrame = endFrame;
            this.framerate = framerate;
        }

        public Animation() {
            this.id = "blank";
            this.startFrame = 0;
            this.endFrame = 0;
            this.framerate = 0.0f;
        }
    }


    public int pX, pY;

    protected Image spriteSheet;

    private int indexOffset;
    private int currentAnimation;

    private double lastUpdate;
    private float frameInterval;

    public ArrayList<Animation> animations;

    public static Sprite nullSprite() {
        return Sprite.load("null");
    }

    public static Sprite load(String id) {
        SpriteData data = SpriteData.load(id);

        Sprite loadedSprite = new Sprite();
        loadedSprite.pX = loadedSprite.pY = 0;
        loadedSprite.spriteSheet = Image.getImage(data.getId());
        loadedSprite.animations = data.getAnimations();

        if(loadedSprite.animations.size() < 1) {
            loadedSprite.animations.add(new Sprite.Animation());
        }

        loadedSprite.setAnimation(0);

        return loadedSprite;
    }

    public static Sprite create(Image sourceSheet, ArrayList<Animation> animations) {
        if(sourceSheet == null) return null;
        if(sourceSheet.getTile(0) == null) return null;

        Sprite newSprite = new Sprite();

        newSprite.pX = newSprite.pY = 0;

        newSprite.spriteSheet = sourceSheet;
        newSprite.animations = Objects.requireNonNullElseGet(animations, ArrayList::new);

        if(newSprite.animations.size() < 1) {
            newSprite.animations.add(new Sprite.Animation());
        }

        newSprite.setAnimation(0);

        return newSprite;
    }

    public void setAnimation(int animNumber) {
        if(animNumber > animations.size() - 1) {
            return;
        }

        currentAnimation = animNumber;
        Animation selected = animations.get(animNumber);

        if(animations.get(currentAnimation).framerate > 0 && selected.startFrame != selected.endFrame) frameInterval = 1000000000 / selected.framerate;
        else frameInterval = 0;

        indexOffset = 0;
        lastUpdate = System.nanoTime();
    }

    @Override
    public void update(double frameTime) {
        if(frameTime - lastUpdate > frameInterval && frameInterval > 0) {
            int startFrame = animations.get(currentAnimation).startFrame;
            int endFrame = animations.get(currentAnimation).endFrame;
            int length = endFrame - startFrame;
            indexOffset = (indexOffset + 1) % (length + 1);
            lastUpdate = frameTime;
        }
    }

    @Override
    public BufferedImage image() {
        return spriteSheet.getTile(indexOffset + animations.get(currentAnimation).startFrame);
    }

    @Override
    public int drawX() {
        return pX;
    }

    @Override
    public int drawY() {
        return pY;
    }

}
