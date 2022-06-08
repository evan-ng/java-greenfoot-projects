import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * StatBar is the stat bar that displays the time, day,
 * current funds, food supply, number of customers, and pests.
 * 
 * @author Evan Ng
 * @version 1 (28/04/2020)
 */
public class StatBar extends Actor
{
    private int currentFunds;
    private int foodSupply;
    private int numberOfCustomers;
    private double rating;
    
    private int day;
    private int time;
    private int dayCycle;
    
    // Declare Instance Colors
    private Color backgroundColor;
    private Color borderColor;
    private Color outsideBorderColor;
    private Color backgroundColorBot;
    private Color borderColorBot;
    private Color outsideBorderColorBot;
    
    private int width;
    private int height;
    private int fullHeight;
    
    private VCENDayCycleDisplay timeDisplay;
    private boolean added = false;
    
    // Declare Instance Images
    private GreenfootImage image;
    private GreenfootImage meat;
    private GreenfootImage person;
    private GreenfootImage star;
    
    /**
     * Main constructor - sets all variables to default values.
     */
    public StatBar()
    {
        currentFunds = 1000000;
        foodSupply = 100;
        numberOfCustomers = 22;
        rating = 3.2;
        
        day = 1;
        time = 0;
        dayCycle = 2400;
        
        // Sets Instance Colours
        backgroundColor = new Color (255, 225, 210);
        borderColor = new Color (210, 160, 140);
        outsideBorderColor = new Color (115, 81, 68);
        
        backgroundColorBot = new Color (210, 255, 248);
        borderColorBot = new Color (140, 210, 195);
        outsideBorderColorBot = new Color (68, 115, 107);
        
        width = 960;
        height = 50;
        fullHeight = 80;
        
        timeDisplay = new VCENDayCycleDisplay​(0, dayCycle, 200, 1400, 1600, 10, height - 4, fullHeight - 4, 0);
        
        // Sets Instance Images
        image = new GreenfootImage (width, fullHeight);
        this.setImage (image);
        
        buildDisplay();
    }
    
    /**
     * Constructor that takes six ints - use to adjust variables from the start
     * 
     * @param time              Time now
     * @param day               Current day
     * @param currentFunds      Current funds of restaurant
     * @param foodSupply        Current food supply in percentage
     * @param numberOfCustomers Current number of customers in restaurant
     * @param rating             Current rating of restaurant
     */
    public StatBar(int time, int day, int currentFunds, int foodSupply, int numberOfCustomers, double rating)
    {
        this();
        
        this.currentFunds = currentFunds;
        this.foodSupply = foodSupply;
        this.numberOfCustomers = numberOfCustomers;
        this.rating = rating;
        
        this.day = day;
        this.time = time;
        
        timeDisplay.update(time);
        
        // Covers the previous texts
        buildTextArea();
        // Rewrites the texts
        addText();
    }
    
    private void buildDisplay()
    {
        // Outside Border
        image.setColor (outsideBorderColor);
        image.fillRect(0, 0, width, height); // Top bar
        image.fillRect(0, 0, height, fullHeight); // Time border
        image.setColor (outsideBorderColorBot);
        image.fillRect(5*width/8, height + 2, 3*width/8 - 2, fullHeight-height); // Bottom bar
        
        // Inside border
        image.setColor (borderColor);
        image.fillRect(height, 2, width/4 - height/2 - 1, height-4); // Top left
        image.fillRect(height/2 + width/4 + 1, 2, width/4 - height/2 - 1, height-4); // Top left
        image.fillRect(width/2 + 2, 2, width/2 - 4, height-4); // Top right (money)
        image.setColor (borderColorBot);
        image.fillRect(5*width/8 + 2, height + 4, width/8-3, fullHeight-height-6); // Bottom left
        image.fillRect(6*width/8 + 1, height + 4, width/8-3, fullHeight-height-6); // Bottom middle
        image.fillRect(7*width/8, height + 4, width/8-4, fullHeight-height-6); // Bottom right
        
        // Covers the previous texts
        buildTextArea();
        // Rewrites the texts
        addText();
        
        addIcons();
    }
    
    private void buildTextArea()
    {
        // Inside border
        image.setColor (backgroundColor);
        image.fillRect(height+2, 4, width/4 - height/2 - 5, height-8); // Top left
        image.fillRect(height/2 + width/4 + 3, 4, width/4 - height/2 - 5, height-8); // Top middle
        image.fillRect(width/2 + 4, 4, width/2 - 8, height-8); // Top right (money)
        image.setColor (backgroundColorBot);
        image.fillRect(5*width/8 + width/16 + 4, height + 6, width/16-7, fullHeight-height-10); // Bottom left
        image.fillRect(6*width/8 + width/16 + 3, height + 6, width/16-7, fullHeight-height-10); // Bottom middle
        image.fillRect(7*width/8 + width/16 + 2, height + 6, width/16-8, fullHeight-height-10); // Bottom right
    }
    
    private void addText()
    {
        // Time text at top left box
        String hour = time/100 + "";
        if(hour.length() < 2)
            hour = "0" + hour;
        String minute = time%100*60/100 + "";
        if(minute.length() < 2)
            minute = "0" + minute;
        GreenfootImage textImageTopLeft = new GreenfootImage(hour + ":" + minute, 42, null, null);
        image.drawImage(textImageTopLeft, 18*height/16 + width/16, 4);
        
        // Day text at top middle box
        GreenfootImage textImageTopMid = new GreenfootImage("Day " + day, 42, null, null);
        image.drawImage(textImageTopMid, 5*height/8 + 5*width/16 + 2, 4);
        
        // Current funds text at top right box
        GreenfootImage textImageTopRight = new GreenfootImage("$ " + currentFunds, 42, null, null);
        image.drawImage(textImageTopRight, width - 15 - textImageTopRight.getWidth(), 4);
        
        // Food supply text at bottom left box
        GreenfootImage textImageBotLeft = new GreenfootImage(foodSupply + "%", 23, null, null);
        image.drawImage(textImageBotLeft, 5*width/8 + width/8 - 5 - textImageBotLeft.getWidth(), height + textImageBotLeft.getHeight()/5);
        
        // Number of customers text at bottom middle box
        GreenfootImage textImageBotMid = new GreenfootImage(numberOfCustomers + "", 23, null, null);
        image.drawImage(textImageBotMid, 6*width/8 + width/8 - 9 - 6*textImageBotMid.getWidth()/7, height + textImageBotLeft.getHeight()/5);
        
        // Rating text at bottom right box
        GreenfootImage textImageBotRight = new GreenfootImage(rating + "", 23, null, null);
        image.drawImage(textImageBotRight, 7*width/8 + width/8 - 13 - 6*textImageBotRight.getWidth()/7, height + textImageBotLeft.getHeight()/5);
    }
    
    private void addIcons()
    {
        // setMeatImage();
        meat = new GreenfootImage("meat.png"); //imports image from folder
        meat.scale(22, 22); //scales new image
        image.drawImage(meat, 4*width/8 + width/8 + 6 + 5*meat.getWidth()/7, height + meat.getHeight()/5 + 1);
        
        // setPersonImage();
        person = new GreenfootImage("man01.png"); //imports image from folder
        person.scale(22, 22); //scales new image
        image.drawImage(person, 5*width/8 + width/8 + 6 + 5*person.getWidth()/7, height + person.getHeight()/5);
        
        // setStarImage();
        star = new GreenfootImage("star.png"); //imports image from folder
        star.scale(22, 22); //scales new image
        image.drawImage(star, 6*width/8 + width/8 + 4 + 5*star.getWidth()/7, height + star.getHeight()/5);
    }
    
    /**
     * Updates the visuals based on the given variables
     * 
     * @param time              Time now
     * @param day               Current day
     * @param currentFunds      Current funds of restaurant
     * @param foodSupply        Current food supply in percentage
     * @param numberOfCustomers Current number of customers in restaurant
     * @param rating            Current rating of restaurant
     */
    public void update (int time, int day, int currentFunds, int foodSupply, int numberOfCustomers, double rating)
    {
        if (this.time != time || this.day != day || this.currentFunds != currentFunds || 
            this.foodSupply != foodSupply || this.numberOfCustomers != numberOfCustomers ||
            this.rating != rating)
        {
            this.time = time;
            this.day = day;
            this.currentFunds = currentFunds;
            this.foodSupply = foodSupply;
            this.numberOfCustomers = numberOfCustomers;
            this.rating = rating;
            
            // Covers the previous texts
            buildTextArea();
            // Rewrites the texts
            addText();
            
            timeDisplay.update(time-500);
        }
    }
    
    public void act()
    {
        if(getWorld() != null && added == false)
        {
            getWorld().addObject(timeDisplay, height/2, fullHeight/2);
            added = true;
        }
    }
}
