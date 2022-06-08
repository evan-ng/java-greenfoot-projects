import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sheep here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sheep extends Actor
{
    
    /**
     * Act - do whatever the Sheep wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Sheep()
    {
        
    }
    
    public void act() 
    {
        // Ends the game once a turtle touches the sheep
        if(isTouching(Turtle.class))
        {
            Greenfoot.setWorld(new EndScreen());
            MyWorld.hold.mark();
            MyWorld.canMakePear.mark();
            MyWorld.canMakeTurtle.mark();
            Pear.setPears(0);
        }
    }    
}
