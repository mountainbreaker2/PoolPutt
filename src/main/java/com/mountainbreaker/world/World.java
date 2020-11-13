package com.mountainbreaker.world;

import com.mountainbreaker.core.DynamicObject;
import com.mountainbreaker.graphics.Drawable;
import com.mountainbreaker.graphics.Viewport;
import com.mountainbreaker.input.InputEvent;
import com.mountainbreaker.input.Interactive;
import com.mountainbreaker.ui.Widget;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

public class World extends DynamicObject implements Interactive {

    private final float killX = 100000000.0f;
    private final float killY = 100000000.0f;

    private final ArrayList<Entity> entities;
    private final ArrayList<Entity> drawList;
    private final ArrayList<Entity> killList;

    private final ArrayList<Camera> cameras;

    public World() {

        entities = new ArrayList<>();
        drawList = new ArrayList<>();
        killList = new ArrayList<>();
        cameras = new ArrayList<>();

    }

    public void addEntity(Entity e) {
        if(e == null) return;

        entities.add(e);
        if(e instanceof Camera) cameras.add((Camera)e);
    }

    @Override
    public void update(double frameTime) {
        drawList.clear();

        for(Entity e : killList) {
            entities.remove(e);
        }
        killList.clear();

        for(Entity e : entities) {
            if(e.active) e.update(frameTime);
            if(Math.abs(e.getX()) > killX || Math.abs(e.getY()) > killY) {
                killList.add(e);
            }
            else if(e.visible) {
                drawList.add(e);
            }
        }
    }

    public void addToKillList(Entity entity) {
        if(entity != null) killList.add(entity);
    }

    public ArrayList<Entity> getDrawList() { return drawList; }

    public ArrayList<Camera> getCameras() {
        return cameras;
    }

    @Override
    public boolean onInteract(InputEvent e) {
        return false;
    }

    @Override
    public boolean onAction(InputEvent e) {
        return false;
    }
}
