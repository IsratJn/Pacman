
package elements;


public class Blinky extends Enemy {

    public Blinky() {
        super(new String[]{"blinky.png", "vulnerable_ghost.png"});

        
        setMoveDirection(Enemy.MOVE_UP);
    }

    public void backToLastPosition() {
        this.pos.comeBack();
    }
}
