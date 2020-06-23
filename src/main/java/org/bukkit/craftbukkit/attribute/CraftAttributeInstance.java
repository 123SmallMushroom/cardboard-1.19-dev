package org.bukkit.craftbukkit.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;

public class CraftAttributeInstance implements AttributeInstance {

    private final EntityAttributeInstance handle;
    private final Attribute attribute;

    public CraftAttributeInstance(EntityAttributeInstance handle, Attribute attribute) {
        this.handle = handle;
        this.attribute = attribute;
    }

    @Override
    public Attribute getAttribute() {
        return attribute;
    }

    @Override
    public double getBaseValue() {
        return handle.getBaseValue();
    }

    @Override
    public void setBaseValue(double d) {
        handle.setBaseValue(d);
    }

    @Override
    public Collection<AttributeModifier> getModifiers() {
        List<AttributeModifier> result = new ArrayList<AttributeModifier>();
        for (EntityAttributeModifier nms : handle.getModifiers())
            result.add(convert(nms));

        return result;
    }

    @Override
    public void addModifier(AttributeModifier modifier) {
        handle.addModifier(convert(modifier));
    }

    @Override
    public void removeModifier(AttributeModifier modifier) {
        handle.removeModifier(convert(modifier));
    }

    @Override
    public double getValue() {
        return handle.getValue();
    }

    @Override
    public double getDefaultValue() {
       return handle.getAttribute().getDefaultValue();
    }

    public static EntityAttributeModifier convert(AttributeModifier bukkit) {
        return new EntityAttributeModifier(bukkit.getUniqueId(), bukkit.getName(), bukkit.getAmount(), EntityAttributeModifier.Operation.values()[bukkit.getOperation().ordinal()]);
    }

    public static AttributeModifier convert(EntityAttributeModifier nms) {
        return new AttributeModifier(nms.getId(), nms.getName(), nms.getValue(), AttributeModifier.Operation.values()[nms.getOperation().ordinal()]);
    }

}