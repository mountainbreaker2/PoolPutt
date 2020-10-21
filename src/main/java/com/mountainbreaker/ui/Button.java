package com.mountainbreaker.ui;

import com.mountainbreaker.input.Interactive;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Button extends Area implements Interactive {
    public Button(int pX, int pY, int width, int height, Color bgColor, BackgroundStyle bgStyle) {
        super(pX, pY, width, height, bgColor, bgStyle);
    }

    @Override
    public boolean onInteract(MouseEvent m, KeyEvent k) {
        if(k != null || m == null) { return false;}

        if(m.getID() == MouseEvent.MOUSE_CLICKED){

        }

        return false;
    }
}
