package com.mountainbreaker.ui;

import com.mountainbreaker.graphics.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Widget implements Drawable {
    public static class Margins {
        public int left, right, top, bottom;

        public Margins() { left = right = top = bottom = 0; }
        public Margins(int l, int r, int t, int b) { left = l; right = r; top = t; bottom = b; }
    }
    protected final Rectangle bounds = new Rectangle();
    protected final Margins margins = new Margins();

    protected String id;

    protected BufferedImage surface;

    protected Widget parent = null;
    protected final ArrayList<Widget> children = new ArrayList<>();

    protected void resurface() {
        surface = null;
        surface = new BufferedImage(bounds.width + margins.left + margins.right, bounds.height + margins.top + margins.bottom, BufferedImage.TYPE_INT_ARGB);
    }

    public Widget() {
        moveTo(0, 0);
        setBounds( 1, 1);

        id = Long.toString(System.currentTimeMillis());
    }

    public Widget(int atX, int atY, int width, int height) {
        moveTo(atX, atY);
        setBounds(width, height);

        id = Long.toString(System.currentTimeMillis());
    }

    public void moveTo(int toX, int toY) {
        bounds.x = toX;
        bounds.y = toY;
    }

    public void setBounds(int width, int height) {
        bounds.width = Math.max(width + margins.left + margins.right, 1);
        bounds.height = Math.max(height + margins.top + margins.bottom, 1);

        resurface();
    }

    public void setMargins(Margins m) {
        margins.left = m.left;
        margins.right = m.right;
        margins.top = m.top;
        margins.bottom = m.bottom;

        resurface();
    }

    public void addChild(Widget child) {
        child.parent = this;
        children.add(child);

        resurface();
    }

    public void setId(String id) { this.id = id; }

    public Widget findById(String id) {
        if(this.id.equals(id)) return this;

        for(Widget w : children) {
            Widget found = w.findById(id);
            if(found != null) return found;
        }

        return null;
    }

    @Override
    public void update(double frameTime) {
    }

    @Override
    public BufferedImage image() {
        BufferedImage composite = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = composite.getGraphics();
        g.drawImage(surface, 0, 0, bounds.width, bounds.height, null);
        for(Widget w : children) {
            g.drawImage(w.image(), w.bounds.x, w.bounds.y, w.bounds.width, w.bounds.height, null);
        }
        g.dispose();

        return composite;
    }

    @Override
    public int getX() {
        return bounds.x;
    }

    @Override
    public int getY() {
        return bounds.y;
    }
}
