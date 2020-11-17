package com.mountainbreaker.input;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InputListener implements MouseListener, MouseMotionListener, KeyListener {
    private Canvas parent;
    private final ArrayList<Interactive> listeners = new ArrayList<>();

    public InputListener(Canvas parent, float viewScale) {
        this.parent = parent;
        InputEvent.setInputScale(viewScale);
    }

    public void addInteractionListener(Interactive i) {
        listeners.add(i);
    }

    private void pollListeners(KeyEvent k, MouseEvent m) {
        if(k != null) {
            Point mPos = new Point(-1, -1);
            if(parent != null) {
                mPos = parent.getMousePosition();
            }
            if(mPos == null) mPos = new Point(-1, -1);

            InputEvent e = new InputEvent(k, mPos.x, mPos.y);
            for (Interactive i : listeners) {
                if (i.onInteract(e)) return;
            }

            return;
        }

        if(m != null) {
            InputEvent e = new InputEvent(m);
            for (Interactive i : listeners) {
                if (i.onInteract(e)) return;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        pollListeners(e, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pollListeners(e, null);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pollListeners(e, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        pollListeners(null, e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pollListeners(null, e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pollListeners(null, e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        pollListeners(null, e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        pollListeners(null, e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        pollListeners(null, e);
    }

    @Override
    public void mouseMoved(MouseEvent e) { pollListeners(null, e); }
}
