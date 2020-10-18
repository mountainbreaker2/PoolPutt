package com.mountainbreaker.input;

import javax.swing.event.MouseInputListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class InputBinder implements KeyListener, MouseInputListener {

    private Vector<InputAction> boundKeyDownActions = new Vector<>();
    private Vector<InputAction> boundKeyUpActions = new Vector<>();
    private Vector<InputAction> boundKeyPressedActions = new Vector<>();

    public void bindAction(InputAction action) {
        switch (action.getTiming()) {

            case ON_KEY_DOWN:
                boundKeyDownActions.add(action);
                break;
            case ON_KEY_UP:
                boundKeyUpActions.add(action);
                break;
            case ON_KEY_TYPED:
                boundKeyPressedActions.add(action);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        for(InputAction action : boundKeyPressedActions) {
            if(action.getBoundKey() == e.getKeyCode()) {
                action.boundAction();
                return;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(InputAction action : boundKeyDownActions) {
            if(action.getBoundKey() == e.getKeyCode()) {
                action.boundAction();
                return;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        for(InputAction action : boundKeyUpActions) {
            if(action.getBoundKey() == e.getKeyCode()) {
                action.boundAction();
                return;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
