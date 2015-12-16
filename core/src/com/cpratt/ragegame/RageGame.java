package com.cpratt.ragegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.cpratt.helpers.AssetLoader;
import com.cpratt.screens.GameScreen;

public class RageGame extends Game {

    @Override
    public void create() {
        Gdx.app.log("ZBGame", "created");
        AssetLoader.load();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
