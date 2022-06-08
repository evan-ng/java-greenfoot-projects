import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Critic will not only pay, but will also change the star rating of the restaurant.
 * Critic pays more than Customer. Images from https://iconscout.com.
 * 
 * @author Raymond Zhang
 * @version 2 (14/05/2020)
 */
public class Critic extends Client
{
    private int starsGiven;
    private double paidAmount;
    
    /**
     * Constructor that takes two ints - How long a critic
     * can wait and how fast they can eat
     * 
     * @param patience     How long Critic can wait for food to arrive
     * @param eatSpeed     How fast Critic can eat
     */
    public Critic(int patience, int eatSpeed)
    {
        super(patience, eatSpeed);
        
        image = new GreenfootImage("critic.png");
        image.scale(35, 90);
        setImage(image);
    }
    
    public void act() 
    {
        super.act();
    }
    
    /**
     * Will change star rating upon finishing food. Will also pay for food.
     */
    public void finishMeal()
    {
        if(getSatisfaction() == 1)
        {
            //world.setStarRating(world.getStarRating() - 1);
            ((SimWorld)getWorld()).setStarRating(((SimWorld)getWorld()).getStarRating() - 0.2);
        }
        else if(getSatisfaction() == 2)
        {
            //world.setStarRating(world.getStarRating());
            ((SimWorld)getWorld()).setStarRating(((SimWorld)getWorld()).getStarRating());
        }
        else if(getSatisfaction() == 3)
        {
            //world.setStarRating(world.getStarRating() + 1);
            ((SimWorld)getWorld()).setStarRating(((SimWorld)getWorld()).getStarRating() + 0.2);
        }
        
        paidAmount = (getSatisfaction() * 4) + (((SimWorld)getWorld()).getStarRating() * 6);
        getWorld().addObject(new PriceChange((int)paidAmount, true), getX(), getY() - 30);
        ((SimWorld)getWorld()).setCurrentFunds(((SimWorld)getWorld()).getCurrentFunds() + (int)paidAmount);
        
        //((SimWorld)getWorld()).setStarRating(5);
    }
}