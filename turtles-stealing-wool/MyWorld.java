import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends greenfoot.World
{
    static SimpleTimer hold;
    static SimpleTimer canMakePear;
    static Sheep sheep;
    
    static SimpleTimer canMakeTurtle;
    
    static int score = 0;
    static int addedScore = 0;
    SimpleTimer scoreTime;
    Label scoreDisplay;
    
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 400x400 cells with a cell size of 1x1 pixels.
        super(400, 400, 1); 
        
        // Creates the score label
        scoreDisplay = new Label(0, 30);
        addObject(scoreDisplay, 40, 30);
        
        // Instantiate the sheep and add to the world
        sheep = new Sheep();
        addObject(sheep, getWidth() / 2, getHeight() / 2);
        
        // Creates the timers necessary to play the game
        hold = new SimpleTimer();
        scoreTime = new SimpleTimer();
        canMakePear = new SimpleTimer();
        canMakeTurtle = new SimpleTimer();
        
        // Starts the timers at 0 milliseconds
        hold.mark();
        scoreTime.mark();
        canMakePear.mark();
        canMakeTurtle.mark();
        
        // Begins spawning the turtles
        addTurtle();
    }
    
    /**
     * Creates pears at a random position around the sheep
     */
    public void addPear()
    {
        Pear pear = new Pear(sheep.getX(), sheep.getY());
        int x = Greenfoot.getRandomNumber(getWidth()/3) + getWidth() / 3;
        int y = Greenfoot.getRandomNumber(getHeight()/3) + getHeight() / 3;
        addObject(pear, x, y);
    }
    
    /**
     * Spawns the turtles at the edges of the world
     */
    public void addTurtle()
    {
       Turtle turtle = new Turtle(sheep.getX(), sheep.getY());
       if(Greenfoot.getRandomNumber(2) == 1)
       {
           int x = Greenfoot.getRandomNumber(getWidth());
           int y;
           if(Greenfoot.getRandomNumber(2) == 1)
           {
               y = 0;
           }
           else
           {
               y = getHeight();
           }
           addObject(turtle, x, y);
       }
       else
       {
           int x;
           int y = Greenfoot.getRandomNumber(getHeight());
           if(Greenfoot.getRandomNumber(2) == 1)
           {
               x = 0;
           }
           else
           {
               x = getWidth();
           }
           addObject(turtle, x, y);
       }
    }
    
    /**
     * Spawns the pears around the sheep when the user presses the X button
     */
    public void makePears()
    {
        if(Greenfoot.isKeyDown("x"))
        {
            // Does not allow more than 10 pears to spawn at a time
            if(Pear.getPears() >= 10)
            {
                canMakePear.mark();
            }
            // Every 170 milliseconds while the user presses the X button, 
            // a pear spawns
            if(canMakePear.millisElapsed() > 170)
            {
                addPear();
                canMakePear.mark();
            }
        }
        else
        {
            hold.mark();
        }
    }
    
    /**
     * Spawns the turtles at the edges of the world every 10000 milliseconds
     */
    public void makeTurtles()
    {
        if(canMakeTurtle.millisElapsed() > 10000)
        {
             addTurtle();
             canMakeTurtle.mark();
        }
    }
    
    /**
     * A getter method for the score
     */
    static public int getScore()
    {
        return score;
    }
    
    /**
     * A getter method for the added score
     */
    static public int getAddedScore()
    {
        return addedScore;
    }
    
    /**
     * A setter method for the added score
     */
    static public void setAddedScore(int x)
    {
        addedScore = x;
    }
    
    public void act() 
    {
        // Checks if a turtle can spawn and spawns one if possible
        makeTurtles();
        
        // Checks if a pear can spawn and spawns one if possible
        makePears();
        
        // Sets the score based on the time passed and number of times a turtle has 
        // been hit by a pear
        score = scoreTime.millisElapsed()/50 + addedScore;
        scoreDisplay.setValue(score);
    }    
}
