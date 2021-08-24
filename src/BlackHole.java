import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackHole extends Emitter {
    private final float SIZE;
    public BlackHole(int x, int y, int index, float size) {
        super(x, y, index, new AbstractForce() {
            @Override
            public PVector on(Emitter source, Entity e) {
                PVector force = source.location.get();
                if (force == source.location)
                {
                    return new PVector(0,0);
                }
                force.sub(e.location);
                float distance = force.mag();
                distance = clamp(distance,5.0f, 25.0f);
                force.normalize();
                float strength = (source.getMass() * e.getMass()) / (distance * distance);
                force.mult(strength);
                return force;
            }
        });
        velocity.x = 5;
        this.SIZE = size;
    }

    protected void move()
    {

    }

    @Override
    public void render(BufferedImage img) {
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.BLACK);
        g.fillOval(getX(),getY(),(int) SIZE,(int)SIZE);
    }

    @Override
    protected float getMass() {
        return SIZE;
    }

    @Override
    protected float getBounciness() {
        return 0.5f;
    }
}
