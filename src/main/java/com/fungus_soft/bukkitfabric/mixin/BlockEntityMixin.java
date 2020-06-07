package com.fungus_soft.bukkitfabric.mixin;

import org.bukkit.craftbukkit.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.persistence.CraftPersistentDataTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.fungus_soft.bukkitfabric.interfaces.IMixinBlockEntity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;

@Mixin(BlockEntity.class)
public class BlockEntityMixin implements IMixinBlockEntity {

    private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
    public CraftPersistentDataContainer persistentDataContainer;

    @Override
    public CraftPersistentDataContainer getPersistentDataContainer() {
        return persistentDataContainer;
    }

    @Inject(at = @At("TAIL"), method = "fromTag")
    public void loadEnd(CompoundTag tag, CallbackInfo callback) {
        this.persistentDataContainer = new CraftPersistentDataContainer(DATA_TYPE_REGISTRY);

        CompoundTag persistentDataTag = tag.getCompound("PublicBukkitValues");
        if (persistentDataTag != null)
            this.persistentDataContainer.putAll(persistentDataTag);
    }

    @Inject(at = @At("RETURN"), method = "toTag")
    public void saveEnd(CompoundTag tag, CallbackInfoReturnable callback) {
        if (this.persistentDataContainer != null && !this.persistentDataContainer.isEmpty())
            tag.put("PublicBukkitValues", this.persistentDataContainer.toTagCompound());
    }


}