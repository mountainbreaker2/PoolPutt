package com.mountainbreaker.ui;

import com.mountainbreaker.input.InputEvent;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Text extends Widget {
    private String text;
    private Color textColor;
    private Color backgroundColor;

    public Text() {
        setText("", Color.BLACK);
    }

    public Text(String text) {
        setText(text, Color.BLACK);
    }

    public void setText(String text, Color textColor) {
        this.text = text;
        this.textColor = textColor;

        needUpdate = true;
    }

    @Override
    protected void resurface() {
        FontRenderContext frContext = new FontRenderContext(new AffineTransform(), false, true);
        Font font = new Font("Tahoma", Font.PLAIN, 8);
        setBounds((int)(font.getStringBounds(this.text, frContext).getWidth() + 2), (int)(font.getStringBounds(this.text, frContext).getHeight() + 1));

        surface = null;
        surface = new BufferedImage(bounds.width + margins.left + margins.right, bounds.height + margins.top + margins.bottom, BufferedImage.TYPE_INT_ARGB);

        Graphics g = surface.getGraphics();
        g.setFont(font);
        g.setColor(textColor);
        g.drawString(this.text, margins.left, bounds.height - 2 + margins.bottom);
        g.dispose();
    }

    @Override
    public boolean onAction(InputEvent e) {
        return false;
    }
}
