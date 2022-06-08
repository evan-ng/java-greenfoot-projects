import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * This is the sim world, where the actual simulation takes place.
 * This simulation is of a restaurant with a table in the middle.
 * In the middle of the table, a cook makes food for customers, who are
 * seated at the top, left, and bottom of the table. These interactions occur
 * during the day. There are also pests that spawn in this simulation. They
 * scurry or fly about, bugging nearby customers, making them more likely to
 * leave without paying. To avoid this, staff are there to kill the pests with
 * homing forks. Health Inspectors also arrive to check the number of pests in
 * the restaurant. If there are too many, or if there are pests nearby, the
 * inspector will fine the restaurant and may reduce the star rating. If the
 * inspector finds a rat nearby, they will immediately leave, fine, and reduce
 * the restaurant's star rating by 1.0. At night, things are a little different.
 * All customers and staff leave. Only pests remain. At 23:00, a security system
 * activates and sends homing forks towards the pests, killing them all instantly.
 * However, rats have taken note of this and decided to invade after the security
 * system activates. There is a backstory to the rats, which can be found after
 * clicking the help button on the starting screen. The help button also describes
 * some other info about the simulation. The rats go to the table and
 * steal food, reducing the food supply. Food supply is one of the 6 stats displayed.
 * One is time, another is day, and one is current money. The food supply, number of
 * customers, and current star rating is also among the 6 stats displayed. Food
 * supply is replenished during the day, at the price of money, of course. Money can
 * change in various ways: customers paying, staff getting wages, and inspectors' fines.
 * All together, the staff, customers, inspectors, and pests work together to make
 * the simulation function.
 * <p>
 * This world takes eight ints from the previous world, PointsWorld,
 * where points are allocated in order to adjust variables to make
 * the simulation different.
 * <p>
 * The simulation has time, which is displayed from 0:00 to 23:59.
 * A widget also displays this in visual form. As time cycles, days pass.
 * After 4 days, the simulation comes to an end, and a score is tallied up based
 * on the initial funds, current funds, and current star rating of the restaurant.
 * Sounds from https://tabletopaudio.com.
 * 
 * @author Amy Cheung
 * @author Alex Mar
 * @author Evan Ng
 * @author Raymond Zhang
 * @version 23 (14/05/2020)
 */
public class SimWorld extends World
{
    // Instance variables related to the restaurant
    private int startingFunds; //Adjustable
    private int currentFunds;
    private int foodSupply;
    private double starRating;

    // Instance variables related to spawn rates
    private int customerSpawnRate; //Adjustable
    private int criticSpawnRate; //Adjustable
    private int inspectorSpawnRate; //Adjustable
    private int ratSpawnRate; //Adjustable
    private int bugSpawnRate; //Adjustable

    // Instance variables related to number of objects
    private int numberOfCustomers;
    private int numberOfPests;

    // Instance variables related to Humans
    private int eatSpeed; //Adjustable
    private int moveSpeed;
    private int cookSpeed; //Adjustable

    // Instanve variables related to food refill
    private int foodRefillTime;
    private int foodRefillCost;
    private int foodRefillAmount;

    // Instance variables related to time
    private int time;
    private int timePassSpeed;
    private int dayLimit;
    private int day;

    // Instance variables related to opening & closing times
    private boolean open;
    private int prepTime;
    private int openingTime;
    private int cleanTime;
    private int closingTime;

    private int transitionTime;

    // Instance variables related to the background
    private GreenfootImage background;
    private Color backColor = new Color(85, 49, 26);
    private GreenfootSound backgroundMusic;

    private StatBar statBar;
    private Button openStatus;
    private Transition transition;

    //Instance vriables related to customer
    private int patience = 100; //default patience level, used for customer's health bar
    Table t1;

    /**
     * Constructor for objects of class SimWorld. Takes in eight ints
     * from the points world such that variables changed there can be
     * used here.
     * 
     * @param startingFunds         Money at the beginning of the simulation
     * @param customerSpawnRate     Rate at which customers spawn (1 is least, 10 is greatest)
     * @param criticSpawnRate       Rate at which critics spawn (1 is least, 10 is greatest)
     * @param inspectorSpawnRate    Rate at which health inspectors spawn (1 is least, 10 is greatest)
     * @param ratSpawnRate          Rate at which rats spawn (1 is least, 10 is greatest)
     * @param bugSpawnRate          Rate at which bugs spawn (1 is least, 10 is greatest)
     * @param eatSpeed              How fast customers eat
     * @param cookSpeed             How fast cooks cook
     */
    public SimWorld(int startingFunds, int customerSpawnRate, int criticSpawnRate, int inspectorSpawnRate,
    int ratSpawnRate, int bugSpawnRate, int eatSpeed, int cookSpeed)
    {    
        // Create a new world with 960x640 cells with a cell size of 1x1 pixels.
        super(960, 640, 1); 

        backgroundMusic = new GreenfootSound("143_Elegant_Dinner_Party.mp3");
        backgroundMusic.playLoop();
        
        this.startingFunds = startingFunds;
        currentFunds = startingFunds;
        foodSupply = 70;
        starRating = 4;

        this.customerSpawnRate = customerSpawnRate;
        this.criticSpawnRate = criticSpawnRate;
        this.inspectorSpawnRate = inspectorSpawnRate;
        this.ratSpawnRate = ratSpawnRate;
        this.bugSpawnRate = bugSpawnRate;

        numberOfCustomers = getObjects(Actor.class).size();
        numberOfPests = 34;

        this.eatSpeed = eatSpeed;
        this.cookSpeed = cookSpeed;

        foodRefillTime = 150;
        foodRefillCost = 8;
        foodRefillAmount = 5;

        time = 500;
        timePassSpeed = 1;
        dayLimit = 5;
        day = 1;

        open = true;
        prepTime = 400;
        openingTime = 500;
        cleanTime = 2000;
        closingTime = 2300;

        transitionTime = 250;

        addBackground();

        statBar = new StatBar(time, day, currentFunds, foodSupply, numberOfCustomers, starRating);
        addObject(statBar, 480, 40);

        openStatus = new Button(100, 28, 0, 2, 16, new Color (255, 225, 210), new Color (115, 81, 68));
        addObject(openStatus, 100, 65);

        addObject(new Staff(), 200, 175);
        addObject(new Staff(), 900, 400);
        
        t1 = new Table(580, 360); //placeholder
    }

    /**
     * Act - do whatever the SimWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();

        // Ensures no errors if mouse is not there
        if (mouse != null){
            // If the mouse is clicks
            if (Greenfoot.mouseClicked(null))
            {
                startEndWorld();
            }
        }

        if (time < closingTime && time > openingTime)
        {
            open = true;
        }
        else
        {
            open = false;
        }

        // Kills all the pests before the RAT INVASION
        if ((time > closingTime - 10 && time < closingTime + 10))
        {
            killAllPests();
            backgroundMusic.stop();
            backgroundMusic = new GreenfootSound("209_Rat_Battle.mp3");
            backgroundMusic.playLoop();
        }

        // If time is greater than 23:59, go to 1:00
        if (time > 2399)
        {
            time-=2400;
            day++;
            transition = new Transition(true);
        }

        // Spawns a customer.
        if (open && time < cleanTime-100)
        {
            spawnCritic(100);
            spawnCustomer(100);
        }
        t1.addedToWorld(this);

        spawnPests();

        if(open)
            spawnHealthInspector(100);
        if(getObjects(HealthInspector.class).size() > 0 && time > cleanTime) //They must leave when closed!
        {
            ArrayList<HealthInspector> inspector = (ArrayList)getObjects(HealthInspector.class);
            for (HealthInspector i : inspector)
            {
                i.exit();
            }
        }
        
        if(getObjects(Cook.class).size() == 0 && day == 1 && time < 1200) //Adds cook.
        {
            addObject(new Cook(11 - cookSpeed), 850, 365);
        }

        // Refills food during the opening time
        if (time > prepTime && time < cleanTime)
            refillFood();

        // Makes time pass
        time += timePassSpeed;

        // Checks how many clients there are and sets the variable accordingly
        if (numberOfCustomers != getObjects(Client.class).size())
            numberOfCustomers = getObjects(Client.class).size();

        // Staff return during Prep Time
        if (time > prepTime && time < openingTime)
            returnStaff();

        if (time > cleanTime && time < closingTime)
            leaveStaff();

        if (time > cleanTime && time < closingTime && getObjects(Staff.class).size() == 0)
            open = false;
            
        // Ensures no food is left on the table
        if (getObjects(Food.class).size() > 0 && time > closingTime)
        {
            ArrayList<Food> food = (ArrayList)getObjects(Food.class);
            for (Food f : food)
            {
                removeObject(f);
            }
        }
        
        // Ensures there are no clients at night
        if (getObjects(Client.class).size() > 0 && time > 2200)
        {
            ArrayList<Client> client = (ArrayList)getObjects(Client.class);
            for (Client c : client)
            {
                removeObject(c);
            }
        }
        
        // Ensures all seats are set to empty
        if (time > 2200)
        {
            ArrayList<Seat> seat = (ArrayList)getObjects(Seat.class);
            for (Seat s : seat)
            {
                s.setIsTaken(false);
            }
        }

        setPaintOrder(Transition.class,Staff.class);
        
        changeBackground();
        
        // Ends sim after a certain number of days have passed
        endSim();

        // Updates the open status of the restaurant
        updateOpenStatus();

        // Updates the stat bar to the most recent numbers
        statBar.update(time, day, currentFunds, foodSupply, numberOfCustomers, starRating);
    }

    private void addBackground()
    {
        background = new GreenfootImage("wood.jpg");
        background.scale(getWidth(), getHeight());
        setBackground(background);
    }
    
    private void changeBackground()
    {
        if (time > prepTime && time < openingTime)
        {
            background = new GreenfootImage("wood.jpg");
            background.scale(getWidth(), getHeight());
            setBackground(background);
        }
        else if ((time > closingTime && time < 2400)||(time > cleanTime && getObjects(Staff.class).size() == 0))
        {
            background = new GreenfootImage("darkwood.jpg");
            background.scale(getWidth(), getHeight());
            setBackground(background);
        }
    }

    private void killAllPests()
    {
        ArrayList<Pest> pests = (ArrayList)getObjects(Pest.class);
        for (Pest p : pests)
        {
            if (p.getIsTargeted() == false)
            {
                addObject(new Weapon(p), 0, getHeight()/2);
                p.setIsTargeted(true);
            } 
        }
    }

    /**
     * Static method that gets the distance between the x,y coordinates of two Actors
     * using Pythagorean Theorum.
     * 
     * @param a     First Actor
     * @param b     Second Actor
     * @return float
     * (author: jordan cohen)
     */
    public static float getDistance (Actor a, Actor b)
    {
        double distance;
        double xLength = a.getX() - b.getX();
        double yLength = a.getY() - b.getY();
        distance = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
        return (float)distance;
    }

    private void updateOpenStatus()
    {
        if (time > closingTime)
            openStatus.setText("Closed   ");
        else if (time > cleanTime && !open)
            openStatus.setText("Closed   ");
        else if (time > cleanTime)
            openStatus.setText("Cleaning    ");
        else if (time > openingTime || open)
            openStatus.setText("Open    ");
        else if (time > prepTime)
            openStatus.setText("Preparing  ");
        else if (time < prepTime)
            openStatus.setText("Closed   ");
    }

    private void startEndWorld()
    {
        backgroundMusic.stop();
        Greenfoot.setWorld(new EndWorld(startingFunds, currentFunds, starRating));
    }

    private void refillFood()
    {
        if (foodRefillTime == 0)
        {
            // Resets the timer
            foodRefillTime = 150;

            // Changes foodRefillAmount by one or none to give a bit of random element
            int refill = foodRefillAmount + Greenfoot.getRandomNumber(2) - 1;

            // Cost to refill food supply
            int cost = 0;

            // Increases food supply for a cost.
            if (foodSupply + refill < 100) // If refilling does not get it to 100%
            {
                foodSupply += refill; // Refills food supply
                cost = foodRefillCost + Greenfoot.getRandomNumber(2) - 1; // Sets cost
                currentFunds -= cost; // Subtracts cost
            }
            else // If refilling gets it to 100% or it is already 100%
            {
                // Gets an average cost for amount of food being refilled
                cost = foodRefillCost*(100-foodSupply)/refill; // Will be 0 if foodSupply is 100

                foodSupply = 100; // Sets to 100% food supply
                currentFunds -= cost; // Subtracts cost
            }

            addObject(new PriceChange (cost, false), 700, 53);
        }
        else
        {
            foodRefillTime--;
        }
    }

    private void returnStaff()
    {
        if(getObjects(Staff.class).size() == 0)
        {
            addObject(new Staff(), 40, 225);
            backgroundMusic.stop();
            backgroundMusic = new GreenfootSound("143_Elegant_Dinner_Party.mp3");
            backgroundMusic.playLoop();
        }
        else if(getObjects(Staff.class).size() == 1)
            addObject(new Staff(), 920, 225);
        if(getObjects(Cook.class).size() == 0)
        {
            Cook cook = new Cook(11 - cookSpeed);
            addObject(cook, 920, 360);
            cook.setReadyToCook(false);
            cook.setLeaving(false);
        }
    }

    private void leaveStaff()
    {
        ArrayList<Staff> staff = (ArrayList)getObjects(Staff.class);
        for (Staff s : staff)
        {
            s.leave();
        }
        
        ArrayList<Cook> cook = (ArrayList)getObjects(Cook.class);
        for (Cook c : cook)
        {
            c.setReadyToCook(false);
            c.setLeaving(true);
        }
    }

    private void spawnCustomer(int patience) {
        if (Greenfoot.getRandomNumber(310 - customerSpawnRate*25) == 0 && open && getObjects(Client.class).size() < 9)
        {
            //Seat s = t1.getSeat(0);
            Seat s = null;

            ArrayList<Integer> seatsAvailable = new ArrayList<Integer>();
            // A test for setting seats.
            for (int i = 0; i < t1.getNumSeats(); i++)
            {
                //System.out.println("loop " + i);

                if (t1.getSeatStatus(i) == false)
                {
                    seatsAvailable.add(i);
                }

                /*
                if (t1.getSeatStatus(i) == false)
                {
                s = t1.getSeat(i); //change for seat number testing
                t1.setSeatStatus(i, true); // Sets the seat to taken status
                System.out.println("break when i = " + i);
                break; // Ends the loop
                }
                 */
            }

            int x = Greenfoot.getRandomNumber(seatsAvailable.size());
            s = t1.getSeat(seatsAvailable.get(x));
            t1.setSeatStatus(seatsAvailable.get(x), true);

            /*
            if(t1.getSeatStatus(0) == false)
            {
            s = t1.getSeat(0);
            }
             */

            Customer customer = new Customer(100, 11 - eatSpeed);

            addObject(customer, 0, 350);
            //customer.addedToWorld(this);
            if(s != null)
            {
                customer.setSeat(s);
            }
        }
    }

    private void spawnCritic(int patience) {
        if (Greenfoot.getRandomNumber(700 - criticSpawnRate*55) == 0 && open && getObjects(Client.class).size() < 9)
        {
            //Seat s = t1.getSeat(0);
            Seat s = null;

            ArrayList<Integer> seatsAvailable = new ArrayList<Integer>();
            // A test for setting seats.
            for (int i = 0; i < t1.getNumSeats(); i++)
            {
                //System.out.println("loop " + i);

                if (t1.getSeatStatus(i) == false)
                {
                    seatsAvailable.add(i);
                }

                /*
                if (t1.getSeatStatus(i) == false)
                {
                s = t1.getSeat(i); //change for seat number testing
                t1.setSeatStatus(i, true); // Sets the seat to taken status
                System.out.println("break when i = " + i);
                break; // Ends the loop
                }
                 */
            }

            int x = Greenfoot.getRandomNumber(seatsAvailable.size());
            s = t1.getSeat(seatsAvailable.get(x));
            t1.setSeatStatus(seatsAvailable.get(x), true);

            /*
            if(t1.getSeatStatus(0) == false)
            {
            s = t1.getSeat(0);
            }
             */

            Critic critic = new Critic(100, 11 - eatSpeed);

            addObject(critic, 0, 350);
            //customer.addedToWorld(this);
            if(s != null)
            {
                critic.setSeat(s);
            }
        }
    }
    
    private void spawnHealthInspector (int rating)
    {
        ArrayList<HealthInspector> inspector = (ArrayList)getObjects(HealthInspector.class);
        if(getObjects(HealthInspector.class).size() < 1)
        {
            if(Greenfoot.getRandomNumber(600 - inspectorSpawnRate*30) == 0 && open)
            {
                addObject(new HealthInspector(100), 0, 360);
            }   
        }
    }

    private void spawnPests()
    {
        if (Greenfoot.getRandomNumber(500 - bugSpawnRate*45) == 0 && open)
        {
            addObject(new Bug(), Greenfoot.getRandomNumber(560) + 200, Greenfoot.getRandomNumber(460) + 120);
        }

        spawnRat();
    }

    private void spawnRat()
    {
        int rate;
        if (open)
            rate = 1000;
        else if (!open && time > cleanTime) // Staff left "early"
            rate = ratSpawnRate*44 + 20;
        else if (time > closingTime || time < prepTime - 250)
            rate = ratSpawnRate*44 + 15;
        else
            rate = 3000;

        if (Greenfoot.getRandomNumber(2*(rate - ratSpawnRate*45)) == 0)
        {
            int wall = Greenfoot.getRandomNumber(3);

            if (wall == 0)
                addObject(new Rat(!open), 30, Greenfoot.getRandomNumber(460) + 130);
            else if (wall == 1)
                addObject(new Rat(!open), Greenfoot.getRandomNumber(560) + 200, getHeight() - 20);
            else if (wall == 2)
                addObject(new Rat(!open), getWidth() - 20, Greenfoot.getRandomNumber(460) + 130);
        }
    }

    private void endSim()
    {
        if (day == dayLimit)
        {
            addObject(transition, getWidth()/2, getHeight()/2);
            transitionTime--;
            if (transitionTime == 0)
            {
                startEndWorld();
            }
        }
    }

    /**
     * Method that reduces the food suppply. For when rats steal food
     * or cook makes food.
     * 
     * @param amount    How much food is lost.
     */
    public void loseFood(int amount)
    {
        if(foodSupply >= amount)
            foodSupply -= amount;
    }

    /**
     * Method that returns the current star rating.
     * 
     * @return double    Current star rating.
     */
    public double getStarRating()
    {
        return starRating;
    }

    /**
     * Method that sets the current star rating
     * 
     * @param starRating    Current star rating
     */
    public void setStarRating(double starRating)
    {
        this.starRating = starRating;
        for (int i=9; i>=3; i--) // This is from danpost from https://www.greenfoot.org/topics/5561
        {
            this.starRating = ((double)Math.round(starRating*Math.pow(10, i)))/Math.pow(10, i);
        }
        if (starRating < 0)
            this.starRating = 0.0;
        if (starRating > 5)
            this.starRating = 5.0;
    }

    /**
     * Method that returns the customer eat speed
     * 
     * @return int    Customer eat speed.
     */
    public int getEatSpeed()
    {
        return eatSpeed;
    }
    
    /**
     * Method that returns the current food supply
     * 
     * @return int      Current food supply
     */
    public int getFoodSupply()
    {
        return foodSupply;
    }
    
    /**
     * Method that returns the current time
     * 
     * @return int    Current time
     */
    public int getTime()
    {
        return time;
    }
    
    /**
     * Method that returns the current funds
     * 
     * @return int    Current funds
     */
    public int getCurrentFunds()
    {
        return currentFunds;
    }

    /**
     * Method that sets the current funds
     * 
     * @param currentFunds    Current funds
     */
    public void setCurrentFunds(int currentFunds)
    {
        this.currentFunds = currentFunds;
    }

    /**
     * Method that sets the status of a seat to is taken
     * or is not taken
     * 
     * @param seatNum       Which seat is being changed
     * @param isTaken       Whether or not a customer has taken the seat (true if is taken)
     */
    public void setSimWorldSeatStatus(int seatNum, boolean isTaken)
    {
        t1.setSeatStatus(seatNum, isTaken);
    }
}