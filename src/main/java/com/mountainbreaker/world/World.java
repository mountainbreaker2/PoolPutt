package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Sprite;
import com.mountainbreaker.graphics.Viewport;

import java.util.ArrayList;
import java.util.Vector;

public class World {
    private Viewport viewport;

    private int sizeX, sizeY;
    private int tilesX, tilesY;
    private int tileSize;
    private float viewOffX, viewOffY;
    private float viewDeltaX, viewDeltaY;

    private Vector<Tile> components;
    private Vector<Sprite> drawList;

    private final ArrayList<Tile> terrain;

    private double lastUpdate;

    public World(Viewport viewport, int sizeX, int sizeY, int tileSize) {
        this.viewport = viewport;

        if(viewport != null) {
            this.sizeX = Math.max(viewport.width, sizeX);
            this.sizeY = Math.max(viewport.height, sizeY);
        }
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.tileSize = Math.min(1, tileSize);
        this.tilesX = sizeX / tileSize;
        this.tilesY = sizeY / tileSize;

        viewOffX = 0.0f;
        viewOffY = 0.0f;

        components = new Vector<>();
        drawList = new Vector<>();

        terrain = new ArrayList<>();

        lastUpdate = System.nanoTime();
    }

    public void build() {
        for(int i = 0; i < tilesX * tilesY; i++) {
            terrain.add(new Tile(Sprite.load("terrain"), (float)(i % tilesX * tileSize), (float)(i / tilesY * tileSize), true, true));
        }
    }

    public boolean addComponent(Tile wc) {
        if(wc == null) return false;

        components.add(wc);

        return true;
    }

    public void update(double frameTime) {
        drawList.clear();
        double deltaTime = (frameTime - lastUpdate);

        setViewOffX(getViewOffX() + viewDeltaX);
        setViewOffY(getViewOffY() + viewDeltaY);

        //System.out.println("Terrain size: " + terrain.size());
        /*for(Tile t : terrain) {
            if(t.sprite != null) {
                if (t.px + t.width * viewport.scale >= viewOffX && t.px <= viewOffX + viewport.width && t.py + t.height * viewport.scale >= viewOffY && t.py <= viewOffY + viewport.height) {
                    // Set sprite position to WorldComponent position translated into viewport (screen) space
                    // then add it to the draw list passed to the viewport.
                    t.sprite.px = (int)(t.px - viewOffX);
                    t.sprite.py = (int)(t.py - viewOffY);
                    drawList.add(t.sprite);
                }
            }
            else {
                System.out.println("Sprite null");
            }

        }*/

        for(Tile c : components) {
            if(c.active) c.tick(frameTime);
            if(c.visible) {
                if (c.px + c.width * viewport.scale >= viewOffX && c.px <= viewOffX + viewport.width && c.py + c.height * viewport.scale >= viewOffY && c.py <= viewOffY + viewport.height) {
                    // Set sprite position to WorldComponent position translated into viewport (screen) space
                    // then add it to the draw list passed to the viewport.
                    c.sprite.px = wtvX(c.px);
                    c.sprite.py = wtvY(c.py);
                    drawList.add(c.sprite);
                }
            }
        }

        lastUpdate = frameTime;
        viewport.render(drawList);

    }

    public Viewport getViewport() {return viewport;}

    public void setViewport(Viewport v) {viewport = v;}

    public float getViewOffX() {
        return viewOffX;
    }

    public float getViewOffY() {
        return viewOffY;
    }

    public void setViewOffX(float toX) {
        if(toX + viewport.width > sizeX) {
            viewOffX = sizeX - viewport.width;
            return;
        }
        if(toX < 0) {
            viewOffX = 0.0f;
            return;
        }

        viewOffX = toX;
    }

    public void setViewOffY(float toY) {
        if(toY + viewport.height > sizeY) {
            viewOffY = sizeY - viewport.height;
            return;
        }
        if(toY < 0) {
            viewOffY = 0.0f;
            return;
        }

        viewOffY = toY;
    }

    public void addOffX(float deltaX) {
        setViewOffX(viewOffX + deltaX);
    }

    public void addOffY(float deltaY) {
        setViewOffY(viewOffY + deltaY);
    }

    public void setViewPos(float px, float py) {
        setViewOffX(px);
        setViewOffY(py);
    }

    public void setViewMotionX(float dXperSec) {
        viewDeltaX = dXperSec;
    }

    public void setViewMotionY(float dYperSec) {
        viewDeltaY = dYperSec;
    }

    public void setViewMotion(float dXperSec, float dYperSec) {
        setViewMotionX(dXperSec);
        setViewMotionY(dYperSec);
    }

    public int getSizeX() { return sizeX;}

    public int getSizeY() { return sizeY;}

    public float vtwX(int viewX) { return (viewX + viewOffX) / viewport.scale; }

    public float vtwY(int viewY) { return (viewY + viewOffY) / viewport.scale; }

    public int wtvX(float worldX) { return (int)((worldX - viewOffX) * viewport.scale); }

    public int wtvY(float worldY) { return (int)((worldY - viewOffY) * viewport.scale); }
}
