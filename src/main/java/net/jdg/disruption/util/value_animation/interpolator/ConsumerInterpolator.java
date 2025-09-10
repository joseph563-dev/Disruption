package net.jdg.disruption.util.value_animation.interpolator;


import net.jdg.disruption.util.value_animation.Easing;

import java.util.function.Consumer;

public class ConsumerInterpolator extends Interpolater<Integer> {
    private final Consumer<Float>[] funcs;
    private final IntInterpolator interpolator;

    @SafeVarargs
    public ConsumerInterpolator(Easing easing, Consumer<Float>... funcs) {
        super(null);
        this.funcs = funcs;
        this.interpolator = new IntInterpolator(easing);
    }

    @SafeVarargs
    public ConsumerInterpolator(Consumer<Float>... funcs) {
        super(null);
        this.funcs = funcs;
        this.interpolator = new IntInterpolator();
    }

    @Override
    public Integer withEasing(Easing easing, float delta, Integer unused, Integer unused2) {
        var i = interpolator.withEasing(easing, delta,0, funcs.length - 1);
        funcs[i].accept(delta);
        return 0;
    }

    @Override
    public Integer interpolate(float delta, Integer unused, Integer unused2) {
        var i = interpolator.interpolate(delta,0, funcs.length - 1);
        funcs[i].accept(delta);
        return 0;
    }
}
