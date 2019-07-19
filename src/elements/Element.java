package elements;

import utils.Consts;
import utils.Position;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;

public abstract class Element implements Serializable {

    protected ImageIcon[] directions;
    protected ImageIcon imageIcon;
    protected Position pos;
    protected boolean isTransposable;
    protected boolean isVisible;

    // Tipo de elemento
    // 1 Pacman
    // 2 Ball
    // 3 Enemy
    // 4 Fruit
    // 5 Wall
    private final int typeElement;

    protected Element(String[] imageName, int dir, int typeElement) {
        this.pos = new Position(1, 1);
        this.isTransposable = true;

        directions = new ImageIcon[imageName.length];

        for (int i = 0; i < imageName.length; i++) {
            directions[i] = getImageIcon(imageName[i]);
        }

        this.typeElement = typeElement;

        setImageIcon(dir);
    }

    private ImageIcon getImageIcon(String imageName) {
        try {
            ImageIcon imageIconFunc = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + imageName);
            Image img = imageIconFunc.getImage();
            BufferedImage bi;
            Graphics g;

            // Cria tamanho diferentes de imagem para elementos doferentes
            switch (this.typeElement) {
                // Pacman
                case 1:
                    bi = new BufferedImage(Consts.CELL_SIZE - 10, Consts.CELL_SIZE - 10, BufferedImage.TYPE_INT_ARGB);
                    g = bi.createGraphics();
                    g.drawImage(img, 0, 0, Consts.CELL_SIZE - 10, Consts.CELL_SIZE - 10, null);
                    break;

                default:
                    bi = new BufferedImage(Consts.CELL_SIZE, Consts.CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
                    g = bi.createGraphics();
                    g.drawImage(img, 0, 0, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
                    break;
            }
            imageIconFunc = new ImageIcon(bi);

            return imageIconFunc;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    protected final void setImageIcon(int dir) {
        imageIcon = directions[dir];
    }

    public boolean overlap(final Element elem) {
        double xDist = Math.abs(elem.pos.getX() - this.pos.getX());
        double yDist = Math.abs(elem.pos.getY() - this.pos.getY());

        return (xDist < 0.85 && yDist < 0.85);
    }

    public boolean overlap(final List<Element> elem) {
        Iterator<Element> it = elem.listIterator();
        while (it.hasNext()) {
            Wall w = (Wall) it.next();
            double xDist = Math.abs(w.pos.getX() - this.pos.getX());
            double yDist = Math.abs(w.pos.getY() - this.pos.getY());

            if (xDist < 1.0 && yDist < 1.0) {
                return true;
            }
        }

        return false;
    }

    public String getStringPosition() {
        return ("(" + pos.getX() + ", " + pos.getY() + ")");
    }

    public boolean setPosition(double x, double y) {
        return pos.setPosition(x, y);
    }

    public boolean isTransposable() {
        return isTransposable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setTransposable(boolean isTransposable) {
        this.isTransposable = isTransposable;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    abstract public void autoDraw(Graphics g);

    // Pacman
    public boolean moveUp() {
        return this.pos.moveUp();
    }

    public boolean moveDown() {
        return this.pos.moveDown();
    }

    public boolean moveRight() {
        return this.pos.moveRight();
    }

    public boolean moveLeft() {
        return this.pos.moveLeft();
    }

    // Enemy
    public boolean moveUpEnemy() {
        return this.pos.moveUpEnemy();
    }

    public boolean moveDownEnemy() {
        return this.pos.moveDownEnemy();
    }

    public boolean moveRightEnemy() {
        return this.pos.moveRightEnemy();
    }

    public boolean moveLeftEnemy() {
        return this.pos.moveLeftEnemt();
    }
    
    // Slow Enemy
    public boolean moveUpSlowEnemy() {
        return this.pos.moveUpSlowEnemy();
    }

    public boolean moveDownSlowEnemy() {
        return this.pos.moveDownSlowEnemy();
    }

    public boolean moveRightSlowEnemy() {
        return this.pos.moveRightSlowEnemy();
    }

    public boolean moveLeftSlowEnemy() {
        return this.pos.moveLeftSlowEnemt();
    }
    
    public Position getPos() {
        return pos;
    }

    public ImageIcon getImgElement() {
        return imageIcon;
    }

    protected boolean isPosInteger() {
        int x = (int) pos.getX();
        int y = (int) pos.getY();

        if((x - pos.getX()) == 0 && (y - pos.getY()) == 0)
            return true;
        else
            return false;
    }
}
