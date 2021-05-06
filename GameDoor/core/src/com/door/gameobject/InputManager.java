package com.door.gameobject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Door;

public class InputManager {

    public static void handleInput(OrthographicCamera camera){
        if(Gdx.input.justTouched()){
            GameManager.temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(GameManager.temp);
            float touchX = GameManager.temp.x;
            float touchY = GameManager.temp.y;
            for (int i = 0; i < GameManager.doors.size; i++) {
                DoorClass door = GameManager.doors.get(i);
                if(!door.isOpen){
                    if(handleDoor(door, touchX, touchY, i)){
                        break;
                    }
                }
            }
            handleRestart(touchX, touchY);
        }
    }

    public static boolean handleDoor(DoorClass door, float touchX, float touchY, int doorIndex) {
        if ((touchX >= door.position.x) && touchX <= (door.position.x + door.width) &&
                (touchY >= door.position.y) && touchY <= (door.position.y + door.height)) {
            switch (GameManager.level) {
                case START:
                    GameManager.doors.get(GameManager.getGoatIndices(doorIndex).random()).isOpen = true;
                    GameManager.level = GameManager.Level.CONFIRM;
                    TextManager.setSelectedDoor(doorIndex);
                    break;
                case CONFIRM:
                    door.isOpen = true;
                    if(!door.isGoat){
                        GameManager.hasWon = true;
                    }
                    GameManager.level = GameManager.Level.END;
                    break;
            }
            return true;
        }
        return false;
    }

    public static void handleRestart(float touchX, float touchY){
        if ((touchX >= GameManager.restartSprite.getX()) && touchX <= (GameManager.restartSprite.getX() + GameManager.restartSprite.getWidth()) && (touchY >= GameManager.restartSprite.getY()) && touchY <= (GameManager.restartSprite.getY() + GameManager.restartSprite.getHeight())) {
            GameManager.restartGame();
        }
    }
}
