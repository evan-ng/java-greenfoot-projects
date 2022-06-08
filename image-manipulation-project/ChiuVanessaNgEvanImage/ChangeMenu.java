import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * ChangeMenu is where you have all the option display in the menu for players to change the image. 
 * Hover over an icon first to choose first to choose which action to use to change the image.
 * This include changing colour, rotation, filter, redo, etc
 * Under each catogories there are options that can help the user to make a different image. 
 * 
 * @author Evan Ng
 * @author Vanessa Chiu
 * @version 2 (23/05/2020)
 * 
 * Colour bucket by Panda Icons from thenounproject.com.
 * Transform by The Icon Z from thenounproject.com.
 * Sliders by Emma Mitchell from thenounproject.com.
 * Undo by M Yudi Maulana from thenounproject.com.
 * Overlap by Gagana from thenounproject.com.
 */
public class ChangeMenu extends Actor
{
    private int width;
    private int fullWidth;
    private int height;
    private int size;

    private int numIcons;
    private int[] iconX;
    private int[] iconY;
    private GreenfootImage[] iconImages;

    private int[] numDrops;
    private boolean[] iconDropped;
    private boolean[][] allDropHover;

    private String[] dropTextsA;
    private String[] dropTextsB;
    private String[] dropTextsC;
    private String[] dropTextsD;
    private String[] dropTextsE;
    private String[][] allStrings;

    private Color backColor;
    private Color lineColor;
    private Color textColor;

    private boolean added = false;

    private GreenfootImage image;
    private int textSize;

    /**
     * Constructor for ChangeMenu, sets all values to defaults.
     * 
     */
    public ChangeMenu()
    {
        width = 30;
        fullWidth = 300;
        height = 575;
        size = 27;

        numIcons = 5;
        iconX = new int[numIcons];
        iconY = new int[numIcons];
        // This array contains the values that determine how many options are in each icon.
        numDrops = new int[] {8, 3, 5, 2, 3};
        iconDropped = new boolean[] {false, false, false, false, false};
        // The different images for the icons
        iconImages = new GreenfootImage[] {new GreenfootImage("colourbucket.png"), new GreenfootImage("overlap.png"), 
            new GreenfootImage("rotate.png"), new GreenfootImage("sliders.png"), new GreenfootImage("undo.png")};

        allDropHover = new boolean[numIcons][5];

        for (int i = 0; i < numIcons; i++)
        {
            allDropHover[i] = new boolean[numDrops[i]];

            for (int j = 0; j < numDrops[i]; j++)
            {
                allDropHover[i][j] = false;
            }
        }

        // The texts for the different options
        dropTextsA = new String[] {"Increase red", "Increase green", "Increase blue", "Increase saturation", "Decrease saturation", "Increase constrast", "Decrease contrast", "Change hue"};
        dropTextsB = new String[] {"Greyscale", "Negative", "Vignette"};
        dropTextsC = new String[] {"Flip horizontally", "Flip vertically", "Rotate clockwise 90°", "Rotate counterclockwise 90°", "Rotate 180°"};
        dropTextsD = new String[] {"Blur filter", "Smart blur filter"};
        dropTextsE = new String[] {"Undo (Z)", "Redo (Y)", "Reset"};

        allStrings = new String[][] {dropTextsA, dropTextsB, dropTextsC, dropTextsD, dropTextsE};

        // All the colours
        backColor = new Color(60, 60, 60);
        lineColor = new Color(75, 75, 75);
        textColor = new Color(255, 255, 255);

        image = new GreenfootImage(fullWidth, height);
        image.setColor(backColor);
        image.fillRect(0, 0, width, height);
        this.setImage(image);
        textSize = 14;
    }

    private void buildDisplay()
    {
        // Builds the display
        int spacing = 60;
        int d = size + spacing;
        int x = getX() - (fullWidth-width)/2 - (size + width)/4;
        int defaultY = getY() - height/2 + 75;

        image.setColor(lineColor);

        for (int i = 0; i < numIcons; i++)
        {
            image.drawRect(x, defaultY + d * i, size, size);
            iconX[i] = x + 3;
            iconY[i] = defaultY + d * i + 3;
        }
        addIcons();
    }

    private void addIcons()
    {
        // Adds all the icons to the menu
        for (int i = 0; i < numIcons; i++)
        {
            GreenfootImage icon = iconImages[i];
            icon.scale(size - 6, size - 6);
            image.drawImage(icon, iconX[i], iconY[i]);
        }
    }

    /**
     * Act() method just checks for mouse input
     */
    public void act ()
    {
        if (!added)
        {
            buildDisplay();
            added = true;
        }

        checkMouse();
    }

    /**
     * Check for user hovering over an icon
     */
    private void checkMouse ()
    {
        MouseInfo m = Greenfoot.getMouseInfo();
        for (int i = 0; i < numIcons; i++)
        {
            // If mouse is hovering over an icon, drop the list.
            if (m != null && m.getX() > 0 && m.getX() < (iconX[i] + size) && 
            m.getY() > (iconY[i] + size/2) && m.getY() < (iconY[i] + size * 2))
                dropDown(i);
            // If mouse is not hovering over an icon, no icon will appear.
            // If an icon is already dropped down, the droplist will disappear if mouse is far
            // enough from the menu.
            else if (m != null && m.getX() > 200)
            {
                image.clear();
                buildDisplay();
            }

            // If the icon is dropped, check for hovering over a specific option
            if(iconDropped[i])
                for (int j = 0; j < numDrops[i]; j++)
                {
                    int maxWidth = 0;
                    int maxHeight = 0;
                    int spacing = 15;

                    for (int k = 0; k < numDrops[i]; k++)
                    {
                        GreenfootImage tempTextImage = new GreenfootImage (allStrings[i][k], textSize, new Color(255, 255, 255), new Color(60, 60, 60));
                        if (tempTextImage.getWidth() > maxWidth)
                            maxWidth = tempTextImage.getWidth();
                        if (tempTextImage.getHeight() > maxHeight)
                            maxHeight = tempTextImage.getHeight();
                    }

                    allDropHover[i][j] = false;
                    
                    // Checks if mouse is hovering over a specific option.
                    if (m != null && m.getX() > (iconX[i] + size) && m.getX() < (iconX[i] + size + maxWidth + 8) && 
                    m.getY() > (iconY[i] + (maxHeight + spacing) * j + maxHeight/2 + 16) && m.getY() < (iconY[i] + (maxHeight + spacing) * (j + 1) + maxHeight/2) + 16)
                    {
                        allDropHover[i][j] = true;
                    }
                }
        }
    }

    /**
     * Tells the menu that the given icon will drop a the droplist of options.
     * 
     * @param iconNumber Which icon in the menu it is (0 is the top icon, 4 is the bottom)
     */
    private void dropDown (int iconNumber)
    {
        for (int i = 0; i < numIcons; i++)
        {
            if (i == iconNumber)
                iconDropped[i] = true;
            else
                iconDropped[i] = false;
        }
        addDropDown();
    }

    private void addDropDown()
    {
        image.clear();
        buildDisplay();

        boolean allFalse = true;

        // Checks if a droplist should be dropped
        // Then adds the droplist
        for (int i = 0; i < numIcons; i++)
        {
            if (iconDropped[i] == true)
            {
                int maxWidth = 0;
                int maxHeight = 0;
                int spacing = 15;

                // Determines the size of the droplist based on the number of options and the 
                // width of the strings
                for (int j = 0; j < numDrops[i]; j++)
                {
                    GreenfootImage tempTextImage = new GreenfootImage (allStrings[i][j], textSize, new Color(255, 255, 255), new Color(60, 60, 60));
                    if (tempTextImage.getWidth() > maxWidth)
                        maxWidth = tempTextImage.getWidth();
                    if (tempTextImage.getHeight() > maxHeight)
                        maxHeight = tempTextImage.getHeight();
                }
                
                // Creates a rectangle for the droplist
                GreenfootImage tempImage = new GreenfootImage (maxWidth + 8, maxHeight * numDrops[i] + spacing * (numDrops[i] - 1) + 8);
                tempImage.setColor (new Color(60, 60, 60));
                tempImage.fill();

                // Adds the strings to the droplist
                for (int j = 0; j < numDrops[i]; j++)
                {
                    GreenfootImage tempTextImage = new GreenfootImage (allStrings[i][j], textSize, new Color(255, 255, 255), new Color(60, 60, 60));

                    tempImage.drawImage (tempTextImage, 4, 4 + (maxHeight + spacing) * j);
                }

                // Adds the droplist to the menu
                image.drawImage (tempImage, iconX[i] + size, iconY[i]);
                allFalse = false;
            }
        }
        
        // If no droplist should be dropped, no droplists will remain.
        if (allFalse)
        {
            image.clear();
            buildDisplay();
        }
    }

    /**
     * Returns the whether a mouse is hovering over a certain button.
     * 
     * @param iconNum       The icon in the menu (0 is the top icon)
     * @param dropDownNum   the number of additional options in each icon
     */
    public boolean getButtonHover (int iconNum, int dropDownNum)
    {
        return allDropHover[iconNum][dropDownNum];
    }
}
