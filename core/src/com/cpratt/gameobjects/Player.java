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
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, GS.GRAVITY_ACCELERATION);
        bounds = new Rectangle();
    }

    /**
     * Handles update of player movement and collisions between env and player.
     * @param delta framerate
     * @param blocks block asssets
     */
    public void update(float delta, List<Block> blocks) {

        // Calculate new vectors and update the bounding rectangle to match
        velocity.add(acceleration.cpy().scl(delta));
        position.add(velocity.cpy().scl(delta));
        bounds.set(position.x, position.y, GS.PLAYER_WIDTH, GS.PLAYER_HEIGHT);

        // For every asset, determine if there was a collision, and handle player vectors appropriately.
        // Example: Player collides with block top, player's y velocity should be 0 and y pos should match block y + player height
        for (Block block : blocks) {
            if (collides(position, bounds, block)) {

                // Calculate overlaps between player and colliding block. Used to determine player final destination relative to block
                float xOverlapLeft = Math.abs(position.x + GS.PLAYER_WIDTH - block.getPosition().x);
                float xOverlapRight = Math.abs(block.getPosition().x + GS.BLOCK_WIDTH - position.x);

                float yOverlapTop = Math.abs(position.y + GS.PLAYER_HEIGHT - block.getPosition().y);
                float yOverlapBottom = Math.abs(block.getPosition().y + GS.BLOCK_HEIGHT - position.y);

                float xOverlap = xOverlapLeft < xOverlapRight ? xOverlapLeft : xOverlapRight;
                float yOverlap = yOverlapTop < yOverlapBottom ? yOverlapTop : yOverlapBottom;

                System.out.println("yOverlap: " + yOverlap);
                System.out.println("xOverlap: " + xOverlap);

                // Slightly favor y since block top collisions are most common
                if (yOverlap < xOverlap + 1) {
                    if (yOverlapTop < yOverlapBottom) {
                        position.y = block.getPosition().y - GS.PLAYER_HEIGHT;
                    } else {
                        position.y = block.getPosition().y + GS.BLOCK_HEIGHT;
                    }
                    velocity.y = 0;
                } else {
                    if (xOverlapLeft < xOverlapRight) {
                        position.x = block.getPosition().x - GS.PLAYER_WIDTH;
                    } else {
                        position.x = block.getPosition().x + GS.BLOCK_WIDTH;
                    }
                    stopMoving();
                }
                System.out.println();
                break;
            }
        }

        // Allows player to continue moving left / right after jumping over an obstacle
        Vector2 tmpPos = new Vector2();
        float tmpVelX = isRightKeyPressed() ? GS.PLAYER_LATERAL_VELOCITY : isLeftKeyPressed() ? -GS.PLAYER_LATERAL_VELOCITY : 0;
        float tmpVelY = velocity.y;
        tmpVelX *= delta;
        tmpVelY *= delta;
        tmpPos.x = position.x + tmpVelX;
        tmpPos.y = position.y + tmpVelY;
        Rectangle tmpBounds = new Rectangle(tmpPos.x, tmpPos.y, GS.PLAYER_WIDTH, GS.PLAYER_HEIGHT);

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

    /**
     * Determine if collision has happened. Only calculate collision if block is within range of player.
     */
    public boolean collides(Vector2 position, Rectangle bounds, Block block) {
        if (Math.abs(position.x - block.getPosition().x) <= GS.BLOCK_WIDTH
                && Math.abs(position.y - block.getPosition().y) <= GS.BLOCK_HEIGHT) {
            return (Intersector.overlaps(bounds, block.getBounds()));
        }
        return false;
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
