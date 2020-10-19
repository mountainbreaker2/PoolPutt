package com.mountainbreaker.ui;

import com.mountainbreaker.graphics.Sprite;
import com.mountainbreaker.graphics.TiledImage;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Text extends Widget {
    private String text;

    public Text() {
        setText("");
    }

    public Text(String text) {
        setText(text);
    }

    public void setText(String text) {
        this.text = text;

        FontRenderContext frContext = new FontRenderContext(new AffineTransform(), false, true);
        Font font = new Font("Tahoma", Font.PLAIN, 8);
        setBounds((int)(font.getStringBounds(this.text, frContext).getWidth() + 2), (int)(font.getStringBounds(this.text, frContext).getHeight() + 1));

        Graphics g = surface.getGraphics();
        g.setFont(font);
        g.drawString(this.text, margins.left, bounds.height - 2 + margins.bottom);
        g.dispose();
    }

}
