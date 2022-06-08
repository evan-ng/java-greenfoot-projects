import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

/**
 * Simple class that serves to be an Actor to display the image.
 * 
 * (Revised 11/14 to avoid crashing if user cancels import operation).
 * 
 * @author Jordan Cohen
 * @version November 2013, revised
 */
public class ImageHolder extends Actor
{
    private ArrayList<GreenfootImage> saveList = new ArrayList<GreenfootImage>();
    private int currentSave = -1;
    
    private GreenfootImage imageToDisplay; 

    /**
     * Construct an ImageHolder with a file name. If there is an error, 
     * show a blank GreenfootImage.
     * 
     * @param fileName  Name of image file to be displayed.
     */
    public ImageHolder (String fileName)
    {
        openFile (fileName);
    }

    /**
     * Attempt to open a file and assign it as this Actor's image
     * 
     * @param fileName  Name of the image file to open (must be in this directory)
     * @return boolean  True if operation successful, otherwise false
     */
    public boolean openFile (String fileName)
    {
        try {
            if (fileName != null)
            {
                imageToDisplay = new GreenfootImage (fileName);
                setImage(imageToDisplay);
                currentSave = -1;
                save();
            }
            else
                return false;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
        return true;
    }

    public void fitScreen()
    {
        if (imageToDisplay.getWidth() > imageToDisplay.getHeight())
            imageToDisplay.scale(750, 750 * imageToDisplay.getHeight() / imageToDisplay.getWidth());
        else
            imageToDisplay.scale(550 * imageToDisplay.getWidth() / imageToDisplay.getHeight(), 550);
    }
    
    /**
     * save the changes that are made  
     */
    public void save()
    {
        if (saveList.size() - 1 > currentSave)
        {
            ArrayList<GreenfootImage> tempList = new ArrayList<GreenfootImage>();
            for(int i = 0; i < currentSave + 1; i++)
            {
                tempList.add(saveList.get(i));
            }
            saveList = tempList;
        }
        
        currentSave++;
        saveList.add(currentSave, new GreenfootImage(imageToDisplay));
        fitScreen();
    }
    
    /**
     *  undo the changes that were made 
     */
    public void undo()
    {
        if (currentSave > 0)
        {
            currentSave--;
            imageToDisplay = new GreenfootImage(saveList.get(currentSave));
            setImage(imageToDisplay);
            fitScreen();
        }
    }
    
    public void redo()
    {
        if (saveList.size() - 1 > currentSave)
        {
            currentSave++;
            imageToDisplay = saveList.get(currentSave);
            setImage(imageToDisplay);
            fitScreen();
        }
    }
    
    /**
     * Reset the whole image to the original image (removes all adjustments)
     */
    public void reset()
    {
        if (saveList.size() > 0)
        {
            imageToDisplay = new GreenfootImage(saveList.get(0));
            setImage(imageToDisplay);
            fitScreen();
        }
    }
    
    /**
     * Allows access to my awtImage - the backing data underneath the GreenfootImage class.
     * 
     * @return BufferedImage returns the backing image for this Actor as an AwtImage
     */
    public BufferedImage getBufferedImage ()
    {
        return this.getImage().getAwtImage();
    }

    /**
     * sets the awtImage - the backing data underneath the GreenfootImage class.
     * 
     * @param newBi The BufferedImage to convert to a GreenfootImage.
     */
    public void setBufferedImage (BufferedImage newBi)
    {
        imageToDisplay = Processor.createGreenfootImageFromBI(newBi);
        setImage(imageToDisplay);
    }
}
