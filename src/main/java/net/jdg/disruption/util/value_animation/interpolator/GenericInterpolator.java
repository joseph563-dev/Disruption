package net.jdg.disruption.util.value_animation.interpolator;


import net.jdg.disruption.util.value_animation.Easing;

public class GenericInterpolator<T> extends Interpolater<T> {
    private final T[] objs;
    private final IntInterpolator interpolator;

    @SafeVarargs
    public GenericInterpolator(Easing easing, T... objs) {
        super(null);
        this.objs = objs;
        this.interpolator = new IntInterpolator(easing);
    }

    @SafeVarargs
    public GenericInterpolator(T... objs) {
        super(null);
        this.objs = objs;
        this.interpolator = new IntInterpolator();
    }

    @Override
    public T withEasing(Easing easing, float delta, T unused, T unused2) {
        var i = interpolator.withEasing(easing, delta,0, objs.length - 1);
        return objs[i];
    }

    @Override
    public T interpolate(float delta, T unused, T unused2) {
        var i = interpolator.interpolate(delta,0, objs.length - 1);
        return objs[i];
    }
}
