package com.mountainbreaker.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Area extends Widget {
    public enum BackgroundStyle {
        STYLE_SHARP,
        STYLE_ROUNDED,
        STYLE_CAPSULE
    }

    protected Color backgroundColor = Color.DARK_GRAY;
    protected BackgroundStyle bgStyle = BackgroundStyle.STYLE_SHARP;
    protected BufferedImage backgroundImage = null;

    @Override
    protected void resurface() {
        super.resurface();

        if(bgStyle == null) bgStyle = BackgroundStyle.STYLE_SHARP;

        Graphics g = surface.getGraphics();
        if(backgroundImage == null) {
            g.setColor(backgroundColor);
            switch(bgStyle) {
                case STYLE_SHARP:
                    g.fillRect(0, 0, bounds.width, bounds.height);
                    break;
                case STYLE_ROUNDED:
                    g.fillRoundRect(0, 0, bounds.width - 1, bounds.height - 1, 6, 6);
                    break;
                case STYLE_CAPSULE:
                    g.fillOval(0, 0, 12, bounds.height);
                    g.fillOval(bounds.width - 12, 0, 12, bounds.height);
                    g.fillRect(6, 0, bounds.width - 12, bounds.height);
                    break;
            }
        }

        g.dispose();
    }

    public Area(int pX, int pY, int width, int height, Color bgColor, BackgroundStyle bgStyle) {
        super(pX, pY, width, height);

        this.backgroundColor = bgColor;
        this.bgStyle = bgStyle;

        resurface();
    }

}
