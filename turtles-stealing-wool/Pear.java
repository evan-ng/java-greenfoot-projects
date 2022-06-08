import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pear here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pear extends Actor
{
    GreenfootImage image;
    int variation;
    
    int x;
    int y;
    
    SimpleTimer grow;
    
    boolean move = false;
    
    int speed = 1;
    SimpleTimer faster = new SimpleTimer();
    
    static int numberOfPears = 0;
    boolean auto = false;
    
    /**
     * The constructor for the pears. Takes two integer values as parameters for the
     * coordinates for the pear
     */
    public Pear(int x, int y)
    {
        // Gets the image for the pear
        image = new GreenfootImage("pear.png");
        
        // Gets a random value between 0 and 10 to change the size of the pear
        variation = Greenfoot.getRandomNumber(10);
        
        // Sets the image of the pear
        image.scale(1, 1);
        setImage(image);
        
        // Sets the parameters as the coordinates for the pear
        this.x = x;
        this.y = y;
        
        // Starts the pop in visual for the pear
        grow = new SimpleTimer();
        grow.mark();
        
        // Increases the number of pears variable
        numberOfPears++;
    }
    
    /**
     * Creates an animation in which the pear appears
     */
    public void grow()
    {
        if(grow.millisElapsed() > 100 && grow.millisElapsed() < 120)
        {
            change(2);
        }
        else if(grow.millisElapsed() > 150 && grow.millisElapsed() < 170)
        {
            change(4);
        }
        else if(grow.millisElapsed() > 200 && grow.millisElapsed() < 220)
        {
            change(8);
        }
        else if(grow.millisElapsed() > 250 && grow.millisElapsed() < 270)
        {
            change(20);
        }
        else if(grow.millisElapsed() > 310 && grow.millisElapsed() < 320)
        {
            change(40);
        }
    }
    
    /**
     * Takes in an integer as a parameter to set the size of the pear
     */
    public void change(int size)
    {
        image = new GreenfootImage("pear.png");
        image.scale(size + variation, size + variation);
        
        setImage(image);
    }
    
    /**
     * Increases the speed at which the pear moves after being released
     */
    public void speedUp()
    {
        if(move == true)
        {
            speed = 1 + faster.millisElapsed() / 50;
        }
    }
    
    /**
     * Moves the pear away from the sheep after the user releases the x button and 
     * allows for more pears to spawn
     */
    public void flyAway()
    {
        if(!Greenfoot.isKeyDown("x"))
        {
            move = true;
            numberOfPears = 0;
        }
        else if (Greenfoot.isKeyDown("x") && move != true)
        {
            faster.mark();
            shake();
        }
        if(move == true)
        {
            speedUp();
            move(-speed);
        }
    }
    
    /**
     * Shakes the pear
     */
    public void shake()
    {
        if(Greenfoot.getRandomNumber(2) == 1)
        {
            move(1);
        }
        else
        {
            move(-1);
        }
    }
    
    /**
     * A setter method for the number of pears variable
     */
    public static void setPears(int x)
    {
        numberOfPears = x;
    }
    
    /**
     * Spawns the turtles at the edges of the world
     */
    public static int getPears()
    {
        return numberOfPears;
    }
    
    public void act() 
    {
        grow();
        turnTowards(x, y);
        flyAway();
        if(isAtEdge() == true)
        {
            getWorld().removeObject(this);
        }
    }    
}
