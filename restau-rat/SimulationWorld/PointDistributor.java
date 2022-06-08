import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * PointDistributor is a simple Greenfoot Actor that displays a number (points)
 * in a Button. The number can be changed using two arrows placed on the
 * left and right sides of the display. The amount of changer per click
 * can be changed. The display is set such that the points have a maximum
 * and minimum value.
 * 
 * @author Evan Ng
 * @version 1 (23/04/2020)
 */
public class PointDistributor extends Actor
{
    // Declare Instance Variables related to Points
    private int defaultPoints;
    private int currentPoints;
    private int minPoints;
    private int maxPoints;
    private int changeRate;
    private int clicksFromDefault;
    
    // Declare Instance Variables related to Size
    private int width;
    private int height;
    private int cornerRadius;
    private int borderWidth;
    
    private boolean displayAdded;
    
    // Declare Instance Variables related to Text
    private String text;
    private int fontSize;

    // Declare Instance Colors
    private Color backgroundColor;
    private Color borderColor;
    
    // Declare Instance Arrows
    private Arrow leftArrow;
    private Arrow rightArrow;
    
    // Decalre Instance Variables related to Arrows
    private boolean leftLocked;
    private boolean rightLocked;
    
    // Declare Instance Button
    private Button button;
    
    // Declare Instance Image
    private GreenfootImage image;
    
    /**
     * Main constructor - sets all the variables to default values.
     * This is mostly used for testing purposes.
     */
    public PointDistributor()
    {
        defaultPoints = 0;
        currentPoints = defaultPoints;
        minPoints = -10;
        maxPoints = 10;
        changeRate = 2;
        
        clicksFromDefault = 0;
        
        width = 100;
        height = 50;
        cornerRadius = 5;
        borderWidth = 2;
        fontSize = 22;
        
        displayAdded = false;

        // Sets Instance Colours
        backgroundColor = new Color (255, 225, 210);
        borderColor = new Color (210, 160, 140);
        
        leftArrow = new Arrow(20, 12, 1, 1);
        rightArrow = new Arrow(20, 12, 1, 3);
        changeTransparency();
        
        leftLocked = false;
        rightLocked = false;
        
        button = new Button(width, height, cornerRadius, borderWidth, fontSize, backgroundColor, borderColor);
        
        image = button.getImage();
        this.setImage (image);
        
        buildDisplay();
    }
    
    /**
     * Constructor that takes five ints and two Colors - for adjusting size and colors of 
     * the display: width and height of the display, the border, and font size.
     * 
     * @param defaultPoints     Default points upon starting/restarting
     * @param minPoints         Minimum points allowed
     * @param maxPoints         Maximum points allowed
     * @param changeRate        How much each arrow click changes points
     * @param width             Width of display
     * @param height            Height of display
     * @param cornerRadius      Radius of curve on the corners
     * @param borderThickness   Width of border
     * @param fontSize          Size of the text
     * @param backColor         Color of background
     * @param borderColor       Color of border
     */
    public PointDistributor(int defaultPoints, int minPoints, int maxPoints, int changeRate,
                            int width, int height, int cornerRadius, int borderThickness, int fontSize,
                            Color backColor, Color borderColor)
    {
        this();
        
        this.defaultPoints = defaultPoints;
        currentPoints = defaultPoints;
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
        this.changeRate = changeRate;
        
        this.width = width;
        this.height = height;
        this.cornerRadius = cornerRadius;
        this.borderWidth = borderThickness;
        this.fontSize = fontSize;
        
        displayAdded = false;

        // Sets Instance Colours
        backgroundColor = backColor;
        this.borderColor = borderColor;
        
        changeTransparency();
        
        button = new Button(width, height, cornerRadius, borderWidth, fontSize, backgroundColor, borderColor);
        
        image = button.getImage();
        this.setImage (image);
        
        buildDisplay();
    }
    
    private void buildDisplay()
    {
       if(getWorld() != null && displayAdded == false)
        {
            getWorld().addObject(button, getX(), getY());
            getWorld().addObject(leftArrow, getX() - width/2 - 20, getY());
            getWorld().addObject(rightArrow, getX() + width/2 + 20, getY());
        
            button.setText("" + currentPoints);
       }
    }
    
    private void changeTransparency()
    {
        if(currentPoints == minPoints || leftLocked)
        {
            leftArrow.setTransparency(50);
        }
        else
        {
            leftArrow.setTransparency(175);
        }
        
        if (currentPoints == maxPoints || rightLocked)
        {
            rightArrow.setTransparency(50);
        }
        else
        {
            rightArrow.setTransparency(175);
        }
    }
    
    /**
     * Act - do whatever the PointDistributor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        if (mouse != null && mouse.getActor() != null){
            // If the mouse is hovering over the left button and clicks it
            if (mouse.getActor().equals(leftArrow) && Greenfoot.mouseClicked(null))
            {
                if (currentPoints - changeRate >= minPoints && leftLocked == false)
                {
                    currentPoints -= changeRate;
                    clicksFromDefault--;
                }
                changeTransparency();
                button.setText("" + currentPoints);
            }
            else if (mouse.getActor().equals(rightArrow) && Greenfoot.mouseClicked(null))
            {
                if (currentPoints + changeRate <= maxPoints && rightLocked == false)
                {
                    currentPoints += changeRate;
                    clicksFromDefault++;
                }
                changeTransparency();
                button.setText("" + currentPoints);
            }
        }
        
        buildDisplay();
    }    
    
    /**
     * Returns how many points the display is currently on.
     * 
     * @return int Amount of points currently.
     */
    public int getCurrentPoints()
    {
        return currentPoints;
    }
    
    /**
     * Returns how many times were clicked away from default.
     * Ex. If default value was 5, current value is 10, and change rate is 5,
     * 1 is returned.
     * Ex. If default value was 6, current value is 0, and change rate is 2,
     * -3 is returned.
     * 
     * @return int Amount of clicks away from default, 0 means at default.
     */
    public int getClicksFromDefault()
    {
        return clicksFromDefault;
    }
    
    /**
     * Returns the button.
     * 
     * @return int Amount of points currently.
     */
    public Button getButton()
    {
        return button;
    }
    
    /**
     * Locks or unlocks the chosen arrow (left or right)
     * 
     * @param locked    Whether the chosen arrow is locked or unlocked: true is locked,
     *                  false is unlocked
     * @param left      Chosen arrow: true is left arrow, false is right arrow
     */
    public void lockArrow(boolean locked, boolean left)
    {
        if (left)
        {
            leftLocked = locked;
        }
        else
        {
            rightLocked = locked;
        }
        changeTransparency();
    }
    
    /**
     * Returns the left arrow.
     * 
     * @return Arrow Returns the left arrow.
     */
    public Arrow getLArrow()
    {
        return leftArrow;
    }
    
    /**
     * Returns the right arrow.
     * 
     * @return Arrow Returns the right arrow.
     */
    public Arrow getRArrow()
    {
        return rightArrow;
    }
}
