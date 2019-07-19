/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import elements.Ball;
import elements.Element;
import elements.PowerPellet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import utils.Consts;

public abstract class Stage {

    // Mapa
    protected int map[][];

    // Lista de bolinhas
    protected List<Ball> balls;

    // Lista de PowerPellet
    protected List<PowerPellet> powerPellet;

    // Lista de blocos
    protected List<Element> walls;

    // Total de bolinhas
    protected int tballs;

    // Pontos da fase
    protected int points;

    // Bloco
    protected Image brick;

    public Stage() {
        this.map = new int[Consts.NUM_CELLS][Consts.NUM_CELLS];
        this.balls = new ArrayList<>();
        this.walls = new ArrayList<>();
        this.powerPellet = new ArrayList<>();
        this.points = 0;

        // Bordas
        for (int i = 0; i < Consts.NUM_CELLS; i++) {
            map[i][0] = 1;
            map[0][i] = 1;
            map[i][Consts.NUM_CELLS - 1] = 1;
            map[Consts.NUM_CELLS - 1][i] = 1;
        }
    }

    public void setBlock(String imageName) {
        // Imagem
        try {
            this.brick = Toolkit.getDefaultToolkit().getImage(
                    new java.io.File(".").getCanonicalPath() + Consts.PATH + imageName);
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    // Obter cenario
    public int[][] getMap() {
        return map;
    }

    public int map(int x, int y) {
        return map[x][y];
    }

    // Pintar cenario
    public abstract void paintScene(Graphics g);

    // Termina de definir cenario
    protected abstract void drawSceneFinal();

    // Obter elementos bolinhas
    public List<Ball> getBalls() {
        return balls;
    }
    
    public void setBalls(List<Ball> balls){
        this.balls = balls;
    }
    
    public void setPowerPellet(List<PowerPellet> powerPellet){
        this.powerPellet = powerPellet;
    }

    // Obter lista de bloquinhos
    public List<Element> getWalls() {
        return walls;
    }
    
    public void setWalls(List<Element> walls){
        this.walls = walls;
    }

    // Obter total de bolinhas
    public int getTotalBall() {
        return this.balls.size();
    }

    // Obter total de pontos
    public int getPoints() {
        return points;
    }

    public List<PowerPellet> getPowerPellet() {
        return powerPellet;
    }
    // Obter total de Power
    public int getTotalPowerPellet() {
        return this.powerPellet.size();
    }
}
