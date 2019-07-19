/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

/**
 *
 * @author lllgabrielll
 */
public class Inky extends Enemy implements Runnable {

    // Se movimentar aleatorio
    public static int MOVE_ALEAT = 0;

    // Se movimentar paralelamente ao pacman
    public static int MOVE_PAC = 1;

    private int stateDirection;

    private double distanceBlinky;

    public Inky() {
        super(new String[]{"inky.png", "vulnerable_ghost.png"});
        this.stateDirection = MOVE_ALEAT;
    }

    public double getDistanceBlinky() {
        return distanceBlinky;
    }

    public void setDistanceBlinky(double x1, double y1, double x2, double y2) {
        this.distanceBlinky = Math.hypot(x1 - x2, y1 - y2);
    }

    public void backToLastPosition() {
        this.pos.comeBack();
    }

    public int getStateDirection() {
        return stateDirection;
    }

    public void setStateDirection(int stateDirection) {
        this.stateDirection = stateDirection;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }

            if (distanceBlinky < 4) {
                stateDirection = MOVE_PAC;

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }

                stateDirection = MOVE_ALEAT;

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }

            }

        }
    }

}
