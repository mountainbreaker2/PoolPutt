package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Sprite;

public class Tile extends Placeable {
    protected boolean visible;
    protected boolean active;

    public Tile(Sprite sprite, float atWorldX, float atWorldY, boolean visible, boolean active) {
        super(atWorldX, atWorldY, sprite);

        if(sprite == null) this.visible = false;
        else this.visible = visible;

        this.active = active;
    }

    public void tick(double frameTime) {
        if(sprite != null) {
            sprite.tick(frameTime);
        }
    }
}
