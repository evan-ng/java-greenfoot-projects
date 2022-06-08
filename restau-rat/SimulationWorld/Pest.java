 import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;


/**
 * This is pest. During the day, pest will randomly move around.
 * Pest will bother nearby Clients and will reduce their patience.
 * Staff will hit them with Weapons, and once hit, Pest will die.
 * 
 * @author Evan Ng
 * @author Alex Mar
 * @version 4 (14/05/2020)
 */
public abstract class Pest extends Actor
{
    // Declare Instance Variables related to Speed
    protected int minSpeed;
    protected int maxSpeed;
    protected int changeFrequency;
    protected int xSpeed;
    protected int ySpeed;
    
    // Declare Instance Variables related to Movement Limits
    protected int maxXpos;
    protected int minXpos;
    protected int maxYpos;
    protected int minYpos;
    
    // Declare Instance Variables related to Customer Bothering
    protected int botherRange;
    protected int botherRate;
    protected int count;
    
    // Declare Instance Variables related to Death
    protected boolean dead = false;
    protected double shrinkSpeed;
    protected boolean isTargeted;
    
    // Declare Instance Variables related to Display
    protected int imageWidth;
    protected int imageHeight;
    
    // Declare Instance Images
    protected GreenfootImage image;
    
    /**
     * Changes the speed of Rat at the given changeFrequency. The higher
     * changeFrequency is, the less Rat changes speed/direction.
     * The speed is changed to a random magnitude between minSpeed and maxSpeed.
     */
    protected void changeSpeed()
    {
        // Generates a random number to determine if the Rat changes speed/direction
        // in the x and/or y direction.
        int xChangeChance = Greenfoot.getRandomNumber(changeFrequency);
        int yChangeChance = Greenfoot.getRandomNumber(changeFrequency);

        if (xChangeChance == 0)
        {
            // Sets xSpeed to a random number between minSpeed and maxSpeed.
            xSpeed = Greenfoot.getRandomNumber(maxSpeed - minSpeed) + minSpeed;

            // Determines if xSpeed is negative or positive.
            if (Math.random() < 0.5)
            {
                xSpeed *= -1;
            }
        }
        if (yChangeChance == 0)
        {
            // Sets ySpeed to a random number between minSpeed and maxSpeed.
            ySpeed = Greenfoot.getRandomNumber(maxSpeed - minSpeed) + minSpeed;;

            // Determines if ySpeed is negative or positive.
            if (Math.random() < 0.5)
            {
                ySpeed *= -1;
            }
        }
    }
    
    /**
     * Keeps Pest within the given position limits. If Pest is outside the given
     * position limits, Pest will change direction to avoid the limit.
     */
    protected void stayInLimit()
    {
        if (getX() > maxXpos || getX() < minXpos)
        {
            xSpeed *= -1;
        }
        if (getY() > maxYpos || getY() < minYpos)
        {
            ySpeed *= -1;
        }
        
        // Gets Bug to move out of the limit zone.
        setLocation(getX() + xSpeed, getY() + ySpeed);
    }
    
    /**
     * Pest will decrease customers' satisfaction levels within a certain range.
     */
    protected void botherCustomer()
    {
        List<Client> clients = getObjectsInRange(botherRange, Client.class);
        for(Client c: clients)
            c.setPatience(c.getPatience() - botherRate);
    }
    
    /**
     * Will quickly shrink once Pest is dead. Will delete itself
     * at the end.
     */
    protected abstract void shrinkUntilDelete();
    
    /**
     * Pest will die once this method is called.
     */
    public abstract void die();
    
    /**
     * Return whether Pest is dead.
     * 
     * @return boolean The living state of Pest, true if Pest is dead.
     */
    public boolean getDead()
    {
        return dead;
    }
    
    /**
     * Returns whether Pest is targeted by a Staff 
     * 
     * @return boolean Whether or not pest is targeted by Staff, true if Targeted.
     */
    public boolean getIsTargeted()
    {
        return isTargeted;
    }
    
    /**
     * Sets whether Pest is targeted by a Staff
     * 
     * @param isTargeted Wheter or not pest is targeted by Staff, true if Targeted.
     */
    public void setIsTargeted(boolean isTargeted)
    {
        this.isTargeted = isTargeted;
    }
}
