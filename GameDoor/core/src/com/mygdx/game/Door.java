package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.door.gameobject.GameManager;
import com.door.gameobject.InputManager;

public class Door extends ApplicationAdapter {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private float w, h;

	@Override
	public void create(){
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(w, h);
		camera.setToOrtho(false);
		batch = new SpriteBatch();
		GameManager.initialize(w, h);
	}

	public void dispose(){
		batch.dispose();
		GameManager.dispose();
	}

	@Override
	public void render(){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1,1,1,1);
		batch.setProjectionMatrix(camera.combined);
		InputManager.handleInput(camera);
		batch.begin();
		GameManager.renderGame(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
