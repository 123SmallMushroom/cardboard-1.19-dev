package com.javazilla.bukkitfabric.mixin.screen;

import org.cardboardpowered.impl.inventory.CardboardInventoryView;
import org.bukkit.entity.Player;
import org.spongepowered.asm.mixin.Mixin;

import com.javazilla.bukkitfabric.impl.inventory.SmithingInventoryImpl;
import com.javazilla.bukkitfabric.interfaces.IMixinServerEntityPlayer;

import net.minecraft.screen.SmithingScreenHandler;

@Mixin(SmithingScreenHandler.class)
public class MixinSmithingScreenHandler extends MixinForgingScreenHandler {

    private CardboardInventoryView bukkitEntity;

    @Override
    public CardboardInventoryView getBukkitView() {
        if (bukkitEntity != null)
            return bukkitEntity;

        SmithingInventoryImpl inventory = new SmithingInventoryImpl(this.input, this.output);
        bukkitEntity = new CardboardInventoryView((Player)((IMixinServerEntityPlayer)this.player).getBukkitEntity(), inventory, (SmithingScreenHandler)(Object)this);
        return bukkitEntity;
    }

}