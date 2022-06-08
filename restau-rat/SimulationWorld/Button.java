import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Button is a simple Greenfoot Actor that displays a line of text
 * in a rectangular display which can have rounded corners.
 * 
 * @author Evan Ng
 * @version 2 (23/04/2020)
 */
public class Button extends Actor
{
    // Declare Instance Variables related to Display
    private int imageWidth;
    private int imageHeight;
    private int cornerDiameter;
    private int borderWidth;
    
    // Declare Instance Variables related to Text
    private String text;
    private int fontSize;

    // Declare Instance Colors
    private Color backgroundColor;
    private Color borderColor;

    // Declare Instance Images
    private GreenfootImage image;
    
    /**
     * Main constructor - sets all the variables to default values.
     * This is mostly used for testing purposes.
     */
    public Button()
    {
        // Sets Instance Variables
        imageWidth = 200;
        imageHeight = 300;
        cornerDiameter = 10;
        borderWidth = 2;
        fontSize = 22;

        // Sets Instance Colours
        backgroundColor = new Color (255, 225, 210);
        borderColor = new Color (210, 160, 140);

        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        // Creates the button display
        buildDisplay(imageWidth, imageHeight);
    }
    
    /**
     * Constructor that takes five ints and two Colors - for adjusting size and colors of 
     * the display: width and height of the display, the border, and font size.
     * 
     * @param width             Width of display
     * @param height            Height of display
     * @param cornerRadius      Radius of curve on the corners
     * @param borderThickness   Width of border
     * @param fontSize          Size of the text
     * @param backColor         Color of background
     * @param borderColor       Color of border
     */
    public Button(int width, int height, int cornerRadius, int borderThickness, int fontSize,
                  Color backColor, Color borderColor)
    {
        imageWidth = width;
        imageHeight = height;
        cornerDiameter = cornerRadius * 2;
        borderWidth = borderThickness;
        
        this.fontSize = fontSize;

        backgroundColor = backColor;
        this.borderColor = borderColor;
            
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        // Creates the button display
        buildDisplay(imageWidth, imageHeight);
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

        if (text != null)
        {
            addText();
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
    
    private void addText()
    {
        Font font = new Font("", false, false, fontSize);
        String text = this.text;

        image.setColor (new Color (0, 0, 0));
        image.setFont (font);

        // The maximum width of a line
        int width = imageWidth;
        // Determines how wide the text is with the given font
        int textWidth = new GreenfootImage(text, font.getSize(), null, null).getWidth();
        // Font size
        int size = font.getSize();
        // Determines how tall a character is with the given font
        int height = new GreenfootImage(text, size, null, null).getHeight();
        
        image.drawString (text, (width - textWidth)/2, (imageHeight + height) / 2 - imageHeight/20);
    }
    
    /**
     * Sets the text. The text will be displayed as a single line.
     * <br>
     * In order to center the text, add spaces to the string.
     * Add spaces to the front of the text if the text is displayed too far the left and
     * vice versa if it is displayed too far to the right.
     * <br>
     * Ex: popUp.setText("TEXT WORDS        ");
     * <br>
     * Note: Text cannot be centered automatically because Greenfoot cannot accurately
     * determine the widths of texts (especially when using different fonts).
     * 
     * @param text String that will be displayed at the top of the pop up
     */
    public void setText(String text)
    {
        if (this.text != text)
        {
            this.text = text;

            if (this.text != null)
            {
                buildDisplay(imageWidth, imageHeight);
                addText();
            }
        }
    }
    
    /**
     * Updates the size and colors of the display
     * 
     * @param width             Width of display
     * @param height            Height of display
     * @param cornerRadius      Radius of curve on the corners
     * @param borderThickness   Width of border
     * @param fontSize          Size of the font
     * @param backColor         Color of background
     * @param borderColor       Color of border
     */
    public void update(int width, int height, int cornerRadius, int borderThickness,
                       int fontSize, Color backColor, Color borderColor)
    {
        imageWidth = width;
        imageHeight = height;
        cornerDiameter = cornerRadius * 2;
        borderWidth = borderThickness;
        
        this.fontSize = fontSize;

        backgroundColor = backColor;
        this.borderColor = borderColor;
            
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        // Creates the button display
        buildDisplay(imageWidth, imageHeight);
    }
}
