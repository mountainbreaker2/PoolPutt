package com.mountainbreaker.world;

public interface Controllable {
    String getControllerId();
    void onMove(Vec2 direction, float speed);
    void onInstruction(String instruction);

}
