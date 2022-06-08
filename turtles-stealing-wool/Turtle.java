import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Turtle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Turtle extends Actor
{
    int x;
    int y;
    
    /**
     * The constructor for the turtles. Takes two integer values as parameters for the
     * coordinates for the turtles
     */
    public Turtle(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void act() 
    {
        // Moves the turtles towards the sheep
        turnTowards(x, y);
        move(1);
        delete();
    }    
    
    public void delete()
    {
        // If a pear touches the turtle, the pear gets deleted and the turtles 
        // are pushed back away frp, the sheep
        if(isTouching(Pear.class))
        {
            removeTouching(Pear.class);
            move(-150);
            turn(Greenfoot.getRandomNumber(140) - 70);
            move(-90);
            MyWorld.setAddedScore(MyWorld.getAddedScore() + 10);
        }
    }
}
