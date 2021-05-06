package com.door.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.StringBuilder;

public class TextManager {

    static BitmapFont font;

    static String start = "SELECT A DOOR";
    static StringBuffer confirm;
    static String win = "You Win!";
    static String lose = "You Lose!";
    static float width, height;

    public static void initialize(float width, float height){
        TextManager.width = width;
        TextManager.height = height;
        font = new BitmapFont();
        font.setColor(Color.CYAN);
        font.getData().setScale(width/800f);

        confirm = new StringBuffer((String) "You selected door no.Do you want to switch or stay?");
    }

    public static void displayMessage(SpriteBatch batch){
        GlyphLayout glyphLayout = new GlyphLayout();
        switch (GameManager.level){
            case START:
                glyphLayout.setText(font, start);
                font.draw(batch, glyphLayout, (width / 2 - glyphLayout.width / 2), GameManager.doors.first().closeSprite.getY() / 2 + glyphLayout.height / 2);
                break;
            case CONFIRM:
                glyphLayout.setText(font, confirm);
                font.draw(batch, glyphLayout, (width / 2 - glyphLayout.width/2), GameManager.doors.first().closeSprite.getY()/2 + glyphLayout.height/2);
                break;
            case END:
                if(GameManager.hasWon){
                    glyphLayout.setText(font, win);
                    font.draw(batch, glyphLayout, (glyphLayout.width / 2 - glyphLayout.width/2), GameManager.doors.first().closeSprite.getY()/2 + glyphLayout.height/2);
                }
                else{
                    glyphLayout.setText(font, lose);
                    font.draw(batch, glyphLayout, (width / 2 - glyphLayout.width / 2), GameManager.doors.first().closeSprite.getY() / 2 + glyphLayout.height / 2);
                }
                break;
        }
    }

    public static void setSelectedDoor(int doorIndex){
        confirm.insert(confirm.indexOf("door no") + "door no".length(), " " + (doorIndex + 1));
    }
}
