package com.javazilla.bukkitfabric.mixin.entity;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.javazilla.bukkitfabric.impl.entity.ItemEntityImpl;
import com.javazilla.bukkitfabric.interfaces.IMixinInventory;
import com.javazilla.bukkitfabric.interfaces.IMixinPlayerInventory;
import com.javazilla.bukkitfabric.interfaces.IMixinServerEntityPlayer;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;

@Mixin(ItemEntity.class)
public class MixinItemEntity extends MixinEntity {

    @Shadow
    public int pickupDelay;

    @Shadow
    public UUID owner;

    @Inject(at = @At(value = "HEAD"), method = "tick()V")
    private void setBukkit(CallbackInfo callbackInfo) {
        if (null == bukkit)
            this.bukkit = new ItemEntityImpl(CraftServer.INSTANCE, (ItemEntity) (Object) this, (ItemEntity) (Object) this);
    }

    /**
     * @reason EntityPickupItemEvent
     * @author BukkitFabricMod
     */
    @Overwrite
    public void onPlayerCollision(PlayerEntity entityhuman) {
        if (this.world.isClient) return;
        ItemStack itemstack = ((ItemEntity)(Object)this).getStack();
        Item item = itemstack.getItem();
        int i = itemstack.getCount();

        // CraftBukkit start - fire PlayerPickupItemEvent
        int canHold = ((IMixinPlayerInventory)entityhuman.inventory).canHold(itemstack);
        int remaining = i - canHold;

        if (this.pickupDelay <= 0 && canHold > 0) {
            itemstack.setCount(canHold);
            // Call legacy event
            PlayerPickupItemEvent playerEvent = new PlayerPickupItemEvent((org.bukkit.entity.Player) ((IMixinServerEntityPlayer)entityhuman).getBukkitEntity(), (org.bukkit.entity.Item) this.getBukkitEntity(), remaining);
            //playerEvent.setCancelled(!entityhuman.canPickUpLoot);
            Bukkit.getServer().getPluginManager().callEvent(playerEvent);
            if (playerEvent.isCancelled()) {
                itemstack.setCount(i); // SPIGOT-5294 - restore count
                return;
            }

            // Call newer event afterwards
            EntityPickupItemEvent entityEvent = new EntityPickupItemEvent((org.bukkit.entity.Player) ((IMixinServerEntityPlayer)entityhuman).getBukkitEntity(), (org.bukkit.entity.Item) this.getBukkitEntity(), remaining);
            //entityEvent.setCancelled(!entityhuman.canPickUpLoot);
            Bukkit.getServer().getPluginManager().callEvent(entityEvent);
            if (entityEvent.isCancelled()) {
                itemstack.setCount(i); // SPIGOT-5294 - restore count
                return;
            }
            itemstack.setCount(canHold + remaining); // = i
            this.pickupDelay = 0;
        } else if (this.pickupDelay == 0) this.pickupDelay = -1;

        if (this.pickupDelay == 0 && (this.owner == null || this.owner.equals(entityhuman.getUuid())) && entityhuman.inventory.insertStack(itemstack)) {
            entityhuman.sendPickup((ItemEntity)(Object)this, i);
            if (itemstack.isEmpty()) {
                this.remove();
                itemstack.setCount(i);
            }
            entityhuman.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), i);
            entityhuman.method_29499((ItemEntity)(Object)this);
        }
    }

}