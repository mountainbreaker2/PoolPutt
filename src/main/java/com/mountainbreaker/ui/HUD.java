package com.mountainbreaker.ui;

import com.mountainbreaker.graphics.Drawable;
import com.mountainbreaker.input.InputEvent;
import com.mountainbreaker.input.Interactive;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class HUD extends Widget {
    public static class Message {

    }

    public HUD() {
        //master = new Widget();
    }

    public HUD(int width, int height) {
        super(0, 0, width, height);
    }

    @Override
    public boolean onAction(InputEvent e) {
        return false;
    }
}
