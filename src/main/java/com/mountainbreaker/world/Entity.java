package com.mountainbreaker.world;

import com.mountainbreaker.core.DynamicObject;
import com.mountainbreaker.graphics.Drawable;
import com.mountainbreaker.ui.SignalListener;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Entity extends DynamicObject implements SignalListener {
    protected Vec2 translation;
    protected Vec2 size;
    protected Vec2 scale;

    protected boolean active;
    protected boolean visible;

    protected String id;

    protected ArrayList<Drawable> drawableComponents = new ArrayList<>();

    protected World world;

    Entity() {
        translation = new Vec2();
        size = new Vec2();
        scale = new Vec2(1.0f, 1.0f);

        active = true;
        visible = false;

        id = Double.toHexString(System.currentTimeMillis());
    }

    private Entity(float px, float py) {
        translation = new Vec2(px, py);
        size = new Vec2();
        scale = new Vec2(1.0f, 1.0f);

        active = true;
        visible = false;

        setId(Double.toHexString(System.currentTimeMillis()));
    }

    private Entity(String id, float px, float py) {
        translation = new Vec2(px, py);
        size = new Vec2();
        scale = new Vec2(1.0f, 1.0f);

        active = true;
        visible = false;

        setId(id);
    }

    public ArrayList<Drawable> getDrawableComponents() { return drawableComponents; }

    public void registerDrawableComponent(Drawable d) {
        if(d != null) {
            drawableComponents.add(d);
            if(drawableComponents.size() > 0) setVisible(true);
        }
    }

    public void recalcSize() {
        for(Drawable d : drawableComponents) {
            BufferedImage bufferedImage = d.image();
        }
    }

    public Entity spawn(World world, String id, float atX, float atY) {
        this.translation = new Vec2(atX, atY);
        this.id = id;
        if(world != null) {
            this.world = world;
            world.addEntity(this);
        }
        return this;
    }

    public void destroy() {
        if(world != null) world.addToKillList(this);
    }

    @Override
    public void update(double frameTime) {}

    @Override
    public void onSignal(String[] signalChain) {} // Do nothing by default

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public float getX() { return translation.x; }

    public float getY() {
        return translation.y;
    }

    public void moveToX(float px) { translation.x = px; }

    public void moveToY(float py) { translation.y = py; }

    public void moveTo(float px, float py) { moveToX(px); moveToY(py); }

    public float getSizeX() { return size.x; }

    public float getSizeY() { return size.y; }

    public void resizeX(float width) { size.x = width; }

    public void resizeY(float height) { size.y = height; }

    public void resize(float width, float height) { resizeX(width); resizeY(height); }

    public float getScaleX() { return scale.x; }

    public float getScaleY() {
        return scale.y;
    }

    public void setScaleX(float sx) { scale.x = sx; }

    public void setScaleY(float sy) { scale.y = sy; }

    public void setScale(float sx, float sy) { setScaleX(sx); setScaleY(sy); }

    public void setVisible(boolean visible) { this.visible = visible; }

    public boolean isVisible() { return visible; }

    public void setActive(boolean active) { this.active = active; }

    public boolean isActive() { return active; }
}
