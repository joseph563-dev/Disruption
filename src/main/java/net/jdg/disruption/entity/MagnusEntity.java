package net.jdg.disruption.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;



public class MagnusEntity extends Monster {


    public MagnusEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 3000.0, true));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50)
                .add(Attributes.ATTACK_DAMAGE, 90)
                .add(Attributes.FOLLOW_RANGE, 10000000)
                .add(Attributes.ATTACK_SPEED, 9000);
    }

    /**
     * This is an entity tick, this runs every tick when your entity is spawned in and loaded
     * A LOT of logic is done here, but for now we have - this.setPersistenceRequired(); -
     * This makes him no longer despawn
     */

    @Override
    public void tick() {
        super.tick();
        this.setPersistenceRequired();
    }
}
