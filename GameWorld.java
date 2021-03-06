import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import java.util.List;
import java.util.Iterator;

/**
 * <b>A general world class for the Minesweeper game.</b>
 * @author Erik Martin
 * @version 2014.11.11
 */
public class GameWorld extends World {
    private static Tile[][] tiles = new Tile[32][24];
    
    /**
     * Creates a world of 32x24 Tile objects, randomly picks which Tile objects have mines,
     * and adds the Tile objects to the world.
     */
    public GameWorld() {    
        // Create a new world with 32x24 cells with a cell size of 24x24 pixels.
        super(32, 24, 24); 
        
        //Declare the game cells and add them to the world
        for(int j = 0; j < tiles.length; j++) {
            for(int k = 0; k < tiles[j].length; k++) {
                tiles[j][k] = new Tile();
            }
        }
        
        //Randomly pick which cells have mines (There will be 64 mines)
        Random r = new Random();
        int[][] selected = new int[64][2];
        int num = 0;
        while(num < 64) {
            int x = r.nextInt(33);
            int y = r.nextInt(25);
            
            //Verify that the cell selected is a new cell
            boolean repeated = false;
            for(int j = 0; j < 64; j++) {
                int tempX = selected[j][0];
                int tempY = selected[j][1];
                if((x == tempX) || (y == tempY)) {
                    repeated = true;
                }
            }
            if(!repeated) {
                selected[num][0] = x;
                selected[num][1] = y;
                num++;
            }
        }
        for(int j = 0; j < 64; j++) {
            tiles[selected[j][0]][selected[j][1]].setHasMine(true);
        }
        
        //Add the cells to the game world
        for(int j = 0; j < tiles.length; j++) {
            for(int k = 0; k < tiles[j].length; k++) {
                addObject(tiles[j][k], j, k);
            }
        }
    }
    
    /**
     * Once a Tile with a mine is selected, the world reveals all unflagged mines and incorrectly flagged cells.
     * @param tile the Tile that was incorrectly selected.
     */
    public static void revealMines(Tile tile) {
        for(int j = 0; j < tiles.length; j++) {
            for(int k = 0; k < tiles[j].length; k++) {
                if(tiles[j][k].returnHasMine() && !tiles[j][k].returnFlagged()) { //Has a mine and is not flagged
                    tiles[j][k].setImage("mine-unselected.png");
                }
                else if(!tiles[j][k].returnHasMine() && tiles[j][k].returnFlagged()) { //Is flagged but has no mine
                    tiles[j][k].setImage("flag-wrong.png");
                }
            }
        }
        if(tile.returnHasMine()) {
            tile.setImage("mine-selected.png");
        }
    }
}
