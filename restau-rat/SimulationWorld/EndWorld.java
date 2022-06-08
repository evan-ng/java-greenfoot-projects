import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the end world, which appears at the end of the simulation.
 * This will display a score that is calculated based on the starting
 * and current funds as well as the star rating. Sounds from https://tabletopaudio.com.
 * 
 * @author Amy Cheung
 * @author Alex Mar
 * @author Evan Ng
 * @author Raymond Zhang
 * @version 2 (14/05/2020)
 */
public class EndWorld extends World
{
    // Instance Variables related to score
    private int finalScore;
    
    // Instance Buttons that will display text
    private Button redoButton;
    private Button scoreButton;
    private Button scoreTextButton;
    private Button howScoreButton;
    private Button overButton;
    
    // Instance Variables related to background
    GreenfootImage background;
    private Color backColor = new Color(40, 80, 60);
    private GreenfootSound backgroundMusic;
    
    /**
     * Constructor for objects of class EndWorld. Takes two ints and
     * one double ( the starting and current funds, and star rating in
     * order to calculate the score.
     * 
     * @param startingFunds     Money at the start of the simulation
     * @param currentFunds      Money at the end of the simulation
     * @param starRating        Star rating at the end of the simulation
     */
    public EndWorld(int startingFunds, int currentFunds, double starRating)
    {    
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        
        backgroundMusic = new GreenfootSound("81_1920s_Speakeasy.mp3");
        backgroundMusic.playLoop();
        
        // Calculates profit
        int profit = (currentFunds - startingFunds);
        
        // Calculates the final score
        finalScore = (currentFunds + (int)((double)profit * starRating)) * 10;
        
        // Adds the visuals
        addButtons();
        addBackground();
        
        // Adds a transition into the end screen.
        addObject(new Transition(false), getWidth()/2, getHeight()/2);
    }
    
    /**
     * Act - do whatever the PopUp wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        // Ensures no errors if mouse is not there
        if (mouse != null && mouse.getActor() != null){
            // If the mouse is hovering over the start button and clicks it
            if (mouse.getActor().equals(redoButton) && Greenfoot.mouseClicked(null))
            {
                // Goes to the start world.
                startStartWorld();
            }
            // When mouse hovers over the score, it will show how score is calculated
            if (mouse.getActor().equals(scoreButton))
            {
                howScoreButton.setText(" How Score is Calculated: (moneyNow + profit x starRating) x 10                                  ");
            }
            else
            {
                howScoreButton.setText("");
            }
        }
    }
    
    private void addButtons()
    {
        // Adds all the different buttons
        redoButton = new Button(getWidth()/9, getHeight()/12, 10, 5, 24, new Color (255, 225, 210), 
                                 new Color (210, 160, 140));
        redoButton.setText(" REDO    ");
        addObject(redoButton, getWidth()/2, getHeight()/2 + getHeight()/3);
        
        scoreButton = new Button(12*getWidth()/50, getHeight()/7, 0, 5, 60, new Color (255, 225, 210, 100), 
                                 new Color (210, 160, 140, 100));
        scoreButton.setText("" + finalScore + "  ");
        addObject(scoreButton, getWidth()/2, 21*getHeight()/36);
        
        scoreTextButton = new Button(getWidth()/9, getHeight()/12, 10, 5, 12, new Color (255, 225, 210, 0), 
                                 new Color (210, 160, 140, 0));
        scoreTextButton.setText(" Score:    ");
        addObject(scoreTextButton, getWidth()/2, 39*getHeight()/72);
        
        howScoreButton = new Button(getWidth(), getHeight()/12, 10, 5, 15, new Color (255, 225, 210, 0), 
                                 new Color (210, 160, 140, 0));
        howScoreButton.setText("");
        addObject(howScoreButton, getWidth()/2, 34*getHeight()/72);
        
        overButton = new Button(getWidth() + 30, 12*getHeight()/60, 0, 5, 78, new Color (255, 225, 210, 33), 
                                 new Color (210, 160, 140, 0));
        overButton.setText("  SIMULATION OVER            ");
        addObject(overButton, getWidth()/2, getHeight()/4);
    }
    
    private void addBackground()
    {
        // Makes the background into wood.
        background = new GreenfootImage("wood.jpg");
        background.scale(getWidth(), getHeight());
        setBackground(background);
    }
    
    private void startStartWorld()
    {
        backgroundMusic.stop();
        Greenfoot.setWorld(new StartWorld());
    }
}
