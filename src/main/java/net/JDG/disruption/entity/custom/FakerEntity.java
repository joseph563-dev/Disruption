package net.JDG.disruption.entity.custom;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class FakerEntity extends Monster {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 1.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 3000.0, true));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50d)
                .add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.ATTACK_DAMAGE, 90)
                .add(Attributes.ATTACK_SPEED, 9000)
                .add(Attributes.FOLLOW_RANGE, 24d);


    }


    public FakerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 24;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;


        }

    }



    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }


    }

}


