import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Client is the superclass for critic and client.
 * Client, once spawned, will claim a seat, and walk a
 * determined path towards their seat. Once seated,
 * the Client waits for food. If they wait too long, they
 * leave without paying. If they get their food in time, they
 * eat for as long as eatSpeed determines, pay, and leave in
 * a determined path. Raymond has completed most of this class,
 * Evan has made minor adjustments.
 * 
 * @author Raymond Zhang
 * @version 5 (14/05/2020)
 */
public abstract class Client extends Actor
{
    protected double satisfaction; // How satisfied client is.

    protected HealthBar patienceBar;
    protected int patience = 100;

    protected int tableNumber;
    protected Seat seat;

    protected boolean reachedSeat;
    protected boolean receivedFood;

    protected int serviceSpeed;
    protected int moveSpeed;
    protected int eatSpeed;
    protected int eatTimer;
    
    protected Food food;
    
    protected GreenfootImage image;

    private int count = 0;

    /**
     * Abstract method that is called once the
     * client finishes their meal. (Pay and/or
     * change star rating).
     */
    public abstract void finishMeal();

    /**
     * A constructor that takes two ints - determines
     * how patient a client is and how fast they will eat.
     * 
     * @param patience    How long a client can wait.
     * @param eatSpeed    How fast a client can eat.
     */
    public Client(int patience, int eatSpeed)
    {
        this.patience = patience;
        patienceBar = new HealthBar(this.patience, this);
        reachedSeat = false;
        receivedFood = false;
        
        this.eatSpeed = eatSpeed*10;
        eatTimer = this.eatSpeed;
        
        food = new Food(40);

        moveSpeed = 4;
    }

    /**
     * Act - do whatever the Client wants to do. This method is called whenever the 
     * 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        boolean removeMe = false;
        
        /*
        if(count == 30 && !receivedFood) //SUBJECT TO CHANGE
        {
            setReceivedFood(true, getWorld());
            //System.out.println("count = 300");
        }*/
        if (!reachedSeat && seat != null) {
            findSeat();
            if(reachedSeat)
            {
                addedToWorld(getWorld());
            }
        }
        if (reachedSeat) {
            count++;
            if (!receivedFood) {
                patience--;
                patienceBar.update(patience);
                if (patience <= 0) {
                    leaveStore();
                    removeMe = true;
                }
            }
            else {
                if (eatTimer <= 0) {
                    leaveStore();
                    removeMe = true;
                }
                else {
                    eat();
                }
            }
        }
        if (((SimWorld)getWorld()).getTime() > 2000)
        {
            leaveStore();
            removeMe = true;
            patience = 0;
            eatTimer = 0;
        }
        if (removeMe && atWorldEdge()) {
            seat.setIsTaken(false);
            getWorld().removeObject(this);
        }
    }    

    /**
     * Sets the seat of which the client claims
     * 
     * @param s     The seat the Client will sit at.
     */
    public void setSeat(Seat s) 
    {
        seat = s;
    }
    
    /**
     * Gets the seat of which the client claims
     * 
     * @return Seat     The seat the Client will sit at.
     */
    public Seat getSeat() 
    {
        return seat;
    }

    /**
     * Adds the patience bar to the Client and
     * updates it
     * 
     * @param w     The World Client is in.
     */
    public void addedToWorld (World w)
    {
        w.addObject (patienceBar, getX(), getY());
        patienceBar.update(patience);
        //System.out.println("Add in patience bar");
    }

    /**
     * When called, Client will enter the world by moving.
     */
    public void enterWorld()
    {
        move(1);
    }

    /**
     * When called, the client will eat. Adds food if Client
     * starts eating. Counts down based on eatSpeed and will
     * finish the customer's meal once done.
     */
    public void eat()
    {
        if(eatTimer == eatSpeed)
        {
            addFood();
        }
        eatTimer--;
        if(eatTimer <= 0)
        {
            finishMeal();
            getWorld().removeObject(food);
            leaveStore();
            //System.out.println("leave store");
        }
        //System.out.println("Eat timer: " + eatTimer);
    }

    /**
     * Detects if Client is at the edge of the world.
     */
    public boolean atWorldEdge() 
    {
        /*if (getX() < - (getWidth() / 2) || getX() > getWorld().getWidth() + (myWidth / 2))
        return true;
        else if (getY() < - (myWidth / 2) || getY() > getWorld().getHeight() + (myWidth / 2))
        return true;
        else
        return false;
         */
        if (getX() < 20 || getX() > getWorld().getWidth() - 20)
            return true;
        else if (getY() < 20 || getY() > getWorld().getHeight() - 20)
            return true;
        return false;
    }

    /**
     * The pathway of which client will leave the store.
     * Must be called continuously so Client can follow through.
     */
    public void leaveStore()
    {
        if (getX() > 225)
        {
            setRotation(0);
            move(-moveSpeed);
        }
        else if (getY() < 345)
        {
            setRotation(90);
            move(moveSpeed);
        }
        else if (getY() > 360)
        {
            setRotation(270);
            move(moveSpeed);
        }
        else
        {
            setRotation(0);
            move(-moveSpeed);
        }
    }

    /**
     * Returns the satisfcation of the Client.
     * This value is used to determine how much Client
     * will pay and how much Critic will change the star rating.
     * 
     * @return int     How satisfied client is. 1 is least satisfied. 3 is most.
     */
    public int getSatisfaction()
    {
        if(((SimWorld)getWorld()).getStarRating() == 1 || ((SimWorld)getWorld()).getStarRating() == 2)
        {
            if(patience <= 100 && patience > 66)
            {
                return 3;
            }
            else if(patience <= 66 && patience > 33)
            {
                return 2;
            }
            else
            {   
                return 1;
            }
        }   
        else if(((SimWorld)getWorld()).getStarRating() == 3 || ((SimWorld)getWorld()).getStarRating() == 4)
        {
            if(patience <= 100 && patience > 66)
            {
                return 3;
            }
            else if(patience <= 66 && patience > 33)
            {
                return 2;
            }
            else
            {   
                return 1;
            }
        }   
        else 
        {
            if(patience <= 100 && patience > 66)
            {
                return 3;
            }
            else if(patience <= 66 && patience > 33)
            {
                return 2;
            }
            else
            {   
                return 1;
            }
        }   
    }

    /**
     * Determines whether or not Client received food from the chef.
     * 
     * @param receivedFood     Whether or not Client received food
     * @param w                The World Client is in.
     */
    public void setReceivedFood(boolean receivedFood, World w)
    {
        this.receivedFood = receivedFood;
        w.removeObject(patienceBar);
    }
    
    /**
     * Returns whether or not CLient received food from the chef.
     * 
     * @return boolean  Wheter or not Client received food
     */
    public boolean getReceivedFood()
    {
        return receivedFood;
    }
    
    /**
     * Sets the patience of the Client
     * 
     * @param patience     How much Client can wait.
     */
    public void setPatience(int patience)
    {
        if (patience > 10)
            this.patience = patience;
    }
    
    /**
     * Gets the patience of the Client
     * 
     * @return int  How much Client can wait.
     */
    public int getPatience()
    {
        return patience;
    }
    
    /**
     * Returns whether or not Client reached their seat.
     * 
     * @return boolean Wheter or not Client reached their seat (true if reached the Seat)
     */
    public boolean getReachedSeat()
    {
        return reachedSeat;
    }

    /**
     * Adds a Food object for the customer to "eat"
     */
    public void addFood()
    {
        if (seat.getSeatNumber() == 0 || seat.getSeatNumber() == 1 || seat.getSeatNumber() == 2)
            getWorld().addObject(food, getX(), getY() + 60);
        if (seat.getSeatNumber() == 3 || seat.getSeatNumber() == 4 || seat.getSeatNumber() == 5)
            getWorld().addObject(food, getX() + 70, getY());
        if (seat.getSeatNumber() == 6 || seat.getSeatNumber() == 7 || seat.getSeatNumber() == 8)
            getWorld().addObject(food, getX(), getY() - 70);
    }
    
    /**
     * This is the path the Client must follow to reach their Seat.
     * Must be called continuously for Client to follow through.
     */
    public void findSeat()
    {
        if (seat.getSeatNumber() == 0)
        {
            move(moveSpeed);
            if (getX() >= 175 && getX() <= 180 && getRotation() == 0)
            {
                setRotation(270);
                move(moveSpeed);
                //System.out.println("getX() == 180 && getRotation() == 0");
            }
            else if (getX() >= 735 && getX() <= 740 && getRotation() == 0)
            {
                setRotation(90);
                move(moveSpeed);
                //System.out.println("getX() == 7450");
            }
            if (getY() >= 95 && getY() <= 100 && getRotation() == 270)
            {
                setRotation(0);
                move(moveSpeed);
                //System.out.println("getY() == 100");
            }
            else if(getY() >= 160 && getY() <= 165 && getRotation() == 90)
            {
                setRotation(0);
                //System.out.println("getY() == 140");
                reachedSeat = true;
                setLocation(735, 160);
                //System.out.println("reached seat");
            }
        }
        else if (seat.getSeatNumber() == 1)
        {
            move(moveSpeed);
            if (getX() >= 175 && getX() <= 185 && getRotation() == 0)
            {
                setRotation(270);
                move(moveSpeed);
                //System.out.println("getX() == 90 && getRotation() == 0");
            }
            else if (getX() >= 580 && getX() <= 585 && getRotation() == 0)
            {
                setRotation(90);
                move(moveSpeed);
                //System.out.println("getX() == 300");
            }
            if (getY() >= 100 && getY() <= 110 && getRotation() == 270)
            {
                setRotation(0);
                move(moveSpeed);
                //System.out.println("getY() == 40");
            }
            else if(getY() >= 160 && getY() <= 165 && getRotation() == 90)
            {
                setRotation(0);
                //System.out.println("getY() == 50");
                reachedSeat = true;
                setLocation(580, 160);
            }
        }
        else if (seat.getSeatNumber() == 2)
        {
            move(moveSpeed);
            if (getX() >= 180 && getX() <= 185 && getRotation() == 0)
            {
                setRotation(270);
                move(moveSpeed);
                //System.out.println("getX() == 90 && getRotation() == 0");
            }
            else if (getX() >= 435 && getX() <= 440 && getRotation() == 0)
            {
                setRotation(90);
                move(moveSpeed);
                //System.out.println("getX() == 300");
            }
            if (getY() >= 100 && getY() <= 105 && getRotation() == 270)
            {
                setRotation(0);
                move(moveSpeed);
                //System.out.println("getY() == 40");
            }
            else if(getY() >= 160 && getY() <= 165 && getRotation() == 90)
            {
                setRotation(0);
                //System.out.println("getY() == 50");
                reachedSeat = true;
                setLocation(435, 160);
            }
        }
        else if (seat.getSeatNumber() == 3)
        {
            move(moveSpeed);
            if (getX() >= 180 && getX() <= 185 && getRotation() == 0)
            {
                setRotation(270);
                move(moveSpeed);
                //System.out.println("getX() == 90 && getRotation() == 0");
            }
            else if (getX() >= 310 && getX() <= 315 && getRotation() == 0)
            {
                setRotation(0);
                reachedSeat = true;
                setLocation(310, 250);
            }
            if (getY() >= 250 && getY() <= 255 && getRotation() == 270)
            {
                setRotation(0);
                move(moveSpeed);
                //System.out.println("getY() == 40");
            }
        }
        else if (seat.getSeatNumber() == 4)
        {
            move(moveSpeed);
            if(getX() >= 310 && getX() <= 315)
            {
                reachedSeat = true;
                setLocation(310, 355);
            }
        }
        else if (seat.getSeatNumber() == 5)
        {
            move(moveSpeed);
            if (getX() >= 180 && getX() <= 185 && getRotation() == 0)
            {
                setRotation(90);
                move(moveSpeed);
                //System.out.println("getX() == 90 && getRotation() == 0");
            }
            else if (getX() >= 310 && getX() <= 315 && getRotation() == 0)
            {
                setRotation(0);
                reachedSeat = true;
                setLocation(310, 460);
            }
            if (getY() >= 460 && getY() <= 465 && getRotation() == 90)
            {
                setRotation(0);
                move(moveSpeed);
                //System.out.println("getY() == 40");
            }
        }
        else if (seat.getSeatNumber() == 6)
        {
            move(moveSpeed);
            if (getX() >= 180 && getX() <= 185 && getRotation() == 0)
            {
                setRotation(90);
                move(moveSpeed);
                //System.out.println("getX() == 90 && getRotation() == 0");
            }
            else if (getX() >= 435 && getX() <= 440 && getRotation() == 0)
            {
                setRotation(270);
                move(moveSpeed);
                //System.out.println("getX() == 300");
            }
            if(getY() >= 590 && getY() <= 595 && getRotation() == 90)
            {
                setRotation(0);
                move(moveSpeed);
                //System.out.println("getY() == 50");
            }
            else if (getY() >= 550 && getY() <= 555 && getRotation() == 270)
            {
                setRotation(0);
                reachedSeat = true;
                setLocation(435, 550);
            }
        }
        else if (seat.getSeatNumber() == 7)
        {
            move(moveSpeed);
            if (getX() >= 180 && getX() <= 185 && getRotation() == 0)
            {
                setRotation(90);
                move(moveSpeed);
                //System.out.println("getX() == 90 && getRotation() == 0");
            }
            else if (getX() >= 580 && getX() <= 585 && getRotation() == 0)
            {
                setRotation(270);
                move(moveSpeed);
                //System.out.println("getX() == 300");
            }
            if(getY() >= 590 && getY() <= 595 && getRotation() == 90)
            {
                setRotation(0);
                move(moveSpeed);
                //System.out.println("getY() == 50");
            }
            else if (getY() >= 550 && getY() <= 555 && getRotation() == 270)
            {
                setRotation(0);
                reachedSeat = true;
                setLocation(580, 550);
            }
        }
        else if (seat.getSeatNumber() == 8)
        {
            move(moveSpeed);
            if (getX() >= 180 && getX() <= 185 && getRotation() == 0)
            {
                setRotation(90);
                move(moveSpeed);
                //System.out.println("getX() == 90 && getRotation() == 0");
            }
            else if (getX() >= 725 && getX() <= 730 && getRotation() == 0)
            {
                setRotation(270);
                move(moveSpeed);
                //System.out.println("getX() == 300");
            }
            if(getY() >= 590 && getY() <= 595 && getRotation() == 90)
            {
                setRotation(0);
                move(moveSpeed);
                //System.out.println("getY() == 50");
            }
            else if (getY() >= 550 && getY() <= 555 && getRotation() == 270)
            {
                setRotation(0);
                reachedSeat = true;
                setLocation(725, 550);
            }
        }
    }
}