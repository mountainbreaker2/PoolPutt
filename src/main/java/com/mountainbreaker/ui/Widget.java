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

    protected BufferedImage surface;

    protected Widget parent = null;
    protected final ArrayList<Widget> children = new ArrayList<>();

    private void resurface() {
        surface = null;
        surface = new BufferedImage(bounds.width + margins.left + margins.right, bounds.height + margins.top + margins.bottom, BufferedImage.TYPE_INT_RGB);
    }

    public Widget() {
        moveTo(0, 0);
        setBounds( 1, 1);
    }

    public Widget(int atX, int atY, int width, int height) {
        moveTo(atX, atY);
        setBounds(width, height);
    }

    public void moveTo(int toX, int toY) {
        bounds.x = toX;
        bounds.y = toY;
    }

    public void setBounds(int width, int height) {
        bounds.width = Math.max(width, 1);
        bounds.height = Math.max(height, 1);

        resurface();
    }

    public void setMargins(Margins m) {
        margins.left = m.left;
        margins.right = m.right;
        margins.top = m.top;
        margins.bottom = m.bottom;

        resurface();
    }

    @Override
    public BufferedImage image() {
        return surface;
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
