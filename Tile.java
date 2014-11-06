import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile extends Actor
{
    private boolean hasMine, flagged;
    private int x, y;
    
    public void act() 
    {
        //Test for the mouse being clicked on this cell
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int mouseX = 0, mouseY = 0;
        
        if(Greenfoot.mouseClicked(this)) {
            if(mouse.getButton() == 1)
                this.doClicked();
            else if(mouse.getButton() == 3)
                this.doFlagged();
        }
    }
    
    public void setX(int tempX) {
        x = tempX;
    }
    
    public int getX() {
        return x;
    }
    
    public void setY(int tempY) {
        y = tempY;
    }
    
    public int getY() {
        return y;
    }
    
    public void setHasMine(boolean b) {
        hasMine = b;
    }
    
    public boolean returnHasMine() {
        return hasMine;
    }
    
    public void setFlagged(boolean flag) {
        flagged = flag;
    }
    
    public boolean returnFlagged() {
        return flagged;
    }
    
    public int getNumberAdjacent() {
        List<Tile> adjacent = getObjectsInRange(1, Tile.class);
	Iterator itr = adjacent.iterator();
	int num = 0;
	while(itr.hasNext()) {
	    Tile temp = itr.next();
	    if(temp.returnHasMine()) {
		num++;
	    }
	}
	return num;
    }
    
    public void doClicked() {
        if(hasMine) {
            GameWorld.revealMines(this);
        }
        else {
            switch(getNumberAdjacent()) {
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
    }
    
    public void doFlagged() {
        if(!flagged) {
            setImage("flag.png");
        }
        else {
            setImage("blank.png");
        }
    }
}
