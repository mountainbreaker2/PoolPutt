package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Sprite;

public class Entity extends Tile {

    public float vx, vy;
    public float ax, ay;

    public Entity(String id, float atWorldX, float atWorldY, boolean visible, boolean active) {
        super(id, atWorldX, atWorldY, visible, active);
    }

    @Override
    public void tick(double frameTime) {
        super.tick(frameTime);
    }
}
