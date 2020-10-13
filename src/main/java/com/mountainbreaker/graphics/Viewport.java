package com.mountainbreaker.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Viewport extends JFrame {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    private static int nextViewNum = 0;
    private int thisViewNumber;

    public int width = DEFAULT_WIDTH;
    public int height = DEFAULT_HEIGHT;

    public float scale = 1.0f;

    public String name = "";

    public Canvas parentCanvas = null;

    public Viewport() {
        super("Screen " + nextViewNum);
        thisViewNumber = nextViewNum;
        nextViewNum++;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Viewport(Canvas canvas, String applicationName) {
        super(applicationName);
        thisViewNumber = nextViewNum;
        nextViewNum++;

        width = canvas.getPreferredSize().width;
        height = canvas.getPreferredSize().height;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        pack();

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        parentCanvas = canvas;

        parentCanvas.createBufferStrategy(3);
    }

    public void render(Vector<Sprite> spriteList) {
        if(parentCanvas != null && parentCanvas.getBufferStrategy() != null) {
            Graphics g = parentCanvas.getBufferStrategy().getDrawGraphics();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            for(Sprite sprite : spriteList) {
                int drawSize = (int)(sprite.spriteSheet.tileSize * scale);
                g.drawImage(sprite.image(), sprite.px, sprite.py, drawSize, drawSize, null);
            }

            g.dispose();
            parentCanvas.getBufferStrategy().show();
            getToolkit().sync();
        }

    }
}
