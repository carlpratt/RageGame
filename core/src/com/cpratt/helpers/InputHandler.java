package com.cpratt.helpers;

import com.badlogic.gdx.InputProcessor;
import com.cpratt.gameobjects.Player;

public class InputHandler implements InputProcessor {

    private Player player;

    public InputHandler(Player player) {
        this.player = player;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.jump();
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            // TODO: Find out what class these enums exist in
            case 19 :
                player.setUpKeyPressed(true);
                if (player.getVelocity().y == 0) {
                    player.jump();
                }
                break;
            case 21 :
                player.setLeftKeyPressed(true);
                if (player.isRightKeyPressed())
                    player.stopMoving();
                else
                    player.moveLeft();
                break;
            case 22 :
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
            case 19 :
                player.setUpKeyPressed(false);
                break;
            case 21 :
                player.setLeftKeyPressed(false);
                if (player.isRightKeyPressed())
                    player.moveRight();
                else
                    player.stopMoving();
                break;
            case 22 :
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

