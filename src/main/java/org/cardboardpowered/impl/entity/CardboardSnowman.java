package org.cardboardpowered.impl.entity;

import net.minecraft.entity.passive.SnowGolemEntity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowman;

public class CardboardSnowman extends CardboardGolem implements Snowman {

    public CardboardSnowman(CraftServer server, SnowGolemEntity entity) {
        super(server, entity);
    }

    @Override
    public boolean isDerp() {
        return !getHandle().hasPumpkin();
    }

    @Override
    public void setDerp(boolean derpMode) {
        getHandle().setHasPumpkin(!derpMode);
    }

    @Override
    public SnowGolemEntity getHandle() {
        return (SnowGolemEntity) nms;
    }

    @Override
    public String toString() {
        return "Snowman";
    }

    @Override
    public EntityType getType() {
        return EntityType.SNOWMAN;
    }

}