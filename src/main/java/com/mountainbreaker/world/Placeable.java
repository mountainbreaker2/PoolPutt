package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Sprite;

public class Placeable {
    protected float px, py;
    protected Sprite sprite;

    Placeable() {
        px = py = 0.0f;
        sprite = null;
    }

    Placeable(float px, float py, Sprite sprite) {
        this.px = px;
        this.py = py;
        this.sprite = sprite;
    }

    public float getX() {
        return px;
    }

    public float getY() {
        return py;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setX(float px) {
        this.px = px;
    }

    public void setY(float py) {
        this.py = py;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

}
