package com.mountainbreaker.ui;

import com.mountainbreaker.graphics.Sprite;
import com.mountainbreaker.graphics.TiledImage;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ScreenText extends Sprite {
    private String text;
    private int marTop, marBottom, marLeft, marRight;

    public ScreenText() {
        marTop = marBottom = marLeft = marRight = 1;
        setText("");
    }

    public ScreenText(String text, int marginTop, int marginBottom, int marginLeft, int marginRight) {
        marTop = marginTop;
        marLeft = marginLeft;
        marBottom = marginBottom;
        marRight = marginRight;

        spriteSheet = new TiledImage();

        setText(text);
    }

    public void setText(String text) {
        this.text = text;

        FontRenderContext frContext = new FontRenderContext(new AffineTransform(), false, true);
        Font font = new Font("Tahoma", Font.PLAIN, 8);
        int strWidth = (int)(font.getStringBounds(this.text, frContext).getWidth());
        int strHeight = (int)(font.getStringBounds(this.text, frContext).getHeight());

        spriteSheet.baseImage = new BufferedImage(marLeft + strWidth + marRight, marTop + strHeight + marBottom, BufferedImage.TYPE_INT_RGB);
        spriteSheet.tileSize = Math.max(marLeft + strWidth + marRight, marTop + strHeight + marBottom);

        Graphics g = spriteSheet.baseImage.getGraphics();
        g.setFont(font);
        g.drawString(this.text, marLeft, strHeight);
        g.dispose();
    }

    @Override
    public BufferedImage image() {
        return spriteSheet.baseImage;
    }
}
