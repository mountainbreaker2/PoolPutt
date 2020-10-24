package com.mountainbreaker.core;

import com.mountainbreaker.graphics.Drawable;
import com.mountainbreaker.graphics.Viewport;
import com.mountainbreaker.input.InputListener;
import com.mountainbreaker.ui.*;
import com.mountainbreaker.world.World;
import com.mountainbreaker.world.Tile;

//import java.awt.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;

public class PoolPutt extends Canvas implements Runnable {

    public static final int WIDTH = 256;
    public static final int HEIGHT = 208;
    public static final float SCALE = 3.0f;
    public static final String NAME = "Pool Putt";

    World world;

    InputListener inputListener;

    HUD hud;

    float speed = 3.0f;

    public static final int FRAMERATE = 60;

    boolean running = false;

    PoolPutt() {

        world = new World(new Viewport(this, NAME, WIDTH, HEIGHT, SCALE), (int)(WIDTH * 1.5), (int)(HEIGHT * 1.5), 16);

        inputListener = new InputListener(this, SCALE);
        hud = new HUD(WIDTH, HEIGHT);
        hud.addChild(new Button(10, 10, 50, 50, Color.DARK_GRAY, Area.BackgroundStyle.STYLE_SHARP));
        inputListener.addInteractionListener(hud);
        addMouseListener(inputListener);
        addKeyListener(inputListener);

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
                world.update(timeNow);
                world.getViewport().render(new Drawable[] {hud});

                ArrayList<Drawable> drawList = world.getDrawList();
                drawList.add(hud);
                world.getViewport().render(drawList.toArray(new Drawable[0]));

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
