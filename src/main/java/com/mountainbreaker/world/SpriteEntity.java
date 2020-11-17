package com.mountainbreaker.world;

import com.mountainbreaker.graphics.Sprite;
import com.mountainbreaker.input.InputEvent;

import java.util.Objects;

public class SpriteEntity extends Entity {
    private Sprite sprite;

    public SpriteEntity() {
        sprite = Sprite.nullSprite();
        registerDrawableComponent(sprite);

    }

    public SpriteEntity(Sprite sprite) {
        this.sprite = Objects.requireNonNullElseGet(sprite, Sprite::nullSprite);
        size = new Vec2(this.sprite.image().getWidth(), this.sprite.image().getHeight());

        registerDrawableComponent(this.sprite);
    }

}
