package net.jdg.disruption.util.value_animation.interpolator;


import net.jdg.disruption.util.value_animation.Easing;

import java.util.function.Function;

public class FunctionInterpolator<T> extends Interpolater<T> {

    private final Function<Float, T>[] funcs;
    private final IntInterpolator interpolator;

    @SafeVarargs
    public FunctionInterpolator(Function<Float, T>... funcs) {
        super(null);
        this.funcs = funcs;
        this.interpolator = new IntInterpolator();
    }

    @SafeVarargs
    public FunctionInterpolator(Easing easing, Function<Float, T>... funcs) {
        super(null);
        this.funcs = funcs;
        this.interpolator = new IntInterpolator(easing);
    }

    @Override
    public T interpolate(float delta, T unused, T unused2) {
        var i = interpolator.interpolate(delta,0, funcs.length - 1);
        return funcs[i].apply(delta);
    }

    @Override
    public T withEasing(Easing easing, float delta, T unused, T unused2) {
        var i = interpolator.withEasing(easing, delta,0, funcs.length - 1);
        return funcs[i].apply(delta);
    }
}
