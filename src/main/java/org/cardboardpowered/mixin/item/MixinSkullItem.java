package org.cardboardpowered.mixin.item;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.item.SkullItem;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;

@Mixin(value = SkullItem.class, priority = 900)
public class MixinSkullItem extends WallStandingBlockItem {

    public MixinSkullItem(Block standingBlock, Block wallBlock, Settings settings) {
        super(standingBlock, wallBlock, settings);
    }

    /**
     * @reason Bukkit
     * @author BukkitFabricMod
     */
    @Overwrite
    // TODO on 1.17 return type is void now, also the method seems to be empty
    public void postProcessNbt(NbtCompound tag) {
        super.postProcessNbt(tag);
        if (tag.contains("SkullOwner", 8) && !StringUtils.isBlank(tag.getString("SkullOwner"))) {
            GameProfile gameprofile = new GameProfile((UUID) null, tag.getString("SkullOwner"));
            tag.put("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), gameprofile));
            return;
        } else {
            NbtList textures = tag.getCompound("SkullOwner").getCompound("Properties").getList("textures", 10);
            for (int i = 0; i < textures.size(); i++) {
                if (textures.get(i) instanceof NbtCompound && !((NbtCompound) textures.get(i)).contains("Signature", 8) && ((NbtCompound) textures.get(i)).getString("Value").trim().isEmpty()) {
                    tag.remove("SkullOwner");
                    break;
                }
            }
            return;
        }
    }

}