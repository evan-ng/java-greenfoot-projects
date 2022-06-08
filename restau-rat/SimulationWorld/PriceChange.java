import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Price change will show how much a price has changed.
 * It will move upwards slowly, while slowly growing more
 * transparent, and delete itself at the end.
 * 
 * @author Evan Ng
 * @version 1 (07/05/2020)
 */
public class PriceChange extends Actor
{
    private int speed;
    private int transparency;
    
    GreenfootImage image;
    
    /**
     * Constructor that takes one int and one boolean.
     * Determines what is displayed in the text.
     * 
     * @param money     How much money is lost or gained.
     * @param positive  Determines if there is a positive or negative sign in front
     *                  of the text, true if positive
     */
    public PriceChange(int money, boolean positive)
    {
        String dollar;
        if (positive)
            dollar = "+ $";
        else
            dollar = "- $";
        
        transparency = 100;
        
        if (money == 0)
            transparency = 0;
            
        image = new GreenfootImage(dollar + money, 20, null, null);
        image.setTransparency(transparency);
        setImage(image);
        
        speed = 1;
    }
    
    /**
     * Act - do whatever the PriceChange wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (transparency % 2 == 0 && transparency > 60)
            setLocation(getX(), getY() - speed);
        else if (transparency % 4 == 0 && transparency > 30)
            setLocation(getX(), getY() - speed);
        else if (transparency % 6 == 0)
            setLocation(getX(), getY() - speed);
            
        image.setTransparency(transparency);
        
        transparency--;
        
        if (transparency <= 0)
        {
            getWorld().removeObject(this);
        }
    }    
}
