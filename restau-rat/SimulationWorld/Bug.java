import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Bug is a Greenfoot Actor that moves around the screen.
 * Bug flies randomly within a given boundary within a given speed miniumum and limit.
 * It also reduces nearby customers' satisfaction.
 * 
 * @author Evan Ng 
 * @version 1.3 (15/03/2020)
 */
public class Bug extends Pest
{
    /**
     * Main constructor - sets all the variables to default values.
     */
    public Bug()
    {
        // Sets Instance Variables related to Speed
        minSpeed = 0;
        maxSpeed = 4;
        changeFrequency = 30;
        xSpeed = Greenfoot.getRandomNumber(maxSpeed - minSpeed) + minSpeed;
        ySpeed = Greenfoot.getRandomNumber(maxSpeed - minSpeed) + minSpeed;
        
        // Sets Instance Variables related to Movement Limits
        maxXpos = 940;
        minXpos = 100;
        maxYpos = 600;
        minYpos = 120;
        
        // Sets Instance Variables related to Display
        imageWidth = 12;
        imageHeight = 12;
        
        // Sets Instance Variables related to Customer Bothering
        botherRange = 100;
        botherRate = 1;
        count = 0;
        
        // Sets Instance Variables related to Death
        shrinkSpeed = 1.2;
        isTargeted = false;
        
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);
        
        // Sets the image to a dark circle
        image.setColor (new Color (20, 20, 50));
        image.fillOval (0, 0, imageWidth, imageHeight);
    }
    
    /**
     * Act - do whatever the Bug wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (!dead)
        {
            count++;
            // Changes xSpeed and ySpeed of Bug
            changeSpeed();
            // Moves Bug xSpeed in the x direction and ySpeed in y direction
            setLocation(getX() + xSpeed, getY() + ySpeed);
            // Keeps Bug in the given limits
            stayInLimit();
            if (count % 5 == 0)
                botherCustomer();
        }
        else
        {
            shrinkUntilDelete();
        }
    }    
    
    /**
     * When Bug dies, Bug will shrink until it reaches 1 pixel wide and 1 pixel tall.
     * It then removes itself.
     * The bigger shrinkSpeed is, the faster Bug will shrink. However, shrinkSpeed 
     * must be greater than 1.
     */
    protected void shrinkUntilDelete()
    {
        if (dead)
        {
            if (imageWidth > 1 && imageHeight > 1)
            {
                imageWidth /= shrinkSpeed;
                imageHeight /= shrinkSpeed;
                
                image = new GreenfootImage (imageWidth, imageHeight);
                this.setImage (image);
                // Adds the circle again to update the display
                image.setColor (new Color (50, 50, 100));
                image.fillOval (0, 0, imageWidth, imageHeight);
            }
            else
            {
                getWorld().removeObject(this);
            }
        }
    }
    
    /**
     * Bug will start dying. It stops moving, shrinks, and deletes itself. Before
     * shrinking, it grows to 1.5 times its size for an instant for a nicer death
     * animation (using a fundamental animation principle: "anticipation").
     */
    public void die()
    {
        if (!dead)
        {
            dead = true;
            
            // Bug stops moving when dead.
            xSpeed = 0;
            ySpeed = 0;
            
            // The following sets Bug up for the death animation.
            // Increases the size of the image
            imageWidth *= 1.5;
            imageHeight *= 1.5;
            image = new GreenfootImage (imageWidth, imageHeight);
            this.setImage (image);
            // Adds the circle again to update the display
            image.setColor (new Color (50, 50, 100));
            image.fillOval (0, 0, imageWidth, imageHeight);
        }
    }
}