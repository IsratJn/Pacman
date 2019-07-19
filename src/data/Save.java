package data;

import elements.Ball;
import elements.Blinky;
import elements.Cherry;
import elements.Clyde;
import elements.Element;
import elements.Enemy;
import elements.Inky;
import elements.PacMan;
import elements.Pinky;
import elements.PowerPellet;
import elements.Strawberry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Save implements Serializable {
    public PacMan pacMan;
    
    public Clyde clyde;
    public Blinky blinky;
    public Inky inky;
    public Pinky pinky;
    
    public Strawberry strawberry;
    public Cherry cherry;
    
    public ArrayList<Element> elemArray;
    public ArrayList<Enemy> enemys;
    public List<Element> walls;
    
    public List<Ball> balls;
    public List<PowerPellet> powerPellet;
    
    public int controlScene;

    public Save(){}
}
