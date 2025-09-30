package net.jdg.disruption.entity.impl;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class StareAngerableEntity extends PathfinderMob {
    private int eyeStareAngerAmount = 1;
    private int stareAngerAmount = 1;
    private int angerDecayAmount = 1;
    private int anger = 0;
    private boolean shouldTickAnger = true;
    private boolean shouldAngerDecay = true;

    public StareAngerableEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public int getEyeStareAngerAmount() {
        return eyeStareAngerAmount;
    }

    public int getStareAngerAmount() {
        return stareAngerAmount;
    }

    public void setEyeStareAngerAmount(int eyeStareAngerAmount) {
        this.eyeStareAngerAmount = eyeStareAngerAmount;
    }

    public void setStareAngerAmount(int stareAngerAmount) {
        this.stareAngerAmount = stareAngerAmount;
    }

    public void setShouldTickAnger(boolean shouldTickAnger) {
        this.shouldTickAnger = shouldTickAnger;
    }

    public void setShouldAngerDecay(boolean shouldAngerDecay) {
        this.shouldAngerDecay = shouldAngerDecay;
    }

    public boolean shouldTickAnger() {
        return this.shouldTickAnger;
    }

    public boolean shouldAngerDecay() {
        return shouldAngerDecay;
    }

    public int getAngerDecayAmount() {
        return angerDecayAmount;
    }

    public void setAngerDecayAmount(int angerDecayAmount) {
        this.angerDecayAmount = angerDecayAmount;
    }

    public int getAnger() {
        return anger;
    }

    public void setAnger(int anger) {
        this.anger = anger;
    }

    public boolean isPlayerStaringAtEyes(Player player) {
        Vec3 vec3 = player.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > 1.0 - 0.025 / d0 && player.hasLineOfSight(this);
    }

    public boolean isPlayerStaringAt(Player player) {
        return player.hasLineOfSight(this);
    }

    public void tickAnger() {
        if (shouldTickAnger) {
            //noinspection resource
            for (Player player : level().players()) {
                var normalStare = isPlayerStaringAt(player);
                var eyeStare = isPlayerStaringAtEyes(player);
                if (normalStare || eyeStare) anger += (normalStare && ! eyeStare) ? stareAngerAmount : eyeStareAngerAmount;
                else if (shouldAngerDecay) anger = Math.max(0, anger - angerDecayAmount);
            }
        }
    }
}
