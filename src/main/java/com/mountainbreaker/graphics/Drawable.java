package com.mountainbreaker.graphics;

import java.awt.image.BufferedImage;

public interface Drawable {
    BufferedImage image();
    void update(double frameTime);

    int getX();
    int getY();
}
