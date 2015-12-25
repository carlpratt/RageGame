package com.cpratt.helpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.cpratt.gameobjects.Player;
import com.cpratt.gameworld.GameWorld;

public class InputHandler implements InputProcessor {

    private GameWorld world;
    private Player player;

    public InputHandler(GameWorld world) {
        this.world = world;
        this.player = world.getPlayer();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (world.isGameOver()) {
            world.restart();
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.DPAD_UP :
                player.setUpKeyPressed(true);
                if (player.getVelocity().y == 0) {
                    player.jump();
                }
                break;
            case Input.Keys.DPAD_LEFT :
                player.setLeftKeyPressed(true);
                if (player.isRightKeyPressed())
                    player.stopMoving();
                else
                    player.moveLeft();
                break;
            case Input.Keys.DPAD_RIGHT :
                player.setRightKeyPressed(true);
                if (player.isLeftKeyPressed())
                    player.stopMoving();
                else
                    player.moveRight();
                break;
            default :
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.DPAD_UP :
                player.setUpKeyPressed(false);
                break;
            case Input.Keys.DPAD_LEFT :
                player.setLeftKeyPressed(false);
                if (player.isRightKeyPressed())
                    player.moveRight();
                else
                    player.stopMoving();
                break;
            case Input.Keys.DPAD_RIGHT :
                player.setRightKeyPressed(false);
                if (player.isLeftKeyPressed())
                    player.moveLeft();
                else
                    player.stopMoving();
                break;
            default :
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}

