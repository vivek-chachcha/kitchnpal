package com.kitchnpal.kitchnpal;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class EntryScreen implements Screen {

    final MyKitchnpalApp app;
    private Texture backgroundImage;

    OrthographicCamera camera;

    public EntryScreen(final MyKitchnpalApp a) {
        app = a;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);

        backgroundImage = new Texture(Gdx.files.internal("turkey.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        app.batch.setProjectionMatrix(camera.combined);

        app.batch.begin();
        app.batch.draw(backgroundImage, 140, 200);
        app.font.draw(app.batch, "Kitchnpal", 200, 600);

        app.batch.end();

        if (Gdx.input.isTouched()) {
            app.setScreen(new MainScreen(app));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose () {
    }

}