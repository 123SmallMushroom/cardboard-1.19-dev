/**
 * The Bukkit for Fabric Project
 * Copyright (C) 2020 Javazilla Software and contributors
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either 
 * version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.javazilla.bukkitfabric.mixin;

import org.bukkit.event.Cancellable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.javazilla.bukkitfabric.impl.BukkitEventFactory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;

@Mixin(StatHandler.class)
public class MixinStatHandler {

    @Overwrite
    public void increaseStat(PlayerEntity player, Stat<?> statistic, int i) {
        int j = (int) Math.min((long) this.getStat(statistic) + (long) i, 2147483647L);

        Cancellable cancellable = BukkitEventFactory.handleStatisticsIncrease(player, statistic, this.getStat(statistic), j);
        if (cancellable != null && cancellable.isCancelled()) return;
        this.setStat(player, statistic, j);
    }

    @Shadow
    public void setStat(PlayerEntity player, Stat<?> statistic, int i) {
    }

    @Shadow
    public int getStat(Stat<?> statistic) {
        return 0;
    }

}