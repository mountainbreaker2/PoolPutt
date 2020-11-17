package com.mountainbreaker.world;

import com.mountainbreaker.core.DynamicObject;
import com.mountainbreaker.graphics.Drawable;
import com.mountainbreaker.graphics.Image;
import com.mountainbreaker.graphics.Sprite;
import com.mountainbreaker.input.InputEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class TileMap extends Entity {
    String tilesetId;
    Image tileset;
    int tilesX, tilesY, tileSize;
    char[] mapData;
    private final ArrayList<Entity> tiles = new ArrayList<>();

    public TileMap() {
        tilesetId = "null";
        tileset = null;

        tilesX = tilesY = 0;
        tileSize = 1;
    }

    public void loadTileset(String tilesetId, int tileSize) {
        this.tileset = Image.getImage(tilesetId);
        if(this.tileset == null) return;

        this.tilesetId = tilesetId;
        this.tileSize = Math.max(tileSize, 1);
        this.tileset.setTiled(this.tileSize);
    }

    public void buildTileMap(int tilesX, int tilesY) {
        if(tilesX < 1 || tilesY < 1) return;

        for(Entity tile : tiles) {
            tile.destroy();
        }
        tiles.clear();

        int mapArea = tilesX * tilesY;
        mapData = new char[mapArea];

        for(int i = 0; i < mapArea; i++) {
            mapData[i] = (char)(i % 6);
        }

        this.tilesX = tilesX;
        this.tilesY = tilesY;

        for(int t = 0; t < mapArea; t++) {
            Sprite.Animation appearance = new Sprite.Animation();
            appearance.startFrame = mapData[t];
            appearance.endFrame = mapData[t];
            Sprite tile = Sprite.create(tileset, new ArrayList<>(Collections.singletonList(appearance)));
            float tX = ((float)(t % tilesX) * this.tileSize) + getX();
            float tY = ((float)(t / tilesX) * this.tileSize) + getY();

            SpriteEntity spriteEntity = (SpriteEntity)(new SpriteEntity(tile).spawn(world, "tile" + t, tX, tY));
            tiles.add(spriteEntity);
        }
    }

    @Override
    public void update(double frameTime) {
        for(Drawable d : drawableComponents) {
            if(d instanceof DynamicObject) ((DynamicObject) d).update(frameTime);
        }
    }

    public boolean isTilesetLoaded() { return (tileset != null); }

    public boolean isMapDataLoaded() { return (mapData.length > 0); }

}
