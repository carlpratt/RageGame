package com.cpratt.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cpratt.settings.GS;

import java.util.List;

public class Player {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private Rectangle bounds;

    private boolean rightKeyPressed = false;
    private boolean leftKeyPressed = false;
    private boolean upKeyPressed = false;

    public Player(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, GS.GRAVITY_ACCELERATION);
        bounds = new Rectangle();
    }

    public void update(float delta, List<Block> blocks) {

        // Calculate new vectors and update the bounding rectangle to match
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));
        bounds.set(position.x, position.y, GS.PLAYER_WIDTH, GS.PLAYER_HEIGHT);
    }

    public void jump() {
        System.out.println("JUMP");
        velocity.y = GS.PLAYER_VERTICAL_VELOCITY;
        acceleration.y = GS.GRAVITY_ACCELERATION;
    }

    public void moveRight() {
        velocity.x = GS.PLAYER_LATERAL_VELOCITY;
    }

    public void moveLeft() {
        velocity.x = -GS.PLAYER_LATERAL_VELOCITY;
    }

    public void stopMoving() {
        velocity.x = 0;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isRightKeyPressed() {
        return rightKeyPressed;
    }

    public void setRightKeyPressed(boolean rightKeyPressed) {
        this.rightKeyPressed = rightKeyPressed;
    }

    public boolean isLeftKeyPressed() {
        return leftKeyPressed;
    }

    public void setLeftKeyPressed(boolean leftKeyPressed) {
        this.leftKeyPressed = leftKeyPressed;
    }

    public boolean isUpKeyPressed() {
        return upKeyPressed;
    }

    public void setUpKeyPressed(boolean upKeyPressed) {
        this.upKeyPressed = upKeyPressed;
    }
}
