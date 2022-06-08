import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Code for Image Manipulation Array Assignment built upon Starter code.
 * 
 * The class Processor contains all of the code to actually perform
 * transformation. The rest of the classes serve to support that
 * capability. This World allows the user to choose transformations
 * and open files.
 * 
 * @author Jordan Cohen
 * @author Vanessa Chiu
 * @author Evan Ng
 * 
 * @version June 2020
 */
public class Background extends World
{
    // Constants:
    private final String STARTING_FILE = "dandelion.jpg";

    // Objects and Variables:
    private ImageHolder image;

    private TextButton openFile;
    private TextButton export;
    private TextButton png;

    private ChangeMenu menu;

    private String fileName;
    
    private int count;

    private GreenfootImage background;
    private Color backgroundColor;

    /**
     * Constructor for objects of class Background.
     * 
     */
    public Background()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 

        // Initialize buttons and the image
        image = new ImageHolder(STARTING_FILE);
        image.fitScreen();
        openFile = new TextButton("" + STARTING_FILE);
        export = new TextButton("Export as: ");
        png = new TextButton("png.");
        menu = new ChangeMenu();
        count = 0;

        // Add objects to the screen
        addObject (image, 415, 313);
        addObject (menu, 150, 313);
        addObject (openFile, 415, 12);
        
        addObject (export, 730, 12);
        addObject (png, 779, 12);

        backgroundColor = new Color (40, 40, 40);
        addBackground();
    }

    /**
     * Act() method checks for mouse input and key input (z and y)
     */
    public void act ()
    {
        checkMouse();

        count ++;
        
        // If the user holds down the z or y key, the undo and redo will slowly speed up.
        // The count gap is to ensure only one undo/redo occurs on one button press.
        if (Greenfoot.isKeyDown("z"))
        {
            if(count % 20 == 0 || (count > 20 && count % 3 == 0) || (count > 50 && count % 2 == 0) || count > 125)
                image.undo();
        }
        else if (Greenfoot.isKeyDown("y"))
        {
            if(count % 20 == 0 || (count > 20 && count % 3 == 0) || (count > 50 && count % 2 == 0) || count > 125)
                image.redo();
        }
        else
            count = -1;
    }

    /**
     * Check for user clicking on a button
     */
    private void checkMouse ()
    {
        // Avoid excess mouse checks - only check mouse if somethething is clicked.
        if (Greenfoot.mouseClicked(null))
        {
            if (Greenfoot.mouseClicked(openFile))
            {
                openFile ();
            }
            else if (Greenfoot.mouseClicked(png))
            {
                savePNG ();
            }
            if (menu.getButtonHover(0, 0))
            {
                Processor.increaseRed(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(0, 1))
            {
                Processor.increaseGreen(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(0, 2))
            {
                Processor.increaseBlue(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(0, 3))
            {
                Processor.increaseSaturation(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(0, 4))
            {
                Processor.decreaseSaturation(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(0, 5))
            {
                Processor.changeContrast(image.getBufferedImage(), 64);
                image.save();
            }
            else if (menu.getButtonHover(0, 6))
            {
                Processor.changeContrast(image.getBufferedImage(), -64);
                image.save();
            }
            else if (menu.getButtonHover(0, 7))
            {
                Processor.changeHue(image.getBufferedImage());
                image.save();
            }
            if (menu.getButtonHover(1, 0))
            {
                Processor.greyscale(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(1, 1))
            {
                Processor.negative(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(1, 2))
            {
                Processor.vignette(image.getBufferedImage());
                image.save();
            }
            if (menu.getButtonHover(2, 0))
            {    
                Processor.flipHorizontal(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(2, 1))
            {
                Processor.flipVertical(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(2, 2))
            {
                image.setBufferedImage(Processor.rotate90clock(image.getBufferedImage()));
                image.save();
                image.fitScreen();
            }
            else if (menu.getButtonHover(2, 3))
            {
                image.setBufferedImage(Processor.rotate90counter(image.getBufferedImage()));
                image.save();
                image.fitScreen();
            }
            else if (menu.getButtonHover(2, 4))
            {
                Processor.flipHorizontal(image.getBufferedImage());
                Processor.flipVertical(image.getBufferedImage());
                image.save();
            }
            if (menu.getButtonHover(3, 0))
            {
                Processor.blur(image.getBufferedImage());
                image.save();
            }
            else if (menu.getButtonHover(3, 1))
            {
                Processor.backBlur(image.getBufferedImage());
                image.save();
            }
            if (menu.getButtonHover(4, 0))
            {
                image.undo();
            }
            else if (menu.getButtonHover(4, 1))
            {
                image.redo();
            }
            else if (menu.getButtonHover(4, 2))
            {
                image.reset();
                image.save();
            }
        }
    }

    /**
     * Allows the user to open a new image file.
     */
    private void openFile ()
    {
        // Use a JOptionPane to get file name from user
        String fileName = JOptionPane.showInputDialog("Please input a file name with extension");

        // If the file opening operation is successful, update the text in the open file button
        if (image.openFile (fileName))
        {
            image.fitScreen();
            String display = "" + fileName;
            openFile.update (display);
        }
    }

    private boolean savePNG ()
    {
        String fileName = JOptionPane.showInputDialog("Input file name (no extension)");
        
        try {
            if (fileName != null)
            {
                BufferedImage bi = image.getBufferedImage();
                fileName += ".png";
                File f = new File (fileName);
                ImageIO.write(bi, "png", f); // need to do some imports
            }
            else
                return false;
        }
        catch (IOException e)
        {
            return false;
        }
        return true;
    }
    
    private void addBackground()
    {
        // Makes the background
        background = new GreenfootImage (getWidth(), getHeight());
        background.setColor(backgroundColor);
        background.fill();
        background.setColor(new Color(60, 60, 60));
        background.fillRect(0, 0, getWidth(), 25);
        background.fillRect(0, 0, 30, getHeight());
        background.setColor(backgroundColor);
        background.fillRect(0, 25, getWidth(), 2);
        setBackground(background);
    }
}

