package net.JDG.disruption.entity.custom;

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
                    .add(Attributes.MAX_HEALTH, 50d)
                    .add(Attributes.MOVEMENT_SPEED, 1)
                    .add(Attributes.ATTACK_DAMAGE, 90)
                    .add(Attributes.ATTACK_SPEED, 9000)
                    .add(Attributes.FOLLOW_RANGE, 24d);


        }
    }
