package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Camera extends Entity implements Drawable {
    private float viewWidth, viewHeight;
    private BufferedImage cameraBuffer;

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
    public void update(double frameTime) {

    }

    @Override
    public BufferedImage image() {
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
}
