import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Transition here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Transition extends Actor
{
    GreenfootImage image;
    int transparency;
    boolean fadeIn;
    int count = 0;
    
    /**
     * Consturctor that takes one boolean - determines whether or not
     * the transition fades in or fades out
     * 
     * @param fadeIn    Whether or not the Transition fades in or out,
     *                  True if fades in. Will delete itself at the end
     *                  of a fade out.
     */
    public Transition(boolean fadeIn)
    {
        image = new GreenfootImage(1000, 1000);
        this.setImage(image);
        
        this.fadeIn = fadeIn;
        
        image.setColor (new Color (0, 0, 0));
        image.fillRect (0, 0, 1000, 1000);
        
        if (fadeIn)
            transparency = 5;
        else
            transparency = 250;
        
        image.setTransparency(transparency);
    }
    
    /**
     * Act - do whatever the Transition wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //if (count % 2 == 0)
            if (fadeIn && transparency < 255)
                transparency++;
            else if (!fadeIn && transparency > 0)
                transparency -= 2;
        
        image.setTransparency(transparency);
        
        if (!fadeIn && transparency == 0)
            getWorld().removeObject(this);
    }    
}
