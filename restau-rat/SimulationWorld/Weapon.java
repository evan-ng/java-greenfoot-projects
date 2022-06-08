import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Weapon will hone on a given target Pest. Once hit,
 * both the pest and Weapon will be removed from the World.
 * Will delete itself if Pest is removed before Weapon hits it.
 * 
 * @author Evan Ng
 * @version 1 (12/05/2020)
 */
public class Weapon extends Actor
{
    private int moveSpeed;
    
    private Pest target;
    
    private int width;
    private int height;
    
    /**
     * Consturctor that takes one Pest. This pest will be the target in which
     * the weapon will hone in on and attack.
     * 
     * @param target    Pest that Weapon will attack and kill.
     */
    public Weapon(Pest target)
    {
        width = 75;
        height = 75;
        
        GreenfootImage image = getImage();
        image.scale(width, height);
        image.rotate(90);
        setImage(image);
        
        if (target.getWorld() == null)
        {
            getWorld().removeObject(this);
        }
        
        this.target = target;
        
        moveSpeed = 0;
        
        
        if (target.getWorld() != null)
            turnTowards(target.getX(), target.getY());
    }
    
    /**
     * Act - do whatever the Weapon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        moveSpeed++;
        
        if (target.getWorld() == null)
        {
            getWorld().removeObject(this);
        }
        
        if (target.getWorld() != null)
        {
            turnTowards(target.getX(), target.getY());
            move(moveSpeed);
            
            killPest();
        }
    }    
    
    private void killPest()
    {   
        if (this.intersects(target) == true)
        {
            target.die();
            getWorld().removeObject(this);
        }
    }
}
