package com.mountainbreaker.ui;

import com.mountainbreaker.input.InputEvent;

import java.awt.event.KeyEvent;


public class Menu extends Widget {
    int selected = 0;
    String[] options;

    Menu(String[] options) {
        this.options = options;

    }

    @Override
    public boolean onAction(InputEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) selected = Math.max(selected - 1, 0);
        if(e.getKeyCode() == KeyEvent.VK_DOWN) selected = (selected + 1) % options.length;

        if(e.getKeyCode() == KeyEvent.VK_ENTER) onSignal(options[selected]);

        return true;
    }
}
