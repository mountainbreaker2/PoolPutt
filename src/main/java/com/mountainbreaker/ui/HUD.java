package com.mountainbreaker.ui;

public class HUD {
    Widget master;

    public HUD() {
        master = new Widget();
    }

    public HUD(int width, int height, Widget masterWidget) {
        if(masterWidget == null) {
            master = new Widget(0, 0, width, height);
        }
        else master = masterWidget;
    }

    public void addWidget(Widget widget) {
        master.addChild(widget);
    }

}
