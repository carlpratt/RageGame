package com.cpratt.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
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
        velocity = new Vector2(0, 100);
        acceleration = new Vector2(0, 460);
        bounds = new Rectangle();
    }

    public void update(float delta, List<Block> blocks) {

        velocity.add(acceleration.cpy().scl(delta));

        position.add(velocity.cpy().scl(delta));

        bounds.set(position.x, position.y, 15, 20);

        for (Block block : blocks) {
            if (collides(position, bounds, block)) {

                float xOverlapLeft = Math.abs(position.x + 15 - block.getPosition().x);
                float xOverlapRight = Math.abs(block.getPosition().x + 20 - position.x);

                float yOverlapTop = Math.abs(position.y + 20 - block.getPosition().y);
                float yOverlapBottom = Math.abs(block.getPosition().y + 20 - position.y);

                float xOverlap = xOverlapLeft < xOverlapRight ? xOverlapLeft : xOverlapRight;
                float yOverlap = yOverlapTop < yOverlapBottom ? yOverlapTop : yOverlapBottom;

                System.out.println("yOverlap: " + yOverlap);
                System.out.println("xOverlap: " + xOverlap);

                if (yOverlap < xOverlap + 1) {
                    if (yOverlapTop < yOverlapBottom) {
                        position.y = block.getPosition().y - 20;
                    } else {
                        position.y = block.getPosition().y + 20;
                    }
                    velocity.y = 0;
                } else {
                    if (xOverlapLeft < xOverlapRight) {
                        position.x = block.getPosition().x - 15;
                    } else {
                        position.x = block.getPosition().x + 20;
                    }
                    stopMoving();
                }
                System.out.println();
                break;
            }
        }

        // Allows player to continue moving left / right after jumping over an obstacle
        Vector2 tmpPos = new Vector2();
        float tmpVelX = isRightKeyPressed() ? 100 : isLeftKeyPressed() ? -100 : 0;
        float tmpVelY = velocity.y;
        tmpVelX *= delta;
        tmpVelY *= delta;
        tmpPos.x = position.x + tmpVelX;
        tmpPos.y = position.y + tmpVelY;
        Rectangle tmpBounds = new Rectangle(tmpPos.x, tmpPos.y, 15, 20);

        boolean collision = false;

        for (Block b : blocks) {
            if (collides(tmpPos, tmpBounds, b))
                collision = true;
        }

        if (!collision) {
            System.out.println("MOVING");
            if (isRightKeyPressed()) {
                moveRight();
            }
            if (isLeftKeyPressed()) {
                moveLeft();
            }
        }
    }

    public boolean collides(Vector2 position, Rectangle bounds, Block block) {
        if (Math.abs(position.x - block.getPosition().x) < 20 && Math.abs(position.y - block.getPosition().y) < 40) {
            return (Intersector.overlaps(bounds, block.getBounds()));
        }
        return false;
    }

    public void jump() {
        System.out.println("JUMP");
        velocity.y = -200;
        acceleration.y = 460;
    }

    public void moveRight() {
        velocity.x = 100;
    }

    public void moveLeft() {
        velocity.x = -100;
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
