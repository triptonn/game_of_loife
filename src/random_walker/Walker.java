package random_walker;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

public class Walker {
    Point pos;
    ArrayList<Point> history = new ArrayList<Point>();

    Walker() {
        int x = RandomWalker.WINDOW_WIDTH / 2;
        int y = RandomWalker.WINDOW_HEIGHT / 2;
        this.pos = new Point(x, y);
        history.add(this.pos);
    }

    public void move() {
        Random r = new Random();
        int value = r.nextInt(4);
        System.out.println("rnd_val: " + value);
        switch (value) {
            case 0:
                System.out.println("Move up");
                moveUp();
                Point newUpPos = new Point(this.pos.x, this.pos.y);
                addToHistory(newUpPos);
                break;
            case 1:
                System.out.println("Move down");
                moveDown();
                Point newDownPos = new Point(this.pos.x, this.pos.y);
                addToHistory(newDownPos);
                break;
            case 2:
                System.out.println("Move left");
                moveLeft();
                Point newLeftPos = new Point(this.pos.x, this.pos.y);
                addToHistory(newLeftPos);
                break;
            case 3:
                System.out.println("Move right");
                moveRight();
                Point newRightPos = new Point(this.pos.x, this.pos.y);
                addToHistory(newRightPos);
                break;
            default:
                break;
        }
        DrawingPanel.setHistory(this.history);
    }

    public void moveUp() {
        this.pos.y += 5;
    }

    public void moveDown() {
        this.pos.y -= 5;
    }

    public void moveLeft() {
        this.pos.x -= 5;
    }

    public void moveRight() {
        this.pos.x += 5;
    }

    public Point getPos() {
        return this.pos;
    }

    private void addToHistory(Point p) {
        System.out.println(this.history);
        boolean isNew = true;
        System.out.println("Init is new: " + isNew);
        for (Point pt : this.history) {
            System.out.println("Looking at " + pt);
            if (p.x == pt.x && p.y == pt.y) {
                isNew = false;
            }
            System.out.println("isNew after" + pt + ": " + isNew);
        }

        System.out.println("Point: " + p + " is new: " + isNew);

        if (isNew) {
            this.history.add(p);
        }
    }
}
