/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import elements.Ball;
import elements.PowerPellet;
import elements.Wall;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Consts;

public class Stage2 extends Stage {

    public Stage2() {
        this.drawSceneFinal();
    }

    @Override
    public void paintScene(Graphics g) {
        g.fillRect(0, 0, Consts.CELL_SIZE * Consts.NUM_CELLS, Consts.CELL_SIZE * Consts.NUM_CELLS + 50);

        // Desenha cenario
        for (int i = 0; i < Consts.NUM_CELLS; i++) {
            for (int j = 0; j < Consts.NUM_CELLS; j++) {
                if (map[i][j] == 1) {
                    g.drawImage(brick, j * Consts.CELL_SIZE, i * Consts.CELL_SIZE,
                            Consts.CELL_SIZE, Consts.CELL_SIZE, null);
                } else {
                    g.fillRect(j * Consts.CELL_SIZE, i * Consts.CELL_SIZE,
                            Consts.CELL_SIZE, Consts.CELL_SIZE);
                }
            }
        }

        // Desenhar bolinhas da tela
        Iterator<Ball> it = balls.listIterator();
        while (it.hasNext()) {
            it.next().autoDraw(g);
        }

        Iterator<PowerPellet> it2 = powerPellet.listIterator();
        while (it2.hasNext()) {
            it2.next().autoDraw(g);
        }

        // Calcula a quantidade de pontos nessa fase
        points = (tballs - balls.size()) * 10;
    }

    @Override
    protected void drawSceneFinal() {
        try {
            Scanner mapRead = new Scanner(new FileInputStream("./src/maps/map2.txt"));
            for (int i = 0; i < Consts.NUM_CELLS; i++) {
                for (int j = 0; j < Consts.NUM_CELLS; j++) {
                    map[i][j] = (int) mapRead.nextByte();
                }
            }
            mapRead.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro na abertura do arquivo.");
            Logger.getLogger(Stage1.class.getName()).log(Level.SEVERE, null, ex);
        }    // Criar bolinhas

        for (int x = 0; x < Consts.NUM_CELLS; x++) {
            for (int y = 0; y < Consts.NUM_CELLS; y++) {
                switch (map[x][y]) {
                    case 0:
                        this.balls.add(new Ball("ball.png", 10, x, y));
                        break;
                    case 1:
                        this.walls.add(new Wall("brick.png", x, y));
                        break;
                    case 2:
                        this.powerPellet.add(new PowerPellet("power_pellet.png", 50, x, y));
                        break;
                    default:
                        break;
                }
            }
        }

        map[1][1] = 0;

        // Total de bolinhas na tela
        tballs = balls.size();
    }
}
