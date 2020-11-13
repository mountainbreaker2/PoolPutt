package com.mountainbreaker.ui;

import com.fasterxml.jackson.databind.JsonNode;
import com.mountainbreaker.core.JSONLoader;
import com.mountainbreaker.input.InputEvent;

public class HUD extends Widget {

    public HUD() {
        //master = new Widget();
    }

    public HUD(int width, int height) {
        super(0, 0, width, height);
    }

    public void loadUI(String name) {
        JsonNode node = null;
        try {
            node = JSONLoader.parse(getClass().getResourceAsStream("/data/ui/play.json"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        JsonNode widget = null;
        if(node != null) {
            node.findValue("widgetType");
        }

    }

    @Override
    public boolean onAction(InputEvent e) {
        return false;
    }

    @Override
    public void onSignal(String message) {
        String[] tags = message.split(":");

        for(String tag : tags) {
            System.out.println(tag);
        }

    }
}
