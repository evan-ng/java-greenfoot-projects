import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Health Inspector will enter the restaurant and linger for a while.
 * During this time, Health Inspector will fine the resturant if they see
 * a pest and may reduce the star rating. When Health Inspector spots a
 * rat, they will immediately leave, fine, and reduce star rating by 1.0.
 * Amy completed the majority of this class. Evan made minor adjustments.
 * Images from https://iconscout.com.
 * 
 * @author Amy Cheung
 * @version 3 (12/05/2020)
 */
public class HealthInspector extends Actor
{
    private int rating = 100;
    protected HealthBar ratingBar;

    private int minPests = 1;
    private int maxPests = 10;

    private int pestDetectRange = 200;
    private int ratDetectRange = 250;

    private double ratFindStar = 1.0;
    private boolean foundRat = false;

    private boolean evaluated = false;
    private int timePassed = 0;

    /**
     * Constructor that takes one int - Will determine
     * how lenient/strict the HealthInspector will be.
     * 
     * @param rating    How many pests HealthInspetor can take.
     */
    public HealthInspector (int rating)
    {
        this.rating = rating;
        ratingBar = new HealthBar(rating, this);

        GreenfootImage image = new GreenfootImage("inspector.png");
        image.scale(35, 90);
        setImage(image);
    }

    /**
     * Adds and updates the ratingBar.
     * 
     * @param w The world HealthInspector is in.
     */
    public void addedToWorld (World w)
    {
        w.addObject (ratingBar, getX(), getY());
        ratingBar.update(rating);
    }

    /**
     * Act - do whatever the HealthInspector wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        timePassed++;
        if (!evaluated)
        {
            walkAround();

            if(timePassed % 50 == 0)
                findPests();
        }
        else if (evaluated)
        {
            move(-1);
            if (getX() < 5)
            {
                double deduction;
                if (foundRat)
                {
                    deduction = ratFindStar;
                }
                else
                {// THIS IS THE BIG ISSUE  HERE
                    deduction = 1.0 - 0.1*(int)((int)(rating/10));
                }
                
                setStar(deduction);

                getWorld().removeObject(this);
            }
        }

        if (timePassed > 500)
            evaluated = true;
    }  

    private void walkAround()
    {
        if (getX() < 200)
        {
            move(3);
        }
    }

    /**
     * Gets total amount of Pests and decreases rating and funds accordingly
     */
    private void findPests()
    {
        int totalPests = getWorld().getObjects(Pest.class).size();
        int pestsInRange = ((ArrayList)getObjectsInRange(pestDetectRange, Pest.class)).size();

        int fine = pestsInRange;

        if (totalPests >= maxPests && !evaluated)
        // If there are max pests in the entire restaurant
        {
            rating -= 35;
            ratingBar.update(rating);

            fine = 100-rating;
            ((SimWorld)getWorld()).setCurrentFunds(((SimWorld)getWorld()).getCurrentFunds() - fine);
            getWorld().addObject(new PriceChange(fine, false), getX(), getY() - 30);

            evaluated = true;
        }
        if (((ArrayList)getObjectsInRange(ratDetectRange, Rat.class)).size() > 0 && !evaluated)
        // If a rat is detected within rat detect range
        {
            rating = 0;
            ratingBar.update(rating);

            fine = 50;
            ((SimWorld)getWorld()).setCurrentFunds(((SimWorld)getWorld()).getCurrentFunds() - fine);
            getWorld().addObject(new PriceChange(fine, false), getX(), getY() - 30);

            foundRat = true;
            evaluated = true;
        }
        if (pestsInRange >= minPests && pestsInRange < maxPests && !evaluated) 
        // If there are pests in between min and max within pest detect range
        {   
            if (rating > 0)
            {
                rating -= 5;
                ratingBar.update(rating);
                ((SimWorld)getWorld()).setCurrentFunds(((SimWorld)getWorld()).getCurrentFunds() - fine);

                getWorld().addObject(new PriceChange(fine, false), getX(), getY() - 30);
            }    
        }
    }   

    private void setStar(double deduction)
    {
        if (((SimWorld)getWorld()).getStarRating() - deduction > 0)
            ((SimWorld)getWorld()).setStarRating(((SimWorld)getWorld()).getStarRating() - deduction);
        else
            ((SimWorld)getWorld()).setStarRating(0);
    }

    /**
     * Will have the HealthInspector complete their inspection
     * once this method is called.
     */
    public void exit()
    {
        evaluated = true;
    }
}
