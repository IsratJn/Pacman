/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;


public class Clyde extends Enemy {

    
    public static int MOVE_ALEAT = 0;

    
    public static int MOVE_PAC = 1;

    private int stateDirection;

    private double distancePacman;
    
    public Clyde() {
        super(new String[] {"clyde.png","vulnerable_ghost.png"});
        this.stateDirection = MOVE_ALEAT;
    }
    
    public void backToLastPosition() {
        this.pos.comeBack();
    }
    
    public double getDistancePacman() {
        return distancePacman;
    }

    public void setDistancePacman(double x1, double y1, double x2, double y2) {
        this.distancePacman = Math.hypot(x1 - x2, y1 - y2);
    }
    
    public int getStateDirection() {
        return stateDirection;
    }

    public void setStateDirection(int stateDirection) {
        this.stateDirection = stateDirection;
    }
    
}
