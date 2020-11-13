package com.mountainbreaker.input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputEvent {
    public static final int INPUT_INVALID = 0;
    public static final int INPUT_KEY = 1;
    public static final int INPUT_MOUSE = 2;

    protected static boolean[] keys = null;
    protected static boolean[] buttons = null;

    private static float inputScale;

    private final int inputType;

    private final int eventType;
    private final int keyCode;
    private final int mouseX;
    private final int mouseY;

    public static void setInputScale(float inputScale) { InputEvent.inputScale = inputScale;}
    public static float getInputScale() { return inputScale;}

    InputEvent(KeyEvent k, int mouseX, int mouseY) {
        if(keys == null) keys = new boolean[KeyEvent.VK_Z - KeyEvent.VK_0 + 1];
        if(buttons == null) buttons = new boolean[MouseInfo.getNumberOfButtons()];

        switch (k.getID()) {
            case KeyEvent.KEY_TYPED:
                break;
            case KeyEvent.KEY_PRESSED:
                keys[k.getKeyCode() - KeyEvent.VK_0] = true;
                break;
            case KeyEvent.KEY_RELEASED:
                keys[k.getKeyCode() - KeyEvent.VK_0] = false;
                break;
        }

        inputType = INPUT_KEY;

        eventType = k.getID();
        keyCode = k.getKeyCode();

        this.mouseX = (int)(mouseX / inputScale);
        this.mouseY = (int)(mouseY / inputScale);
    }

    InputEvent(MouseEvent m) {
        if(keys == null) keys = new boolean[KeyEvent.VK_Z - KeyEvent.VK_0];
        if(buttons == null) buttons = new boolean[MouseInfo.getNumberOfButtons()];

        switch (m.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                buttons[m.getButton()] = true;
                break;
            case KeyEvent.KEY_RELEASED:
                buttons[m.getButton()] = false;
                break;
        }

        inputType = INPUT_MOUSE;

        eventType = m.getID();
        keyCode = -1;

        mouseX = (int)(m.getX() / inputScale);
        mouseY = (int)(m.getY() / inputScale);
    }

    public int getEventType() {
        return eventType;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean keyState(int keyCode) {
        if(keyCode > keys.length - 1) return false;
        return keys[keyCode];
    }

    public boolean buttonState(int button) {
        if(button > buttons.length - 1) return false;
        return buttons[button];
    }

    public int getInputType() {
        return inputType;
    }
}
