import java.awt.Color;

public class SmartPixel implements TickObject {

    private Color color;
    private float x, y;

    public SmartPixel(int x, int y, Color color) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick() {
        float red = (float) color.getRed()/255;
        float blue = (float) color.getBlue()/255;
        float green = (float) color.getGreen()/255;
        x += red;
        y += green;

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
        //return new Color(0,(x)/(x+y),y/(x+y));
        //return Color.RED;
        return color;
    }
}
