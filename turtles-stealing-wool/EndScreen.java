import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EndScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndScreen extends World
{

    /**
     * Constructor for objects of class EndScreen.
     * 
     */
    public EndScreen()
    {    
        // Create a new world with 400x400 cells with a cell size of 1x1 pixels.
        super(400, 400, 1);
        prepare();
    }
    
    // This method creates the lables which show the Game Over message
    public void prepare()
    {
        // Show the Game Over message
        Label overText = new Label("Game Over", 70);
        addObject(overText, getWidth()/2, getHeight()/2 - 90);
        
        String message = "A turtle stole your wool.";
        Label subTitle = new Label(message, 30);
        addObject(subTitle, getWidth()/2, getHeight()/2);
        
        // Shows the score
        String scoreText = "Score: " + MyWorld.getScore();
        Label scoreDisplay = new Label(scoreText, 50);
        addObject(scoreDisplay, getWidth()/2, getHeight()/2 + 90);
    }
    
    public void act()
    {
        // When the user presses Z, the game restarts
        if (Greenfoot.isKeyDown("z"))
        {
            MyWorld.setAddedScore(0);
            Greenfoot.setWorld(new TitleScreen());
        }
        if(Greenfoot.isKeyDown("x")){}
    }
}
