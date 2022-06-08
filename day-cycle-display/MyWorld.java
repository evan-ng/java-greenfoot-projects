import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @author Vanessa Chiu
 * @author Evan Ng
 * @version 4 (11/3/2020)
 */
public class MyWorld extends World
{
    private VCENDayCycleDisplay dayCycle;
    private VCENDayCycleDisplay dayCycle2;
    private VCENDayCycleDisplay dayCycle3;
    private VCENDayCycleDisplay dayCycle4;
    private VCENDayCycleDisplay dayCycle5;
    
    private int time = 0;
    
    /**
     * Create 3 different display, each start at different time. 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        
        dayCycle = new VCENDayCycleDisplay ();
        addObject (dayCycle, 200, 200);
        
        dayCycle2 = new VCENDayCycleDisplay (45, 300, 0);
        addObject (dayCycle2, 300, 200);
        
        dayCycle3 = new VCENDayCycleDisplay (time + 100, 3000, 1000, 1400, 1900);
        addObject (dayCycle3, 400, 200);
        
        dayCycle4 = new VCENDayCycleDisplay (25, 125, 0);
        addObject (dayCycle4, 480, 200);
        
        dayCycle5 = new VCENDayCycleDisplay (3000, 3600, 300, 1800, 2100, 5, 25, 125, 0, new Color (50, 50, 50), new Color (236, 130, 160), 
        new Color (191, 206, 250), new Color (157, 106, 196), new Color (70, 70, 100), new Color (255, 150, 150), new Color (50, 50, 50));
        addObject (dayCycle5, 120, 200);
    }
    
    /**
     *  How the user can control the time. If presses left key, the time will 
     *  decrease by 5. If user presses right key, the time will increase by 5
     */
    public void act()
    {
        // if press left button, the time will decrease by 5
        if(Greenfoot.isKeyDown("left"))
        {
            time -= 5;
        }   
        // if press right, the time will increase by 5 
        if(Greenfoot.isKeyDown("right"))
        {
            time += 5;
        }
        // update the dayCycleDisplays 
        dayCycle.update(time - 100);
        dayCycle2.update(time);
        dayCycle3.update(time + 100);
        dayCycle4.update(time);
        dayCycle4.update(25, (int)(40*(Math.cos((double)time/400) + 2)), 0);
        dayCycle5.update(time + 3000);
        dayCycle5.update(25, (int)(40*(Math.cos((double)time/400) + 2)), 0);
    }
}
