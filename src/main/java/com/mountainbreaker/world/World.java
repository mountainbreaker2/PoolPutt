package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Drawable;
import com.mountainbreaker.graphics.Viewport;
import com.mountainbreaker.ui.Text;
import com.mountainbreaker.ui.Widget;

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
    private final ArrayList<Drawable> drawList;

    private final ArrayList<Tile> terrain;

    private double lastUpdate;

    private ArrayList<Widget> uiWidgets;

    public World(Viewport viewport, int sizeX, int sizeY, int tileSize) {
        this.viewport = viewport;

        if(viewport != null) {
            this.sizeX = Math.max(viewport.width, sizeX);
            this.sizeY = Math.max(viewport.height, sizeY);
        }
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.tileSize = Math.max(1, tileSize);
        this.tilesX = sizeX / tileSize;
        this.tilesY = sizeY / tileSize;

        viewOffX = 0.0f;
        viewOffY = 0.0f;

        components = new Vector<>();
        drawList = new ArrayList<>();

        terrain = new ArrayList<>();

        uiWidgets = new ArrayList<>();

        lastUpdate = System.nanoTime();
    }

    public void build() {
        for(int i = 0; i < tilesX * tilesY; i++) {
            float atX = (float)((i % tilesX) * tileSize);
            float atY = (float)((i / tilesX) * tileSize);
            terrain.add(new Tile("terrain", atX, atY, true, true));
        }
    }

    public void addComponent(Tile wc) {
        if(wc == null) return;

        components.add(wc);
    }

    public void addWidget(Widget widget) {
        uiWidgets.add(widget);
    }

    public void update(double frameTime) {
        drawList.clear();

        setViewOffX(getViewOffX() + viewDeltaX);
        setViewOffY(getViewOffY() + viewDeltaY);

        int startX = (int)viewOffX / tileSize;
        int lastX = (int)(viewOffX + viewport.width) / tileSize;
        int startY = (int)viewOffY / tileSize;
        int lastY = (int)(viewOffY + viewport.height) / tileSize;
        for(int x = startX; x <= lastX; x++) {
            for(int y = startY; y <= lastY; y++) {
                int index = x + y * tilesX;
                if(index < terrain.size()) {
                    Tile t = terrain.get(index);
                    t.tick(frameTime);
                    t.sprite.px = us_wtvX(t.px);
                    t.sprite.py = us_wtvY(t.py);
                    drawList.add(t.sprite);
                }
            }
        }

        for(Tile c : components) {
            if(c.active) c.tick(frameTime);
            if(c.visible) {
                c.sprite.px = us_wtvX(c.px);
                c.sprite.py = us_wtvY(c.py);
                drawList.add(c.sprite);
            }
        }

        for(Widget w : uiWidgets) {
            //w.tick(frameTime);
            drawList.add(w);
        }

    }

    public ArrayList<Drawable> getDrawList() {
        return drawList;
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


    // View to World coordinates
    public float vtwX(int scaledX) { return (scaledX / viewport.scale + viewOffX); }

    public float vtwY(int scaledY) { return (scaledY / viewport.scale + viewOffY); }

    // World to view coordinates
    public int us_wtvX(float worldX) { return (int)(worldX - viewOffX); }

    public int us_wtvY(float worldY) { return (int)(worldY - viewOffY); }

    public int indexEast(int startingIndex, int distance) {
        return startingIndex + distance;
    }

    public int indexWest(int startingIndex, int distance) {
        return startingIndex - distance;
    }

    public int indexNorth(int startingIndex, int distance) {
        return startingIndex - tilesX * distance;
    }
    public int indexSouth(int startingIndex, int distance) {
        return startingIndex + tilesX * distance;
    }

    public void setTile(float atX, float atY, int type) {
        int targetIndex = ((int)atX / tileSize) + ((int)atY / tileSize) * tilesX;
        if(targetIndex < terrain.size()) {
            terrain.get(targetIndex).sprite.setAnimation(type);
        }
    }
}
