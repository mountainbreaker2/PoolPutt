package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Sprite;

public class Tile extends Placeable {
    protected boolean visible;
    protected boolean active;

    protected float width, height;

    public Tile(String id, float atWorldX, float atWorldY, boolean visible, boolean active) {
        super(atWorldX, atWorldY, id);

        this.visible = visible;
        width = sprite.image().getWidth();
        height = sprite.image().getHeight();

        this.active = active;
    }

    public void tick(double frameTime) {
        sprite.tick(frameTime);
    }
}
