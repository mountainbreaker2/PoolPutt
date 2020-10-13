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
            SpriteData data = new SpriteData();
        }

        JsonNode node;
        try {
            //System.out.println(genPath(id));
            node = JSONLoader.parse(Sprite.class.getResourceAsStream(genPath(id)));
        } catch (Throwable e) {
            return spriteList.get(0);
        }

        SpriteData data = JSONLoader.fromJSON(node, SpriteData.class);
        spriteList.add(data);

        return data;
    }

    private static String genPath(String id) {
        return "/data/world/" + id + ".json";
    }
    ///////////////////////////////////////////////////////////////////
    // Object Data
    private String id;
    private Vector<Sprite.Animation> animations;

    ///////////////////////////////////////////////////////////////////
    // Constructors
    SpriteData() {
        id = "null";
        animations = new Vector<>();
    }

    SpriteData(String spriteSheetFile, Vector<Sprite.Animation> animations) {
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

    public Vector<Sprite.Animation> getAnimations() {
        return animations;
    }

    public void setAnimations(Vector<Sprite.Animation> animations) {
        this.animations = animations;
    }
}
