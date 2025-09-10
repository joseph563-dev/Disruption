package net.jdg.disruption.util.value_animation.interpolator;


import net.jdg.disruption.util.value_animation.Easing;

public class IntInterpolator extends Interpolater<Integer> {

    public static final IntInterpolator linear = new IntInterpolator();

    public IntInterpolator(Easing easing) {
        super(easing);
    }

    public IntInterpolator() {
        super();
    }

    @Override
    public Integer withEasing(Easing easing, float delta, Integer min, Integer max) {
        return (int) (Math.floor(easing.ease(delta, min, max, 1.0f)));
    }
    @Override
    public Integer interpolate(float delta, Integer min, Integer max) {
        return (int) (Math.floor(easing.ease(delta, min, max, 1.0f)));
    }
}
