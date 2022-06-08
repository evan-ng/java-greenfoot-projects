import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Cook will make food at given intervals. If there is not customer to
 * give the food to, Cook will wait until there is. Cook will display the
 * progress of the "cooking". Images from https://iconscout.com.
 * 
 * @author Amy Cheung
 * @author Evan Ng
 * @version 5 (14/05/2020)
 */
public class Cook extends Actor
{
    private Seat seat;
    
    private int serveSpeed;
    private int serveTime;
    
    private int moveSpeed;
    private boolean readyToCook;
    private boolean leaving;
    
    private boolean foodReady;
    private boolean served;
    
    private boolean paid;
    private int wage;
    
    private Client currentClient;
    private ArrayList<Client> clients;
    
    private HealthBar cookBar;
    private boolean haveBar;
    
    GreenfootImage myImage;
    
    /**
     * Main constructor - Sets all the variables for cook and sets cookSpeed
     * to a default value.
     */
    public Cook()
    {
        myImage = new GreenfootImage("chef.png");
        myImage.scale(45, 95);
        setImage(myImage);
        
        serveSpeed = 30;
        serveTime = serveSpeed;
        
        readyToCook = false;
        leaving = false;
        
        foodReady = false;
        served = false;
        
        paid = false;
        wage = 30;
        
        moveSpeed = 3;
        
        cookBar = new HealthBar(serveSpeed, this);
        haveBar = false;
    }
    
    /**
     * Constructor that takes on int - will change cookSpeed.
     * 
     * @param cookSpeed     How fast the cook can make food.
     */
    public Cook(int cookSpeed)
    {
        this();
        
        serveSpeed = cookSpeed * 10;
        serveTime = serveSpeed;
        
        cookBar = new HealthBar(serveSpeed, this);
        haveBar = false;
    }
    
    /**
     * Act - do whatever the Cook wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        enter();
        leave();
        if (readyToCook)
        {
            cook();
            if (foodReady)
            {
                determineClient();
                feedClient();
            }
        }
        
        if(!haveBar)
        {
            getWorld().addObject (cookBar, getX(), getY() + 20);
            haveBar = true;
        }
        /*
        seats = (ArrayList)getWorld().getObjects(Seat.class);
        //if (timePassed % 10 == 0)
        {
            for (int i = 0; i < 8; i++)
            {
                served = false;
                seat = seats.get(i);
                Client c = (Client)getOneObjectAtOffset(this.getImage().getWidth()  + 1, 0, Client.class);
                if (c != null && c.reachedSeat == true)
                {
                    cook();
                    walkToSeat();
                    serveFood();
                    c.receivedFood = true;
                    walkBack();
                }   
            } 
        }    
        */
    }   
    
    private void cook()
    {
        if (serveTime > 0 && !foodReady && !served && ((SimWorld) getWorld()).getFoodSupply() >= 5)
        {
            serveTime--;
            foodReady = false;
            //System.out.println("serve time: " + serveTime);
            
            cookBar.update(serveSpeed - serveTime);
        }
        else if (serveTime == 0 && !foodReady && !served)
        {
            served = false;
            foodReady = true;
            
            ((SimWorld) getWorld()).loseFood(Greenfoot.getRandomNumber(4) + 1); // Cook uses food.
            
            //System.out.println("Food Ready");
        }
        else if (serveTime == 0 && served)
        {
            served = false;
            foodReady = false;
            serveTime = serveSpeed;
            
            //System.out.println("Served Food");
            
            cookBar.update(serveSpeed - serveTime);
        }
    }
    
    private void determineClient()
    {
        clients = (ArrayList)((SimWorld)getWorld()).getObjects(Client.class);
        if (clients.size() > 0 && foodReady)
        {
            currentClient = null;
            int currentPatience = 99999;
            for (int i = 0; i < ((ArrayList)((SimWorld)getWorld()).getObjects(Client.class)).size(); i++)
            {
                if (clients.get(i).getPatience() < currentPatience && clients.get(i).getPatience() > 0 &&
                    !clients.get(i).getReceivedFood() && clients.get(i).getReachedSeat())
                {
                    currentClient = clients.get(i);
                    currentPatience = clients.get(i).getPatience();
                }
            }
        }
    }
    
    private void feedClient()
    {
        if (currentClient != null && served == false && foodReady == true)
        {
            if (currentClient.getPatience() > 0 && currentClient.getReachedSeat() && !currentClient.getReceivedFood())
            {
                currentClient.setReceivedFood(true, getWorld());
                served = true;
                foodReady = false;
                
                //System.out.println("Client is fed");
            }
        }
    }
    
    private void enter()
    {
        if (!readyToCook && !leaving)
        {
            if (getX() > 580)
            {
                setLocation(getX() - moveSpeed, getY());
                //System.out.println("Entering" + getX());
            }
            else
            {
                readyToCook = true;
                //System.out.println("Done entering: " + getX() + " is less than 550");
            }
        }
    }
    
    private void leave()
    {
        if (!readyToCook && leaving)
        {
            if (getX() < 950)
            {
                setLocation(getX() + moveSpeed, getY());
                //System.out.println("Leaving");
            }
            else
            {
                if (!paid)
                {
                    ((SimWorld)getWorld()).setCurrentFunds(((SimWorld)getWorld()).getCurrentFunds() - wage);
                    paid = true;
                }
                getWorld().removeObject(this);
                //System.out.println("Left");
            }
        }
    }
    
    /**
     * Sets whether or not Cook is ready to make food or not.
     * Cook is not ready if Cook is leaving or entering the restaurant.
     * 
     * @param readyToCook     Whether or not Cook is ready to cook (true is ready to cook)
     */
    public void setReadyToCook(boolean readyToCook)
    {
        this.readyToCook = readyToCook;
    }
    
    /**
     * Sets whether or not Cook is leaving the restaurant
     * 
     * @param leaving     Whether or not Cook is leaving the restaurant (true is leaving)
     */
    public void setLeaving(boolean leaving)
    {
        this.leaving = leaving;
    }
    
    /*private void walkToSeat()//pathways
    {
        if (seat.getSeatNumber() == 0)
        {
            if (getY() != 140)
            {
                setLocation(getX(),getY() - 1);
            }
            if (getY() == 140 && getX() != 745)
            {
                move(-1);
            }
        }
        else if (seat.getSeatNumber() == 1)
        {
            if (getY() > 140)
            {
                setLocation(getX(),getY() - 1);
            }
            if (getY() == 140 && getX() != 580)
            {
                setLocation(getX() - 1, getY());
            }
        }    
        else if (seat.getSeatNumber() == 2)
        {
            if (getY() > 140)
            {
                setLocation(getX(),getY() - 1);
            }
            if (getY() == 140 && getX() != 412)
            {
                setLocation(getX() - 1, getY());
            }
        }    
        else if (seat.getSeatNumber() == 3)
        {
            if (getY() > 140)
            {
                setLocation(getX(),getY() - 1);
            }
            else if (getY() == 140 && getX() > 280)
            {
                setLocation(getX() - 1, getY());
            }
            if (getX() == 280 && getY() < 250)
            {
                setLocation(getX(),getY() + 1);
            }
        } 
        else if (seat.getSeatNumber() == 4)
        {
            if (getY() >= 130)
            {
                setLocation(getX(),getY() - 1);
            }
            if (getX() >= 200)
            {
                setLocation(getX() - 1, getY());
            }
            if (getY() <= 350)
            {
                setLocation(getX(),getY() + 1);
            }
        }    
        else if (seat.getSeatNumber() == 5)
        {
            if (getY() >= 130)
            {
                setLocation(getX(),getY() - 1);
            }
            if (getX() >= 200)
            {
                setLocation(getX() - 1, getY());
            }
            if (getY() <= 460)
            {
                setLocation(getX(),getY() + 1);
            }
        }    
        else if (seat.getSeatNumber() == 6)
        {
            if (getY() <= 550)
            {
                setLocation(getX(),getY() + 1);
            }
            if (getX() >= 412)
            {
                setLocation(getX() - 1, getY());
            }
        }    
        else if (seat.getSeatNumber() == 7)
        {
            if (getY() <= 550)
            {
                setLocation(getX(),getY() + 1);
            }
            if (getX() >= 580)
            {
                setLocation(getX() - 1, getY());
            }
        }    
        else if (seat.getSeatNumber() == 8)
        {
            if (getY() <= 550)
            {
                setLocation(getX(),getY() + 1);
            }
            if (getX() >= 745)
            {
                setLocation(getX() - 1, getY());
            }
        }    
        
    } 
    
    private void walkBack()
    {
        if(seat.getSeatNumber() == 0 || seat.getSeatNumber() == 1 || seat.getSeatNumber() == 2 || seat.getSeatNumber() == 6 || seat.getSeatNumber() == 7 || seat.getSeatNumber() == 8)
        {
            if (getX() != 850)
            {
                move(1);
                if (getY() < 375)
                {
                    setLocation (getX(), getY() + 1);
                } 
                else if (getY() > 375)
                {
                    setLocation (getX(), getY() + 1);
                } 
            }   
        }
        else if(seat.getSeatNumber() == 3 || seat.getSeatNumber() == 4 || seat.getSeatNumber() == 5)
        {
            if(getY() != 140 )
            {
                setLocation (getX(), getY() -1);
            }
            if(getY() == 140 && getX() != 850)
            {
                move(1);
            }
            if(getY() == 140 && getX() == 850)
            {
                if (getY() != 375)
                {
                    setLocation (getX(), getY() +1);
                }   
            }
        }
    }
    
    
    private void serveFood()
    {
        //img of food on table
        served = true;
    }   
    
    private boolean getServe()
    {
        return served;
    }   */
}
