package com.mountainbreaker.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class Viewport extends JFrame {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    public int width = DEFAULT_WIDTH;
    public int height = DEFAULT_HEIGHT;

    public float scale = 1.0f;

    private BufferedImage unscaledBuffer;

    public String name = "";

    public Canvas parentCanvas = null;

    private Viewport() {
        super("Screen");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Viewport(Canvas canvas, String applicationName, int sizeX, int sizeY, float viewScale) {
        super(applicationName);

        parentCanvas = canvas;

        scale = Math.max(viewScale, 1.0f);

        width = sizeX;
        height = sizeY;

        parentCanvas.setMinimumSize(new Dimension((int)(width * scale), (int)(height * scale)));
        parentCanvas.setMaximumSize(new Dimension((int)(width * scale), (int)(height * scale)));
        parentCanvas.setPreferredSize(new Dimension((int)(width * scale), (int)(height * scale)));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        pack();

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        parentCanvas.createBufferStrategy(2);

        unscaledBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void render(Sprite[] spriteList) {
        if(parentCanvas != null && parentCanvas.getBufferStrategy() != null) {
            //g.setColor(Color.BLACK);
            //g.fillRect(0, 0, (int)(width), (int)(height));
            Graphics g = unscaledBuffer.getGraphics();
            for(Sprite sprite : spriteList) {
                g.drawImage(sprite.image(), sprite.px, sprite.py, sprite.spriteSheet.tileSize, sprite.spriteSheet.tileSize, null);
            }

            g.dispose();

            int drawX = (int)(unscaledBuffer.getWidth() * scale);
            int drawY = (int)(unscaledBuffer.getHeight() * scale);
            g = parentCanvas.getBufferStrategy().getDrawGraphics();
            g.drawImage(unscaledBuffer, 0, 0, drawX, drawY, null);
            g.dispose();
            parentCanvas.getBufferStrategy().show();
            getToolkit().sync();
        }

    }

}
