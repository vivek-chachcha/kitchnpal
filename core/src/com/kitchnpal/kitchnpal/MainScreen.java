package com.kitchnpal.kitchnpal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.kitchnpal.kitchnpal.MyKitchnpalApp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class MainScreen implements Screen {
    final MyKitchnpalApp app;
    public OrthographicCamera camera;

    
    public MainScreen(final MyKitchnpalApp a) {
        this.app = a;
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override public boolean touchDown (int screenX, int screenY, int pointer, int button) {
                // ignore if its not left mouse button or first touch pointer
                if (button != Input.Buttons.LEFT || pointer > 0) return false;
                return false;
            }
            
            @Override public boolean touchDragged (int screenX, int screenY, int pointer) {
                return false;
            }
            
            @Override public boolean touchUp (int screenX, int screenY, int pointer, int button) {
                if (button != Input.Buttons.LEFT || pointer > 0) return false;
                return false;
            }
        });
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
    }
    
    @Override
    public void show() {
        
    }
    
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        app.batch.setProjectionMatrix(camera.combined);
        app.batch.begin();
        //All Rendering
        app.font.draw(app.batch, "Welcome", 100, 600);
        app.batch.end();
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
    
    @Override
    public void hide() {
        
    }
    
    @Override
    public void dispose() {

    }
}