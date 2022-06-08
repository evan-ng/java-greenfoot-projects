import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * A Generic Button to display text that is clickable. Owned by a World, which controls click
 * capturing.
 * 
 * @author Jordan Cohen
 * @version v0.1.5
 */
public class TextButton extends Actor
{
    // Declare private variables
    private GreenfootImage myImage;
    private String buttonText;
    private int textSize;

    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text  String value to display
     * 
     */
    public TextButton (String text)
    {
        this(text, 16);
    }

    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text  String value to display
     * @param textSize  size of text, as an integer
     * 
     */
    public TextButton (String text, int textSize)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, textSize, new Color(255, 255, 255), new Color(60, 60, 60));
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (new Color(60, 60, 60));
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);
        setImage(myImage);
    }

    /**
     * Change the text displayed on this Button
     * 
     * @param   text    String to display
     * 
     */
    public void update (String text)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, 16, new Color(255, 255, 255), new Color(60, 60, 60));
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (new Color(60, 60, 60));
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);
        setImage(myImage);
    }

}
