public abstract class Emitter extends Entity {
    private final AbstractForce MY_FORCE;

    public Emitter(int x, int y, int index, AbstractForce force)
    {
        super(x,y,index);
        MY_FORCE = force;
    }

    @Override
    protected void pre_motion() {
        PixelPool.applyForce(this, MY_FORCE);
        //acceleration.mult(0);
        //velocity.mult(0);
    }
}
