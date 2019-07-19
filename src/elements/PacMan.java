package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;

public class PacMan extends Element implements Serializable, Runnable {

    public static final int STOP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_UP = 3;
    public static final int MOVE_DOWN = 4;

    private int MOVE_RIGHT_STATE = 0;
    private int MOVE_DOWN_STATE = 2;
    private int MOVE_LEFT_STATE = 4;
    private int MOVE_UP_STATE = 6;

    private int movDirection = STOP;
    private int movBefDirection = STOP;
    private int life = 3;

    private int score;
    private int aux_score;

    private boolean turn; 
    private int nextDirection; 

    public PacMan() {
        super(new String[]{"pacman_right.png", "pacman_right2.png", "pacman_down.png", "pacman_down2.png",
            "pacman_left.png", "pacman_left2.png", "pacman_up.png", "pacman_up2.png"}, 0, 1);
        this.isVisible = true;
        this.isTransposable = false;
        this.score = 0;
        this.aux_score = 0;
    }

    public void changeDirection(int dir) {
        setImageIcon(dir);
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, imageIcon, pos.getY(), pos.getX());
    }

    public void backToLastPosition() {
        this.pos.comeBack();
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean getTurn() {
        return turn;
    }

    public void setNextDirection(int nextDirection) {
        this.nextDirection = nextDirection;
    }

    public void setMovDirection(int direction) {
        movDirection = direction;
    }

    public boolean overlapBall(final Element elem) {
        double xDist = Math.abs(elem.pos.getX() - this.pos.getX());
        double yDist = Math.abs(elem.pos.getY() - this.pos.getY());

        return (xDist < 0.45 && yDist < 0.45);
    }

    public void move() {
        boolean flag = false; //flag para checar se entrou no if abaixo
        //caso o pacman esteja em uma posição inteira e o usuário teclou alguma seta
        if (turn && this.isPosInteger()) {
            this.setMovDirection(nextDirection);
            turn = false;
            flag = true;
        }
        //verifica a próxima direção do pacman e executa a mudança
        switch (movDirection) {
            case MOVE_LEFT:
                if (flag) {
                    this.changeDirection(3);
                }
                this.moveLeft();
                break;

            case MOVE_RIGHT:
                if (flag) {
                    this.changeDirection(0);
                }
                this.moveRight();
                break;

            case MOVE_UP:
                if (flag) {
                    this.changeDirection(4);
                }
                this.moveUp();
                break;

            case MOVE_DOWN:
                if (flag) {
                    this.changeDirection(2);
                }
                this.moveDown();
                break;

            default:
                break;
        }
    }

    // Adicionar vida
    public void addLife() {
        life++;
    }

    public void setLife(int life) {
        this.life = life;
    }

    // Remover vida
    public void removeLife() {
        life--;
    }

    public int getLife() {
        return life;
    }

    public int getMovDirection() {
        return movDirection;
    }

    public void setMovBefDirection(int movBefDirection) {
        this.movBefDirection = movBefDirection;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void resetScore() {
        this.aux_score -= 10000;
    }

    public void resetTotalScore() {
        this.aux_score = 0;
        this.score = 0;
    }

    public void scorePoints(int points) {
        this.score += points;
        this.aux_score += points;
    }

    public int getScoreAux() {
        return aux_score;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }

            // A cada 0.3s acontece uma a atualização da imagem do pacman
            switch (movDirection) {
                case MOVE_RIGHT:
                    if (MOVE_RIGHT_STATE == 0) {
                        changeDirection(1);
                        MOVE_RIGHT_STATE = 1;
                    } else {
                        changeDirection(0);
                        MOVE_RIGHT_STATE = 0;
                    }
                    break;

                case MOVE_DOWN:
                    if (MOVE_DOWN_STATE == 2) {
                        changeDirection(3);
                        MOVE_DOWN_STATE = 3;
                    } else {
                        changeDirection(2);
                        MOVE_DOWN_STATE = 2;
                    }
                    break;

                case MOVE_LEFT:
                    if (MOVE_LEFT_STATE == 4) {
                        changeDirection(5);
                        MOVE_LEFT_STATE = 5;
                    } else {
                        changeDirection(4);
                        MOVE_LEFT_STATE = 4;
                    }
                    break;

                case MOVE_UP:
                    if (MOVE_UP_STATE == 6) {
                        changeDirection(7);
                        MOVE_UP_STATE = 7;
                    } else {
                        changeDirection(6);
                        MOVE_UP_STATE = 6;
                    }
                    break;

                case STOP:
                    switch (movBefDirection) {
                        case MOVE_RIGHT:
                            if (MOVE_RIGHT_STATE == 0) {
                                changeDirection(1);
                                MOVE_RIGHT_STATE = 1;
                            } else {
                                changeDirection(0);
                                MOVE_RIGHT_STATE = 0;
                            }
                            break;

                        case MOVE_DOWN:
                            if (MOVE_DOWN_STATE == 2) {
                                changeDirection(3);
                                MOVE_DOWN_STATE = 3;
                            } else {
                                changeDirection(2);
                                MOVE_DOWN_STATE = 2;
                            }
                            break;

                        case MOVE_LEFT:
                            if (MOVE_LEFT_STATE == 4) {
                                changeDirection(5);
                                MOVE_LEFT_STATE = 5;
                            } else {
                                changeDirection(4);
                                MOVE_LEFT_STATE = 4;
                            }
                            break;

                        case MOVE_UP:
                            if (MOVE_UP_STATE == 6) {
                                changeDirection(7);
                                MOVE_UP_STATE = 7;
                            } else {
                                changeDirection(6);
                                MOVE_UP_STATE = 6;
                            }
                            break;
                    }
                    break;
            }
        }
    }

}
