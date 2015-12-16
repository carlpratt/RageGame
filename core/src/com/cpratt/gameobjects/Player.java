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

        boolean collision = false;

        for (Block block : blocks) {
            if (collides(block)) {
                Gdx.app.log("Collides", "true");
                if (position.y < block.getPosition().y && position.x > block.getPosition().x - 15 && position.x < block.getPosition().x + 20) {
                    Gdx.app.log("Collides", "bottom");
                    position.y = block.getPosition().y - 20;
                    velocity.y = 0;
//                    acceleration.y = 0;
                } else if (position.y >= block.getPosition().y) {
                    if (position.x >= block.getPosition().x) {
                        Gdx.app.log("Collides", "right side");
                        position.x = block.getPosition().x + 20;
                        stopMoving();
                    } else if (position.x <= block.getPosition().x) {
                        Gdx.app.log("Collides", "left side");
                        position.x = block.getPosition().x - 15;
                        stopMoving();
                    }
                }
                collision = true;
                break;
            }
        }

//        if (!collision) {
//            if (isLeftKeyPressed()) {
//                moveLeft();
//            } else if (isRightKeyPressed()) {
//                moveRight();
//            }
//        }

        Gdx.app.log("x", Float.toString(position.x));
        Gdx.app.log("y", Float.toString(position.y));

//        if (!collision) {
//            velocity.y = 100;
//        }

//        if (!collision) {
//            acceleration.y = 460;
//        }

//        if (position.y + 20 >= 180) {
////            position.y = 162;
//            velocity.y = 0;
//            acceleration.y = 0;
//        } else {
//            acceleration.y = 460;
//        }

//        if (position.y + 12 > GS.SCREEN_HEIGHT) {
//            velocity.y = 100;
//        }
    }

    public boolean collides(Block block) {
        if (Math.abs(position.x - block.getPosition().x) < 20 && Math.abs(position.y + 20 - block.getPosition().y) < 25) {
            return (Intersector.overlaps(bounds, block.getBounds()));
        }
        return false;
    }

    public void jump() {
        velocity.y = -200;
        acceleration.y = 460;
    }

    public void moveRight() {
        velocity.x = 200;
    }

    public void moveLeft() {
        velocity.x = -200;
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
