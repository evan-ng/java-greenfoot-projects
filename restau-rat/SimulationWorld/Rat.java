import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rat appears during the simulation to steal food, and prevent the restaurant
 * from becoming popular and famous. Alex did day time behaviours, Evan did night time.
 * Images from https://hotemoji.com.
 * 
 * @author Alex Mar
 * @version April 24, 2020
 */
public class Rat extends Pest
{
    private boolean facingRight;
    
    private boolean thief;
    
    private boolean topPath;
    private boolean stole;
    private int pathStage;
    
    private Food food;
    
    /**
     * Main constructor - sets all the variables to default values.
     * This rat will move randomly within certain limits.
     */
    public Rat()
    {
        // Sets Instance Variables related to Speed
        minSpeed = 0;
        maxSpeed = 2;
        changeFrequency = 30;
        xSpeed = Greenfoot.getRandomNumber(maxSpeed - minSpeed) + minSpeed;
        ySpeed = Greenfoot.getRandomNumber(maxSpeed - minSpeed) + minSpeed;

        // Sets Instance Variables related to Movement Limits
        maxXpos = 950;
        minXpos = 20;
        maxYpos = 630;
        minYpos = 120;

        // Sets Instance Variables related to Display
        imageWidth = 40;
        facingRight = true;
        
        // Sets Instance Variables related to Customer Bothering
        botherRange = 100;
        botherRate = 2;
        count = 0;
        
        // Sets Instance Variables related to Death
        shrinkSpeed = 1.2;
        isTargeted = false;
        
        thief = false;
        stole = false;
        
        food = null;

        image = getImage();
        image.scale(imageWidth, imageWidth);
        setImage(image);
    }
    
    /**
     * This constructor determines wheter or not the rat will move
     * randomly or if the rat will move along a determined path.
     * 
     * @param thief When true, the rat will go into thief mode and
     *              follow a determined path, ultimately stealing
     *              food. When false, the rat will simply move randomly
     */
    public Rat(boolean thief)
    {
        this();
        this.thief = thief;
        
        pathStage = 0;
        
        // This randomly chooses between the top path or bottom path
        // (above or below the table)
        if(Greenfoot.getRandomNumber(3) == 0)
            topPath = false;
        else
            topPath = true;
    }

    /**
     * Act - do whatever the Rat wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!dead)
        {
            if(!thief)
            {
                count++;
                // Changes xSpeed and ySpeed of Rat
                changeSpeed();
                // Moves Rat xSpeed in the x direction and ySpeed in y direction
                setLocation(getX() + xSpeed, getY() + ySpeed);
                // Keeps Rat in the given limits
                stayInLimit();
                avoidTable();
                
                // Keeps Rat facing the correct direction
                changeFace();
                
                if (count % 5 == 0)
                    botherCustomer();
            }
            else
            {
                // Rat follows a determined path and steals food
                followPath();
            }
        }
        else
        {
            if(food != null)
            {
                getWorld().removeObject(food);
            }
            shrinkUntilDelete();
        }
    }

    private void followPath()
    {
        xSpeed = 6;
        ySpeed = 5;
        
        // If the rat is behind the table, it will not go along
        // most of the path
        if (pathStage == 0 && getX() > 800 && getX() < 960)
        {
            pathStage = 2;
        }
        // If the rat is below the table, it will not go along
        // the beginning of the path
        if (pathStage == 0 && getY() > 590 && getY() < 640)
        {
            pathStage = 1;
        }
        
        // Branch into top path and bottom path
        if (pathStage == 0)
        {
            if(getY() > 135 && topPath)
                setLocation(getX(), getY() - Math.abs(ySpeed));
            else if (getY() < 630 && !topPath)
                setLocation(getX(), getY() + Math.abs(ySpeed));
            else if (Greenfoot.getRandomNumber(2) == 0) // Lets rats vary their paths a bit
                if (topPath)
                    setLocation(getX(), getY() - Math.abs(ySpeed));
                else
                    setLocation(getX(), getY() + Math.abs(ySpeed));
            else
                pathStage = 1;
        }
        
        // Go through top/bottom path
        if (pathStage == 1)
        {
            if(getX() < 850 && getX() > 0)
                setLocation(getX() + Math.abs(xSpeed), getY());
            else if (Greenfoot.getRandomNumber(2) == 0) // Lets rats vary their paths a bit
                setLocation(getX() + Math.abs(xSpeed), getY());
            else
                pathStage = 2;
        }
        
        // Now at the right side of table
        if (pathStage == 2)
        {
            boolean stillMoving = false;
            if (getY() < 640 && getY() > 392)
                stillMoving = true;
            if (getY() > 0 && getY() < 328)
                stillMoving = true;
            else if (Greenfoot.getRandomNumber(2) == 0) // Lets rats vary their paths a bit
                stillMoving = true;
                
            if (stillMoving)
                if (getY() < 360)
                    setLocation(getX(), getY() + Math.abs(ySpeed));
                else
                    setLocation(getX(), getY() - Math.abs(ySpeed));
            else
            {
                pathStage = 3;
                image.mirrorHorizontally();
            }
        }
        
        // Going into the table
        if (pathStage == 3)
        {
            if(getX() < 960 && getX() > 550)
                setLocation(getX() - Math.abs(xSpeed), getY());
            else
            {
                pathStage = 4;
                image.mirrorHorizontally();
                food = new Food();
                if (((SimWorld) getWorld()).getFoodSupply() >= 5)
                    getWorld().addObject(food, getX(), getY() - 12);
                ((SimWorld) getWorld()).loseFood(Greenfoot.getRandomNumber(4) + 1); // Rat steals food.
            }
        }
        
        // Leaving the restaurant
        if (pathStage == 4)
        {
            if (getX() < 950)
            {
                setLocation(getX() + Math.abs(xSpeed), getY());
                food.setLocation(getX(), getY() - 12);
            }
            else
            {
                getWorld().removeObject(food);
                getWorld().removeObject(this);
            }
        }
    }
    
    private void changeFace()
    {
        // Will make rat change the way its facing
        // It is not perfect, but it works well enough.
        boolean right = facingRight;
        if (ySpeed < 0)
        {
            right = false;
        }
        else if (ySpeed > 0)
        {
            right = true;
        }
        
        if (facingRight != right)
        {
            image.mirrorHorizontally();
            facingRight = right;
        }
    }
    
    private void avoidTable()
    {
        // Ensures that rat will not go onto the tables or chairs
        if (isTouching(Table.class) || isTouching(Seat.class))
        {
            xSpeed *= -1;
            ySpeed *= -1;
            setLocation(getX() + xSpeed, getY() + ySpeed);
        }
    }
    
    /**
     * When Rat dies, Rat will shrink until it reaches 1 pixel wide and 1 pixel tall.
     * It then removes itself.
     * The bigger shrinkSpeed is, the faster Rat will shrink. However, shrinkSpeed 
     * must be greater than 1.
     */
    protected void shrinkUntilDelete()
    {
        if (dead)
        {
            if (imageWidth > 1 && imageHeight > 1)
            {
                imageWidth /= shrinkSpeed;

                image = getImage();
                image.scale(imageWidth, imageWidth);
                setImage(image);
            }
            else
            {
                getWorld().removeObject(this);
            }
        }
    }

    /**
     * Rat will start dying. It stops moving, shrinks, and deletes itself. Before
     * shrinking, it grows to 1.5 times its size for an instant for a nicer death
     * animation (using a fundamental animation principle: "anticipation").
     */
    public void die()
    {
        dead = true;

        // Rat stops moving when dead.
        xSpeed = 0;
        ySpeed = 0;

        // The following sets Rat up for the death animation.
        // Increases the size of the image
        imageWidth *= 1.5;
        imageHeight *= 1.5;
        
        image = getImage();
        image.scale(imageWidth, imageWidth);
        this.setImage (image);
    }
}