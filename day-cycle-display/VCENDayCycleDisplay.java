import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
    
/**
 * VCENDayCycleDisplay is a Greenfoot Actor that displays time.
 * The time is displayed through visuals in a rectangular box:
 * a sun rises and sets, and a moon does the same within it.
 * <p>
 * The time is split into four stages: dawn, day, dusk, and night.
 * Altogether, the four stages make one cycle.
 * <br>
 * The default length a cycle is 2400. This number, however, is
 * completely arbitrary, as one can have time increase by 1, 50, or 1000
 * every second, thus changing the length of time it takes to pass
 * one cycle.
 * <p>
 * When updating the display, one can input a time to update the display. 
 * This value can be any integer.
 * The class determines which stage (dawn, day, dusk, night)
 * the time belongs to and adjusts the visuals accordingly. 
 * <br>
 * Example: with a cycle of 2400, and a given time of 4700, it will be night.
 * <br>
 * The given time is not like time on a clock which cycles from 12:00am to 11:59pm.
 * Rather, the given time can be thought of as time passed from the start.
 * <p>
 * The default size of the display box is 40x200 pixels, but the display box
 * can be any size other than 0x0 pixels.
 * <p>
 * Each time stage is depicted through different background colours,
 * which can be customized. The default colours are pink, blue, purple, 
 * and (very) dark blue for dawn, day, dusk, and night respectively.
 * 
 * @author Vanessa Chiu
 * @author Evan Ng
 * @version 4 (11/3/2020)
 */
public class VCENDayCycleDisplay extends Actor
{
    // Declare Instance Variables related to Time
    private int oneCycleTime;
    private int dawnTime = 0;
    private int dayTime;
    private int duskTime;
    private int nightTime;
    private int givenTime;
    private int currTime;
    
    // Declare Instance Variables related to Display
    private int barWidth;
    private int barHeight;
    private int borderWidth;
    
    // Declare Instance Color objects
    private Color dawnColor;
    private Color dayColor;
    private Color duskColor;
    private Color nightColor;
    private Color currColor;
    private Color fadeColor;
    private Color borderColor;
    private Color sunColor;
    private Color sunshineColor;
    private Color moonColor;
    private Color moonShineColor;
        
    // Declare Instance Variables for Star Position
    private int numberStars;
    private int[] starPosX;
    private int[] starPosY;
    
    // Declare Instance Images
    private GreenfootImage bar;
     
    /**
     * Main constructor - sets all the variables to the default values.
     */
    public VCENDayCycleDisplay()
    {
        // Sets Instance Variables related to Time
        oneCycleTime = 2400;
        dawnTime = 0;
        dayTime = 250;
        duskTime = 1150;
        nightTime = 1400;
        givenTime = 0;
        currTime = givenTime;
    
        // Sets Instance Variables related to Display
        barWidth = 40;
        barHeight = 200;
        borderWidth = 1;
    
        // Sets Instance Color objects
        dawnColor = new Color (255, 159, 138);
        dayColor = new Color (156, 219, 250);
        duskColor = new Color (175, 115, 180);
        nightColor = new Color (48, 38, 88);
        borderColor = new Color (50, 50, 50);
        sunColor = new Color (255, 220, 180);
        moonColor = new Color (247, 247, 255, 255);
        
        // Sets Instance Variables related to Stars
        numberStars = 15;
        starPosX = new int [numberStars];
        starPosY = new int [numberStars];
        
        // Sets the image to GreenfootImage bar
        bar = new GreenfootImage(barWidth, barHeight);
        this.setImage(bar);
        
        // Adds the visuals
        makeStarPosition();
        updateVisuals();
    }
    
    /**
     * Constructor that takes one int - for when time does not start at 0, dawn.
     * 
     * @param timeNow Current time
     */
    public VCENDayCycleDisplay(int timeNow)
    {
        this();
        
        // Sets the current time to the given time
        givenTime = timeNow;
        currTime = givenTime;
        
        // Ensures that the times that this class uses stays within the 
        // timeframe of between 0 and oneCycleTime
        adjustTime();
        
        // Adds the visuals
        makeStarPosition();
        updateVisuals();
    }
    
    /**
     * Constructor that takes three ints - changes the
     * size of the display and the border on it.
     * 
     * @param width             Width of display bar, must be greater than 0
     * @param height            Height of display bar, must be greater than 0
     * @param borderThickness   Width of border, cannot be negative
     */
    public VCENDayCycleDisplay(int width, int height, int borderThickness)
    {
        this();
        
        // Sets Instance Variables related to Display
        barWidth = width;
        barHeight = height;
        borderWidth = borderThickness;
        
        // Sets the image to GreenfootImage bar
        bar = new GreenfootImage(barWidth, barHeight);
        this.setImage(bar);
        
        // Adds the visuals
        makeStarPosition();
        updateVisuals();
    }
    
    /**
     * Constructor that takes five ints - changes the length of a day and
     * adjusts when the day, dusk, or night starts.
     * <br>
     * Dawn is set to 0 (dawn always starts the day cycle)
     * <p>
     * Warning: setting dayTimeStart to 0 will remove dawn. However, this 
     * is not recommended, because the visuals look less appealing.
     * Similarily, setting duskTimeStart to dayTimeStart removes day, 
     * nightTimeStart to duskTimeStart removes dusk, and nightTimeStart 
     * to oneDayLength removes night. These are all not recommended actions.
     * 
     * @param timeNow           Time now
     * @param oneDayLength      Length of a day (cycle), cannot be negative
     * @param dayTimeStart      Time when day starts, must be between 0 and oneDayLength
     * @param duskTimeStart     Time when dusk starts, must be between dayTimeStart and oneDayLength
     * @param nightTimeStart    Time when night starts, must be between duskTimeStart and oneDayLength
     */
    public VCENDayCycleDisplay(int timeNow, int oneDayLength, int dayTimeStart, int duskTimeStart, int nightTimeStart)
    {
        this();
        
        // Sets the current time to the given time
        oneCycleTime = oneDayLength;
        dawnTime = 0;
        dayTime = dayTimeStart;
        duskTime = duskTimeStart;
        nightTime = nightTimeStart;
        givenTime = timeNow;
        currTime = givenTime;
        
        // Ensures that the times that this class uses stays within the 
        // timeframe of between 0 and oneCycleTime
        adjustTime();
        
        // Adds the visuals
        makeStarPosition();
        updateVisuals();
    }
    
    /**
     * Constructor that takes nine ints - they determine at what time the display
     * starts, adjust the length of a day, when day, dusk, and night starts, and
     * the size of the display the border.
     * <p>
     * Dawn is set to 0 (dawn always starts the day cycle).
     * <p>
     * Warning: setting dayTimeStart to 0 will remove dawn. However, this 
     * is not recommended, because the visuals look less appealing.
     * Similarily, setting duskTimeStart to dayTimeStart removes day, 
     * nightTimeStart to duskTimeStart removes dusk, and nightTimeStart 
     * to oneDayLength removes night. These are all not recommended actions.
     * 
     * @param timeNow           Time now
     * @param oneDayLength      Length of a day (cycle), cannot be negative
     * @param dayTimeStart      Time when day starts, must be between 0 and oneDayLength
     * @param duskTimeStart     Time when dusk starts, must be between dayTimeStart and oneDayLength
     * @param nightTimeStart    Time when night starts, must be between duskTimeStart and oneDayLength
     * @param numberOfStars     Number of stars that appear at night, cannot be negative
     * @param width             Width of display bar, must be greater than 0
     * @param height            Height of display bar, must be greater than 0
     * @param borderThickness   Width of border, cannot be negative
     */
    public VCENDayCycleDisplay(int timeNow, int oneDayLength, int dayTimeStart, int duskTimeStart, int nightTimeStart,
                               int numberOfStars, int width, int height, int borderThickness)
    {
        this();
        
        // Sets the times to the given times
        oneCycleTime = oneDayLength;
        dawnTime = 0;
        dayTime = dayTimeStart;
        duskTime = duskTimeStart;
        nightTime = nightTimeStart;
        givenTime = timeNow;
        currTime = givenTime;
        
        // Sets Instance Variables related to Display
        barWidth = width;
        barHeight = height;
        borderWidth = borderThickness;
        
        // Sets Instance Variables related to Stars
        numberStars = numberOfStars;
        starPosX = new int [numberStars];
        starPosY = new int [numberStars];
        
        // Sets the image to GreenfootImage bar
        bar = new GreenfootImage(barWidth, barHeight);
        this.setImage(bar);
        
        // Ensures that the times that this class uses stays within the 
        // timeframe of between 0 and oneCycleTime
        adjustTime();
        
        // Adds the visuals
        makeStarPosition();
        updateVisuals();
    }
    
    /**
     * Constructor that takes six Colors - for adjusting the colors of the sky,
     * the moon, and the sun.
     * <p>
     * Alpha values of the color parameters are ignored to ensure the best visuals.
     * <br>
     * The color of the stars are the same as the color of the moon.
     * <br>
     * Setting ColorDawn to ColorDay will make dawn appear to be day,
     * effectively removing it.
     * 
     * @param colorDawn         Color of sky at dawn
     * @param colorDay          Color of sky at day
     * @param colorDusk         Color of sky at dusk
     * @param colorNight        Color of sky at night
     * @param colorSun          Color of sun
     * @param colorMoon         Color of moon
     */
    public VCENDayCycleDisplay(Color colorDawn, Color colorDay, Color colorDusk, Color colorNight, Color colorSun, Color colorMoon)
    {
        this();
        
        // Sets Instance Color objects
        // Alpha values are all set to 255.
        dawnColor = new Color (colorDawn.getRed(), colorDawn.getGreen(), colorDawn.getBlue(), 255);
        dayColor = new Color (colorDay.getRed(), colorDay.getGreen(), colorDay.getBlue(), 255);
        duskColor = new Color (colorDusk.getRed(), colorDusk.getGreen(), colorDusk.getBlue(), 255);
        nightColor = new Color (colorNight.getRed(), colorNight.getGreen(), colorNight.getBlue(), 255);
        sunColor = new Color (colorSun.getRed(), colorSun.getGreen(), colorSun.getBlue(), 255);
        moonColor = new Color (colorMoon.getRed(), colorMoon.getGreen(), colorMoon.getBlue(), 255);
        
        // Adds the visuals
        makeStarPosition();
        updateVisuals();
    }
    
    /**
     * Constructor that takes four ints and seven Colors - for adjusting size and 
     * colors of the display: width and height of the display, 
     * size and color of the border, colors of the sky, the moon, and the sun.
     * <p>
     * Alpha values of the color parameters are ignored to ensure the best visuals.
     * <br>
     * The color of the stars are the same as the color of the moon.
     * <br>
     * Setting ColorDawn to ColorDay will make dawn appear to be day,
     * effectively removing it.
     * 
     * @param numberOfStars     Number of stars that appear at night, cannot be negative
     * @param width             Width of display bar, must be greater than 0
     * @param height            Height of display bar, must be greater than 0
     * @param borderThickness   Width of border, cannot be negative
     * @param colorBorder       Color of border
     * @param colorDawn         Color of sky at dawn
     * @param colorDay          Color of sky at day
     * @param colorDusk         Color of sky at dusk
     * @param colorNight        Color of sky at night
     * @param colorSun          Color of sun
     * @param colorMoon         Color of moon
     */
    public VCENDayCycleDisplay(int numberOfStars, int width, int height, int borderThickness,
                               Color colorBorder, Color colorDawn, Color colorDay, Color colorDusk, Color colorNight, 
                               Color colorSun, Color colorMoon)
    {
        this();
        
        // Sets Instance Variables related to Display
        barWidth = width;
        barHeight = height;
        borderWidth = borderThickness;
        
        // Sets Instance Color objects
        // Alpha values are all set to 255.
        borderColor = new Color (colorBorder.getRed(), colorBorder.getGreen(), colorBorder.getBlue(), 255);
        dawnColor = new Color (colorDawn.getRed(), colorDawn.getGreen(), colorDawn.getBlue(), 255);
        dayColor = new Color (colorDay.getRed(), colorDay.getGreen(), colorDay.getBlue(), 255);
        duskColor = new Color (colorDusk.getRed(), colorDusk.getGreen(), colorDusk.getBlue(), 255);
        nightColor = new Color (colorNight.getRed(), colorNight.getGreen(), colorNight.getBlue(), 255);
        sunColor = new Color (colorSun.getRed(), colorSun.getGreen(), colorSun.getBlue(), 255);
        moonColor = new Color (colorMoon.getRed(), colorMoon.getGreen(), colorMoon.getBlue(), 255);
        
        // Sets Instance Variables related to Stars
        numberStars = numberOfStars;
        starPosX = new int [numberStars];
        starPosY = new int [numberStars];
        
        // Sets the image to GreenfootImage bar
        bar = new GreenfootImage(barWidth, barHeight);
        this.setImage(bar);
        
        // Adds the visuals
        makeStarPosition();
        updateVisuals();
    }
    
    /**
     * Constructor that takes nine ints and seven colors - they determine at what time the display
     * starts, adjust the length of a day, when day, dusk, and night starts, 
     * the size of the display as well as the size of the border, 
     * and the colors of the border, sky, sun, and moon.
     * <p>
     * Dawn is set to 0 (dawn always starts the day).
     * <br>
     * Alpha values of the color parameters are ignored to ensure the best visuals.
     * <br>
     * The color of the stars are the same as the color of the moon
     * <p>
     * Warning: setting dayTimeStart to 0 will remove dawn. However, this 
     * is not recommended, because the visuals look less appealing.
     * Similarily, setting duskTimeStart to dayTimeStart removes day, 
     * nightTimeStart to duskTimeStart removes dusk, and nightTimeStart 
     * to oneDayLength removes night. These are all not recommended actions.
     * <br>
     * Instead, setting ColorDawn to ColorDay will make dawn appear to be day,
     * effectively removing it. This is the recommended method to removing a time
     * range.
     * 
     * @param timeNow           Time now
     * @param oneDayLength      Length of a day (cycle), cannot be negative
     * @param dayTimeStart      Time when day starts, must be between 0 and oneDayLength
     * @param duskTimeStart     Time when dusk starts, must be between dayTimeStart and oneDayLength
     * @param nightTimeStart    Time when night starts, must be between duskTimeStart and oneDayLength
     * @param numberOfStars     Number of stars that appear at night, cannot be negative
     * @param width             Width of display bar, must be greater than 0
     * @param height            Height of display bar, must be greater than 0
     * @param borderThickness   Width of border, cannot be negative
     * @param colorBorder       Color of border
     * @param colorDawn         Color of sky at dawn
     * @param colorDay          Color of sky at day
     * @param colorDusk         Color of sky at dusk
     * @param colorNight        Color of sky at night
     * @param colorSun          Color of sun
     * @param colorMoon         Color of moon
     */
    public VCENDayCycleDisplay(int timeNow, int oneDayLength, int dayTimeStart, int duskTimeStart, int nightTimeStart,
                               int numberOfStars, int width, int height, int borderThickness, 
                               Color colorBorder, Color colorDawn, Color colorDay, Color colorDusk, Color colorNight,
                               Color colorSun, Color colorMoon)
    {
        // Sets the times to the given times
        oneCycleTime = oneDayLength;
        dawnTime = 0;
        dayTime = dayTimeStart;
        duskTime = duskTimeStart;
        nightTime = nightTimeStart;
        givenTime = timeNow;
        currTime = givenTime;
        
        // Sets Instance Variables related to Display
        barWidth = width;
        barHeight = height;
        borderWidth = borderThickness;
        
        // Sets Instance Color objects
        // Alpha values are all set to 255.
        borderColor = new Color (colorBorder.getRed(), colorBorder.getGreen(), colorBorder.getBlue(), 255);
        dawnColor = new Color (colorDawn.getRed(), colorDawn.getGreen(), colorDawn.getBlue(), 255);
        dayColor = new Color (colorDay.getRed(), colorDay.getGreen(), colorDay.getBlue(), 255);
        duskColor = new Color (colorDusk.getRed(), colorDusk.getGreen(), colorDusk.getBlue(), 255);
        nightColor = new Color (colorNight.getRed(), colorNight.getGreen(), colorNight.getBlue(), 255);
        sunColor = new Color (colorSun.getRed(), colorSun.getGreen(), colorSun.getBlue(), 255);
        moonColor = new Color (colorMoon.getRed(), colorMoon.getGreen(), colorMoon.getBlue(), 255);
        
        // Sets Instance Variables related to Stars
        numberStars = numberOfStars;
        starPosX = new int [numberStars];
        starPosY = new int [numberStars];
        
        // Sets the image to GreenfootImage bar
        bar = new GreenfootImage(barWidth, barHeight);
        this.setImage(bar);
        
        // Ensures that the times that this class uses stays within the 
        // timeframe of between 0 and oneCycleTime
        adjustTime();
        
        // Adds the visuals
        makeStarPosition();
        updateVisuals();
    }
    
    /**
     * Updates the visuals based on the given time. Will do nothing if 
     * the given time is the same as the previously given time.
     * 
     * @param time  Time now
     */
    public void update(int time)
    {
        if (givenTime != time)
        {
            givenTime = time;
            currTime = givenTime;
            
            // Ensures that the time the class uses fits within the
            // 0 to oneCycleTime timeframe.
            adjustTime();
            
            // Updates the visuals
            updateVisuals();
        }
    }
    
    /**
     * Updates the visuals based on the given time and 
     * changes the colors of the sky, moon, and sun.
     * 
     * @param time          Time now
     * @param colorDawn     Color of sky at dawn
     * @param colorDay      Color of sky at day
     * @param colorDusk     Color of sky at dusk
     * @param colorNight    Color of sky at night
     * @param colorSun      Color of sun
     * @param colorMoon     Color of moon
     */
    public void update(int time, Color colorDawn, Color colorDay, Color colorDusk, Color colorNight,
                       Color colorSun, Color colorMoon)
    {
        if (givenTime != time)
        {
            givenTime = time;
            currTime = givenTime;
            
            adjustTime();
        }
        
        // Updates Instance Color objects
        // Alpha values are all set to 255.
        dawnColor = new Color (colorDawn.getRed(), colorDawn.getGreen(), colorDawn.getBlue(), 255);
        dayColor = new Color (colorDay.getRed(), colorDay.getGreen(), colorDay.getBlue(), 255);
        duskColor = new Color (colorDusk.getRed(), colorDusk.getGreen(), colorDusk.getBlue(), 255);
        nightColor = new Color (colorNight.getRed(), colorNight.getGreen(), colorNight.getBlue(), 255);
        sunColor = new Color (colorSun.getRed(), colorSun.getGreen(), colorSun.getBlue(), 255);
        moonColor = new Color (colorMoon.getRed(), colorMoon.getGreen(), colorMoon.getBlue(), 255);
        
        // Updates the visuals
        updateVisuals();
    }
        
    /**
     * Updates the visuals based on the given time and
     * adjusts when day, dusk, and night starts.
     * <p>
     * Dawn is set to 0 (dawn always starts the day).
     * 
     * @param time              Time now
     * @param dayTimeStart      Time when day starts, must be between 0 and oneDayLength
     * @param duskTimeStart     Time when dusk starts, must be between dayTimeStart and oneDayLength
     * @param nightTimeStart    Time when night starts, must be between duskTimeStart and oneDayLength
     */
    public void update(int time, int dayTimeStart, int duskTimeStart, int nightTimeStart)
    {
        if (givenTime != time)
        {
            givenTime = time;
            currTime = givenTime;
            
            adjustTime();
        }
        
        // Updates Instance Variables related to Time
        dayTime = dayTimeStart;
        duskTime = duskTimeStart;
        nightTime = nightTimeStart;
        
        // Updates the visuals
        updateVisuals();
    }
    
    /**
     * Updates the size of the display by changing the width, height,
     * and borderThickness.
     * <p>
     * None of the values may be negative.
     * 
     * @param width             Width of display bar, must be greater than 0
     * @param height            Height of display bar, must be greater than 0
     * @param borderThickness   Width of border, cannot be negative
     */
    public void update(int width, int height, int borderThickness)
    {
        // Updates Instance Variables related to Display
        barWidth = width;
        barHeight = height;
        borderWidth = borderThickness;
        
        // Sets the image to GreenfootImage bar
        bar = new GreenfootImage(barWidth, barHeight);
        this.setImage(bar);
        
        // Updates the visuals
        updateVisuals();
    }
    
    /**
     * Update the visuals according to timeNow.
     * The background, sun, moon, and the border are added each time 
     * this is called.
     * If currTime is greater than dayTime and smaller than duskTime, 
     * create stars.
     */
    private void updateVisuals()
    {
        // Sets nowColor depending on currTime
        timeNow();
        
        // Fills the background to the current colour based on the time
        bar.setColor(currColor);
        bar.fill();
          
        // Transitions the background color
        bar.setColor (fadeColor);
        bar.fillRect (0, 0, barWidth, barHeight);
            
        // Adds the sun
        bar.setColor(sunColor);
        bar.fillOval (barWidth/5, sunY(), barWidth*3/5, barWidth*3/5);
            
        // Sets the sunshine colours
        sunshineColor = new Color (sunColor.getRed(), sunColor.getGreen(), sunColor.getBlue(), alphaAdjust(0, 255, 0, nightTime));
        bar.setColor(sunshineColor);
        // Adds one sunshine circle bigger than the sun
        bar.fillOval (barWidth/9, sunY()-barWidth/5+barWidth/9, barWidth*7/9, barWidth*7/9);
        // Adds another sunshine circle bigger than the previous sunshine circle
        bar.fillOval (barWidth/15, sunY()-barWidth/5+barWidth/15, barWidth*13/15, barWidth*13/15);
        
        // Adds the moon
        moonColor = new Color (250, 250, 230, alphaAdjust(180, 255, nightTime, oneCycleTime));
        bar.setColor(moonColor);
        bar.fillOval (barWidth/5, moonY(), barWidth*3/5, barWidth*3/5);
        
        // Adds the moon crater
        bar.setColor(new Color (90, 90, 90, 50));
        bar.fillOval (barWidth*4/16, moonY() + barWidth*6/28, barWidth*1/5, barWidth*1/5);
        bar.fillOval (barWidth*7/16, moonY() + barWidth*10/28, barWidth*1/7, barWidth*1/7);
        
        // Sets the moonShine colours
        moonShineColor = new Color (moonColor.getRed(), moonColor.getGreen(), moonColor.getBlue(), alphaAdjust(0, 100, nightTime, oneCycleTime));
        bar.setColor(moonShineColor);
        // Adds one moonShine circle bigger than the sun
        bar.fillOval (barWidth/13, moonY()-barWidth/5+barWidth/13, barWidth*11/13, barWidth*11/13);
        // if currTime is greater than dayTime and smaller than duskTime,
        // create stars
        if (currTime > dayTime && currTime < duskTime)
        {
            makeStarPosition();
        }
        
        for (int i = 0; i < numberStars; i++)
        {
            // Adds the stars
            bar.setColor(moonColor);
            // Needs array in order to have a random position, but
            // a consistant position during the night.
            bar.fillOval (starPosX[i], starPosY[i], barWidth/100 + 1, barWidth/100 + 1);
        }
        
        // Border must be added at the endto ensure that it remains on top
        // of all the other elements ofthe visuals.
        addBorder();
    }
    
    /**
     * Adjusts time such that currTime remains within the timeframe of 
     * 0 and oneCycleTime. While the currTime < 0 is true, it will 
     * add oneCycleTime. If currTime > oneCycleTime, it subtract oneCycleTime
     * from currTime. 
     */
    private void adjustTime()
    {
        while ((currTime < 0) == true)
        {
            currTime += oneCycleTime;
        }
        while ((currTime > oneCycleTime) == true)
        {
            currTime -= oneCycleTime;
        }
    }
    
    /**
     * Change the visual according to the time. If the currTime is less than
     * dayTime, meaning it is dawn and will update the colors accordingly.
     * If the currTime < duskTime, it is day time;  if currTime < nightTime,
     * it is dusk; if currTime < oneCycleTime, it is night. Then it changes
     * the colors accordingly.
     */
    private void timeNow()
    {
        Color layerColor;
        int fadeAlpha = 0;
        // Sets the current sky colour based on the time.
        if (currTime < dayTime) // at Dawn
        {
            // currColor determines the colour of the background.
            currColor = dawnColor;
            
            // layerColor determines the colour that the transition layer
            // will transition to.
            layerColor = dayColor;
            
            // This adjusts the alpha (transparency) that the layerColor
            // will be. This ensure a smooth transition between currColor
            // and layerColor.
            // This fade will occur during the end of dawn.
            fadeAlpha = alphaAdjust(dayTime-(duskTime-dayTime)*1/6, dayTime, true);
        }
        else if (currTime < duskTime) // at Day
        {
            currColor = dayColor;
            // No fade happens during day.
            layerColor = dayColor;
        }
        else if (currTime < nightTime) // at Dusk
        {
            currColor = duskColor;
            layerColor = dayColor;
            // This fade will occur during the beginning of dusk.
            fadeAlpha = alphaAdjust(duskTime, duskTime+(duskTime-dayTime)*1/6, false);
        }
        else // at Night
        {
            currColor = nightColor;
            if (currTime < (nightTime + oneCycleTime)/2) // During the first half of night
            {
                layerColor = duskColor;
                fadeAlpha = alphaAdjust(nightTime, nightTime+(oneCycleTime-nightTime)*1/7, false);
            }
            else // During the last half of night
            {
                layerColor = dawnColor;
                fadeAlpha = alphaAdjust(oneCycleTime-(oneCycleTime-nightTime)*1/7, oneCycleTime, true);
            }
        }
        fadeColor = new Color (layerColor.getRed(), layerColor.getGreen(), layerColor.getBlue(), fadeAlpha);
    }
    
    /**
     * Determine the position where the sun can go, the highest place for the
     * sun can go.
     * 
     * @return int Y position of the sun
     */
    private int sunY()
    {
        // Determines how high the sun goes in the bar based on the time,
        // bar height, and bar width.
        double x = (double)currTime;
        double y = (double)nightTime;
        double w = (double)barWidth;
        double h = (double)barHeight;
        // A quadratic function that inputs x, the current time, and outputs
        // z, the height of the sun in the bar.
        // Based on the form y = a(x - p)(x - q), (x * (x - y)) represents
        // the (x - p)(x - q) portion.
        // While (((3.7 + (-0.0045*(w - 60)))/(y*y))*((w/5) + h)) represents a.
        // The function is translated upwards with the + h, the barHeight,
        // to get the function to reach the desired height.
        // In this calculation, variables y, w, and h act as constant values.
        int z = (int)((((3.7 + (-0.0045*(w - 60)))/(y*y))*((w/5) + h))*(x * (x - y)) + h);
        return z;
    }
       
    /**
     * Determine the highest position can the moon touches
     * 
     * @return int Y position of the moon
     */
    private int moonY()
    {
        // Determines how high the moon goes in the bar based on the time,
        // bar height, and bar width.
        double a = (double)oneCycleTime;
        double b = (double)nightTime;
        double x = (double)currTime;
        double y = a - b;
        double w = (double)barWidth;
        double h = (double)barHeight;
        // A quadratic function that inputs x, the current time, and outputs
        // z, the height of the sun in the bar.
        // Based on the form y = a(x - p)(x - q), (x - a) * (x - b) represents
        // the (x - p)(x - q) portion.
        // While (((3.7 + (-0.0045*(w - 60)))/(y*y))*((w/5) + h)) represents a.
        // The function is translated upwards with the + h, the barHeight,
        // to get the function to reach the desired height.
        // In this calculation, variables y, w, and h act as constant values.
        int z = (int)((((3.7 + (-0.0045*(w - 60)))/(y*y))*((w/5) + h))*((x - a) * (x - b)) + h);
        return z;
    }
    
    /**
     * Sets the positions of the stars; the position will be random 
     * within the border.
     */
    private void makeStarPosition()
    {
        for (int i = 0; i < numberStars; i++)
        {
            starPosX[i] = Greenfoot.getRandomNumber(barWidth);
            starPosY[i] = Greenfoot.getRandomNumber(barHeight);
        }
    }
    
    /**
     * Adjusts alpha values within the given timeframe. Goes from
     * 0 to 255 or 255 to 0.
     * 
     * @param beginningTime Time when the transition begins
     * @param endingTime    Time when the transition ends
     * @param fadeIn        True if goes from alpha value 0 to 255,
     *                      false if goes from alpha value 255 to 0
     * @return int          Alpha value at currTime
     */
    private int alphaAdjust(int beginningTime, int endingTime, boolean fadeIn)
    {
        // If current time is not within the given time frame, the alpha
        // value returned is 0 (the colour will not appear).
        if (currTime < beginningTime || currTime > endingTime)
        {
            return 0;
        }
        
        // These letter variables makes the equation below simpler.
        double a = ((double)currTime - (double)beginningTime);
        double b = ((double)endingTime - (double)beginningTime);
        int c;
        int d;
        
        if (fadeIn)
        // If fadeIn is true, the alpha value will be 0 at beginningTime
        // and 255 at endingTime (the colour fades in).
        {
            c = 0;
            d = -1;
        }
        else
        // If fadeIn is false, the alpha value will be 255 at beginningTime
        // and 0 at endingTime (the colour fades out).
        {
             c = 255;
             d = 1;
        }
        
        // The following equation calculates the opacity at a given time.
        int alpha = c - d * (int)(255*a/b);
            
        // Alpha values must be between 0 and 255, this ensures that any
        // time alpha goes out of the range, the method returns a value within
        // the accepted range.
        if (alpha <= 255 && alpha >= 0)
        {
            return alpha;
        }
        else if (alpha > 255)
        {
            return 255;
        }
        else
        {
            return 0;
        }
    }
    
    /**
     * Adjusts alpha values within the given timeframe.
     * Starts at minAlpha at beginning time and ends at minAlpha
     * at endingTime. maxAlpha is returned when currTime is in
     * between beginningTime and endingTime
     * 
     * @param minAlpha      Lowest alpha value that can be returned
     * @param maxAlpha      Highest alpha value that can be returned
     * @param beginningTime Time when the transition begins
     * @param endingTime    Time when the transition ends
     * @return int          Alpha value at currTime
     */
    private int alphaAdjust(int minAlpha, int maxAlpha, int beginningTime, int endingTime)
    {
        // Takes in an alpha value for the moon and adjusts it based on
        // the time.
        int x = currTime - beginningTime;
        int y = endingTime - beginningTime;
        
        int alpha;
        
        if (x < y/2)
        // If the current time is less than the half the ending time,
        // alpha approaches maxAlpha
        {
            alpha = maxAlpha * x/y + minAlpha;
        }
        else
        // Otherwise, if the current time is equal to or more than half the
        // ending time, alpha approaches minAlpha
        {
            alpha = maxAlpha - maxAlpha * x/y + minAlpha;
        }
        
        // Alpha values must be between 0 and 255, this ensures that any
        // time alpha goes out of the range, the method returns a value within
        // the accepted range.
        if (alpha < 0)
        {
            return 0;
        }
        else if (alpha > 255)
        {
            return 254;
        }
        else
        {
            return alpha;
        }
    }
    
    /**
     * Adds a border to the display.
     */
    private void addBorder()
    {
        bar.setColor (borderColor);
        // Adds top of border
        bar.fillRect (0, 0, barWidth, borderWidth);
        // Adds left of border
        bar.fillRect (0, 0, borderWidth, barHeight);
        // Adds bottom of border
        bar.fillRect (barWidth - borderWidth, 0, barWidth, barHeight);
        // Adds right of border
        bar.fillRect (0, barHeight - borderWidth, barWidth, barHeight);
    }
}
