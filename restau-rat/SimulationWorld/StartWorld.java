import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartWorld here.
 * (This is where there is a start button and a help/tutorial button.)
 * Sounds from https://tabletopaudio.com.
 * 
 * @author Amy Cheung
 * @author Alex Mar
 * @author Evan Ng
 * @author Raymond Zhang
 * @version 1 (11/04/2020)
 */
public class StartWorld extends World
{
    // Instance Variables related to buttons
    private Button startButton;
    private Button helpButton;
    private Button titleButton;
    
    // Instance Variables related to background
    private GreenfootImage background;
    private Color backColor = new Color(10, 40, 60);
    
    // Instance Variables related to the popUp
    private PopUp helpPopUp;
    private int numberOfSlides = 7;
    private String[] helpTitle = new String [numberOfSlides];
    private String[] helpBody = new String [numberOfSlides];
    private int slideNow;
    
    private GreenfootSound backgroundMusic;
    
    /**
     * Constructor for objects of class StartWorld.
     * 
     */
    public StartWorld()
    {    
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        
        backgroundMusic = new GreenfootSound("81_1920s_Speakeasy.mp3");
        backgroundMusic.playLoop();
        
        addBackground();
        addButtons();
        setTexts();
    }
    
    /**
     * Act - do whatever the StartWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        if (mouse != null && mouse.getActor() != null){
            // If the mouse is hovering over the start button and clicks it
            if (mouse.getActor().equals(startButton) && Greenfoot.mouseClicked(null))
            {
                // Goes to the points world.
                startPointsWorld();
            }
            if (mouse.getActor().equals(helpButton) && Greenfoot.mouseClicked(null))
            {
                // Goes to the points world.
                addPopUp();
            }
        }
        
        changeText();
    }    
    
    private void addBackground()
    {
        background = new GreenfootImage("wood2.jpg");
        background.scale(getWidth(), getHeight());
        setBackground(background);
    }

    private void addButtons()
    {
        startButton = new Button(getWidth()/6, getHeight()/9, 10, 5, 36, new Color (255, 225, 210), 
                                 new Color (210, 160, 140));
        startButton.setText(" START    ");
        addObject(startButton, getWidth()/2, getHeight()/2 + getHeight()/6);
        
        helpButton = new Button(getWidth()/6, getHeight()/9, 10, 5, 36, new Color (255, 225, 210), 
                                 new Color (210, 160, 140));
        helpButton.setText(" HELP   ");
        addObject(helpButton, getWidth()/2, getHeight()/2 + getHeight()/3);
        
        titleButton = new Button(getWidth() + 30, 12*getHeight()/60, 0, 5, 68, new Color (255, 225, 210, 33), 
                                 new Color (210, 160, 140, 0));
        titleButton.setText("  RESTAURAT SIMULATOR              ");
        addObject(titleButton, getWidth()/2, getHeight()/4);
    }
    
    private void addPopUp()
    {
        helpPopUp = new PopUp(getWidth()/3, 4*getHeight()/5, 10, 5, true, 3, numberOfSlides, 1, 
                              new Color (255, 225, 210), new Color (210, 160, 140));
        slideNow = 1;
        helpPopUp.setTitle(helpTitle[slideNow - 1]);
        helpPopUp.setBody(helpBody[slideNow - 1]);
        addObject(helpPopUp, getWidth()/2, getHeight()/2);
    }
    
    private void changeText()
    {
        if (helpPopUp != null){
            if (slideNow != helpPopUp.getCurrentSlide())
            {
                slideNow = helpPopUp.getCurrentSlide();
                helpPopUp.setTitle(helpTitle[slideNow - 1]);
                helpPopUp.setBody(helpBody[slideNow -1]);
            }
        }
    }
    
    private void setTexts()
    {
        helpTitle[0] = "WELCOME      ";
        helpBody[0] = "Welcome to our simulation! \n\nClick the arrows to go through the help texts. "
        + "\n(Be a little patient, the text takes time to load)";
        
        helpTitle[1] = "DAYTIME      ";
        helpBody[1] = "During day time, the restaurant is open! Customers, Critics, "
        + "and Inspectors arrive to change your funds and star rating!"
        + "\n\nThey are not the only ones to come though... Pesky pests pester people, making them unhappy. "
        + "The staff must kill them before they cause too much damage. The food supply is also replenished "
        + "during this time. In exchange for food, you must pay with money of course! \n\nAs the day comes to an end, "
        + "so does the flow of customers. When clock strikes 8, its time to clean and leave. \n\n You will have a"
        + "total of 4 days to get the highest profit and star rating possible!";
                      
        helpTitle[2] = "CUSTOMERS & INSPECTORS              ";
        helpBody[2] = "Customers arrive during the day. Once seated, they must wait " +
        "for the cook to give them food. If it takes too long, their patience is depleted " +
        "and they leave without paying. Avoid this by making sure they get food fast! " +
        "When pests are nearby, customers' patience will deplete faster."
        + "\n\nThere is one special customer that not only pays, "
        + "but affects your restaurant's star rating: the Critic! They come with a red notepad,"
        + "taking note of how long the food takes and number of pests."
        + "\n\nInspectors come, using their white notepad to record how many pests "
        + "there are. If they find bugs, they will give a fine. If they see a rat, "
        + "they will immediately fine and reduce your star rating by one.";
        
        helpTitle[3] = "STAFF    ";
        helpBody[3] = "The restaurant has three staff: one cook and two maintenance staff. "
        + "The maintanence staff bumble about the restaurant, killing any pests they find with "
        + "the technologically advanced Homing Fork. The cook makes meals, but can have trouble "
        + "feeding every customer! \nWhen the day ends, the staff leave and are each paid. ";
        
        helpTitle[4] = "PESTS     ";
        helpBody[4] = "In a restaurant not too far away, there are brilliant smells, smells that attract pests! " +
        "During the day, when appetizing aromas are absolutely abundant, bugs and rats storm the restaurant... " +
        "These pests will roam around, pestering nearby customers. " +
        "\n\nThese pests are very concerning to the Health Inspectors and could cause loses in funds and star ratings."
        + " However, they are very vunerable to the Homing Forks and die once hit by one.";
        
        helpTitle[5] = "NIGHT OF THE RATS          ";
        helpBody[5] = "At night, the restuarant is closed. There should be no movement... but there is! " +
        "When clock strikes 11, all pests are targeted with Homing Forks. However, there are a smart bunch. "
        + "\n\nLocal small business Rat-ratouille has been competing with Restaurat since 1932. "
        + "However, the restaurant has fallen under hard times and now, they are desperate. "
        + "To beat their competition, they have been sending rats to raid Restaurat! "
        + "The rats know when the security Forks arrive, and INVADE after it activates. "
        + "During this RAT (🐀) INVASION, rats swarm the restaurant and steal your food!";
        
        helpTitle[6] = "VARIABLES & SCORES           ";
        helpBody[6] = "There are many different variables that can affect this simulation. " +
        "Some of which affect the customers, some affect the staff, and some affect the pests! "
        + "These variables are adjustable within limits! Change these variables to get the best "
        + "scores you can possibly get!";
    }
    
    private void startPointsWorld()
    {
        backgroundMusic.stop();
        Greenfoot.setWorld(new PointsWorld());
    }
}
