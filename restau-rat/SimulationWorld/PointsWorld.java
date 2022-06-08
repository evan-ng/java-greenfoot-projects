import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the points world, where points are distributed to 
 * change the stats and vary the simulation. Hovering over
 * various parts of the world will allow the user to fully
 * understand what can be changed and how it will change
 * the simulation. Sounds from https://tabletopaudio.com.
 * 
 * @author Amy Cheung
 * @author Alex Mar
 * @author Evan Ng
 * @author Raymond Zhang
 * @version 5 (05/05/2020)
 */
public class PointsWorld extends World
{
    // Instance Variables related to points
    private int TOTAL_POINTS = 5;
    private int currentPoints;
    
    //Instance Variables related to the adjustable variables
    private int startingFunds;
    private int customerSpawnRate;
    private int criticSpawnRate;
    private int inspectorSpawnRate;
    private int ratSpawnRate;
    private int bugSpawnRate;
    private int eatSpeed;
    private int cookSpeed;
    
    // Default value (index 0), minimum value(index 1), 
    // maximum value(index 2), changeRate(index 3)
    private int[] startingFundsDMMC = {500, 100, 2500, 100};
    private int[] customerSpawnRateDMMC = {3, 1, 10, 1};
    private int[] criticSpawnRateDMMC = {2, 1, 10, 1};
    private int[] inspectorSpawnRateDMMC = {2, 1, 10, 1};
    private int[] ratSpawnRateDMMC = {5, 1, 10, 1};
    private int[] bugSpawnRateDMMC = {5, 1, 10, 1};
    private int[] eatSpeedDMMC = {3, 1, 10, 1};
    private int[] cookSpeedDMMC = {3, 1, 10, 1};
    
    //Instance Colors
    private Color backC = new Color (255, 225, 210);
    private Color borderC = new Color (210, 160, 140);
    
    // PopUp used to describe variables upon hovering over PointDistributors
    private PopUp variableDescription = new PopUp(3*getWidth()/10 - 10, 9*getHeight()/10, 15, 2, backC, borderC);
    
    // Instance Buttons that will display text and can be clicked
    private Button backButton = new Button(3*getWidth()/10, getHeight()/9, 10, 5, 36, backC, borderC);
    private Button startButton = new Button(3*getWidth()/10, getHeight()/9, 10, 5, 36, backC, borderC);
    private Button totalPointsDisplay = new Button(6*getWidth()/10, getHeight()/9, 10, 5, 36, backC, borderC);
    
    // Declare Instance PointDistrubtors
    private PointDistributor startingFundsDis = new PointDistributor(startingFundsDMMC[0], startingFundsDMMC[1],
        startingFundsDMMC[2], startingFundsDMMC[3], getWidth()/4, getHeight()/12, 5, 5, 22, backC, borderC);
    private PointDistributor customerSpawnRateDis = new PointDistributor(customerSpawnRateDMMC[0], customerSpawnRateDMMC[1],
        customerSpawnRateDMMC[2], customerSpawnRateDMMC[3], getWidth()/4, getHeight()/12, 5, 5, 22, backC, borderC);
    private PointDistributor criticSpawnRateDis = new PointDistributor(criticSpawnRateDMMC[0], criticSpawnRateDMMC[1],
        criticSpawnRateDMMC[2], criticSpawnRateDMMC[3], getWidth()/4, getHeight()/12, 5, 5, 22, backC, borderC);
    private PointDistributor inspectorSpawnRateDis = new PointDistributor(inspectorSpawnRateDMMC[0], inspectorSpawnRateDMMC[1],
        inspectorSpawnRateDMMC[2], inspectorSpawnRateDMMC[3], getWidth()/4, getHeight()/12, 5, 5, 22, backC, borderC);
    
    private PointDistributor ratSpawnRateDis = new PointDistributor(ratSpawnRateDMMC[0], ratSpawnRateDMMC[1],
        ratSpawnRateDMMC[2], ratSpawnRateDMMC[3], getWidth()/4, getHeight()/12, 5, 5, 22, backC, borderC);
    private PointDistributor bugSpawnRateDis = new PointDistributor(bugSpawnRateDMMC[0], bugSpawnRateDMMC[1],
        bugSpawnRateDMMC[2], bugSpawnRateDMMC[3], getWidth()/4, getHeight()/12, 5, 5, 22, backC, borderC);
    private PointDistributor eatSpeedDis = new PointDistributor(eatSpeedDMMC[0], eatSpeedDMMC[1],
        eatSpeedDMMC[2], eatSpeedDMMC[3], getWidth()/4, getHeight()/12, 5, 5, 22, backC, borderC);
    private PointDistributor cookSpeedDis = new PointDistributor(cookSpeedDMMC[0], cookSpeedDMMC[1],
        cookSpeedDMMC[2], cookSpeedDMMC[3], getWidth()/4, getHeight()/12, 5, 5, 22, backC, borderC);
    
    // Instance Variables related to background
    GreenfootImage background;
    private Color backColor = new Color(100, 40, 10);
    private GreenfootSound backgroundMusic;
    
    /**
     * Constructor for objects of class PointsWorld.
     * No parameters are taken.
     */
    public PointsWorld()
    {    
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 
        
        backgroundMusic = new GreenfootSound("81_1920s_Speakeasy.mp3");
        backgroundMusic.playLoop();
        
        addBackground();
        
        addPointDistributors();
        
        variableDescription.setTitle("VARIABLE DESCRIPTION       ");
        variableDescription.setBody("This is will tell you about the different variables when you hover over the point distributors!");
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
            if (mouse.getActor().equals(startButton) && Greenfoot.mouseClicked(null))
            {
                // Goes to the points world.
                startSimWorld();
            }
            if (mouse.getActor().equals(backButton) && Greenfoot.mouseClicked(null))
            {
                // Goes to the points world.
                startStartWorld();
            }
            distributorHover();
        }
        
        currentPoints = TOTAL_POINTS - (startingFundsDis.getClicksFromDefault() + customerSpawnRateDis.getClicksFromDefault() +
        criticSpawnRateDis.getClicksFromDefault() + eatSpeedDis.getClicksFromDefault() + cookSpeedDis.getClicksFromDefault()) +
        (inspectorSpawnRateDis.getClicksFromDefault() + ratSpawnRateDis.getClicksFromDefault() + bugSpawnRateDis.getClicksFromDefault());
        
        totalPointsDisplay.setText(currentPoints + " ");
        
        setVariables();
        
        if (currentPoints == 0 || currentPoints == 1)
        {
            lockDistributors();
        }
    }
    
    private void addBackground()
    {
        background = new GreenfootImage("wood2.jpg");
        background.scale(getWidth(), getHeight());
        setBackground(background);
    }
    
    private void addPointDistributors()
    {
        addObject(variableDescription, getWidth()/6, getHeight()/2);
        
        // Column one
        addObject(startingFundsDis, getWidth()/2 - 10, 2*getHeight()/7);
        addObject(customerSpawnRateDis, getWidth()/2 - 10, 3*getHeight()/7);
        addObject(criticSpawnRateDis, getWidth()/2 - 10, 4*getHeight()/7);
        addObject(inspectorSpawnRateDis, getWidth()/2 - 10, 5*getHeight()/7);
        
        // Column two
        addObject(ratSpawnRateDis, 5*getWidth()/6 - 10, 2*getHeight()/7);
        addObject(bugSpawnRateDis, 5*getWidth()/6 - 10, 3*getHeight()/7);
        addObject(eatSpeedDis, 5*getWidth()/6 - 10, 4*getHeight()/7);
        addObject(cookSpeedDis, 5*getWidth()/6 - 10, 5*getHeight()/7);
        
        addObject(backButton, getWidth()/2 - 10, 7*getHeight()/8);
        addObject(startButton, 5*getWidth()/6 - 10, 7*getHeight()/8);
        addObject(totalPointsDisplay, 2*getWidth()/3 - 10, getHeight()/8);
        
        backButton.setText(" BACK   ");
        startButton.setText(" START    ");
        totalPointsDisplay.setText(TOTAL_POINTS + " ");
    }
    
    private void distributorHover()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        // Ensures no errors if mouse is not there
        if (mouse != null && mouse.getActor() != null){
            // If the mouse is hovering over the distributors
            if (mouse.getActor().equals(startingFundsDis.getButton()) || mouse.getActor().equals(startingFundsDis.getLArrow()) || mouse.getActor().equals(startingFundsDis.getRArrow()))
            {
                variableDescription.setTitle("STARTING FUNDS        ");
                variableDescription.setBody("This is how much money you will start the simulation with!");
            }
            if (mouse.getActor().equals(customerSpawnRateDis.getButton()) || mouse.getActor().equals(customerSpawnRateDis.getLArrow()) || mouse.getActor().equals(customerSpawnRateDis.getRArrow()))
            {
                variableDescription.setTitle("CUSTOMER SPAWN RATE      ");
                variableDescription.setBody("This is the customer spawn rate, which affects the likelyhood of a customer spawning during the day! \n\n1 is the lowest spawn rate and 10 is the highest");
            }
            if (mouse.getActor().equals(criticSpawnRateDis.getButton()) || mouse.getActor().equals(criticSpawnRateDis.getLArrow()) || mouse.getActor().equals(criticSpawnRateDis.getRArrow()))
            {
                variableDescription.setTitle("CRITIC SPAWN RATE        ");
                variableDescription.setBody("This is the critic spawn rate, which affects the likelyhood of a critic spawning during the day! \n\n1 is the lowest spawn rate and 10 is the highest");
            }
            if (mouse.getActor().equals(inspectorSpawnRateDis.getButton()) || mouse.getActor().equals(inspectorSpawnRateDis.getLArrow()) || mouse.getActor().equals(inspectorSpawnRateDis.getRArrow()))
            {
                variableDescription.setTitle("INSPECTOR SPAWN RATE     ");
                variableDescription.setBody("This is the inspector spawn rate, which affects the likelyhood of an inspector spawning during the day!\n\n1 is the lowest spawn rate and 10 is the highest");
            }
            if (mouse.getActor().equals(ratSpawnRateDis.getButton()) || mouse.getActor().equals(ratSpawnRateDis.getLArrow()) || mouse.getActor().equals(ratSpawnRateDis.getRArrow()))
            {
                variableDescription.setTitle("RAT SPAWN RATE         ");
                variableDescription.setBody("This is the rat spawn rate, which affects the likelyhood of rats spawning during the day and night! During the day, rats are less likely to spawn, but during the night, when clock strikes 11, a hoard of rats will enter to steal food. \n\n1 is the lowest spawn rate and 10 is the highest");
            }
            if (mouse.getActor().equals(bugSpawnRateDis.getButton()) || mouse.getActor().equals(bugSpawnRateDis.getLArrow()) || mouse.getActor().equals(bugSpawnRateDis.getRArrow()))
            {
                variableDescription.setTitle("BUG SPAWN RATE         ");
                variableDescription.setBody("This is the bug spawn rate, which affects the likelyhood of bugs spawning during the day!\n\n1 is the lowest spawn rate and 10 is the highest");
            }
            if (mouse.getActor().equals(eatSpeedDis.getButton()) || mouse.getActor().equals(eatSpeedDis.getLArrow()) || mouse.getActor().equals(eatSpeedDis.getRArrow()))
            {
                variableDescription.setTitle("EAT SPEED       ");
                variableDescription.setBody("This is how fast a customer will eat. The faster, the better.\n\n1 is the slowest eat speed and 10 is the highest");
            }
            if (mouse.getActor().equals(cookSpeedDis.getButton()) || mouse.getActor().equals(cookSpeedDis.getLArrow()) || mouse.getActor().equals(cookSpeedDis.getRArrow()))
            {
                variableDescription.setTitle("COOK SPEED       ");
                variableDescription.setBody("Adjust this to set how fast the chef cooks. \n\n1 is the slowest cook speed and 10 is the highest");
            }
            if (mouse.getActor().equals(backButton))
            {
                variableDescription.setTitle("BACK BUTTON       ");
                variableDescription.setBody("This is the back button. To go back to the start screen, click here!");
            }
            if (mouse.getActor().equals(startButton))
            {
                variableDescription.setTitle("START BUTTON        ");
                variableDescription.setBody("This is the start button. After adjusting the variables, click here!");
            }
            if (mouse.getActor().equals(totalPointsDisplay))
            {
                variableDescription.setTitle("TOTAL POINTS        ");
                variableDescription.setBody("This is how many points you have remaining to adjust the variables");
            }
        }
    }
    
    private void setVariables()
    {
        startingFunds = startingFundsDis.getCurrentPoints();
        customerSpawnRate = customerSpawnRateDis.getCurrentPoints();
        criticSpawnRate = criticSpawnRateDis.getCurrentPoints();
        inspectorSpawnRate = inspectorSpawnRateDis.getCurrentPoints();  
        ratSpawnRate = ratSpawnRateDis.getCurrentPoints();
        bugSpawnRate = bugSpawnRateDis.getCurrentPoints();
        eatSpeed = eatSpeedDis.getCurrentPoints();
        cookSpeed = cookSpeedDis.getCurrentPoints();
    }
    
    private void lockDistributors()
    {
        boolean locked;
        if (currentPoints == 0)
        {
            locked = true;
        }
        else
        {
            locked = false;
        }
        startingFundsDis.lockArrow(locked, false);
        customerSpawnRateDis.lockArrow(locked, false);
        criticSpawnRateDis.lockArrow(locked, false);
        inspectorSpawnRateDis.lockArrow(locked, true);
        ratSpawnRateDis.lockArrow(locked, true);
        bugSpawnRateDis.lockArrow(locked, true);
        eatSpeedDis.lockArrow(locked, false);
        cookSpeedDis.lockArrow(locked, false);
    }
    
    private void startSimWorld()
    {
        backgroundMusic.stop();
        Greenfoot.setWorld(new SimWorld(startingFunds, customerSpawnRate, criticSpawnRate, inspectorSpawnRate,
            ratSpawnRate, bugSpawnRate, eatSpeed, cookSpeed));
    }
    
    private void startStartWorld()
    {
        backgroundMusic.stop();
        Greenfoot.setWorld(new StartWorld());
    }
}
