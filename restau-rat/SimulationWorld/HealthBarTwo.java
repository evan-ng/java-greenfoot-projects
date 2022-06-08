import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * HealthBar is a Greenfoot Actor that displays an image displaying the current health of an Actor. 
 * The bar is green when the Actor has health and red when the Actor is missing health.
 * The hit point number is displayed using black text.
 * <p>
 * Values can be inputed in the constructor to make the HealthBar customizable to the game or the Actor.
 * Update methods allows the HealthBar to accurately represent the exact hit points of the Actor as the scenario/game progresses;
 * also allowing for color customization.
 * 
 * 
 * @author Alex Mar
 * @author Amy Cheung
 * @version 1.0.5 March 10, 2020
 */
public class HealthBarTwo extends Actor
{
    //instance variables
    private int maxHP;
    private int curHP = 0;
    private double percentHP;
    private int redBarSize;
    private int greenBarSize;
    private String showValue = curHP + " / " + maxHP;
    
    //instance images
    private GreenfootImage bar;
    private GreenfootImage heart;
    private GreenfootImage blank;
    private GreenfootImage imgone;
    
    //some constants - can be changed to suit size of related objects
    private final int HP_BAR_WIDTH = 100;
    private final int HP_BAR_HEIGHT = 10;
    private final int OFFSET = 36; //can also be 12
    
    //instance objects
    private Actor target;
    
    //some colour objects
    private Color myGreen = new Color (0, 255, 0);
    private Color myRed = new Color (0, 0, 0);
    private Color myGray = new Color (115, 81, 68);
    private Color myBlack = new Color (0, 0, 0);
    
    /**
     * Main constructor - this is only called by the other Constructor
     * and is not intended to be called directly.
     */
    public HealthBarTwo ()
    {
        imgone = new GreenfootImage (HP_BAR_WIDTH + 12, HP_BAR_HEIGHT + 10);//places a new image specified to the width and height
        imgone.setColor(myGray);//sets the color of the new image to gray
        imgone.fill();//fills the image with the color
        imgone.setColor(myBlack);
        imgone.drawRect(6,2,HP_BAR_WIDTH + 1, HP_BAR_HEIGHT + 1);//draws a rectangle with specified width and height
        
        bar= new GreenfootImage (HP_BAR_WIDTH, HP_BAR_HEIGHT);
        blank = new GreenfootImage(1,1);
        bar.setColor(myGreen);
        bar.fill();
        imgone.drawImage(bar,7,3);//draws a bar
        
        heart = new GreenfootImage("heart.png");//imports image from folder
        heart.scale(20, 20);//scales new image
        imgone.drawImage(heart, 0, 0);//draws the new heart image
        
        imgone.drawString(showValue, 22, 12);
        
        
        setImage(imgone);
        
        if (curHP == maxHP)
        {
            this.setImage(imgone);//displays HP bar even at max hit points
            //this.setImage(blank); this can be used to hide the HP bar when Actor is at max hit points
        }
        else
        {
            this.setImage(imgone);//displays HP bar
        }
    }
    
    /**
     * Takes two values for current and max hit points, allows actor to have a hit point value that is not the max HP
     * 
     * @param inMaxHP       New max hit points
     * @param inCurHP       New current hit points
     */
    public HealthBarTwo(int inMaxHP, int inCurHP)
    {
        this();
        maxHP = inMaxHP;
        curHP = inCurHP;
    }
    
    /**
     * Takes one int and sets max hit points and current hit points to the same value
     * 
     * @param inMaxHP       New max hit points
     */
    public HealthBarTwo(int inMaxHP)
    {
        this();
        maxHP = inMaxHP;
        curHP = inMaxHP;
    }
    
    /**
     * Takes one int and set max hit points and current hit points to the same value
     * <p>
     * Assigns a health bar that follows an actor
     * <p>
     * If actor is no longer in the world, the health bar will also be removed
     * 
     * @param inMaxHP       New max hit points
     * @param target        An actor that the health bar will be assigned to
     */
    public HealthBarTwo (int inMaxHP, Actor target)
    {
        this(inMaxHP);
        this.target = target;//HP bar follows Actor
    }
    
    /**
     * Act - do whatever the HealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(target.getWorld() != null)
        {
            setLocation (target.getX(), target.getY()- OFFSET);
        }
        else
        {
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Changes the color of the health bar.
     * 
     * @param newCurHP      New current hit points
     * @param newMaxHP      New max hit points
     */
    public void update(int newCurHP, int newMaxHP)
    {
        if (newCurHP != curHP || newMaxHP != maxHP)
        {
            if (newCurHP == newMaxHP)
            {
                this.setImage(blank);// hides the HP bar if new current hit points is equal to new max hit points
            }
            else
            {
                // Redraw HP bar w/ appropriate amount of green and red based on
                // current HP divided by max HP
                curHP = newCurHP;
                maxHP = newMaxHP;
                percentHP = (double) curHP / maxHP;// determines how much of the HP bar is filled by the hit points level
                greenBarSize = (int) (percentHP * HP_BAR_WIDTH);//displaysthe hit point level
                redBarSize = HP_BAR_WIDTH - greenBarSize;
                
                imgone.setColor(myGray);
                imgone.fill();
                imgone.setColor(myBlack);
                imgone.drawRect(3,2,HP_BAR_WIDTH + 1, HP_BAR_HEIGHT + 1);
                
                bar.setColor(myGreen);
                bar.fillRect(0, 0, greenBarSize, HP_BAR_HEIGHT);
                bar.setColor(myRed);
                bar.fillRect(greenBarSize, 0, redBarSize, HP_BAR_HEIGHT);
                this.setImage(bar);
                imgone.drawImage(bar,4,3);
                
                heart.scale(20, 20);//scales the heart image
                imgone.drawImage(heart, 0, 0);
                
                imgone.drawString(curHP + " / " + maxHP, 22, 12);
                
                setImage (imgone);
            }
        }
    }
    
    /**
     * Updates health bar to accurately represent the current hit points of the Actor as the scenario/game progresses.
     * 
     * @param newCurHP      New current hit points
     */
    public void update(int newCurHP)
    {
        if (newCurHP != curHP)
        {
            if (newCurHP == maxHP)
            {
                this.setImage(blank);// hides the HP bar if new current hit points is equal to new max hit points
            }
            else
            {
                // Redraw HP bar w/ appropriate amount of green and red based on
                // current HP divided by max HP
                curHP = newCurHP;
                percentHP = (double) curHP / maxHP;// determines how much of the HP bar is filled by the hit points level
                greenBarSize = (int) (percentHP * HP_BAR_WIDTH);//displaysthe hit point level
                redBarSize = HP_BAR_WIDTH - greenBarSize;//replaces the missing hit point level as red
                bar.fillRect(greenBarSize, 0, redBarSize, HP_BAR_HEIGHT);
                this.setImage(bar);
                
                GreenfootImage imgone = new GreenfootImage (HP_BAR_WIDTH + 6, HP_BAR_HEIGHT + 6);
                imgone.setColor(myGray);
                imgone.fill();
                imgone.setColor(myBlack);
                imgone.drawRect(3,2,HP_BAR_WIDTH + 1, HP_BAR_HEIGHT + 1);
                
                
                bar.setColor(myGreen);
                bar.fillRect(0, 0, greenBarSize, HP_BAR_HEIGHT);
                bar.setColor(myRed);
                bar.fillRect(greenBarSize, 0, redBarSize, HP_BAR_HEIGHT);
                this.setImage(bar);
                imgone.drawImage(bar,4,3);
                
                heart.scale(20, 20);
                imgone.drawImage(heart, 0, 0);
                
                imgone.drawString(curHP + " / " + maxHP, 22, 12);
                
                
                setImage (imgone);
            }
        }
    }
}
