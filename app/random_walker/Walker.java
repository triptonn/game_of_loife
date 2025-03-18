package random_walker;

import java.util.Random;
import java.awt.Point;

public class Walker {
    Point pos;

    Walker() {
        int x = RandomWalker.WINDOW_WIDTH / 2;
        int y = RandomWalker.WINDOW_HEIGHT / 2;
        this.pos = new Point(x, y);
    }

    public void move() {
        Random r = new Random();
        int value = r.nextInt(4);
        switch (value) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveLeft();
                break;
            case 3:
                moveRight();
                break;
            default:
                break;
        }
    }

    public void moveUp() {
        this.pos.y += 1;
    }

    public void moveDown() {
        this.pos.y -= 1;
    }

    public void moveLeft() {
        this.pos.x -= 1;
    }

    public void moveRight() {
        this.pos.x += 1;
    }

    public Point getPos() {
        return this.pos;
    }

}
