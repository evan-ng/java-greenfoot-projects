import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * CloseButton is a Greenfoot Actor used in the class PopUp.
 * This is just a simple X.
 * 
 * @author Evan Ng
 * @version 1 (11/04/2020)
 */
public class CloseButton extends Actor
{
    // Declare Instance Variables related to Display
    private int imageWidth;
    private int imageHeight;
    private int crossWidth;
    private int borderWidth;
    
    // Declare Instance Colors
    private Color crossColor;
    private Color borderColor;
    
    // Declare Instance Images
    private GreenfootImage image;
    
    /**
     * Main constructor - sets all the variables to default values.
     */
    public CloseButton()
    {
        imageWidth = 20;
        imageHeight = 20;
        crossWidth = 3;
        borderWidth = 1;
        
        crossColor = new Color (255, 255, 255);
        borderColor = new Color (0, 0, 0);
        
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        buildDisplay(imageWidth, imageHeight);
    }  
    
    private void buildDisplay(int width, int height)
    {
        buildBorder(width, height);
        
        int[] xCords1 = new int[] {borderWidth, (crossWidth + 2 * borderWidth), imageWidth - borderWidth, 
                                  (imageWidth - (crossWidth + 2 * borderWidth))};
        int[] yCords1 = new int[] {(crossWidth + 2 * borderWidth), borderWidth, 
                                  (imageHeight - (crossWidth + 2 * borderWidth)), imageHeight - borderWidth};
        int[] xCords2 = new int[] {borderWidth, (crossWidth + 2 * borderWidth), imageWidth - borderWidth, 
                                  (imageWidth - (crossWidth + 2 * borderWidth))};
        int[] yCords2 = new int[] {(imageHeight - (crossWidth + 2 * borderWidth)), imageHeight - borderWidth,
                                   (crossWidth + 2 * borderWidth), borderWidth};
        int nCords = 4;
        
        image.setColor(crossColor);
        
        image.fillPolygon(xCords1, yCords1, nCords);
        image.fillPolygon(xCords2, yCords2, nCords);
    }
    
    private void buildBorder(int width, int height)
    {
        int[] xCords1 = new int[] {0, (crossWidth + 2 * borderWidth), imageWidth, 
                                  (imageWidth - (crossWidth + 2 * borderWidth))};
        int[] yCords1 = new int[] {(crossWidth + 2 * borderWidth), 0, 
                                  (imageHeight - (crossWidth + 2 * borderWidth)), imageHeight};
        int[] xCords2 = new int[] {0, (crossWidth + 2 * borderWidth), imageWidth, 
                                  (imageWidth - (crossWidth + 2 * borderWidth))};
        int[] yCords2 = new int[] {(imageHeight - (crossWidth + 2 * borderWidth)), imageHeight,
                                   (crossWidth + 2 * borderWidth), 0};
        int nCords = 4;
        
        image.setColor(borderColor);
        
        image.fillPolygon(xCords1, yCords1, nCords);
        image.fillPolygon(xCords2, yCords2, nCords);
    }
}
