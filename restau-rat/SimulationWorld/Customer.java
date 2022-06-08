import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Customer is a subclass of Client. Customer will appear in one of four
 * images. After eating, Customer will pay the restaurant. Images from https://iconscout.com.
 * 
 * @author Raymond Zhang
 * @version 3 (14/05/2020)
 */
public class Customer extends Client
{
    private double paidAmount;

    /**
     * Constructor that takes two ints - How long a critic
     * can wait and how fast they can eat
     * 
     * @param patience     How long Critic can wait for food to arrive
     * @param eatSpeed     How fast Critic can eat
     */
    public Customer(int patience, int eatSpeed)
    {
        super(patience, eatSpeed);
        
        int x = Greenfoot.getRandomNumber(4);
        if (x == 0)
        {
            image = new GreenfootImage("customer1.png");
            image.scale(38, 90);
        }
        else if (x == 1)
        {
            image = new GreenfootImage("customer2.png");
            image.scale(32, 90);
        }
        else if (x == 2)
        {
            image = new GreenfootImage("customer3.png");
            image.scale(32, 85);
        }
        else if (x == 3)
        {
            image = new GreenfootImage("customer4.png");
            image.scale(32, 90);
        }
        setImage(image);
    }

    public void act() 
    {
        super.act();
    }

    /**
     * Customer will pay for their meal once they finish it.
     */
    public void finishMeal()
    {
        paidAmount = (getSatisfaction() * 3) + (((SimWorld)getWorld()).getStarRating() * 3);

        getWorld().addObject(new PriceChange((int)paidAmount, true), getX(), getY() - 30);
        
        //System.out.println("Paid: " + paidAmount);

        //world.setMoney(world.getMoney() + paidAmount);
        ((SimWorld)getWorld()).setCurrentFunds(((SimWorld)getWorld()).getCurrentFunds() + (int)paidAmount);
    }
}