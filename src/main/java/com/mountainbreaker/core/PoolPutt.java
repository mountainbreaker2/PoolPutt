package com.mountainbreaker.core;

import com.mountainbreaker.graphics.Sprite;
import com.mountainbreaker.graphics.Viewport;
import com.mountainbreaker.world.World;
import com.mountainbreaker.world.Tile;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PoolPutt extends Canvas implements Runnable {

    public static final int WIDTH = 260;
    public static final int HEIGHT = 200;
    public static final float SCALE = 3.0f;
    public static final String NAME = "Pool Putt";

    World world;

    float speed = 3.0f;

    public static final int FRAMERATE = 60;

    boolean running = false;

    PoolPutt() {
        setMinimumSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
        setMaximumSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
        setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));

        world = new World(new Viewport(this, NAME, SCALE), WIDTH * 2, HEIGHT * 2, 16);
        world.getViewport().scale = 3.0f;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                switch(e.getButton()) {
                    case MouseEvent.BUTTON1:
                        world.addComponent(new Tile(Sprite.load("monster"), world.vtwX(e.getX()), world.vtwY(e.getY()), true, true));
                        break;
                    case MouseEvent.BUTTON3:
                        System.out.println("Right clicked");
                        world.addComponent(new Tile(Sprite.load("ghost"), world.vtwX(e.getX()), world.vtwY(e.getY()), true, true));
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                        world.setViewMotionY(speed);
                        break;
                    case KeyEvent.VK_UP:
                        world.setViewMotionY(-speed);
                        break;
                    case KeyEvent.VK_LEFT:
                        world.setViewMotionX(-speed);
                        break;
                    case KeyEvent.VK_RIGHT:
                        world.setViewMotionX(speed);
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_UP:
                        world.setViewMotionY(0.0f);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        world.setViewMotionX(0.0f);
                        break;
                }
            }

        });

        //world.build();

        requestFocus();

    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        double lastUpdate = System.nanoTime();
        double frameInterval = 1000000000D / FRAMERATE;
        while(running) {
            double timeNow = System.nanoTime();
            if(timeNow - lastUpdate > frameInterval) {
                world.update(timeNow);

                lastUpdate = timeNow;
            }
            else {
                try {
                    Thread.sleep(2);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        new PoolPutt().start();
    }
}
