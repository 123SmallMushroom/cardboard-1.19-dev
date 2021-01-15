package org.cardboardpowered.impl.entity;

import com.destroystokyo.paper.entity.Pathfinder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.javazilla.bukkitfabric.interfaces.IMixinEntity;

import java.util.Set;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.DragonBattle;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.loot.LootTable;
import org.cardboardpowered.impl.CardboardDragonBattle;

public class CardboardEnderdragon extends CardboardComplexEntity implements EnderDragon {

    public CardboardEnderdragon(CraftServer server, EnderDragonEntity entity) {
        super(server, entity);
    }

    @Override
    public Set<ComplexEntityPart> getParts() {
        Builder<ComplexEntityPart> builder = ImmutableSet.builder();
        for (EnderDragonPart part : getHandle().parts)
            builder.add((ComplexEntityPart) ((IMixinEntity)part).getBukkitEntity());
        return builder.build();
    }

    @Override
    public EnderDragonEntity getHandle() {
        return (EnderDragonEntity) nms;
    }

    @Override
    public String toString() {
        return "Dragon";
    }

    @Override
    public EntityType getType() {
        return EntityType.ENDER_DRAGON;
    }

    @Override
    public Phase getPhase() {
        return Phase.values()[getHandle().getDataTracker().get(EnderDragonEntity.PHASE_TYPE)];
    }

    @Override
    public void setPhase(Phase phase) {
        getHandle().getPhaseManager().setPhase(getMinecraftPhase(phase));
    }

    public static Phase getBukkitPhase(PhaseType phase) {
        return Phase.values()[phase.getTypeId()];
    }

    public static PhaseType getMinecraftPhase(Phase phase) {
        return PhaseType.getFromId(phase.ordinal());
    }

    @Override
    public BossBar getBossBar() {
        return getDragonBattle().getBossBar();
    }

    @Override
    public DragonBattle getDragonBattle() {
        return new CardboardDragonBattle(getHandle().getFight());
    }

    @Override
    public int getDeathAnimationTicks() {
        return getHandle().ticksSinceDeath;
    }

    @Override
    public Pathfinder getPathfinder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LivingEntity getTarget() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAware() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isInDaylight() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAware(boolean arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTarget(LivingEntity arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public LootTable getLootTable() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getSeed() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLootTable(LootTable arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setSeed(long arg0) {
        // TODO Auto-generated method stub
        
    }
}
