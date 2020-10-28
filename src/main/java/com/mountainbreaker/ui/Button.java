package com.mountainbreaker.ui;

import com.mountainbreaker.input.InputEvent;
import com.mountainbreaker.input.Interactive;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Button extends Area {
    public Button(int pX, int pY, int width, int height, Color bgColor, BackgroundStyle bgStyle) {
        super(pX, pY, width, height, bgColor, bgStyle);
    }

    @Override
    public boolean onAction(InputEvent e) {
        if(e.getEventType() == MouseEvent.MOUSE_CLICKED){
            onSignal("clicked");

            return true;
        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Exiting");
            System.exit(0);
        }
        return false;
    }
}
