package com.kitchnpal.kitchnpal;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

public class MyKitchnpalApp extends Game {
	public BitmapFont font;
	public SpriteBatch batch;
	Vector3 tp = new Vector3();
	OrthographicCamera camera;
	private FreeTypeFontGenerator generator;

	@Override
	public void create () {
		batch = new SpriteBatch();

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/alpha.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 22;
		parameter.borderWidth = 2;
		font = generator.generateFont(parameter);
		generator.dispose();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		this.setScreen(new EntryScreen(this));
	}

	public void render() {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
