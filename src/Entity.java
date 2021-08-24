import java.awt.image.BufferedImage;

public abstract class Entity {
    protected final PVector location,
            velocity = new PVector(0,0),
            acceleration = new PVector(0,0);

    public final int index;

    public Entity(int x, int y, int index)
    {
        location = new PVector(x,y);
        this.index = index;
    }

    public void tick() {
        pre_motion();
        move();
        post_motion();
    }

    protected void pre_motion(){}

    protected void post_motion(){}

    public void applyForce(PVector force) {
        //Newton’s second law, but with force accumulation. We now add each force to acceleration, one at a time.
        PVector f = force.get();
        f.div(getMass());
        acceleration.add(force);
    }

    protected void move()
    {
        if (location.x > PixelPool.getBaseImage().getWidth()-1 || location.x < 0)
        {
            velocity.x *= - getBounciness();
        }
        if (location.y > PixelPool.getBaseImage().getHeight()-1 || location.y < 0)
        {
            velocity.y *= - getBounciness();
        }
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
    }

    public abstract void render(BufferedImage img);

    public int getX()
    {
        return (int) clamp(location.x,0,PixelPool.getBaseImage().getWidth()-1);
    }

    protected static float clamp(float num, float lowBound, float highBound)
    {
        if (num < lowBound)
        {
            return lowBound;
        }
        else return Math.min(num, highBound);
    }

    public int getY()
    {
        return (int) clamp(location.y,0,PixelPool.getBaseImage().getHeight()-1);
    }
    protected abstract float getMass();

    protected abstract float getBounciness();

    protected void despawn()
    {
        PixelPool.remove(this);
    }
}
