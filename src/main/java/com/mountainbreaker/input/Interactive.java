package com.mountainbreaker.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Interactive {
    boolean onInteract(MouseEvent m, KeyEvent k);
}
