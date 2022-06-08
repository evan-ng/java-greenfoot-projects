/**
 * Starter code for Processor - the class that processes images.
 * <p>
 * This class manipulated Java BufferedImages, which are effectively 2d arrays
 * of pixels. Each pixel is a single integer packed with 4 values inside it.
 * <p>
 * I have included two useful methods for dealing with bit-shift operators so
 * you don't have to. These methods are unpackPixel() and packagePixel() and do
 * exactly what they say - extract red, green, blue and alpha values out of an
 * int, and put the same four integers back into a special packed integer. 
 * 
 * @author Jordan Cohen 
 * @author Evan Ng
 * @author Vanessa Chiu
 * @version June 2020
 */

import greenfoot.*;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.Color;

public class Processor  
{
    /**
     * Example colour altering method by Mr. Cohen. This method will
     * increase the blue value while reducing the red and green values.
     * Changed to better fit the effect
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void increaseBlue (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (blue < 250)
                    blue ++;
                if (red >= 0)
                    red = (int)((double)red * 0.8);
                if (green >= 5)
                    green = (int)((double)green * 0.8);

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Increases the red while decreasing the blue and green values
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void increaseRed (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (red < 250)
                    red ++;
                if (blue >= 0)
                    blue = (int)((double)blue * 0.8);
                if (green >= 5)
                    green = (int)((double)green * 0.8);

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Increases the green while decreasing the blue and red values
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void increaseGreen (BufferedImage bi)
    {
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (green < 250)
                    green ++;
                if (red >= 0)
                    red = (int)((double)red * 0.8);
                if (blue >= 5)
                    blue = (int)((double)blue * 0.8);

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Flips the image horizontally
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void flipHorizontal (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (xSize - x - 1, y, newColour);
            }
        }

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = newBi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Flips the image vertically.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void flipVertical (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, ySize - y - 1, newColour);
            }
        }

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = newBi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Rotates the image by 90 degrees clockwise
     * The size of the buffered image is changed
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static BufferedImage rotate90clock (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (ySize, xSize, 3);

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (ySize - y - 1, x, newColour);
            }
        }

        return newBi;
    }

    /**
     * Rotates the image by 90 degrees counterclockwise
     * The size of the buffered image is changed
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static BufferedImage rotate90counter (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (ySize, xSize, 3);

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (y, xSize - x - 1, newColour);
            }
        }

        return newBi;
    }

    /**
     * Rotates the image 180 degrees
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void rotate180 (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (xSize - x - 1, ySize - y - 1, newColour);
            }
        }

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = newBi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Sets the image to a greyscale version by averaging the red, green, and blue values.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void greyscale (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                int grey = (red + green + blue)/3;

                int newColour = packagePixel (grey, grey, grey, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Inverses the colours of the image
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void negative (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = 255 - rgbValues[1];
                int green = 255 - rgbValues[2];
                int blue = 255 -rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Increases the saturation of the image using HSB values.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void increaseSaturation (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];

                int red = (int)(rgbValues[1]);
                int green = (int)(rgbValues[2]);
                int blue = (int)(rgbValues[3]);

                // Converts the RGB values to HSB values
                float[] hsb = Color.RGBtoHSB(red, green, blue, null);
                
                // Because the saturation value is a value in between 0 and 1, it may not go above 1.
                // Otherwise, it make the pixel pure white.
                if (hsb[1] * 1.2 < 1)
                    hsb[1] *= 1.2;
                    
                rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                rgbValues = unpackPixel (rgb);
                
                red = (int)(rgbValues[1]);
                green = (int)(rgbValues[2]);
                blue = (int)(rgbValues[3]);

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }
    /**
     * Decreases the saturation of the image using HSB values
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void decreaseSaturation (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];

                int red = (int)(rgbValues[1]);
                int green = (int)(rgbValues[2]);
                int blue = (int)(rgbValues[3]);

                // Converts the RGB values to HSB values
                float[] hsb = Color.RGBtoHSB(red, green, blue, null);
                
                // Because the saturation value is a value in between 0 and 1, it may not go below 0.
                // Otherwise, it make the pixel pure black.
                if (hsb[1] / 1.2 > 0)
                    hsb[1] /= 1.2;
                    
                rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                rgbValues = unpackPixel (rgb);
                
                red = (int)(rgbValues[1]);
                green = (int)(rgbValues[2]);
                blue = (int)(rgbValues[3]);

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }
    
    /**
     * Changes the contrast of an image. Formulas from 
     *  https://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/image-processing-algorithms-part-5-contrast-adjustment/
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * @param c     How much contrast is changed. Accepted values range from -255 to 255.
     *              Negative decreases contrast, positive increases contrast.
     *              Magnitude of value determines how much contrast is changed. Larger magnitudes
     *              indicate a larger contrast change.
     */
    public static void changeContrast (BufferedImage bi, int c)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];

                float red = rgbValues[1];
                float green = rgbValues[2];
                float blue = rgbValues[3];
                
                // Formula for determining factor for which colours are multiplied
                float f = (float)(259 * (c + 255))/(float)(255 * (259 - c));
                
                // Formulas that determine the new rgb values
                red = f * (red - 128) + 128;
                green = f * (green - 128) + 128;
                blue = f * (blue - 128) + 128;
                
                // Ensures that rgb values remain in 0-255 range.
                if (red > 255)
                        red = 255;
                    else if (red < 0)
                        red = 0;
                if (green > 255)
                        green = 255;
                    else if (green < 0)
                        green = 0;
                if (blue > 255)
                        blue = 255;
                    else if (blue < 0)
                        blue = 0;

                int newColour = packagePixel ((int)red, (int)green, (int)blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }
    /**
     * Changes the hue of the image using HSB values
     * Red is shifted to orange, orange to yellow, yellow to green, green to cyan, cyan to blue,
     * blue to indigo, indigo to purple, purple to magenta, and magenta to red.
     * The image, however, gets more noise as the hues shift due to rounding errors of floats to ints.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void changeHue (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];

                int red = (int)(rgbValues[1]);
                int green = (int)(rgbValues[2]);
                int blue = (int)(rgbValues[3]);

                // Converts the RGB values to HSB values
                float[] hsb = Color.RGBtoHSB(red, green, blue, null);
                
                // The hue value can be changed by very small incriments and result in
                // very big differences, thus 0.05 is used.
                hsb[0] += 0.05;
                rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                rgbValues = unpackPixel (rgb);
                
                red = (int)(rgbValues[1]);
                green = (int)(rgbValues[2]);
                blue = (int)(rgbValues[3]);

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Blurs the image by taking a pixel and its surrounding pixels and setting the pixel's RGB
     * values to the average of the RGB values of the surrounding pixels.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void blur (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we blur everything (makes sure that when averaging the
        // surrounding pixel colours, it gets the real averages and not averages using new colours.
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);
                
                // Stores the RGB values of the pixel and the surrounding pixels
                ArrayList<Integer> rgbs = new ArrayList<Integer>();
                
                // Adds the pixel's RGB values to the ArrayList
                rgbs.add(bi.getRGB(x, y));
                
                // Gets the surrounding pixels' RGB values. Exceptions are made for pixels at
                // the edges and corners of the image.
                if(x > 0)
                {
                    rgbs.add(bi.getRGB(x - 1, y));
                    if (y > 0)
                        rgbs.add(bi.getRGB(x - 1, y - 1));
                    if (y < ySize - 1)
                        rgbs.add(bi.getRGB(x - 1, y + 1));
                }
                if(x < xSize - 1)
                {
                    rgbs.add(bi.getRGB(x + 1, y));
                    if (y > 0)
                        rgbs.add(bi.getRGB(x + 1, y - 1));
                    if (y < ySize - 1)
                        rgbs.add(bi.getRGB(x + 1, y + 1));
                }
                if (y > 0)
                    rgbs.add(bi.getRGB(x, y - 1));
                if (y < ySize - 1)
                    rgbs.add(bi.getRGB(x, y + 1));

                // Stores the unpacked RGB values of the pixel and the surrounding pixels
                int[][] rgbsValues = new int[rgbs.size()][4];
                
                for (int i = 0; i < rgbs.size(); i++)
                {
                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    rgbsValues[i] = unpackPixel (rgbs.get(i));
                }
                
                // Set to zero, other values are added to this.
                int alpha = 0;
                int red = 0;
                int green = 0;
                int blue = 0;
                
                // Gets the sum of all RGB values.
                for (int i = 0; i < rgbs.size(); i++)
                {
                    alpha += rgbsValues[i][0];
                    red += rgbsValues[i][1];
                    green += rgbsValues[i][2];
                    blue += rgbsValues[i][3];
                }

                // Averages the values by dividing the sums by the number of pixels used in the sum.
                alpha /= rgbs.size();
                red /= rgbs.size();
                green /= rgbs.size();
                blue /= rgbs.size();

                int newColour = packagePixel (red, green, blue, alpha);
                newBi.setRGB (x, y, newColour);
            }
        }

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = newBi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }
    /**
     * Blurs the image by taking a pixel and its surrounding pixels and setting the pixel's RGB
     * values to the average of the RGB values of the surrounding pixels. The blur will only occur
     * for groups of pixels that are similar in value and hue.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void backBlur (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we blur everything (makes sure that when averaging the
        // surrounding pixel colours, it gets the real averages and not averages using new colours.
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                
                // Stores the RGB values of the pixel and the surrounding pixels
                ArrayList<Integer> rgbs = new ArrayList<Integer>();
                
                // Adds the pixel's RGB values to the ArrayList
                rgbs.add(bi.getRGB(x, y));
                
                // Gets the surrounding pixels' RGB values. Exceptions are made for pixels at
                // the edges and corners of the image.
                if(x > 0)
                {
                    rgbs.add(bi.getRGB(x - 1, y));
                    if (y > 0)
                        rgbs.add(bi.getRGB(x - 1, y - 1));
                    if (y < ySize - 1)
                        rgbs.add(bi.getRGB(x - 1, y + 1));
                }
                if(x < xSize - 1)
                {
                    rgbs.add(bi.getRGB(x + 1, y));
                    if (y > 0)
                        rgbs.add(bi.getRGB(x + 1, y - 1));
                    if (y < ySize - 1)
                        rgbs.add(bi.getRGB(x + 1, y + 1));
                }
                if (y > 0)
                    rgbs.add(bi.getRGB(x, y - 1));
                if (y < ySize - 1)
                    rgbs.add(bi.getRGB(x, y + 1));

                // Stores the unpacked RGB values of the pixel and the surrounding pixels
                int[][] rgbsValues = new int[rgbs.size()][4];
                for (int i = 0; i < rgbs.size(); i++)
                {
                    // Call the unpackPixel method to retrieve the four integers for
                    // R, G, B and alpha and assign them each to their own integer
                    rgbsValues[i] = unpackPixel (rgbs.get(i));
                }

                // Set to zero, other values are added to this.
                int alpha = 0;
                int red = 0;
                int green = 0;
                int blue = 0;
                
                for (int i = 0; i < rgbs.size(); i++)
                {
                    alpha += rgbsValues[i][0];
                    red += rgbsValues[i][1];
                    green += rgbsValues[i][2];
                    blue += rgbsValues[i][3];
                }

                // Set to zero, other values are added to this.
                alpha /= rgbs.size();
                red /= rgbs.size();
                green /= rgbs.size();
                blue /= rgbs.size();

                int origRgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (origRgb);

                int origAlpha = rgbValues[0];
                int origRed = rgbValues[1];
                int origGreen = rgbValues[2];
                int origBlue = rgbValues[3];
                
                // The biggest allowed difference
                int m = 10;
                
                // Sets the new colour to the original colour by default
                int newColour = packagePixel (origRed, origGreen, origBlue, origAlpha);
                
                // If the differences in the colours is not too big, the new colour is used.
                if (Math.abs(origAlpha - alpha) < m && Math.abs(origRed - red) < m && 
                Math.abs(origGreen - green) < m && Math.abs(origBlue - blue) < m)
                    newColour = packagePixel (red, green, blue, alpha);
                
                newBi.setRGB (x, y, newColour);
            }
        }

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = newBi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Creates a vignette on the image. A vignette adds reduces the image's brightness or saturation
     * near the edges.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void vignette (BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        int xMid = (xSize - 1)/2;
        int yMid = (ySize - 1)/2;

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int dx = Math.abs(x - xMid);
                int dy = Math.abs(y - yMid);
                int d = (int)(Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)));
                double i = 1.75 - ((double)d*2 / ((double)xMid + (double)yMid));

                if (i > 1)
                {
                    i = 1;
                }

                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);

                int alpha = rgbValues[0];
                int red = (int)(rgbValues[1] * i);
                int green = (int)(rgbValues[2] * i);
                int blue = (int)(rgbValues[3] * i);

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param bi                
     * 
     * @return BufferedImage    Integer representing 32 bit integer pixel ready
     *                          for BufferedImage
     */
    public static BufferedImage deepCopy (BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultip = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultip, null);
    }

    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }

    /**
     * Takes in a BufferedImage and returns a GreenfootImage.
     *
     * @param newBi The BufferedImage to convert.
     *
     * @return GreenfootImage A GreenfootImage built from the BufferedImage provided.
     */
    public static GreenfootImage createGreenfootImageFromBI (BufferedImage newBi)
    {
        GreenfootImage returnImage = new GreenfootImage (newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D)backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);
        return returnImage;
    }
}
