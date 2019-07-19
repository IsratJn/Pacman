package control;

import data.Save;
import elements.Blinky;
import elements.PacMan;
import elements.Element;
import elements.Cherry;
import elements.Clyde;
import elements.Enemy;
import elements.Inky;
import elements.Pinky;
import elements.Strawberry;

import utils.Consts;
import utils.Drawing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import scene.StageGameOver;
import scene.InitStage;

import scene.Stage;
import scene.Stage1;
import scene.Stage2;
import scene.Stage3;

public class GameScreen extends JFrame implements KeyListener, MouseListener {

    private PacMan pacMan;

    private Blinky blinky;
    private Clyde clyde;
    private Inky inky;
    private Pinky pinky;

    private Strawberry strawberry;
    private Cherry cherry;

    private ArrayList<Element> elemArray;
    private ArrayList<Enemy> enemys;

    private final GameController controller = new GameController();
    private final Random random = new Random();
    private final Executor executor_scene_1;

    private Stage scene;

    private Image imgLife;
    private Image imgScore;
    private Image imgNum0, imgNum1, imgNum2, imgNum3,
            imgNum4, imgNum5, imgNum6, imgNum7, imgNum8, imgNum9;

    // Controle de tela
    // 0 - Tela inicial
    // 1 - Primeira tela
    // 2 - Segunda tela
    // 3 - Terceira tela
    // 4 - Tela de fim do jogo
    private int controlScene;

    // Construtor
    public GameScreen() {
        Drawing.setGameScreen(this);
        initComponents();

        this.addKeyListener(this);
        this.addMouseListener(this);

        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom + 50);

        // Lista de elementos
        this.elemArray = new ArrayList<>();
        this.enemys = new ArrayList<>();

        // Pacman
        this.pacMan = new PacMan();
        this.addElement(pacMan);

        // Blinky
        this.blinky = new Blinky();
        this.elemArray.add(blinky);
        this.enemys.add(blinky);

        // Inky
        this.inky = new Inky();
        this.elemArray.add(inky);
        this.enemys.add(inky);

        // Pinky
        this.pinky = new Pinky();
        this.elemArray.add(pinky);
        this.enemys.add(pinky);

        // Clyde
        this.clyde = new Clyde();
        this.elemArray.add(clyde);
        this.enemys.add(clyde);

        this.strawberry = new Strawberry();
        this.cherry = new Cherry();

        try {
            this.imgScore = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "button_score.png");
            this.imgLife = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "pacman_right.png");

            this.imgNum0 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num0.png");
            this.imgNum1 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num1.png");
            this.imgNum2 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num2.png");
            this.imgNum3 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num3.png");
            this.imgNum4 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num4.png");
            this.imgNum5 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num5.png");
            this.imgNum6 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num6.png");
            this.imgNum7 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num7.png");
            this.imgNum8 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num8.png");
            this.imgNum9 = Toolkit.getDefaultToolkit().getImage(
                    new File(".").getCanonicalPath() + Consts.PATH + "num9.png");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.executor_scene_1 = Executors.newCachedThreadPool();
        this.executor_scene_1.execute(pinky);
        this.executor_scene_1.execute(pacMan);
        this.executor_scene_1.execute(inky);

        // Cria cenario
        this.controlScene = 0;
        newScene(controlScene);
    }

    // Define qual será e Cria cenario com todos os seus elementos
    private void newScene(int scene) {
        switch (scene) {
            // Tela Inicial
            case 0:
                this.scene = new InitStage();

                // Total de vidas do pacman
                this.pacMan.setLife(3);

                // Resetar pontos
                this.pacMan.resetTotalScore();

                break;

            // Tela 1
            case 1:
                this.scene = new Stage1();
                this.scene.setBlock("brick.png");

                // Reseta posições
                resetEnemyPac();

                // Determinar posição para strawberry
                int aux1,
                 aux2;
                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.strawberry.setPosition(aux1, aux2);
                this.addElement(strawberry);

                // Determinar posição para cherry
                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.cherry.setPosition(aux1, aux2);
                this.addElement(cherry);

                break;

            // Tela 2
            case 2:
                this.scene = new Stage2();
                this.scene.setBlock("brick.png");

                // Resetar posição
                resetEnemyPac();

                // Determinar posição para strawberry
                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.strawberry.setPosition(aux1, aux2);
                this.addElement(strawberry);

                // Determinar posição para cherry
                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.cherry.setPosition(aux1, aux2);
                this.addElement(cherry);

                break;

            // Tela 3
            case 3:
                this.scene = new Stage3();
                this.scene.setBlock("brick.png");

                // Resetar posição
                resetEnemyPac();

                // Determinar posição para strawberry
                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.strawberry.setPosition(aux1, aux2);
                this.addElement(strawberry);

                // Determinar posição para cherry
                do {
                    aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                    aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                } while (this.scene.map(aux1, aux2) == 1);

                this.cherry.setPosition(aux1, aux2);
                this.addElement(cherry);

                break;

            // Game Over
            case 4:
                this.scene = new StageGameOver();
                break;

            case 5:

                break;
        }
    }

    // Adicionar elementos na lista
    public final void addElement(Element elem) {
        elemArray.add(elem);
    }

    // Remover elementos na lista
    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }

    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
        Graphics g2 = g.create(getInsets().right, getInsets().top,
                getWidth() - getInsets().left, getHeight() - getInsets().bottom);

        // Pintar elementos
        this.controller.drawAllElements(scene, elemArray, g2, controlScene);

        // Se nao for a tela inicial nem a final
        if (controlScene != 0 && controlScene != 4) {

            // Controla o movimento do blinky
            setBlinkyMovDirection();

            // Controla o movimento do pinky
            setPinkyMovDirection();

            // Controla o movimento do inky
            setInkyMovDirection();

            // Controla o movimento do clyde
            setClydeMovDirection();

            // Verificar colisao entre elementos
            if (controller.processAllElements(scene, elemArray, enemys)) {

                // Remove uma vida do pacman
                pacMan.removeLife();

                // Retorna posições iniciais
                resetEnemyPac();

                // Verifica se acabou as vidas
                if (pacMan.getLife() == 0) {
                    System.out.println("Entrou");
                    this.controlScene = 4;
                    newScene(controlScene);
                    return;
                }
            }

            // Verifica se comeu todas as bolinhas
            if (scene.getBalls().isEmpty() && scene.getPowerPellet().isEmpty()) {
                controlScene++;
                newScene(controlScene);
            }

            // Desenhar informações
            int aux = Consts.CELL_SIZE * Consts.NUM_CELLS;

            // Vidas
            for (int i = 0; i < pacMan.getLife(); i++) {
                g2.drawImage(imgLife, 10 + (32 * i), aux + 10, 30, 30, null);
            }

            // Frutas
            if (elemArray.contains(strawberry)) {
                g2.drawImage(strawberry.getImgElement().getImage(), 140, aux + 7, 30, 33, null);
            }

            if (elemArray.contains(cherry)) {
                g2.drawImage(cherry.getImgElement().getImage(), 180, aux + 7, 30, 33, null);
            }

            // Pontuação
            g2.drawImage(imgScore, 340, aux + 2, 75, 45, null);

            // Determinar pontos
            String score = Integer.toString(pacMan.getScore());

            for (int i = 0; i < score.length(); i++) {
                switch (score.charAt(i)) {
                    case '0':
                        g2.drawImage(imgNum0, 410 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '1':
                        g2.drawImage(imgNum1, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '2':
                        g2.drawImage(imgNum2, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '3':
                        g2.drawImage(imgNum3, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '4':
                        g2.drawImage(imgNum4, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '5':
                        g2.drawImage(imgNum5, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '6':
                        g2.drawImage(imgNum6, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '7':
                        g2.drawImage(imgNum7, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '8':
                        g2.drawImage(imgNum8, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    case '9':
                        g2.drawImage(imgNum9, 415 + (30 * i), aux + 8, 30, 30, null);
                        break;
                    default:
                        break;
                }
            }
        }

        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    // Movimentar Blinky
    private void setBlinkyMovDirection() {
        // Verifica movimentação do blinky
        switch (pacMan.getMovDirection()) {
            case PacMan.MOVE_DOWN:

                if (pacMan.getPos().getX() > blinky.getPos().getX()) {
                    blinky.setMoveDirection(Enemy.MOVE_DOWN);
                } else {
                    blinky.setMoveDirection(Enemy.MOVE_UP);
                }
                break;

            case PacMan.MOVE_UP:

                if (pacMan.getPos().getX() > blinky.getPos().getX()) {
                    blinky.setMoveDirection(Enemy.MOVE_DOWN);
                } else {
                    blinky.setMoveDirection(Enemy.MOVE_UP);
                }
                break;

            case PacMan.MOVE_LEFT:

                if (pacMan.getPos().getY() > blinky.getPos().getY()) {
                    blinky.setMoveDirection(Enemy.MOVE_RIGHT);
                } else {
                    blinky.setMoveDirection(Enemy.MOVE_LEFT);
                }
                break;

            case PacMan.MOVE_RIGHT:

                if (pacMan.getPos().getY() > blinky.getPos().getY()) {
                    blinky.setMoveDirection(Enemy.MOVE_RIGHT);
                } else {
                    blinky.setMoveDirection(Enemy.MOVE_LEFT);
                }
                break;
        }
    }

    // Movimenta Inky
    private void setInkyMovDirection() {

        // Se a distância foi menor que 4, se move igual ao Blinky.
        inky.setDistanceBlinky(inky.getPos().getX(), inky.getPos().getY(),
                blinky.getPos().getX(), blinky.getPos().getY());

        if (inky.getDistanceBlinky() < 4 && inky.getStateDirection() == Inky.MOVE_PAC) {
            switch (blinky.getMovDirection()) {
                case Blinky.MOVE_DOWN:

                    if (blinky.getPos().getX() > inky.getPos().getX()) {
                        inky.setMoveDirection(Enemy.MOVE_DOWN);
                    } else {
                        inky.setMoveDirection(Enemy.MOVE_UP);
                    }
                    break;

                case Blinky.MOVE_UP:

                    if (blinky.getPos().getX() > inky.getPos().getX()) {
                        inky.setMoveDirection(Enemy.MOVE_DOWN);
                    } else {
                        inky.setMoveDirection(Enemy.MOVE_UP);
                    }
                    break;

                case Blinky.MOVE_LEFT:

                    if (blinky.getPos().getY() > inky.getPos().getY()) {
                        inky.setMoveDirection(Enemy.MOVE_RIGHT);
                    } else {
                        inky.setMoveDirection(Enemy.MOVE_LEFT);
                    }
                    break;

                case Blinky.MOVE_RIGHT:

                    if (blinky.getPos().getY() > inky.getPos().getY()) {
                        inky.setMoveDirection(Enemy.MOVE_RIGHT);
                    } else {
                        inky.setMoveDirection(Enemy.MOVE_LEFT);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    // Movimenta Pinky
    private void setPinkyMovDirection() {
        switch (pacMan.getMovDirection()) {
            case PacMan.MOVE_DOWN:
                if (pinky.getStateDirection() == Pinky.MOVE_PAC) {
                    pinky.setMoveDirection(Enemy.MOVE_DOWN);
                }
                break;

            case PacMan.MOVE_UP:
                if (pinky.getStateDirection() == Pinky.MOVE_PAC) {
                    pinky.setMoveDirection(Enemy.MOVE_UP);
                }
                break;

            case PacMan.MOVE_LEFT:
                if (pinky.getStateDirection() == Pinky.MOVE_PAC) {
                    pinky.setMoveDirection(Enemy.MOVE_LEFT);
                }
                break;

            case PacMan.MOVE_RIGHT:
                if (pinky.getStateDirection() == Pinky.MOVE_PAC) {
                    pinky.setMoveDirection(Enemy.MOVE_RIGHT);
                }
                break;
        }
    }

    // Movimenta Clyde
    private void setClydeMovDirection() {

        // Se a distância foi menor que 4, se move igual ao Blinky.
        clyde.setDistancePacman(clyde.getPos().getX(), clyde.getPos().getY(),
                pacMan.getPos().getX(), pacMan.getPos().getY());

        // Se a distancia for menor que 4, segue pacman
        if (clyde.getDistancePacman() > 5) {
            switch (pacMan.getMovDirection()) {
                case PacMan.MOVE_DOWN:

                    if (pacMan.getPos().getX() > clyde.getPos().getX()) {
                        clyde.setMoveDirection(Enemy.MOVE_DOWN);
                    } else {
                        clyde.setMoveDirection(Enemy.MOVE_UP);
                    }

                    break;

                case PacMan.MOVE_UP:

                    if (pacMan.getPos().getX() > clyde.getPos().getX()) {
                        clyde.setMoveDirection(Enemy.MOVE_DOWN);
                    } else {
                        clyde.setMoveDirection(Enemy.MOVE_UP);
                    }

                    break;

                case PacMan.MOVE_LEFT:

                    if (pacMan.getPos().getY() > clyde.getPos().getY()) {
                        clyde.setMoveDirection(Enemy.MOVE_RIGHT);
                    } else {
                        clyde.setMoveDirection(Enemy.MOVE_LEFT);
                    }

                    break;

                case PacMan.MOVE_RIGHT:

                    if (pacMan.getPos().getY() > blinky.getPos().getY()) {
                        clyde.setMoveDirection(Enemy.MOVE_RIGHT);
                    } else {
                        clyde.setMoveDirection(Enemy.MOVE_LEFT);
                    }
                    break;
            }
        }
    }

    public void go() {
        // Timer para pintar a tela
        TimerTask repaint = new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        };

        // Time para strawberry
        TimerTask timerStrawberry = new TimerTask() {
            @Override
            public void run() {
                if (controlScene != 0 && controlScene != 4) {
                    if (!strawberry.isVisible()) {

                        // Determinar uma nova posição para strawberry
                        // a cada nova aparição
                        int aux1, aux2;
                        do {
                            aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                            aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                        } while (scene.map(aux1, aux2) == 1);

                        strawberry.setPosition(aux1, aux2);

                        // Deixar fruta visivel
                        strawberry.setVisible(true);
                        strawberry.setTransposable(false);

                    } else {

                        // Deixar fruta invisivel
                        strawberry.setVisible(false);
                        strawberry.setTransposable(true);
                    }
                }
            }
        };

        // Time para cherry
        TimerTask timerCherry = new TimerTask() {
            @Override
            public void run() {
                if (controlScene != 0 && controlScene != 4) {
                    if (!cherry.isVisible()) {

                        // Determinar uma nova posição para cherry
                        // a cada nova aparição
                        int aux1, aux2;
                        do {
                            aux1 = random.nextInt(Consts.NUM_CELLS - 1);
                            aux2 = random.nextInt(Consts.NUM_CELLS - 1);
                        } while (scene.map(aux1, aux2) == 1);

                        strawberry.setPosition(aux1, aux2);

                        // Deixar fruta visivel
                        cherry.setVisible(true);
                        cherry.setTransposable(false);

                    } else {

                        // Deixar fruta invisivel
                        cherry.setVisible(false);
                        cherry.setTransposable(true);
                    }
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(repaint, 0, Consts.DELAY);
        timer.schedule(timerStrawberry, Consts.TIMER_STRAWBERRY, 15000);
        timer.schedule(timerCherry, Consts.TIMER_CHERRY, 15000);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int aux = controlScene;
        switch (aux) {
            // Tela Inicial
            case 0:
                switch (e.getKeyCode()) {
                    //Caso o usuário digite espaço pausa o pacman
                    case KeyEvent.VK_SPACE:
                        controlScene = 1;
                        newScene(controlScene);
                        break;
                    
                    //Caso o usuário digite Q pergunta se deseja sair do jogo
                    case KeyEvent.VK_Q:
                        if (JOptionPane.showConfirmDialog(null,
                                "Deseja realmente sair ?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
                            System.exit(0);
                        }
                        break;
                    
                    //Caso o usuário digite L executa a operação de salvamento
                    case KeyEvent.VK_L:
                        ObjectInputStream load;
                        try {
                            load = new ObjectInputStream(new FileInputStream("./src/data/save"));
                            Save saveClass = (Save) load.readObject();
                            load.close();

                            scene = new Stage1();
                            scene.setBlock("brick.png");

                            pacMan = saveClass.pacMan;
                            blinky = saveClass.blinky;
                            inky = saveClass.inky;
                            pinky = saveClass.pinky;
                            clyde = saveClass.clyde;
                            cherry = saveClass.cherry;
                            strawberry = saveClass.strawberry;
                            elemArray = saveClass.elemArray;
                            enemys = saveClass.enemys;
                            scene.setBalls(saveClass.balls);
                            scene.setPowerPellet(saveClass.powerPellet);
                            scene.setWalls(saveClass.walls);
                            this.controlScene = saveClass.controlScene;

                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException | ClassNotFoundException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
                break;

            // Tela Final
            case 4:
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    if (JOptionPane.showConfirmDialog(null,
                            "Deseja realmente sair ?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }
                break;

            // Qualquer outra tela
            default:
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        // Setar movimentação do pacman para cima
                        pacMan.setTurn(true);
                        pacMan.setNextDirection(PacMan.MOVE_UP);
                        break;

                    case KeyEvent.VK_DOWN:
                        // Setar movimentação do pacman para baixo
                        pacMan.setTurn(true);
                        pacMan.setNextDirection(PacMan.MOVE_DOWN);
                        break;

                    case KeyEvent.VK_LEFT:
                        // Setar movimentaçao do pacman para a esquerda
                        pacMan.setTurn(true);
                        pacMan.setNextDirection(PacMan.MOVE_LEFT);
                        break;

                    case KeyEvent.VK_RIGHT:
                        // Setar movimentação do pacman para a direita
                        pacMan.setTurn(true);
                        pacMan.setNextDirection(PacMan.MOVE_RIGHT);
                        break;

                    case KeyEvent.VK_SPACE:
                        // Pausa o pacman
                        pacMan.setTurn(true);
                        pacMan.setNextDirection(PacMan.STOP);
                        break;
                    
                    //Executa a operação de salvamento do jogo
                    case KeyEvent.VK_S:
                        try {
                            Save saveClass = new Save();
                            saveClass.pacMan = pacMan;
                            saveClass.blinky = blinky;
                            saveClass.inky = inky;
                            saveClass.pinky = pinky;
                            saveClass.clyde = clyde;
                            saveClass.cherry = cherry;
                            saveClass.strawberry = strawberry;
                            saveClass.elemArray = elemArray;
                            saveClass.enemys = enemys;
                            saveClass.balls = scene.getBalls();
                            saveClass.powerPellet = scene.getPowerPellet();
                            saveClass.walls = scene.getWalls();
                            saveClass.controlScene = this.controlScene;
                            ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream("./src/data/save"));
                            save.writeObject(saveClass);
                            save.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    default:
                        break;
                }

                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCC0604 - Pacman");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int aux = controlScene;
        switch (aux) {
            // Tela inicial
            case 0:
                // Verifica se clicou em algum botao
                int a1 = (Consts.NUM_CELLS * Consts.CELL_SIZE) / 2;
                int x1 = e.getPoint().x;
                int y1 = e.getPoint().y;

                // iniciar jogo
                if ((100 <= y1 && y1 <= 160) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {
                    controlScene = 1;
                    newScene(controlScene);

                } else if ((200 <= y1 && y1 <= 260) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {

                    // Iniciar jogo salvo
                    ObjectInputStream load;
                    try {
                        load = new ObjectInputStream(new FileInputStream("./src/data/save"));
                        Save saveClass = (Save) load.readObject();
                        load.close();

                        scene = new Stage1();
                        scene.setBlock("brick.png");

                        pacMan = saveClass.pacMan;
                        blinky = saveClass.blinky;
                        inky = saveClass.inky;
                        pinky = saveClass.pinky;
                        clyde = saveClass.clyde;
                        cherry = saveClass.cherry;
                        strawberry = saveClass.strawberry;
                        elemArray = saveClass.elemArray;
                        enemys = saveClass.enemys;
                        scene.setBalls(saveClass.balls);
                        scene.setPowerPellet(saveClass.powerPellet);
                        scene.setWalls(saveClass.walls);
                        this.controlScene = saveClass.controlScene;

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else if ((300 <= y1 && y1 <= 360) && (a1 - 110 <= x1 && x1 <= a1 + 110)) {

                    // Sair
                    if (JOptionPane.showConfirmDialog(null,
                            "Deseja realmente sair ?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }

                break;

            // Game Over
            case 4:
                // Verifica se clicou em algum botao
                int a2 = (Consts.NUM_CELLS * Consts.CELL_SIZE) / 2;
                int x2 = e.getPoint().x;
                int y2 = e.getPoint().y;

                // Volta para a tela inicial
                if ((350 <= y2 && y2 <= 410) && (a2 - 210 <= x2 && x2 <= a2 - 10)) {
                    controlScene = 0;
                    newScene(controlScene);
                } else if ((350 <= y2 && y2 <= 410) && (a2 + 10 <= x2 && x2 <= a2 + 210)) {
                    // Sair
                    if (JOptionPane.showConfirmDialog(null,
                            "Deseja realmente sair ?", "Sair", JOptionPane.YES_NO_OPTION) == 0) {
                        System.exit(0);
                    }
                }
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    public void resetEnemyPac() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        this.pacMan.setPosition(1, 1);
        this.pinky.setPosition(9, 9);
        this.blinky.setPosition(9, 9);
        this.clyde.setPosition(9, 9);
        this.inky.setPosition(9, 9);
    }
}
