import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends World
{
    boolean canClick = false;
    SimpleTimer clickTimer = new SimpleTimer();
    
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 400x400 cells with a cell size of 1x1 pixels.
        super(400, 400, 1); 
        prepare();
    }
    
    public void prepare()
    {
        // Displays the title and instructions
        Label title = new Label("Many Turtles", 70);
        addObject(title, getWidth()/2, getHeight()/2 - 90);
        
        String instructions = "Hold X for Pears";
        Label subTitle = new Label(instructions, 30);
        addObject(subTitle, getWidth()/2, getHeight()/2);
        
        String startTask = "Press Z to start";
        Label startDisplay = new Label(startTask, 50);
        addObject(startDisplay, getWidth()/2, getHeight()/2 + 90);
    }
    
    public void act()
    {
        // Create the game world when user presses the Z button after 500 milliseconds
        if (clickTimer != null && clickTimer.millisElapsed() > 500)
        {
            canClick = true;
        }
        if(Greenfoot.isKeyDown("z") && canClick == true)
        {
            Greenfoot.setWorld(new MyWorld());
        }
        if(Greenfoot.isKeyDown("x")){}
    }
}
