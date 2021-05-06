package com.door.gameobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

/**
 * Created by user on 26.02.18.
 */

public class DoorClass {
    public Sprite openSprite, closeSprite;
    public boolean isOpen = false;
    public Vector2 position = new Vector2();
    public boolean isGoat = false;

    public float height, width;

    public void render(SpriteBatch batch){
        if(isOpen){
            openSprite.draw(batch);
        }else{
            closeSprite.draw(batch);
        }
    }
}
