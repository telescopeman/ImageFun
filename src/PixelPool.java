import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelPool {
    private BufferedImage baseImage,
        currentImage;

    public PixelPool(BufferedImage img) {
        setBaseImage(img);
    }

    private void setCurrentImage(BufferedImage img)
    {
        currentImage = img;
    }


    public BufferedImage getBaseImage()
    {
        return baseImage;
    }

    public void setBaseImage(BufferedImage img)
    {
        baseImage = img;
        setCurrentImage(copyImage(img));
    }

    public void tick()
    {
        BufferedImage tempImage = copyImage(currentImage);

    }

    private static BufferedImage copyImage(BufferedImage img)
    {
        BufferedImage copyOfImage =
                new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        return copyOfImage;
    }

    public BufferedImage getCurrentImage()
    {
        return currentImage;
    }
}
