import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Iterator;

/**
 * The class for an individual Tile, containing information about whether it has a mine, whether it is flagged,
 * and how many adjacent Tiles have mines.
 * @author Erik Martin
 * @version 2014.11.11
 */
public class Tile extends Actor {
    private boolean hasMine, flagged;
    private int x, y;
    private int adjacentMines;
    
    /**
     * Act method; checks for the mouse being left- or right-clicked on the Tile.
     */
    public void act() {
        //Test for the mouse being clicked on this cell
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int mouseX = 0, mouseY = 0;
        
        if(Greenfoot.mouseClicked(this)) {
            if(mouse.getButton() == 1) //Left
                this.doClicked();
            else if(mouse.getButton() == 3) //Right
                this.doFlagged();
        }
    }
    
    /**
     * Set the x-position of the Tile in the GameWorld grid.
     * @param tempX the new x-position.
     */
    public void setX(int tempX) {
        x = tempX;
    }
    
    /**
     * Return the current x-position of the Tile in the GameWorld grid.
     * @return x the current x-position.
     */
    public int getX() {
        return x;
    }
    
    /**
     * Set the y-position of the Tile in the GameWorld grid.
     * @param tempY the new y-position.
     */
    public void setY(int tempY) {
        y = tempY;
    }
    
    /**
     * Return the current y-position of the Tile in the GameWorld grid.
     * @return y the current y-position.
     */
    public int getY() {
        return y;
    }
    
    /**
     * Iterates through all adjacent Tiles and adds up the number that have a mine.
     * Stores this number in a class variable.
     */
    public void findAdjacentMines() {
        List<Tile> adjacent = getObjectsInRange(1, Tile.class);
        Iterator itr = adjacent.iterator();
        int num = 0;
        while(itr.hasNext()) {
            Tile temp = (Tile)itr.next();
            if(temp.returnHasMine()) {
                num++;
            }
        }
        adjacentMines = num;
    }
    
    /**
     * Return the number of adjacent Tiles that have a mine.
     * @return adjacentMines the number of adjacent mines.
     */
    public int getAdjacentMines() {
        return adjacentMines;
    }
    
    /**
     * Set whether the Tile has a mine.
     * @param mine a boolean stating whether the Tile has a mine.
     */
    public void setHasMine(boolean mine) {
        hasMine = mine;
    }
    
    /**
     * Return whether the Tile has a mine.
     * @return hasMine a boolean stating whether the Tile has a mine.
     */
    public boolean returnHasMine() {
        return hasMine;
    }
    
    /**
     * Set whether the Tile is flagged.
     * @param flag a boolean stating whether the Tile is flagged.
     */
    public void setFlagged(boolean flag) {
        flagged = flag;
    }
    
    /**
     * Return whether the Tile is flagged.
     * @return flagged a boolean stating whether the Tile is flagged.
     */
    public boolean returnFlagged() {
        return flagged;
    }
    
    /**
     * Fulfill the correct actions for when the Tile is left-clicked.
     * If the tile has a mine, call the GameWorld method revealMines.
     * If the tile does not have a mine, reveal the tile with the number of adjacent mines displayed.
     * If an adjacent tile has no mines nearby (and is thus "empty") reveal all connected "empty" tiles,
     *   by calling the Tile method revealAdjacent.
     * If, however, the adjacent Tiles are incorrectly flagged, call the GameWorld method revealMines.
     * 
     * @see #revealAdjacent(Tile tile)
     * @see GameWorld#revealMines(Tile tile)
     */
    public void doClicked() {
        if(hasMine) {
            GameWorld.revealMines(this);
        }
        else {
            findAdjacentMines();
            switch(adjacentMines) {
                case 0:
                    setImage("empty.png");
                    break;
                case 1:
                    setImage("one.png");
                    break;
                case 2:
                    setImage("two.png");
                    break;
                case 3:
                    setImage("three.png");
                    break;
                case 4:
                    setImage("four.png");
                    break;
                case 5:
                    setImage("five.png");
                    break;
                case 6:
                    setImage("six.png");
                    break;
                case 7:
                    setImage("seven.png");
                    break;
                case 8:
                    setImage("eight.png");
                    break;
                default:
                    setImage("empty.png");
            }
        }
        //Test whether all of the adjacent mines are correctly flagged
        List<Tile> adjacent = getObjectsInRange(1, Tile.class);
        Iterator iter = adjacent.iterator();
        int mines = 0, flaggedRight = 0, flaggedWrong = 0;
        while(iter.hasNext()) {
            Tile temp = (Tile)iter.next();
            if(temp.returnHasMine()) {
                mines++;
                if(temp.returnFlagged()) { //If the tile has a mine and is flagged
                    flaggedRight++;
                }
            }
            else if(temp.returnFlagged()) { //If the tile is flagged, but has no mine
                flaggedWrong++;
            }
        }
        if(flaggedRight == mines) { //If all of the adjacent mines are flagged
            revealAdjacent(this);
        }
        else if(flaggedRight + flaggedWrong == mines) { //If the right number of adjacent tiles are flagged, but incorrectly.
            GameWorld.revealMines(this);
        }
    }
    
    /**
     * Add or remove a flag to/from a Tile.
     */
    public void doFlagged() {
        if(!flagged) {
            setImage("flag.png");
            flagged = true;
        }
        else {
            setImage("blank.png");
        }
    }
    
    /**
     * Reveal all adjacent "empty" Tiles. This method is designed to call itself recursively in conjunction with
     *   the Tile method doClicked.
     * @param tile the Tile originally clicked.
     * 
     * @see #doClicked()
     */
    public static void revealAdjacent(Tile tile) {
        List<Tile> adjacent = tile.getObjectsInRange(1, Tile.class);
        Iterator iter = adjacent.iterator();
        while(iter.hasNext()) {
            Tile temp = (Tile)iter.next();
            if(!temp.returnHasMine()) { //If the adjacent tile is blank or has a number
                temp.doClicked();
            }
        }
    }
}
