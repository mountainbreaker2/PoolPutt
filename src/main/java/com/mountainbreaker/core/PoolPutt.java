package com.mountainbreaker.core;

import com.mountainbreaker.graphics.Viewport;
import com.mountainbreaker.ui.Area;
import com.mountainbreaker.ui.Text;
import com.mountainbreaker.ui.Widget;
import com.mountainbreaker.world.World;
import com.mountainbreaker.world.Tile;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PoolPutt extends Canvas implements Runnable {

    public static final int WIDTH = 256;
    public static final int HEIGHT = 208;
    public static final float SCALE = 3.0f;
    public static final String NAME = "Pool Putt";

    World world;

    Text testText;
    Area testArea;

    float speed = 3.0f;

    public static final int FRAMERATE = 60;

    boolean running = false;

    PoolPutt() {

        world = new World(new Viewport(this, NAME, WIDTH, HEIGHT, SCALE), (int)(WIDTH * 1.5), (int)(HEIGHT * 1.5), 16);

        testArea = new Area(0, (HEIGHT / 4) * 3, WIDTH / 3, HEIGHT / 4, new Color(200, 200, 200), Area.BackgroundStyle.STYLE_SHARP);
        world.addWidget(testArea);

        testText = new Text("Tooooltip");
        testText.moveTo(0, 0);
        testText.setMargins(new Widget.Margins(0, 0, 0, 0));
        testText.setId("testText");

        testArea.addChild(testText);
        //world.addWidget(testText);



        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                switch(e.getButton()) {
                    case MouseEvent.BUTTON1:
                        world.addComponent(new Tile("monster", world.vtwX(e.getX()), world.vtwY(e.getY()), true, true));
                        break;
                    case MouseEvent.BUTTON2:
                        world.setTile(world.vtwX(e.getX()), world.vtwY(e.getY()), 3);
                        break;
                    case MouseEvent.BUTTON3:
                        world.addComponent(new Tile("ghost", world.vtwX(e.getX()), world.vtwY(e.getY()), true, true));
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

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);



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

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                Text t = (Text)testArea.findById("testText");
                //if(t != null) t.setText("Found");
            }
        });

        world.build();

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
                Point p = getMousePosition();
                if(p != null) {
                    //testText.moveTo((int)(p.x / world.getViewport().scale), (int)(p.y / world.getViewport().scale));
                    testText.setText("(" + String.format("%.2f", world.vtwX(p.x)) + ", " + String.format("%.2f", world.vtwY(p.y)) + ")", Color.BLACK);
                }
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
