import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.lang.Math;
//import java.util.Random;

public class PixelPool  {
    private static BufferedImage baseImage;

    private static final LinkedList<Entity> entities = new LinkedList<>();
    private static final LinkedList<Entity> death_queue = new LinkedList<>();
    public static final int SCALE = 10;

    private static boolean queue_full = false;

    public PixelPool(BufferedImage img) {
        setBaseImage(img);
    }

    public static BufferedImage getCurrentImage()
    {
        BufferedImage img = new BufferedImage(baseImage.getWidth(),
                        baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        PVector wind = new PVector((float) 0.001,0);
        PVector gravity = new PVector(0, (float) 0.02);
        for (Entity e : death_queue)
        {
            entities.remove(e);
            System.out.println("Success " + e.index);
        }
        death_queue.clear();

        for (Entity entity : entities) {
            entity.render(img);
            entity.applyForce(gravity);
            //entity.applyForce(wind);
        }
        return img;
    }

    public static void applyForce(Emitter source, AbstractForce MY_FORCE)
    {
        for (Entity entity : entities) {
            if (entity != source) {
                entity.applyForce(MY_FORCE.on(source, entity));
            }
        }
    }

    public static void remove(Entity e)
    {
        death_queue.add(e);
    }

    public static BufferedImage getBaseImage()
    {
        return baseImage;
    }

    public static void setBaseImage(BufferedImage img)
    {
        if (img == null)
        {
            try {
                setBaseImage(RandomImage.get());
                return;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        baseImage = img;
        entities.clear();
        int i = 0;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (!(x % SCALE ==0 && y % SCALE == 0))
                {
                    i++;
                    continue;
                }
                //Retrieving contents of a pixel
                int pixel = img.getRGB(x,y);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values

                entities.add(new SmartPixel(x,y, i, color));


                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                i++;
            }
        }
        entities.add(new BlackHole(320,160,i,17));

    }



    public static void tick()
    {
        for (Entity obj : entities) {
            obj.tick();
        }
    }

}
