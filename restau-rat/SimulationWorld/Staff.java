import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Staff will move randomly during the day, but when leaving,
 * will follow a determined path. Staff will target Pests and
 * shoot forks at them. Images from https://iconscout.com.
 * 
 * @author Amy 
 * @version 2 (05/05/2020)
 */

public class Staff extends Actor
{
    private int myWidth; 
    private Pest targetPest;
    private ArrayList<Pest> pests;

    private int attackRange = 250;

    // Sets Instance Variables related to Movement Limits
    private int maxXpos = 940;
    private int minXpos = 20;
    private int maxYpos = 600;
    private int minYpos = 120;

    private int xSpeed = 1;
    private int ySpeed = 1;
    private int timePassed = 0;
    
    private int wage = 30;
    private boolean paid = false;
    
    private boolean leaving = false;
    private int leaveStage = 0;
    
    GreenfootImage myImage;

    /**
     * Act - do whatever the Bug wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        timePassed++;
        
        myImage = new GreenfootImage("staff.png");
        myImage.scale(38, 90);
        setImage(myImage);
        
        if (!leaving)
            {
            if (timePassed % 50 == 0){
                targetClosestPest ();
            }
            if (targetPest != null && targetPest.getWorld() != null)
            {
                attackPest();
            }
            // If I can't find anything to attack, move in a random direction
            else
            {
                moveRandomly();
            }
        }
        else
        {
            setRotation(0);
            followPath();
        }
    } 

    /**
     * Method automatically called by Greenfoot when an object of this
     * class is added to the World
     * 
     * @param World This parameter is the World being added to.
     */
    public void addedToWorld (World w)
    {
        targetClosestPest();
    }

    /**
     * Private method, called by act(), that constantly checks for closer targets
     */
    private void targetClosestPest ()
    {
        double closestTargetDist = 10000;  // Changed this to make sure that any bug (in range) will override this number
        double distToActor;
        int totalPests;
        // Get a list of all Pests in the World, cast it to ArrayList
        // for easy management

        totalPests = getWorld().getObjects(Pest.class).size();
        // If any pests are found
        if (totalPests > 50) // If lots of pests are found, search small area
        {
            pests = (ArrayList)getObjectsInRange(80, Pest.class);
        }
        else if (totalPests > 20) // If less pests are found, search wider radius
        {
            pests = (ArrayList)getObjectsInRange(180, Pest.class);
        }
        else    // If even fewer pests are found, search the whole World
        {
            pests = (ArrayList)getWorld().getObjects(Pest.class);
        }

        if (pests.size() > 0)
        {
            /**
            // set the first one as my target
            targetPest = pests.get(0);
            // Use method to get distance to target. This will be used
            // to check if any other targets are closer
            closestTargetDist = SimWorld.getDistance(this, targetPest);   
            // Loop through the objects in the ArrayList to find the closest target
             */
            // Evan made this change ^  makes sure that it will only target IF in range

            for (Pest o : pests)
            {
                // Cast for use in generic method
                Actor a = (Actor) o;
                // Measure distance from me

                distToActor = SimWorld.getDistance(this, a); 

                // If I find a Pest closer than my current target, I will change
                // targets
                if (distToActor < closestTargetDist && distToActor < attackRange) //added this
                {
                    targetPest = o;
                    closestTargetDist = distToActor;
                }
            }
        }
    }

    /**
     * Private method, called by act(), that moves toward and kills the target,
     */
    private void attackPest ()
    {
        turnTowards(targetPest.getX(), targetPest.getY());
        stayInBoundary();
        //targetPest.die();

        if (targetPest.getIsTargeted() == false)
        {
            getWorld().addObject(new Weapon(targetPest), getX(), getY());
            targetPest.setIsTargeted(true);
        }
    }

    /**
     * A method to be used for moving randomly if no target is found. 
     */
    private void moveRandomly ()
    {
        setRotation(0);
        changeSpeed();
        setLocation(getX() + xSpeed, getY() + ySpeed);
        stayInBoundary();

        // Gets Bug to move out of the limit zone.
        //setLocation(getX() + xSpeed, getY() + ySpeed);
    }
    
    /**
     * Changes the speed of Rat at the given changeFrequency. The higher
     * changeFrequency is, the less Rat changes speed/direction.
     * The speed is changed to a random magnitude between minSpeed and maxSpeed.
     */
    protected void changeSpeed()
    {
        // Generates a random number to determine if the Rat changes speed/direction
        // in the x and/or y direction.
        int xChangeChance = Greenfoot.getRandomNumber(100);
        int yChangeChance = Greenfoot.getRandomNumber(100);

        if (xChangeChance == 0)
        {
            // Sets xSpeed to a random number between minSpeed and maxSpeed.
            xSpeed = Greenfoot.getRandomNumber(3 - 0) + 0;

            // Determines if xSpeed is negative or positive.
            if (Math.random() < 0.5)
            {
                xSpeed *= -1;
            }
        }
        if (yChangeChance == 0)
        {
            // Sets ySpeed to a random number between minSpeed and maxSpeed.
            ySpeed = Greenfoot.getRandomNumber(3 - 0) + 0;

            // Determines if ySpeed is negative or positive.
            if (Math.random() < 0.5)
            {
                ySpeed *= -1;
            }
        }
    }

    private void stayInBoundary()
    {
        if (getX() > maxXpos || getX() < minXpos)
        {
            xSpeed *= -1;
        }
        if (getY() > maxYpos || getY() < minYpos)
        {
            ySpeed *= -1;
        }
        
        if (isTouching(Table.class))
        {
            xSpeed *= -1;
            ySpeed *= -1;
            setLocation(getX() + xSpeed, getY() + ySpeed);
        }
    }
    
    /**
     * Will make the Staff leave. Staff are paid a wage.
     */
    public void leave()
    {
        leaving = true;
        
        if (!paid)
        {
            ((SimWorld)getWorld()).setCurrentFunds(((SimWorld)getWorld()).getCurrentFunds() - wage);
            paid = true;
        }
    }
    
    private void followPath()
    {
        xSpeed = 6;
        ySpeed = 6;
        
        if (leaveStage == 0 && getX() > 0 && getX() < 150)
        {
            leaveStage = 2;
        }
        if (leaveStage == 0 && getX() > 150 && getX() < 850)
        {
            leaveStage = 1;
        }
        
        // Go from back of restarant to below table
        if (leaveStage == 0)
        {
            if (getY() < 605)
                setLocation(getX(), getY() + Math.abs(ySpeed));
            else if (Greenfoot.getRandomNumber(2) == 0) // Lets staff vary their paths a bit
                setLocation(getX(), getY() + Math.abs(ySpeed));
            else
                leaveStage = 1;
        }
        
        // Go through bottom path
        if (leaveStage == 1)
        {
            if(getX() < 960 && getX() > 150)
                setLocation(getX() - Math.abs(xSpeed), getY());
            else if (Greenfoot.getRandomNumber(2) == 0) // Lets staff vary their paths a bit
                setLocation(getX() - Math.abs(xSpeed), getY());
            else
                leaveStage = 2;
        }
        
        // Now at the left side of table
        if (leaveStage == 2)
        {
            if (getY() > 380)
                setLocation(getX(), getY() - Math.abs(ySpeed));
            else if (getY() < 340)
                setLocation(getX(), getY() + Math.abs(ySpeed));    
            else if (Greenfoot.getRandomNumber(2) == 0) // Lets staff vary their paths a bit
                setLocation(getX(), getY() - Math.abs(ySpeed));
            else
                leaveStage = 3;
        }
        
        // Leaving Restaurant
        if (leaveStage == 3)
        {
            if (getX() > 5)
            {
                setLocation(getX() - Math.abs(xSpeed), getY());
            }
            else
            {
                getWorld().removeObject(this);
            }
        }
    }
}
