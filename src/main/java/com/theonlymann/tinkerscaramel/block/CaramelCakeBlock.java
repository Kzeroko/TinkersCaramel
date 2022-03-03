package com.theonlymann.tinkerscaramel.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CaramelCakeBlock extends CakeBlock {
    public CaramelCakeBlock(Properties p_i48434_1_) {
        super(p_i48434_1_);
    }
    @Override
    public ActionResultType use(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (p_225533_2_.isClientSide) {
            ItemStack itemstack = p_225533_4_.getItemInHand(p_225533_5_);
            if (this.eat(p_225533_2_, p_225533_3_, p_225533_1_, p_225533_4_).consumesAction()) {
                return ActionResultType.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return ActionResultType.CONSUME;
            }
        }

        return this.eat(p_225533_2_, p_225533_3_, p_225533_1_, p_225533_4_);
    }
    private ActionResultType eat(IWorld p_226911_1_, BlockPos p_226911_2_, BlockState p_226911_3_, PlayerEntity p_226911_4_) {
        if (!p_226911_4_.canEat(false)) {
            return ActionResultType.PASS;
        } else {
            p_226911_4_.awardStat(Stats.EAT_CAKE_SLICE);
            p_226911_4_.getFoodData().eat(2, 0.1F);
            p_226911_4_.addEffect(new EffectInstance(Effects.SATURATION, 2, 0));
            int i = p_226911_3_.getValue(BITES);
            if (i < 6) {
                p_226911_1_.setBlock(p_226911_2_, p_226911_3_.setValue(BITES, Integer.valueOf(i + 1)), 3);
            } else {
                p_226911_1_.removeBlock(p_226911_2_, false);
            }

            return ActionResultType.SUCCESS;
        }
    }
}
