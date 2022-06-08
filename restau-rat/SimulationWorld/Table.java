
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * A table with seats around it.
 * 
 * @author Raymond 
 * @version 2 (14/05/2020)
 */
public class Table extends Actor
{
    private int RADIUS = 70;
    private int NUM_SEATS = 9;
    private Seat[] seats; 
    
    int xCoord;
    int yCoord;
    
    int width;
    int height;
    
    /**
     * A constructor that takes two ints - determines where
     * the table and seats will be.
     * 
     * @param startX    X position of table
     * @param startY    Y position of table
     */
    public Table(int startX, int startY) 
    {
        GreenfootImage image = new GreenfootImage("table.png"); //getImage();
        
        width = 450;
        height = 300;
        
        image.scale(width, height);
        setImage(image);
        seats = new Seat[NUM_SEATS];
        xCoord = startX;
        yCoord = startY;
        
        createSeats();
    }
    
    /**
     * Adds Seats to the World
     * 
     * @param w The World Table is in.
     */
    public void addedToWorld(World w) {
        w.addObject(this, xCoord, yCoord);
        for (Seat s : seats) {
            s.addToWorld(w);
        }
    }
    
    private void createSeats()
    {
        for (int i = 0; i < NUM_SEATS; i++) {
            int x = xCoord;
            int y = yCoord;
            
            int topY = yCoord - height/2 - 45;
            int middleX = xCoord - width/2 - 45;
            int bottomY = yCoord + height/2 + 45;
            
            if (i == 0) {
                x = xCoord + width/2 - width/6;
                y = topY;
            }
            else if (i == 1) {
                x = xCoord;
                y = topY;
            }
            else if (i == 2) {
                x = xCoord - width/2 + width/6;
                y = topY;
            }
            else if (i == 3) {
                x = middleX;
                y = yCoord - height/2 + height/6;
            }
            else if (i == 4) {
                x = middleX;
                y = yCoord;
            }
            else if (i == 5) {
                x = middleX;
                y = yCoord + height/2 - height/6;
            }
            else if (i == 6) {
                x = xCoord - width/2 + width/6;
                y = bottomY;
            }
            else if (i == 7) {
                x = xCoord;
                y = bottomY;
            }
            else if (i == 8) {
                x = xCoord + width/2 - width/6;
                y = bottomY;
            }
            seats[i] = new Seat(x, y, i);
        }
        
    }
    
    private void createSeats1()
    {
        for (int i = 0; i < NUM_SEATS; i++) {
            int x = xCoord;
            int y = yCoord;
            
            int changeSpread = 84;
            int changeWidth = 325;
            if (i == 0) {
                x += changeSpread;
                y -= changeWidth;
            }
            else if (i == 1) {
                y -= changeWidth;
            }
            else if (i == 2) {
                x -= changeSpread;
                y -= changeWidth;
            }
            else if (i == 3) {
                x -= changeWidth;
                y -= changeSpread;
            }
            else if (i == 4) {
                x -= changeWidth;
            }
            else if (i == 5) {
                x -= changeWidth;
                y += changeSpread;
            }
            else if (i == 6) {
                x -= changeSpread;
                y += changeWidth;
            }
            else if (i == 7) {
                y += changeWidth;
            }
            else if (i == 8) {
                x += changeSpread;
                y += changeWidth;
            }
            seats[i] = new Seat(x, y, i);
        }
    }
    
    /**
     * Returns the Seat of the given seat number.
     * 
     * @param seatNum   Seat number of the desired Seat
     * @return Seat     Seat of the seat number
     */
    public Seat getSeat(int seatNum) {
        return seats[seatNum];
    }
    
    /**
     * Returns the status of the seat of the given seat number.
     * 
     * @param seatNum   Seat number of the desired Seat
     * @return boolean  Status of the Seat, true if a customer has taken the Seat
     */
    public boolean getSeatStatus(int seatNum)
    {
        return seats[seatNum].getIsTaken();
    }
    
    /**
     * Determines whether or not Seat is taken by a Client
     * 
     * @param seatNum   Seat number of the desired Seat
     * @param isTaken   Whether or not Seat is taken by a Client, true if is taken.
     */
    public void setSeatStatus(int seatNum, boolean isTaken)
    {
        seats[seatNum].setIsTaken(isTaken);
    }
    
    /**
     * Returns the number of seats that are on the table
     * 
     * @return int  Number of Seats
     */
    public int getNumSeats()
    {
        return NUM_SEATS;
    }
}