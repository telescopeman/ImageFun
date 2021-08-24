import java.awt.*;
import java.awt.image.BufferedImage;

public class SmartPixel extends Entity {

    private Color color;
    private int ticks = 0;

    public SmartPixel(int x, int y, int index, Color color) {
        super(x,y,index);
        this.color = color;
        float red = (float) color.getRed()/255;
        float blue = (float) color.getBlue()/255;
        float green = (float) color.getGreen()/255;
        velocity.x = (red - blue);

        //velocity_y += green;
    }

    public void render(BufferedImage img)
    {
        int scale = PixelPool.SCALE;
        if (scale == 1.0) {
            img.setRGB(getX(), getY(), getColor().getRGB());
        }
        else
        {
            Graphics2D g = (Graphics2D) img.getGraphics();
            g.setColor(getColor());
            g.fillRect(getX(),getY(),scale,scale);

        }
    }

    public boolean overlaps(SmartPixel other_pixel)
    {
        return (getX() == other_pixel.getX() && getY() == other_pixel.getY());
    }

    private boolean isVeryGreen()
    {
        return (getColor().getGreen() > getColor().getRed() + getColor().getBlue());
    }

    protected float getBounciness()
    {
        //System.out.println("bounce");
        return clamp((float) getColor().getGreen()/275 +
                        (float) color.getBlue()/600 +
                        (float) color.getRed()/400,
                0,1f);
    }

//    public void collide(SmartPixel other_pixel)
//    {
//        float temp_x = velocity_x;
//        velocity_x += other_pixel.get_velocity_x()/color.getBlue();
//        other_pixel.set_velocity_x(other_pixel.get_velocity_x() + temp_x/other_pixel.getColor().getBlue());
//
//
//        float temp_y = velocity_y;
//        velocity_y += other_pixel.get_velocity_y()/color.getBlue();
//        other_pixel.set_velocity_y(other_pixel.get_velocity_y() + temp_y/other_pixel.getColor().getBlue());
//
//        if (isVeryGreen() && !other_pixel.isVeryGreen())
//        {
//            other_pixel.setColor(other_pixel.getColor().darker());
//        }
//    }

    public void setColor(Color newColor)
    {
        color = newColor;
    }

//    private void elastic_collision(SmartPixel other_pixel)
//    {
//        velocity_x = (
//                (getMass() - other_pixel.getMass())/
//                        (getMass()+ other_pixel.getMass())*velocity_x
//                        + other_pixel.get_velocity_x() * 2 * other_pixel.getMass() /
//                        (getMass() + other_pixel.getMass()));
//
//        velocity_y = (
//                (getMass() - other_pixel.getMass())/
//                        (getMass()+ other_pixel.getMass())*velocity_y
//                        + other_pixel.get_velocity_y() * 2 * other_pixel.getMass() /
//                        (getMass() + other_pixel.getMass()));
//
//    }

    protected float getMass()
    {
        return ((float)color.getRed()+color.getBlue()+color.getGreen())/255;
    }

    public Color getColor()
    {
        return color;
    }
}
