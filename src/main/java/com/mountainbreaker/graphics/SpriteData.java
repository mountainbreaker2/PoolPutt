package com.mountainbreaker.graphics;

import com.fasterxml.jackson.databind.JsonNode;
import com.mountainbreaker.core.JSONLoader;

import java.util.ArrayList;
import java.util.Vector;

public class SpriteData {
    ///////////////////////////////////////////////////////////////////
    // Statics
    private static final ArrayList<SpriteData> spriteList = new ArrayList<>();

    public static SpriteData load(String id) {
        if(spriteList.size() == 0) {
            spriteList.add(new SpriteData());
        }

        for(SpriteData data : spriteList) {
            if(data.id.equals(id)) return data;
        }

        JsonNode node;
        try {
            node = JSONLoader.parse(Sprite.class.getResourceAsStream(genPath(id)));
        } catch (Throwable e) {
            return spriteList.get(0);
        }
        if(node == null) return spriteList.get(0);

        SpriteData data = JSONLoader.fromJSON(node, SpriteData.class);
        /*for(Sprite.Animation anim : data.getAnimations()) {
            System.out.println("ID " + anim.id + " start: " + anim.startFrame + " end: " + anim.endFrame + " framerate: " + anim.framerate);
        }*/
        spriteList.add(data);
        return data;
    }

    private static String genPath(String id) {
        return "/data/world/" + id + ".json";
    }
    ///////////////////////////////////////////////////////////////////
    // Object Data
    private String id;
    private ArrayList<Sprite.Animation> animations;

    ///////////////////////////////////////////////////////////////////
    // Constructors
    SpriteData() {
        id = "null";
        animations = new ArrayList<>();
        animations.add(new Sprite.Animation());
    }

    SpriteData(String spriteSheetFile, ArrayList<Sprite.Animation> animations) {
        this.id = spriteSheetFile;
        this.animations = animations;
    }

    ///////////////////////////////////////////////////////////////////
    // Get/Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Sprite.Animation> getAnimations() {
        return animations;
    }

    public void setAnimations(ArrayList<Sprite.Animation> animations) {
        this.animations = animations;
    }
}
