package com.mountainbreaker.core;

import com.mountainbreaker.graphics.Drawable;
import com.mountainbreaker.graphics.Image;
import com.mountainbreaker.graphics.Sprite;
import com.mountainbreaker.graphics.Viewport;
import com.mountainbreaker.input.InputListener;
import com.mountainbreaker.ui.*;
import com.mountainbreaker.ui.Button;
import com.mountainbreaker.world.Camera;
import com.mountainbreaker.world.TileMap;
import com.mountainbreaker.world.World;

import java.awt.*;

public class PoolPutt extends Canvas implements Runnable {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 150;
    public static final float SCALE = 5.0f;
    public static final String NAME = "Pool Putt";

    World world;
    Camera camera;

    InputListener inputListener;
    Viewport viewport;

    HUD hud;

    public static final int FRAMERATE = 60;

    boolean running = false;

    PoolPutt() {

        viewport = new Viewport(this, NAME, WIDTH, HEIGHT, SCALE);

        world = new World();
        camera = (Camera)(new Camera(WIDTH, HEIGHT).spawn(world, "Camera", 0, 0));
        TileMap tileMap = (TileMap)(new TileMap().spawn(world, "TileMap", 0.0f, 0.0f));
        tileMap.loadTileset("terrain", 16);
        tileMap.buildTileMap(20, 20);

        inputListener = new InputListener(this, SCALE);
        hud = new HUD(WIDTH, HEIGHT);
        Button b = new Button(10, 10, 50, 50, Color.DARK_GRAY, Area.BackgroundStyle.STYLE_SHARP);
        b.setId("button1");
        //hud.addChild(b);
        inputListener.addInteractionListener(hud);
        inputListener.addInteractionListener(world);
        addMouseListener(inputListener);
        addMouseMotionListener(inputListener);
        addKeyListener(inputListener);

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
                hud.update(timeNow);
                Drawable[] drawList = new Drawable[] {world.getCameras().get(0), hud};
                viewport.render(drawList);

                //ArrayList<Drawable> drawList = world.getDrawList();
                //drawList.add(hud);
                //world.getViewport().render(drawList.toArray(new Drawable[0]));

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
