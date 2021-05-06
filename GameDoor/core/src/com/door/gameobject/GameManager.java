package com.door.gameobject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.WindowedMean;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * Created by user on 26.02.18.
 */

public class GameManager {

    static Array<DoorClass> doors;
    static Texture doorTexture;

    static Texture carTexture;
    static Texture goatTexture;
    static Vector3 temp = new Vector3();
    static IntArray goatIndices;

    static Sprite restartSprite;
    static Texture restartTexture;
    static final float RESTART_RESIZE_FACTOR = 5500f;

    static Texture backTexture;
    static Sprite backSprite;

    private static final float DOOR_RESIZE_FACTOR = 2500f;
    private static final float DOOR_VERT_POSITION_FACTOR = 3f;
    private static final float DOOR1_HORIZ_POSITION_FACTOR = 7.77f;
    private static final float DOOR2_HORIZ_POSIRION_FACTOR = 2.57f;
    private static final float DOOR3_HORIZ_POSIRION_FACTOR = 1.52f;
    static float width, height;

    public static enum Level {
        START,
        CONFIRM,
        END
    }
    static Level level;

    static boolean hasWon = false;

    public static void initialize(float width, float height){
        GameManager.width = width;
        GameManager.height = height;
        doorTexture = new Texture(Gdx.files.internal("door_close.png"));
        carTexture = new Texture(Gdx.files.internal("door_open_car.png"));
        goatTexture = new Texture(Gdx.files.internal("door_open_goat.png"));
        goatIndices = new IntArray();
        level = Level.START;
        initDoors();
        TextManager.initialize(width, height);

        restartTexture = new Texture(Gdx.files.internal("restart.png"));
        restartSprite = new Sprite(restartTexture);
        restartSprite.setSize(restartSprite.getWidth() * width / RESTART_RESIZE_FACTOR, restartSprite.getHeight() * height / RESTART_RESIZE_FACTOR);
        restartSprite.setPosition(0, 0);

        backTexture = new Texture(Gdx.files.internal("background.jpg"));
        backSprite = new Sprite(backTexture);
        backSprite.setSize(width, height);
        backSprite.setPosition(0,0f);
    }

    public static void renderGame(SpriteBatch batch){
        backSprite.draw(batch);
        for (DoorClass door : doors) {
            door.render(batch);
        }
        TextManager.displayMessage(batch);
        restartSprite.draw(batch);
    }

    public static void dispose(){
        doorTexture.dispose();
        carTexture.dispose();
        goatTexture.dispose();
        backTexture.dispose();
    }

    public static void initDoors(){
        doors = new Array<DoorClass>();
        for(int i = 0; i < 3; i++) {
           doors.add(new DoorClass());
        }

        doors.get(0).position.set(width/DOOR1_HORIZ_POSITION_FACTOR, height/DOOR_VERT_POSITION_FACTOR);
        doors.get(1).position.set(width/DOOR2_HORIZ_POSIRION_FACTOR, height/DOOR_VERT_POSITION_FACTOR);
        doors.get(2).position.set(width/DOOR3_HORIZ_POSIRION_FACTOR, height/DOOR_VERT_POSITION_FACTOR);

        for (DoorClass door : doors) {
            door.closeSprite = new Sprite(doorTexture);
            door.openSprite = new Sprite();
            door.width = door.closeSprite.getWidth() * (width / DOOR_RESIZE_FACTOR);
            door.height = door.closeSprite.getHeight() * (width / DOOR_RESIZE_FACTOR);
            door.closeSprite.setSize(door.width, door.height);
            door.closeSprite.setPosition(door.position.x, door.position.y);

            door.openSprite.setSize(door.width, door.height);
            door.openSprite.setPosition(door.position.x, door.position.y);
        }

        doors.get(0).openSprite.setRegion(goatTexture);
        doors.get(0).isGoat = true;
        doors.get(1).openSprite.setRegion(carTexture);
        doors.get(1).isGoat = false;
        doors.get(2).openSprite.setRegion(goatTexture);
        doors.get(2).isGoat = false;
    }

    public static IntArray getGoatIndices(int selectedDoorIndex) {
        goatIndices.clear();
        for(int i = 0; i < doors.size; i++) {
            if (i != selectedDoorIndex && doors.get(i).isGoat) {
                goatIndices.add(i);
            }
        }
        return goatIndices;
    }

    public static void restartGame(){
        doors.shuffle();
        doors.get(0).position.set(width / DOOR1_HORIZ_POSITION_FACTOR, height / DOOR_VERT_POSITION_FACTOR);
        doors.get(1).position.set(width / DOOR2_HORIZ_POSIRION_FACTOR, height / DOOR_VERT_POSITION_FACTOR);
        doors.get(2).position.set(width / DOOR3_HORIZ_POSIRION_FACTOR, height / DOOR_VERT_POSITION_FACTOR);

        for(int i = 0; i < GameManager.doors.size; i++) {
           GameManager.doors.get(i).isOpen = false;
            GameManager.doors.get(i).closeSprite.setPosition(GameManager.doors.get(i).position.x, GameManager.doors.get(i).position.y);
            GameManager.doors.get(i).openSprite.setPosition(GameManager.doors.get(i).position.x, GameManager.doors.get(i).position.y);
        }

        GameManager.hasWon = false;
        GameManager.level = GameManager.level.START;
        TextManager.confirm = new StringBuffer((String) "You selected door no.Do you want to switch or stay?");
    }
}
