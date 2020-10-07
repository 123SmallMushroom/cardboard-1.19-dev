package com.javazilla.bukkitfabric.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.javazilla.bukkitfabric.impl.BukkitEventFactory;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@Mixin(PiglinBrain.class)
public class MixinPiglinBrain {

    @Overwrite
    protected static void loot(PiglinEntity entitypiglin, ItemEntity entityitem) {
        stopWalking(entitypiglin);
        ItemStack itemstack;

        // CraftBukkit start
        if (entityitem.getStack().getItem() == Items.GOLD_NUGGET && !BukkitEventFactory.callEntityPickupItemEvent(entitypiglin, entityitem, 0, false).isCancelled()) {
            entitypiglin.sendPickup(entityitem, entityitem.getStack().getCount());
            itemstack = entityitem.getStack();
            entityitem.remove();
        } else if (!BukkitEventFactory.callEntityPickupItemEvent(entitypiglin, entityitem, entityitem.getStack().getCount() - 1, false).isCancelled()) {
            entitypiglin.sendPickup(entityitem, 1);
            itemstack = getItemFromStack(entityitem);
        } else return;
        // CraftBukkit end

        Item item = itemstack.getItem();

        if (isGoldenItem(item)) {
            entitypiglin.getBrain().forget(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            swapItemWithOffHand(entitypiglin, itemstack);
            setAdmiringItem((LivingEntity) entitypiglin);
        } else if (isFood(item) && !hasAteRecently(entitypiglin)) {
            setEatenRecently(entitypiglin);
        } else {
            if (!entitypiglin.tryEquip(itemstack)) barterItem(entitypiglin, itemstack);
        }
    }

    // This class likes private static methods
    @Shadow protected static boolean isGoldenItem(Item item) {return false;}
    @Shadow private static void setEatenRecently(PiglinEntity entitypiglin) {}
    @Shadow private static void setAdmiringItem(LivingEntity entityliving) {}
    @Shadow private static boolean hasAteRecently(PiglinEntity entitypiglin) {return false;}
    @Shadow private static boolean isFood(Item item) {return false;}
    @Shadow private static ItemStack getItemFromStack(ItemEntity entityitem) {return null;}
    @Shadow private static void swapItemWithOffHand(PiglinEntity entitypiglin, ItemStack itemstack) {}
    @Shadow private static void stopWalking(PiglinEntity entitypiglin) {}
    @Shadow private static void barterItem(PiglinEntity entitypiglin, ItemStack itemstack) {}

}