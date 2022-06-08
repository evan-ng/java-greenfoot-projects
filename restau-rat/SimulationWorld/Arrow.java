import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Arrow is a simple Greenfoot Actor used in the class PopUp
 * It displays a triangle that points up, down, left, or right.
 * 
 * @author Evan Ng
 * @version 2 (15/04/2020)
 */
public class Arrow extends Actor
{
    // Declare Instance Variables related to Display
    private int imageWidth;
    private int imageHeight;
    private int borderWidth;
    private int orientation;
    
    // Declare Instance Colors
    private Color arrowColor;
    private Color borderColor;
    
    // Declare Instance Images
    private GreenfootImage image;
    
    /**
     * Main constructor - sets all the variables to default values.
     * Mainly for testing purposes
     */
    public Arrow()
    {
        borderWidth = 1;
        orientation = 3;
        
        if(orientation % 2 == 0)
        {
            imageWidth = 20;
            imageHeight = 12;
        }
        else
        {
            imageWidth = 12;
            imageHeight = 20;
        }
        
        arrowColor = new Color (255, 255, 255);
        borderColor = new Color (0, 0, 0);
        
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        buildDisplay(imageWidth, imageHeight);
    }  
    
    /**
     * Constructor that takes four ints - sets the dimensions of the arrow, 
     * the border, and the direction it points.
     * 
     * @param width         Length of base of arrow
     * @param height        Height of arrow, distance from base to tip of arrow
     * @param borderWidth   Thickness of border
     * @param orientation   Direction arrow points, 0 = down, 1 = left, 2 = up, 3 = right
     */
    public Arrow(int width, int height, int borderWidth, int orientation)
    {
        this();
        
        this.borderWidth = borderWidth;
        this.orientation = orientation;
        
        if(orientation % 2 == 0)
        {
            imageWidth = width;
            imageHeight = height;
        }
        else
        {
            imageWidth = height;
            imageHeight = width;
        }
        
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        buildDisplay(imageWidth, imageHeight);
    }  
    
    private void buildDisplay(int width, int height)
    {
        buildBorder(width, height);
        
        // Builds a triangle
        int[] xCords;
        int[] yCords;
        
        // If up or down, build triangle pointing down
        if(orientation % 2 == 0)
        {
            xCords = new int[] {borderWidth * 2, width - borderWidth * 2, width/2};
            yCords = new int[] {borderWidth, borderWidth, height - borderWidth * 2};
        }
        // If left or right, build triangle pointing left
        else
        {
            xCords = new int[] {width - borderWidth, width - borderWidth, borderWidth};
            yCords = new int[] {borderWidth * 2, height - borderWidth * 2, height/2};
        }
        
        int nCords = 3;
        
        image.setColor(arrowColor);
        
        image.fillPolygon(xCords, yCords, nCords);
        
        changeOrientation();
    }
    
    private void buildBorder(int width, int height)
    {
        // Buils a triangle for the border
        int[] xCords;
        int[] yCords;
        
        if(orientation % 2 == 0)
        {
            xCords = new int[] {0, width, width/2};
            yCords = new int[] {0, 0, height};
        }
        else
        {
            xCords = new int[] {width, width, 0};
            yCords = new int[] {0, height, height/2};
        }
            
        int nCords = 3;
        
        image.setColor(borderColor);
        
        image.fillPolygon(xCords, yCords, nCords);
    }
    
    private void changeOrientation()
    {
        // Default is down and left. When orientation is up or right, it rotates
        // down to up and left to right.
        if (orientation == 2 || orientation == 3)
        {
            image.rotate(180);
        }
    }
    
    /**
     * Sets the transparency of the image.
     * 
     * @param transparency Transparency of the image. A value in the range 0 to 255. 
     *                     0 is completely transparent and 255 is 
     *                     completely opaque.
     */
    public void setTransparency(int transparency)
    {
        image.setTransparency(transparency);
    }
}
