package net.jdg.disruption.util.value_animation;

import net.jdg.disruption.util.value_animation.interpolator.Interpolater;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.LightningBoltRenderer;

import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("unused")
public class VarAnimation<T> {
    
    public record Pair<A, B>(A a, B b) {}
    
    private int keyframeCount = 0;
    private HashMap<Integer, Pair<Interpolater<T>, T>> keyframes;
    private final boolean loops;
    private int keyframe = 0;
    private int lastKeyframeLength = 0;
    private int keyframeLength = 0;
    private int keyframeTime = 0;
    private int length = 0;
    private int time = 0;
    private int currentKeyframeStartTime = 0;
    private int nextKeyframeStartTime = 0;
    private float currentKeyframeProgress = 0;
    private float animationProgress = 0;
    private boolean playing = false;
    private Interpolater<T> currentInterpolator;
    private T currentValue;
    private T lastValue;
    private boolean baked = false;
    private T[] bakedFrames;
    private Interpolater<T> genericInterpolator;

    public VarAnimation(boolean loops) {
        this.loops = loops;
        this.keyframes = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public final <I extends Interpolater<?>> VarAnimation<T> key(int time, I interpolater, T value) {
        if (baked) throw new RuntimeException("Attempted to add a keyframe to baked animation");
        if (genericInterpolator == null) genericInterpolator = (Interpolater<T>) interpolater;
        keyframeCount += 1;
        keyframes.put(time, (Pair<Interpolater<T>, T>) new Pair<>(interpolater, value));
        length = Math.max(time, length);
        onKeyframeAdded((Interpolater<T>) interpolater, value, time);
        return this;
    }

    /***
     * Bakes the animation for better performance, but clears all keyframe data and makes the animation read only.
     * @return itself
     */
    @SuppressWarnings("unchecked")
    public final VarAnimation<T> bake() {
        if (baked) return this;
        bakedFrames = (T[]) new Object[length + 1];
        for (int i = 0; i < length + 1; ++i) {
            bakedFrames[i] = getValueAtTime(i);
        }
        baked = true;
        stop(false);
        keyframes.clear();
        keyframes = null;
        return this;
    }

    /**
     * Invoke once per game tick
     */
    public final void tick() {
        if (playing) {
            if (!baked) {
                var currentKeyframeValue = keyframes.get(currentKeyframeStartTime).b();
                var nextKeyframeValue = keyframes.get(nextKeyframeStartTime).b();
                currentInterpolator = keyframes.get(currentKeyframeStartTime).a();
                animationProgress = (float) time / length;
                onTickStart(false, currentInterpolator, currentValue, time);
                lastValue = currentValue != null ? currentValue : getIndexedKeyValue(0).b();
                currentValue = currentInterpolator.interpolate(currentKeyframeProgress, currentKeyframeValue, nextKeyframeValue);
                if (keyframeTime < keyframeLength) {
                    currentKeyframeProgress = (float) (time - currentKeyframeStartTime) / keyframeLength;
                    keyframeTime += 1;
                } else {
                    keyframeTime = 0;
                    var lastLength = keyframeLength;
                    var nextCurrentLength = getIndexedKeyTime(keyframe + 1);
                    currentKeyframeStartTime = nextCurrentLength;
                    keyframeLength = nextCurrentLength - lastKeyframeLength;
                    lastKeyframeLength = lastLength;
                    keyframe += 1;
                    if (nextKeyframeExists(keyframe)) {
                        nextKeyframeStartTime = getIndexedKeyTime(keyframe + 1);
                    }
                }
                if (loops) time = (time + 1) % (length + 1);
                else if (time < length) time += 1;
                if (time == length) {
                    onAnimationEnd(baked, currentInterpolator, currentValue, length);
                    if (!loops) stop();
                }
                onTickEnd(false, currentInterpolator, currentValue, time);
            } else {
                onTickStart(true, null, currentValue, time);
                lastValue = currentValue != null ? currentValue : bakedFrames[0];
                currentValue = bakedFrames[time];
                if (loops) time = (time + 1) % (length + 1);
                else if (time < length) time += 1;
                if (time == length) {
                    onAnimationEnd(true, null, currentValue, length);
                    if (!loops) stop();
                }
                onTickEnd(true, null, currentValue, time);
            }
        }
    }

    /**
     * Blends animations with the provided easing type
     * @param min Lower blend value
     * @param max Upper blend value
     * @param weight Blend bias
     * @param blendEasing Easing type to blend with
     * @return Blended value
     */
    public T blend(VarAnimation<T> min, VarAnimation<T> max, float weight, Easing blendEasing) {
        if (min.keyframeCount < 2 || max.keyframeCount < 2) return null;
        return genericInterpolator.withEasing(blendEasing, weight, min.get(), max.get());
    }

    /**
     * Blends animations linearly
     * @param min Lower blend value
     * @param max Upper blend value
     * @param weight Blend bias
     * @return Blended value
     */
    public T blend(VarAnimation<T> min, VarAnimation<T> max, float weight) {
        return blend(min, max, weight, Easing.LINEAR);
    }

    public Pair<Integer, Integer> getKeyPair(int index) {
        if (baked) return null;
        if (keyframeCount < 2) return null;
        if (nextKeyframeExists(index)) {
            return new Pair<>(
                    keyframes.keySet().toArray(new Integer[]{})[index],
                    keyframes.keySet().toArray(new Integer[]{})[index + 1]
            );
        } else {
            return (index >= keyframeCount - 1) ?
                    new Pair<>(
                            keyframes.keySet().toArray(new Integer[]{})[keyframeCount - 2],
                            keyframes.keySet().toArray(new Integer[]{})[keyframeCount - 1]
                    ) :
                    new Pair<>(
                            keyframes.keySet().toArray(new Integer[]{})[0],
                            keyframes.keySet().toArray(new Integer[]{})[1]
            );
        }
    }

    public Pair<Integer, Integer> getClosestKeyPair(int time) {
        if (!baked) {
            var keyTimes = keyframes.keySet().toArray(new Integer[]{});
            Arrays.sort(keyTimes);
            for (int i = 0; i < keyframeCount - 1; ++i) {
                var minKeytime = keyTimes[i];
                var maxKeytime = keyTimes[i + 1];
                if (minKeytime - time >= 0) {
                    return new Pair<>(keyTimes[i], keyTimes[i + 1]);
                }
                if (maxKeytime - time >= 0) {
                    return new Pair<>(keyTimes[i], keyTimes[i + 1]);
                }
            }
        }
        return null;
    }

    public T getValueAtTime(int time) {
        if (baked) return bakedFrames[time];
        else {
            var pair = getClosestKeyPair(time);
            if (pair != null) {
                var min = pair.a();
                var max = pair.b();
                var left = keyframes.get(min);
                var right = keyframes.get(max);
                var minVal = left.b();
                var maxVal = right.b();

                var interpolator = left.a();
                var delta = (float) (time - min) / (max - min);
                return interpolator.interpolate(delta, minVal, maxVal);
            } else {
                return null;
            }
        }
    }

    public float getDeltaForTime(int time) {
        if (!baked) {
            var pair = getClosestKeyPair(time);
            if (pair != null) {
                var min = pair.a();
                var max = pair.b();
                return (float) (time - min) / max;
            }
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    public Pair<Interpolater<T>, T> getIndexedKeyValue(int index) {
        if (baked) return null;
        else return (Pair<Interpolater<T>, T>) keyframes.values().toArray(new Pair[]{})[index];
    }

    public int getIndexedKeyTime(int index) {
        if (baked) return 0;
        else return keyframes.keySet().toArray(new Integer[]{})[index];
    }

    public void stop(boolean notify) {
        if (keyframeCount < 2) return;
        if (notify) onStopped(baked, currentInterpolator, currentValue, time);

        time = 0;
        playing = false;

        if (!baked) {
            currentInterpolator = genericInterpolator;
            currentKeyframeProgress = 0;
            lastKeyframeLength = 0;
            keyframeTime = 0;
            keyframe = 0;
            keyframeLength = getIndexedKeyTime(1);
            currentValue = getIndexedKeyValue(0).b();
            currentKeyframeStartTime = getIndexedKeyTime(0);
            nextKeyframeStartTime = getIndexedKeyTime(1);
        }
    }

    public void stop() {
        stop(true);
    }

    public void pause() {
        playing = false;
        if (keyframeCount > 1) onPaused(baked, currentInterpolator, currentValue, time);
    }

    public void play(boolean fromStart, boolean notifyStopped) {
        if (keyframeCount < 2) return;
        if (fromStart) {
            stop(notifyStopped);
        }
        playing = true;

        onPlayed(baked, currentInterpolator, currentValue, time);
    }

    public void play(boolean fromStart) {
        play(fromStart, true);
    }

    public void play() {
        play(false, true);
    }

    public boolean nextKeyframeExists(int index) {

        return index > 0 && index < keyframeCount - 1;
    }

    public boolean isPlaying() {
        return playing && keyframeCount > 1;
    }

    public boolean loops() {
        return loops;
    }

    public int getLength() {
        return length;
    }

    public int getTime() {
        return time;
    }

    /**
     * Use before invoking {@link VarAnimation#tick()} for value accuracy.
     * @param frameDelta inter-tick blending
     * @return Current value
     */
    public T get(float frameDelta) {
        if (keyframeCount < 2) {
            return null;
        } else {
            if (lastValue == null || currentValue == null) return baked ? bakedFrames[0] : getIndexedKeyValue(0).b();
            return currentValue;
        }
    }
    /**
     * Use before invoking {@link VarAnimation#tick()} for value accuracy.
     * Doesn't interpolate between animation frames
     * @return Current value
     */
    public T get() {
        return get(0);
    }

    public float getAnimationProgress() {
        return animationProgress;
    }

    public float getCurrentKeyframeProgress() {
        return baked ? 0 : currentKeyframeProgress;
    }

    public int getCurrentKeyframeStartTime() {
        return baked ? 0 : currentKeyframeStartTime;
    }

    public int getKeyframe() {
        return baked ? 0 : keyframe;
    }

    public int getKeyframeLength() {
        return baked ? 0 : keyframeLength;
    }

    public int getKeyframeTime() {
        return baked ? 0 : keyframeTime;
    }

    public int getLastKeyframeLength() {
        return baked ? 0 : lastKeyframeLength;
    }

    public int getNextKeyframeStartTime() {
        return baked ? 0 : nextKeyframeStartTime;
    }
    /**
     * @param interpolater current interpolater, null if the animation is baked
     * @param value the animations current interpolated value
     * @param time current tick the animation is on
     **/
    public void onKeyframeAdded(Interpolater<T> interpolater, T value, int time) {}
    /**
     * @param interpolater current interpolater, null if the animation is baked
     * @param value the animations current interpolated value
     * @param time current tick the animation is on
     **/
    public void onStopped(boolean baked, Interpolater<T> interpolater, T value, int time) {}
    /**
     * @param interpolater current interpolater, null if the animation is baked
     * @param value the animations current interpolated value
     * @param time current tick the animation is on
     **/
    public void onPaused(boolean baked, Interpolater<T> interpolater, T value, int time) {}
    /**
     * @param interpolater current interpolater, null if the animation is baked
     * @param value the animations current interpolated value
     * @param time current tick the animation is on
     **/
    public void onPlayed(boolean baked, Interpolater<T> interpolater, T value, int time) {}
    /**
     * @param interpolater current interpolater, null if the animation is baked
     * @param value the animations current interpolated value
     * @param time current tick the animation is on
     **/
    public void onAnimationEnd(boolean baked, Interpolater<T> interpolater, T value, int time) {}
    /**
     * @param interpolater current interpolater, null if the animation is baked
     * @param value the animations current interpolated value
     * @param time current tick the animation is on
     **/
    public void onTickStart(boolean baked, Interpolater<T> interpolater, T value, int time) {}
    /**
     * @param interpolater current interpolater, null if the animation is baked
     * @param value the animations current interpolated value
     * @param time current tick the animation is on
     **/
    public void onTickEnd(boolean baked, Interpolater<T> interpolater, T value, int time) {}
}
