import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * DotCarousel is a Greenfoot Actor used in the class PopUp.
 * This is set of dots that are used to indicate which slide
 * the user is on. These slides are represented by dots.
 * <br>
 * The slide the user is on will be represented by a highlighted
 * dot while the other dots are more transparent. The highlighted
 * dot can be changed.
 * 
 * @author Evan Ng
 * @version 1 (12/04/2020)
 */
public class DotCarousel extends Actor
{
    // Declare Instance Variables related to Display
    private int imageWidth;
    private int imageHeight;
    private int dotDiameter;
    private int borderWidth;
    private int numberOfSlides;
    private int currentSlide;
    
    // Declare Instance Colors
    private Color dotColor;
    private Color borderColor;
    
    // Declare Instance Images
    private GreenfootImage image;
    
    /**
     * Main constructor - sets all the variables to default values.
     */
    public DotCarousel()
    {
        numberOfSlides = 5;
        currentSlide = 1;
        dotDiameter = 6;
        borderWidth = 1;
        imageHeight = dotDiameter + borderWidth * 2;
        imageWidth = imageHeight * numberOfSlides * 2 - imageHeight;
        
        dotColor = new Color (255, 255, 255);
        borderColor = new Color (160, 110, 90);
        
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        buildDisplay(imageWidth, imageHeight);
    }  
    
    /**
     * Constructor that takes two ints - for setting the number of dots (slides)
     * and the slide currently displayed.
     * 
     * @param numberOfSlides    Number of dots
     * @param currentSlide      Which dot is highlighted
     */
    public DotCarousel(int numberOfSlides, int currentSlide)
    {
        this();
        
        if (numberOfSlides > 0)
        {
            this.numberOfSlides = numberOfSlides;
        }
        if (currentSlide > 0)
        {
            this.currentSlide = currentSlide;
        }
        
        imageHeight = dotDiameter + borderWidth * 2;
        imageWidth = imageHeight * numberOfSlides * 2 - imageHeight;
        
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        buildDisplay(imageWidth, imageHeight);
    }  
    
    /**
     * Constructor that takes four ints - for setting the number of dots (slides),
     * the slide currently displayed and the size of the dots.
     * 
     * @param numberOfSlides    Number of dots
     * @param currentSlide      Which dot is highlighted
     * @param dotDiameter       Diameter of the dot
     * @param borderWidth       Width of border on dot
     */
    public DotCarousel(int numberOfSlides, int currentSlide, int dotDiameter, int borderWidth)
    {
        this();
        
        if (numberOfSlides > 0)
        {
            this.numberOfSlides = numberOfSlides;
        }
        if (currentSlide > 0)
        {
            this.currentSlide = currentSlide;
        }
        if (dotDiameter > 0)
        {
            this.dotDiameter = dotDiameter;
        }
        if (borderWidth >= 0)
        {
            this.borderWidth = borderWidth;
        }
        
        imageHeight = dotDiameter + borderWidth * 2;
        imageWidth = imageHeight * numberOfSlides * 2 - imageHeight;
         
        // Sets Instance Images
        image = new GreenfootImage (imageWidth, imageHeight);
        this.setImage (image);

        buildDisplay(imageWidth, imageHeight);
    }  
    
    private void buildDisplay(int width, int height)
    {
        image.clear();
        
        buildBorder(width, height);
        
        for (int i = 0; i < numberOfSlides; i++)
        {
            if (i + 1 == currentSlide)
            {
                image.setColor(dotColor);
            }
            else
            {
                image.setColor(new Color (dotColor.getRed(), dotColor.getGreen(), 
                                          dotColor.getBlue(), 150));
            }
            
            image.fillOval(i * imageHeight * 2 + borderWidth, borderWidth, dotDiameter, dotDiameter);
        }
    }
    
    private void buildBorder(int width, int height)
    {
        image.setColor(borderColor);
        for (int i = 0; i < numberOfSlides; i++)
        {
            if (i + 1 == currentSlide)
            {
                image.setColor(borderColor);
            }
            else
            {
                image.setColor(new Color (borderColor.getRed(), borderColor.getGreen(), 
                               borderColor.getBlue(), 140));
            }
            image.fillOval(i * imageHeight * 2, 0, imageHeight, imageHeight);
        }
    } 
    
    /**
     * Adjusts the highlighted dot. Must be greater than 0 and less than or equal
     * to the number of slides.
     * 
     * @param currentSlide      Which dot is highlighted
     */
    public void setCurrentSlide(int currentSlide)
    {
        if (currentSlide > 0 && currentSlide <= numberOfSlides)
        {
            this.currentSlide = currentSlide;
        }
        buildDisplay(imageWidth, imageHeight);
    }
}
