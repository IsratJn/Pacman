package utils;

import control.GameScreen;
import java.awt.Graphics;
import javax.swing.ImageIcon;


public class Drawing {

    static GameScreen screen;

    public static GameScreen getGameScreen() {
        return screen;
    }

    public static void setGameScreen(GameScreen newScreen) {
        screen = newScreen;
    }

    public static void draw(Graphics g, ImageIcon imageIcon, double y, double x) {
        imageIcon.paintIcon(screen, g, (int) Math.round(y * Consts.CELL_SIZE), (int) Math.round(x * Consts.CELL_SIZE));
    }
}
