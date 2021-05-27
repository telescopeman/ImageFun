import javax.imageio.ImageIO;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class PixelPool  {
    private static BufferedImage baseImage;

    public static LinkedList<SmartPixel> pixels = new LinkedList<>();

    public PixelPool(BufferedImage img) {
        setBaseImage(img);
    }

    /**
     * Checks for overlapping pixels.
     * @param x
     * @param y
     * @param smartPixel
     */
    public static void scan(SmartPixel signalling_pixel) {
        for (Iterator<SmartPixel> iterator2 = pixels.iterator(); iterator2.hasNext();) {
            SmartPixel pixel = iterator2.next();
            if (pixel == signalling_pixel)
            {
                continue;
            } else if (pixel.overlaps(signalling_pixel)) {
                signalling_pixel.collide(pixel);
            }
            //img.setRGB(pixel.getX(),pixel.getY(),pixel.getColor().getRGB());
        }

    }

    public BufferedImage getCurrentImage()
    {
        BufferedImage img = null;
        img = new BufferedImage(baseImage.getWidth(),
                        baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (Iterator<SmartPixel> iterator2 = pixels.iterator(); iterator2.hasNext();) {
            SmartPixel pixel = iterator2.next();
            //System.out.println(pixel.getX() + "x, " + pixel.getY() + "y" +
            //        pixel.getColor().getRGB());
            img.setRGB(pixel.getX(),pixel.getY(),pixel.getColor().getRGB());
        }


        return img;
    }

    public static BufferedImage getBaseImage()
    {
        return baseImage;
    }

    public void setBaseImage(BufferedImage img)
    {
        baseImage = img;
        pixels.clear();
        int i = 0;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x,y);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values

                pixels.add(new SmartPixel(x,y, color,i));


                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                i++;
            }
        }
    }

    public void tick()
    {
            for (Iterator<SmartPixel> iterator2 = pixels.iterator(); iterator2.hasNext();) {
                SmartPixel obj = iterator2.next();
                obj.tick();
            }
    }

}
