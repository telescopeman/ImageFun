import javax.imageio.ImageIO;
import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class PixelPool implements TickObject {
    private static BufferedImage baseImage;

    private static LinkedList<SmartPixel> pixels = new LinkedList<>();

    public PixelPool(BufferedImage img) {
        setBaseImage(img);
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
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x,y);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values

                pixels.add(new SmartPixel(x,y, color));


                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
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
