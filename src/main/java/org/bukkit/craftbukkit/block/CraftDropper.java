package org.bukkit.craftbukkit.block;

import net.minecraft.block.Blocks;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.server.world.ServerWorld;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

import org.cardboardpowered.impl.world.WorldImpl;

public class CraftDropper extends CraftLootable<DropperBlockEntity> implements Dropper {

    public CraftDropper(final Block block) {
        super(block, DropperBlockEntity.class);
    }

    public CraftDropper(final Material material, DropperBlockEntity te) {
        super(material, te);
    }

    @Override
    public Inventory getSnapshotInventory() {
        return new CraftInventory(this.getSnapshot());
    }

    @Override
    public Inventory getInventory() {
        if (!this.isPlaced()) {
            return this.getSnapshotInventory();
        }

        return new CraftInventory(this.getTileEntity());
    }

    @Override
    public void drop() {
        Block block = getBlock();

        if (block.getType() == Material.DROPPER) {
            WorldImpl world = (WorldImpl) this.getWorld();
            DropperBlock drop = (DropperBlock) Blocks.DROPPER;

            drop.dispense((ServerWorld) world.getHandle(), this.getPosition());
        }
    }

}