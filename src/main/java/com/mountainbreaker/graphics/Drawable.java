package com.mountainbreaker.graphics;

import java.awt.image.BufferedImage;

public interface Drawable {
    BufferedImage image();

    int drawX();
    int drawY();
}
