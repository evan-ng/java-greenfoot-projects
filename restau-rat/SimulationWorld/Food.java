import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is simply an image of a food item. The size
 * of the image may be changed. Images from https://hotemoji.com.
 * 
 * @author Evan Ng
 * @version 1 (13/05/20)
 */
public class Food extends Actor
{
    GreenfootImage image;

    /**
     * Main constructor - sets all the variables to default values.
     * This is simply an image of a random food item.
     */
    public Food()
    {
        selectImage();
        image.scale(25, 25);
        setImage(image);
    } 

    /**
     * Constructor that takes one int - this will change the size
     * of the image based on the given int.
     */
    public Food(int imageWidth)
    {
        this();
        image.scale(imageWidth, imageWidth);
        setImage(image);
    }

    private void selectImage()
    {
        // Chooses a random food image.
        int x = Greenfoot.getRandomNumber(19);
        if (x == 0)
            image = new GreenfootImage("meat.png");
        else if (x == 1)
            image = new GreenfootImage("burger.png");
        else if (x == 2)
            image = new GreenfootImage("pot.png");
        else if (x == 3)
            image = new GreenfootImage("curry.png");
        else if (x == 4)
            image = new GreenfootImage("sandwich.png");
        else if (x == 5)
            image = new GreenfootImage("taco.png");
        else if (x == 6)
            image = new GreenfootImage("oden.png");
        else if (x == 7)
            image = new GreenfootImage("salad.png");
        else if (x == 8)
            image = new GreenfootImage("can.png");
        else if (x == 9)
            image = new GreenfootImage("cheese.png");
        else if (x == 10)
            image = new GreenfootImage("custard.png");
        else if (x == 11)
            image = new GreenfootImage("coffee.png");
        else if (x == 12)
            image = new GreenfootImage("cake.png");
        else if (x == 13)
            image = new GreenfootImage("tea.png");
        else if (x == 14)
            image = new GreenfootImage("corn.png");
        else if (x == 15)
            image = new GreenfootImage("egg.png");
        else if (x == 16)
            image = new GreenfootImage("sushi.png");
        else if (x == 17)
            image = new GreenfootImage("avocado.png");
        else if (x == 18)
            image = new GreenfootImage("banana.png");
    }
}
