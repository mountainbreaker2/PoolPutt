package com.mountainbreaker.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Viewport extends JFrame {
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    public int width = DEFAULT_WIDTH;
    public int height = DEFAULT_HEIGHT;

    public float scale = 1.0f;

    public String name = "";

    public Canvas parentCanvas = null;

    public Viewport() {
        super("Screen");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Viewport(Canvas canvas, String applicationName, float viewScale) {
        super(applicationName);

        scale = Math.max(viewScale, 1.0f);

        width = (int)(canvas.getPreferredSize().width / scale);
        height = (int)(canvas.getPreferredSize().height / scale);

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
            g.fillRect(0, 0, (int)(width * scale), (int)(height * scale));
            for(Sprite sprite : spriteList) {
                int drawSize = (int)(sprite.spriteSheet.tileSize * scale);
                g.drawImage(sprite.image(), (int)(sprite.px), (int)(sprite.py), drawSize, drawSize, null);
            }

            g.dispose();
            parentCanvas.getBufferStrategy().show();
            getToolkit().sync();
        }

    }
}
