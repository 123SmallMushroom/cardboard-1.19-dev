package com.javazilla.bukkitfabric.impl.entity;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;

import net.minecraft.entity.projectile.thrown.EggEntity;

public class EggImpl extends ThrowableProjectileImpl implements Egg {

    public EggImpl(CraftServer server, EggEntity entity) {
        super(server, entity);
    }

    @Override
    public EggEntity getHandle() {
        return (EggEntity) nms;
    }

    @Override
    public String toString() {
        return "EggImpl";
    }

    @Override
    public EntityType getType() {
        return EntityType.EGG;
    }

}