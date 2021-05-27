import java.awt.*;

public class SmartPixel {

    private Color color;
    private float x, y, velocity_x = 0, velocity_y = 0;
    private int ticks = 0;
    private int index = 0;

    public SmartPixel(int x, int y, Color color,int index) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.index = index;
        float red = (float) color.getRed()/255;
        float blue = (float) color.getBlue()/255;
        float green = (float) color.getGreen()/255;
        velocity_x += red - blue;
        //velocity_y += green;
    }

    public Rectangle getBounds()
    {
        return new Rectangle((int)x-1,(int) y-1,3,3);
    }

    public void tick() {
        //final int C = 367693;

        //velocity_y += ((float) 255*3 - getColor().getBlue()+getColor().getGreen()+getColor().getRed())/(255*1000);
        //velocity_y -= (float) color.getRed()/855;

        if (x > PixelPool.getBaseImage().getWidth()-1 || x < 0)
        {
            velocity_x *= - getBounciness();
        }
        if (y > PixelPool.getBaseImage().getHeight()-1 || y < 0)
        {
            velocity_y *= - getBounciness();
        }

//        int collisions = 0;
//        if (ticks % C == index % C) {
//            for (SmartPixel pixel : PixelPool.pixels) {
//                if (getBounds().intersects(pixel.getBounds())) {
//                    collide(pixel);
//                    collisions++;
//                }
//                if (collisions > 3)
//                {
//                    break;
//                }
//            }
//        }

        move();

        //ticks++;
    }

    public boolean overlaps(SmartPixel other_pixel)
    {
        return (getX() == other_pixel.getX() && getY() == other_pixel.getY());
    }

    private boolean isVeryGreen()
    {
        return (getColor().getGreen() > getColor().getRed() + getColor().getBlue());
    }

    private float getBounciness()
    {
        return (float) getColor().getGreen()/275 + color.getBlue()/600 + color.getRed()/400;
    }

    private void move()
    {
        this.x += velocity_x;
        this.y += velocity_y;

        // collision check

        //PixelPool.scan(this);
    }

    public float get_velocity_x() {
        return velocity_x;
    }

    public float get_velocity_y() {
        return velocity_y;
    }

    public void set_velocity_x(float velocity_x1)
    {
        velocity_x = velocity_x1;
    }
    public void set_velocity_y(float velocity_y1)
    {
        velocity_y = velocity_y1;
    }


    public void collide(SmartPixel other_pixel)
    {
        float temp_x = velocity_x;
        velocity_x += other_pixel.get_velocity_x()/color.getBlue();
        other_pixel.set_velocity_x(other_pixel.get_velocity_x() + temp_x/other_pixel.getColor().getBlue());


        float temp_y = velocity_y;
        velocity_y += other_pixel.get_velocity_y()/color.getBlue();
        other_pixel.set_velocity_y(other_pixel.get_velocity_y() + temp_y/other_pixel.getColor().getBlue());

        if (isVeryGreen() && !other_pixel.isVeryGreen())
        {
            other_pixel.setColor(other_pixel.getColor().darker());
        }
    }

    public void setColor(Color newColor)
    {
        color = newColor;
    }

    private void elastic_collision(SmartPixel other_pixel)
    {
        velocity_x = (
                (getMass() - other_pixel.getMass())/
                        (getMass()+ other_pixel.getMass())*velocity_x
                        + other_pixel.get_velocity_x() * 2 * other_pixel.getMass() /
                        (getMass() + other_pixel.getMass()));

        velocity_y = (
                (getMass() - other_pixel.getMass())/
                        (getMass()+ other_pixel.getMass())*velocity_y
                        + other_pixel.get_velocity_y() * 2 * other_pixel.getMass() /
                        (getMass() + other_pixel.getMass()));

    }

    public float getMass()
    {
        return (color.getRed()+color.getBlue()+color.getGreen())/255;
    }

    public int getX()
    {
        return (int) clamp(x,0,PixelPool.getBaseImage().getWidth()-1);
    }

    private float clamp(float num, float lowBound, float highBound)
    {
        if (num < lowBound)
        {
            return lowBound;
        }
        else if (num > highBound)
        {
            return highBound;
        }
        else
        {
            return num;
        }
    }

    public int getY()
    {
        return (int) clamp(y,0,PixelPool.getBaseImage().getHeight()-1);
    }

    public Color getColor()
    {
        return color;
    }
}
