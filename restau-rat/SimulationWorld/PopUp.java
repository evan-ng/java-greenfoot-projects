import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * PopUp is a Greenfoot Actor that displays text. The text is displayed in a rectangular
 * box. The PopUp displays two types of text: title and body texts. 
 * <br>
 * The title is shown at the top of the display and can be adjusted to be left, center, or 
 * right justified. The body text is shown below the title and is left justified. The body
 * has automatic text wrap.
 * <br>
 * PopUp comes with Arrow, CloseButton, and DotCarousel, all of which can be disabled.
 * <br>
 * The display also has the option of having a pop up animation.
 * 
 * @author Evan Ng
 * @version 9 (23/04/2020)
 */
public class PopUp extends Actor
{
    // Declare Instance Variables related to Display
    private int imageWidth;
    private int imageHeight;
    private int imageWidthTemp;
    private int imageHeightTemp;
    private int cornerDiameter;
    private int borderWidth;
    private boolean accessories;

    // Declare Instance Variables related to Pop Up Animation
    private boolean popUp;
    private int popUpSpeed;
    private int popUpTime;
    private boolean shrink;

    // Declare Instance Variables related to Dot Carousel
    private int numberOfSlides;
    private int currentSlide;

    // Declare Instance Variables related to Text
    private String titleText;
    private String bodyText;
    private String origBodyText;

    // Declare Instance Colors
    private Color backgroundColor;
    private Color borderColor;

    // Declare Instance Images
    private GreenfootImage image;

    // Declare Instance CloseButton
    CloseButton closeButton;

    // Declare Instance DotCarousel
    DotCarousel dotCarousel;

    // Declare Instance Arrows
    Arrow leftArrow;
    Arrow rightArrow;

    /**
     * Main constructor - sets all the variables to default values.
     * This is mostly used for testing purposes.
     */
    public PopUp()
    {
        // Sets Instance Variables related to Display
        imageWidth = 200;
        imageHeight = 300;
        cornerDiameter = 10;
        borderWidth = 2;
        accessories = true;

        imageWidthTemp = 1;
        imageHeightTemp = 1;

        // Sets Instance Variables related to Pop Up Animation
        popUp = true;
        popUpSpeed = 15;

        popUpTime = 0;
        shrink = false;

        // Sets Instance Variables related to DotCarousel
        numberOfSlides = 5;
        currentSlide = 1;

        // Sets Instance Colors
        backgroundColor = new Color (255, 225, 210);
        borderColor = new Color (210, 160, 140);

        // Sets Instance Images
        image = new GreenfootImage (imageWidthTemp + 1, imageHeightTemp + 1);
        this.setImage (image);

        // Sets Instance CloseButton, DotCarousel, and Arrows
        closeButton = new CloseButton();
        dotCarousel = new DotCarousel(numberOfSlides, currentSlide);
        leftArrow = new Arrow(20, 12, 1, 1);
        rightArrow = new Arrow(20, 12, 1, 3);
        leftArrow.setTransparency(175);
        rightArrow.setTransparency(175);

        buildDisplay(imageWidthTemp, imageHeightTemp);
    }

    /**
     * Constructor that takes five ints, one boolean, and two Colors - the size and colours
     * of the display background and border can be adjusted. The corners can also be rounded
     * using cornerRadius. The pop up animation can be enabled or disabled; the time of the
     * animation can also be changed.
     * 
     * @param width             Width of display
     * @param height            Height of display
     * @param cornerRadius      Radius of curve on the corners
     * @param borderThickness   Width of border, set to 0 for no border
     * @param popsUp            Whether display has a pop up animation
     * @param popUpTime         Length of time of pop up animation
     * @param numberOfSlides    Number of dots displayed at bottom
     * @param currentSlide      Which dot is highlighted
     * @param backColor         Color of background
     * @param borderColor       Color of border
     */
    public PopUp(int width, int height, int cornerRadius, int borderThickness, boolean popsUp, 
    int popUpTime, int numberOfSlides, int currentSlide, Color backColor, Color borderColor)
    {
        this();

        // Sets Instance Variables related to Display
        imageWidth = width;
        imageHeight = height;
        cornerDiameter = cornerRadius * 2;
        borderWidth = borderThickness;

        // Sets Instance Variables related to Pop Up Animation
        popUp = popsUp;
        popUpSpeed = popUpTime;

        // Sets Instance Variables related to DotCarousel
        this.numberOfSlides = numberOfSlides;
        this.currentSlide = currentSlide;

        // Sets Instance Colours
        backgroundColor = backColor;
        this.borderColor = borderColor;

        // Sets width and height for display if there is no animation
        if(!popUp)
        {
            imageWidthTemp = width;
            imageHeightTemp = height;

            // Sets Instance Images
            image = new GreenfootImage (imageWidth, imageHeight);
            this.setImage (image);
        }

        // Sets Instance DotCarousel
        dotCarousel = new DotCarousel(numberOfSlides, currentSlide);

        buildDisplay(imageWidth, imageHeight);
    }

    /**
     * Constructor that takes four ints and two Colors - a simpler constructor 
     * that does not have a pop up animation and has no accessories (no close
     * button, arrows, or dot carousel).
     * 
     * @param width             Width of display
     * @param height            Height of display
     * @param cornerRadius      Radius of curve on the corners
     * @param borderThickness   Width of border, set to 0 for no border
     * @param backColor         Color of background
     * @param borderColor       Color of border
     */
    public PopUp(int width, int height, int cornerRadius, int borderThickness, Color backColor, 
    Color borderColor)
    {
        this();

        // Sets Instance Variables related to Display
        imageWidth = width;
        imageHeight = height;
        cornerDiameter = cornerRadius * 2;
        borderWidth = borderThickness;
        accessories = false;

        // Sets Instance Variables related to Pop Up Animation
        popUp = false;
        popUpSpeed = 0;

        // Sets Instance Variables related to DotCarousel
        numberOfSlides = 1;
        currentSlide = 1;

        // Sets Instance Colours
        backgroundColor = backColor;
        this.borderColor = borderColor;

        // Sets width and height for display if there is no animation
        if(!popUp)
        {
            imageWidthTemp = width;
            imageHeightTemp = height;

            // Sets Instance Images
            image = new GreenfootImage (imageWidth, imageHeight);
            this.setImage (image);
        }

        // Sets Instance DotCarousel
        dotCarousel = new DotCarousel(numberOfSlides, currentSlide);

        buildDisplay(imageWidth, imageHeight);
    }

    /**
     * Constructor that takes four ints - a even simpler constructor 
     * that does not have a pop up animation and has no accessories (no close
     * button, arrows, or dot carousel) and uses the default Colors.
     * 
     * @param width             Width of display
     * @param height            Height of display
     * @param cornerRadius      Radius of curve on the corners
     * @param borderThickness   Width of border, set to 0 for no border
     */
    public PopUp(int width, int height, int cornerRadius, int borderThickness)
    {
        this();

        // Sets Instance Variables related to Display
        imageWidth = width;
        imageHeight = height;
        cornerDiameter = cornerRadius * 2;
        borderWidth = borderThickness;
        accessories = false;

        // Sets Instance Variables related to Pop Up Animation
        popUp = false;
        popUpSpeed = 0;

        // Sets Instance Variables related to DotCarousel
        numberOfSlides = 1;
        currentSlide = 1;

        // Sets width and height for display if there is no animation
        if(!popUp)
        {
            imageWidthTemp = width;
            imageHeightTemp = height;

            // Sets Instance Images
            image = new GreenfootImage (imageWidth, imageHeight);
            this.setImage (image);
        }

        // Sets Instance DotCarousel
        dotCarousel = new DotCarousel(numberOfSlides, currentSlide);

        buildDisplay(imageWidth, imageHeight);
    }

    /**
     * Act - do whatever the PopUp wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        // Ensures no errors if mouse is not there or mouse is not above an actor
        if (mouse != null && mouse.getActor() != null){
            // If the mouse is hovering over the close button and clicks it
            if (mouse.getActor().equals(closeButton) && Greenfoot.mouseClicked(null))
            {
                // Remove the pop up and the close button.
                this.getWorld().removeObject(closeButton);
                this.getWorld().removeObject(dotCarousel);
                this.getWorld().removeObject(leftArrow);
                this.getWorld().removeObject(rightArrow);
                shrink = true;
            }
        }

        if (popUp)
        {
            growAnimation();
        }

        if (shrink == true)
        {
            shrinkAnimation();
        }

        if (mouse != null && mouse.getActor() != null){
            // If the mouse is hovering over the left button and clicks it
            if (mouse.getActor().equals(leftArrow) && Greenfoot.mouseClicked(null))
            {
                if (currentSlide - 1 >= 1)
                {
                    this.currentSlide--;
                }
                dotCarousel.setCurrentSlide(currentSlide);
                changeTransparency();
            }
            else if (mouse.getActor().equals(rightArrow) && Greenfoot.mouseClicked(null))
            {
                if (currentSlide + 1 <= numberOfSlides)
                {
                    this.currentSlide++;
                }
                dotCarousel.setCurrentSlide(currentSlide);
                changeTransparency();
            }
        }
    }    

    private void buildDisplay(int width, int height)
    {
        image.clear();

        // Adds the border
        buildBorder(width, height);

        // Sets the color to backgroundColor
        image.setColor (backgroundColor);

        // Puts two rectangles
        image.fillRect (0 + borderWidth, cornerDiameter/2 + borderWidth, 
            width - borderWidth * 2, height - cornerDiameter - borderWidth * 2);
        image.fillRect (cornerDiameter/2 + borderWidth, 0 + borderWidth, 
            width - cornerDiameter - borderWidth * 2, height - borderWidth * 2);

        if (cornerDiameter > 0)
        {
            // Adds the curved corners
            image.fillOval (borderWidth, borderWidth, cornerDiameter, cornerDiameter);
            image.fillOval (width - cornerDiameter - borderWidth, borderWidth, 
                cornerDiameter, cornerDiameter);
            image.fillOval (borderWidth, height - cornerDiameter - borderWidth, 
                cornerDiameter, cornerDiameter);
            image.fillOval (width - cornerDiameter - borderWidth, height - cornerDiameter - 
                borderWidth, cornerDiameter, cornerDiameter);
        }

        if (titleText != null)
        {
            addTitle();
        }
        if (bodyText != null)
        {
            addBody();
        }
    }

    private void buildBorder(int width, int height)
    {
        // This is the ratio of the size of the display with the border to the display
        // without the border
        int r = width / (width - borderWidth);

        // Sets the color to backgroundColor
        image.setColor (borderColor);

        // Puts two rectangles
        image.fillRect (0, cornerDiameter * r / 2, width, height - cornerDiameter * r);
        image.fillRect (cornerDiameter * r / 2, 0, width - cornerDiameter * r, height);

        if (cornerDiameter > 0)
        {
            // Adds the curved corners
            image.fillOval (0, 0, cornerDiameter * r, cornerDiameter * r);
            image.fillOval (width - cornerDiameter * r, 0, cornerDiameter * r, cornerDiameter * r);
            image.fillOval (0, height - cornerDiameter * r, cornerDiameter * r, cornerDiameter * r);
            image.fillOval (width - cornerDiameter * r, height - cornerDiameter * r, 
                cornerDiameter * r, cornerDiameter * r);
        }
    }

    private void growAnimation()
    {
        // A radical equation that takes in popUpSpeed and outputs a value between 0 and 1
        // This will later be multiplied by the imageWidth and imageHeight to get a fraction
        // of those values.
        double y = Math.sqrt((double)popUpTime / (double)popUpSpeed);

        // While the current display is smaller than the given width and height values,
        // the display will grow, getting closer to the given width and height values.
        if (y <= 1 && popUp == true)
        {
            imageWidthTemp = (int)(y * (double)imageWidth) + (imageWidthTemp/20) + 1;
            imageHeightTemp = (int)(y * (double)imageHeight) + (imageHeightTemp/20) + 1;

            image = new GreenfootImage (imageWidthTemp, imageHeightTemp);
            this.setImage (image);

            buildDisplay(imageWidthTemp, imageHeightTemp);
            popUpTime++;
        }
        // Once the display reaches its given width and height values, the pop up animation
        // is stopped.
        else
        {
            // The image is now at its given width and height
            imageWidthTemp = imageWidth;
            imageHeightTemp = imageHeight;

            // Sets the image to the final width and height
            image = new GreenfootImage (imageWidth, imageHeight);
            this.setImage (image);

            // Displays the image
            buildDisplay(imageWidth, imageHeight);

            // PopUp animation is finished.
            popUp = false;

            if(getWorld() != null && accessories == true)
            {
                // Adds Objects
                getWorld().addObject(closeButton, getX() + imageWidth/2, getY() - imageHeight/2);
                getWorld().addObject(dotCarousel, getX(), getY() + 3 * imageHeight/7);
                getWorld().addObject(leftArrow, getX() - imageWidth/2 - 10, getY());
                getWorld().addObject(rightArrow, getX() + imageWidth/2 + 10, getY());

                // Sets transparency of the left and/or right arrow.
                changeTransparency();
            }

            popUpTime = 0;
        }
    }

    private void shrinkAnimation()
    {
        // A radical equation that takes in popUpSpeed and outputs a value between 0 and 1
        // This will later be multiplied by the imageWidth and imageHeight to get a fraction
        // of those values.
        double y = Math.sqrt((double)popUpTime / (double)popUpSpeed);

        // The display will shrink, getting closer to 0.
        if (y <= 1)
        {
            imageWidthTemp = (int)((1 - y) * (double)imageWidth) + 1;
            imageHeightTemp = (int)((1 - y) * (double)imageHeight) + 1;

            image = new GreenfootImage (imageWidthTemp, imageHeightTemp);
            this.setImage (image);

            buildDisplay(imageWidthTemp, imageHeightTemp);
            popUpTime++;
        }
        // Once the display reaches its given width and height values, the display removes itself
        else
        {
            this.getWorld().removeObject(this);
        }
    }

    private void addTitle()
    {
        // Determines a double that when multipled with the font size, gives an adequete
        // font size that matches the pop up animation
        double y = Math.pow(((double)imageWidthTemp / (double)imageWidth), 1.19);

        // Adjusts the font size
        Font font = new Font("", false, false, (int)(22 * y));
        // Sets the text
        String text = titleText;

        // Sets the text colour and font
        image.setColor (new Color (0, 0, 0));
        image.setFont (font);

        // The maximum width of a line
        int width = imageWidthTemp;
        // Determines how wide the text is with the given font
        int textWidth = new GreenfootImage(text, font.getSize(), null, null).getWidth();
        // Font size
        int size = font.getSize();
        // Determines how tall a character is with the given font
        int height = new GreenfootImage(text, size, null, null).getHeight();

        // Adds the text to the display
        image.drawString (text, (width - textWidth)/2, height + cornerDiameter);
    }

    private void addBody()
    {
        // Determines a double that when multipled with the font size, gives an adequete
        // font size that matches the pop up animation
        double y = Math.pow(((double)imageWidthTemp / (double)imageWidth), 1.19);

        if (imageWidthTemp == imageWidth)
        {
            bodyText = setLines(origBodyText);
        }

        // Adjusts the font size
        Font font = new Font("", false, false, (int)(18 * y));
        // Sets the text
        String text = bodyText;

        // Sets the text colour and font
        image.setColor (new Color (0, 0, 0));
        image.setFont (font);

        // Font size
        int size = font.getSize();
        // Determines how tall a character is with the given font
        int height = new GreenfootImage(" ", size, null, null).getHeight();

        // Seperates the text into lines
        String[] lines = text.split("\n");
        // Puts each text line on image
        for (int i = 0; i < lines.length; i++)
        { 
            GreenfootImage line = new GreenfootImage(lines[i], size, null, null);

            // Puts each line on display
            image.drawImage(line, 20, i * height + (int)(22 * y) + 
                (new GreenfootImage(" ", (int)(22 * y), null, null).getHeight())
                + cornerDiameter);
        }
    }

    private String setLines(String text)
    {
        // Determines a double that when multipled with the font size, gives an adequete
        // font size that matches the pop up animation
        double y = Math.pow(((double)imageWidthTemp / (double)imageWidth), 1.19);

        // Adjusts the font size
        Font font = new Font("", false, false, (int)(18 * y));

        // Sets the text colour and font
        image.setColor (new Color (0, 0, 0));
        image.setFont (font);

        int width;
        if (popUp){
            // Maximum width of a line
            width = imageWidthTemp;
        }
        else
        {
            width = imageWidth;
        }

        // Font size
        int size = font.getSize();
        // Determines how tall a character is with the given font
        int height = new GreenfootImage(" ", size, null, null).getHeight();

        // Splits the text into words
        String[] words = text.split(" ");

        // Sets the first word into text
        text = words [0];

        // Determines if words go into new lines or not
        for (int i = 0; i < words.length - 1; i++)
        {
            // Width of the text if the next word was added
            int w = new GreenfootImage(text + " " + words[i + 1], size, null, null).getWidth();

            // If the width of the text with the next word is bigger than the 
            // allowed width, put it in a new line
            if (w > width - 40)
            {
                // Puts text in new line
                text += "\n";
            }
            else
            {
                // Next word remains in line
                text += " ";
            }
            text += words[i + 1];
        }

        return text;
    }

    private void changeTransparency()
    {
        if(currentSlide == 1)
        {
            leftArrow.setTransparency(50);
        }
        else if (currentSlide == numberOfSlides)
        {
            rightArrow.setTransparency(50);
        }
        else
        {
            leftArrow.setTransparency(175);
            rightArrow.setTransparency(175);
        }
    }

    /**
     * Sets the title text. The text will be displayed as a single line at the top 
     * of the pop up.
     * <br>
     * In order to center the text, add spaces to the string.
     * Add spaces to the front of the text if the text is displayed too far the left and
     * vice versa if it is displayed too far to the right.
     * <br>
     * Ex: popUp.setTitle("TITLE WORDS        ");
     * <br>
     * Note: Text cannot be centered automatically because Greenfoot cannot accurately
     * determine the widths of texts (especially when using different fonts).
     * 
     * @param text String that will be displayed at the top of the pop up
     */
    public void setTitle(String text)
    {
        if (titleText != text)
        {
            titleText = text;

            if (titleText != null)
            {
                buildDisplay(imageWidth, imageHeight);
                addTitle();
            }
        }
    }

    /**
     * Sets the body text. The text will be displayed in multiple lines below the title.
     * It will be displayed left justified.
     * 
     * @param text String that will be displayed below the title
     */
    public void setBody(String text)
    {
        if (bodyText != text)
        {
            origBodyText = text;
            bodyText = text;

            if (bodyText != null)
            {
                buildDisplay(imageWidth, imageHeight);

                bodyText = setLines(origBodyText);

                addBody();
            }
        }
    }

    /**
     * Returns which slide the display is currently on.
     * 
     * @return int Slide display is currently showing.
     */
    public int getCurrentSlide()
    {
        return currentSlide;
    }
}
