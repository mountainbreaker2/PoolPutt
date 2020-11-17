package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Drawable;
import com.mountainbreaker.input.InputEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Camera extends Entity implements Drawable {
    private float viewWidth, viewHeight;
    private BufferedImage cameraBuffer;
    private final ArrayList<Entity> drawnEntities = new ArrayList<>();

    public Camera() {
        viewWidth = viewHeight = 1.0f;
        cameraBuffer = new BufferedImage((int)viewWidth, (int)viewHeight, BufferedImage.TYPE_INT_ARGB);
    }

    public Camera(float viewWidth, float viewHeight) {
        this.viewWidth = Math.max(viewWidth, 1.0f);
        this.viewHeight = Math.max(viewHeight, 1.0f);

        cameraBuffer = new BufferedImage((int)viewWidth, (int)viewHeight, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public BufferedImage image() {
        drawnEntities.clear();
        Graphics g = cameraBuffer.getGraphics();

        for(Entity entity : world.getDrawList()) {
            float entityLeft = entity.getX();
            float entityRight = entity.getX() + entity.getSizeX();
            float entityTop = entity.getY();
            float entityBottom = entity.getY() + entity.getSizeY();
            if (entityRight > translation.x) {
                if (entityLeft < translation.x + viewWidth) {
                    if (entityBottom > translation.y) {
                        if (entityTop < translation.y + viewHeight) {
                            drawnEntities.add(entity);
                            ArrayList<Drawable> drawList = entity.getDrawableComponents();
                            for (Drawable d : drawList) {
                                //System.out.println(entity.getId() + " drawn at (" + (int)(entity.getX() - this.getX()) + ", " + (int)(entity.getY() - this.getY()) + ")");
                                g.drawImage(d.image(), (int)(entity.getX() - this.getX()), (int)(entity.getY() - this.getY()), null);
                            }
                        }
                    }
                }
            }
        }

        g.dispose();

        return cameraBuffer;
    }

    @Override
    public int drawX() {
        return 0;
    }

    @Override
    public int drawY() {
        return 0;
    }

    @Override
    public boolean onAction(InputEvent e) {
        if(e.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    acceleration.x = -1.8f;
                    break;
                case KeyEvent.VK_D:
                    acceleration.x = 1.8f;
                    break;
                case KeyEvent.VK_W:
                    acceleration.y = -1.8f;
                    break;
                case KeyEvent.VK_S:
                    acceleration.y = 1.8f;
                    break;
            }
        }

        if(e.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                case KeyEvent.VK_D:
                    acceleration.x = 0.0f;
                    break;
                case KeyEvent.VK_W:
                case KeyEvent.VK_S:
                    acceleration.y = 0.0f;
                    break;
            }
        }
        return false;
    }

    public Vec2 mouseToWorld(int mouseX, int mouseY) {
        float worldX = mouseX + getX();
        float worldY = mouseY + getY();

        return new Vec2(worldX, worldY);
    }

    public ArrayList<Entity> getDrawnEntities() { return drawnEntities; }
}
