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
public class Pinky extends Enemy implements Runnable {

    
    public static int MOVE_ALEAT = 0;

   
    public static int MOVE_PAC = 1;

    private int stateDirection;

    public Pinky() {
        super(new String[]{"pinky.png", "vulnerable_ghost.png"});
        setMoveDirection(Enemy.MOVE_RIGHT);
        this.stateDirection = MOVE_PAC;
    }

    public int getStateDirection() {
        return stateDirection;
    }

    public void setStateDirection(int stateDirection) {
        this.stateDirection = stateDirection;
    }

    public void backToLastPosition() {
        this.pos.comeBack();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            // Altera o estado de movimentação a cada 2s

            // Movimentar aleatorio
            if (stateDirection == MOVE_ALEAT) {
                stateDirection = MOVE_PAC;
            } else {
                // Seguir pacman
                stateDirection = MOVE_ALEAT;
            }
        }
    }

}
