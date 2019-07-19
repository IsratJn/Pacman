/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import utils.Drawing;


public abstract class Enemy extends Element {

    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_UP = 3;
    public static final int MOVE_DOWN = 4;

    public static final int HOUSE = 1;
    public static final int CHASE = 2;
    public static final int DANGER = 3;

   
    protected Point pacman_pos;

   
    protected int map[][];

    private int movDirection;
    private int state;

    public Enemy(String[] imgs) {
        super(imgs, 0, 3);
        this.isVisible = true;
        this.isTransposable = false;
        this.pacman_pos = new Point();
        this.state = 1;                
    }

    public void setMoveDirection(int movDirection) {
        this.movDirection = movDirection;
    }

    public int getMovDirection() {
        return movDirection;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void move() {
        switch (movDirection) {
            case MOVE_LEFT:
                if (state == CHASE) {
                    this.moveLeftSlowEnemy();
                } else {
                    this.moveLeftEnemy();
                }
                break;
            case MOVE_RIGHT:
                
                if (state == CHASE) {
                    this.moveRightSlowEnemy();
                } else {
                    this.moveRightEnemy();
                }
                
                break;
            case MOVE_UP:
                if (state == CHASE) {
                    this.moveUpSlowEnemy();
                } else {
                    this.moveUpEnemy();
                }
                break;
            case MOVE_DOWN:
                if (state == CHASE) {
                    this.moveDownSlowEnemy();
                } else {
                    this.moveDownEnemy();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void autoDraw(Graphics g) {
        switch (this.getState()) {
            case 1:
                Drawing.draw(g, this.directions[0], pos.getY(), pos.getX());
                break;
            case 2:
                Drawing.draw(g, this.directions[1], pos.getY(), pos.getX());
                break;
            case 3:
                this.setVisible(false);
                TimerTask revive = new TimerTask() {
                    @Override
                    public void run() {
                        setState(1);

                    }
                };
                Timer timer = new Timer();
                timer.schedule(revive, 5000);
        }
    }

    public void setPacman_pos(final int x, final int y) {
        this.pacman_pos.x = x;
        this.pacman_pos.y = y;
    }

    public void setMap(int map[][]) {
        this.map = map;
    }
}
