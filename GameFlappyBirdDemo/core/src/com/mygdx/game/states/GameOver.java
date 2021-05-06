package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FlappyDemo;

/**
 * Created by user on 18.02.18.
 */

public class GameOver extends State{

    private Texture background;
    private Texture gameover;

    public GameOver(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        background = new Texture("bg.png");
        gameover = new Texture("gameover.png");
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined );
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y - gameover.getHeight() / 2);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();
        System.out.println("Gameover Disposed");
    }
}
