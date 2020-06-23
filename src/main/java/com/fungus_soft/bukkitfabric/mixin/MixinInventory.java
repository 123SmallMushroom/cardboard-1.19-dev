package com.fungus_soft.bukkitfabric.mixin;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;
import org.spongepowered.asm.mixin.Mixin;

import com.fungus_soft.bukkitfabric.interfaces.IMixinInventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;

@Mixin(SimpleInventory.class)
public class MixinInventory implements IMixinInventory {

    private SimpleInventory get() {
        return (SimpleInventory) (Object) this;
    }

    @Override
    public List<ItemStack> getContents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onOpen(CraftHumanEntity who) {
        get().onOpen((PlayerEntity) who.nms);
    }

    @Override
    public void onClose(CraftHumanEntity who) {
        get().onClose((PlayerEntity) who.nms);
    }

    @Override
    public List<HumanEntity> getViewers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InventoryHolder getOwner() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMaxStackSize(int size) {
        // TODO Auto-generated method stub
    }

    @Override
    public Location getLocation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMaxStackSize() {
        return get().getMaxCountPerStack();
    }

}