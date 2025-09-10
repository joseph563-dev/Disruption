package net.jdg.disruption.util.value_animation.interpolator;


import net.jdg.disruption.util.value_animation.Easing;


/**
 * Allows for the interpolation of any datatype or object
 * @param <T> type to interpolate
 */
public abstract class Interpolater<T> {

    public final Easing easing;

    public Interpolater(Easing easing) {
        this.easing = easing;
    }

    public Interpolater() {
        this.easing = Easing.LINEAR;
    }

    public abstract T interpolate(float delta, T min, T max);

    public abstract T withEasing(Easing easing, float delta, T min, T max);
}
