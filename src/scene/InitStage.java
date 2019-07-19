/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import utils.Consts;

/**
 *
 * @author lllgabrielll
 */
public class InitStage extends Stage {

    private Image imgStart;
    private Image imgLoad;
    private Image imgExit;
    private Image background;

    public InitStage() {
        try {
            this.imgStart = Toolkit.getDefaultToolkit().getImage(
                    new java.io.File(".").getCanonicalPath() + Consts.PATH + "button_start.png");
            this.imgLoad = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "button_load.png");
            this.imgExit = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "button_exit.png");
            this.background = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "background_pacman1.jpg");
        } catch (IOException e) {
            System.err.println("Erro: Imagens da tela inicial não encontradas\n " + e.getMessage());
        }
    }

    @Override
    public void paintScene(Graphics g) {
        int aux = Consts.CELL_SIZE * Consts.NUM_CELLS;
        g.fillRect(0, 0, aux, aux + 50);
        g.drawImage(background, 0, 0, aux, aux+50, null);
        g.drawImage(imgStart, (aux / 2) - 110, 100, 220, 60, null);
        g.drawImage(imgLoad, (aux / 2) - 115, 200, 230, 60, null);
        g.drawImage(imgExit, (aux / 2) - 115, 290, 230, 70, null);
    }

    @Override
    protected void drawSceneFinal() {
    }

}
