import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Seat is where a customer will sit.
 * 
 * @author Raymond 
 * @version 3 (14/05/2020)
 */
public class Seat extends Actor
{
    private int x;
    private int y;
    private boolean isTaken;
    private int seatNumber;
    
    /**
     * Constructor that takes three ints. Determines where
     * the seat is and what number it is.
     * 
     * @param x             X position of Seat
     * @param y             Y position of Seat
     * @param seatNumber    Which seat number this seat is
     */
    public Seat(int x, int y, int seatNumber) 
    {
        this.x = x;
        this.y = y;
        this.isTaken = false;
        this.seatNumber = seatNumber;
        
        GreenfootImage image = getImage();
        image.scale(60, 60);
        setImage(image);
    }
    
    /**
     * Adds this seat to World
     * 
     * @param w The world Seat will be added to.
     */
    public void addToWorld(World w) 
    {
        w.addObject(this, x, y);
    }
    
    /**
     * Returns the seat number
     * 
     * @return int Seat number of Seat
     */
    public int getSeatNumber() 
    {
        return seatNumber;
    }
    
    /**
     * Determines whether or not Seat is taken by a Client
     * 
     * @param isTaken   Whether or not Seat is taken by a Client, true if is taken.
     */
    public void setIsTaken(boolean isTaken)
    {
        this.isTaken = isTaken;
    }
    
    /**
     * Returns whether or not Seat is taken by a Client
     * 
     * @return boolean  Whether or not Seat is taken by a Client, true if is taken.
     */
    public boolean getIsTaken()
    {
        return isTaken;
    }
}