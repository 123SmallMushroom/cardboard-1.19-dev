package org.cardboardpowered.impl.inventory.util;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public final class InventoryCreator {

    public static final InventoryCreator INSTANCE = new InventoryCreator();
    private final CustomInventoryConverter DEFAULT_CONVERTER = new CustomInventoryConverter();
    private final Map<InventoryType, InventoryConverter> converterMap = new HashMap<>();

    private InventoryCreator() {
        converterMap.put(InventoryType.CHEST, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.DISPENSER, new TileInventoryConverter.Dispenser());
        converterMap.put(InventoryType.DROPPER, new TileInventoryConverter.Dropper());
        converterMap.put(InventoryType.FURNACE, new TileInventoryConverter.Furnace());
        converterMap.put(InventoryType.WORKBENCH, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.ENCHANTING, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.BREWING, new TileInventoryConverter.BrewingStand());
        converterMap.put(InventoryType.PLAYER, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.MERCHANT, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.ENDER_CHEST, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.ANVIL, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.BEACON, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.HOPPER, new TileInventoryConverter.Hopper());
        converterMap.put(InventoryType.SHULKER_BOX, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.BARREL, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.BLAST_FURNACE, new TileInventoryConverter.BlastFurnace());
        converterMap.put(InventoryType.LECTERN, new TileInventoryConverter.Lectern());
        converterMap.put(InventoryType.SMOKER, new TileInventoryConverter.Smoker());
        converterMap.put(InventoryType.LOOM, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.CARTOGRAPHY, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.GRINDSTONE, DEFAULT_CONVERTER);
        converterMap.put(InventoryType.STONECUTTER, DEFAULT_CONVERTER);
    }

    public Inventory createInventory(InventoryHolder holder, InventoryType type) {
        return converterMap.get(type).createInventory(holder, type);
    }

    public Inventory createInventory(InventoryHolder holder, InventoryType type, String title) {
        return converterMap.get(type).createInventory(holder, type, title);
    }

    public Inventory createInventory(InventoryHolder holder, int size) {
        return DEFAULT_CONVERTER.createInventory(holder, size);
    }

    public Inventory createInventory(InventoryHolder holder, int size, String title) {
        return DEFAULT_CONVERTER.createInventory(holder, size, title);
    }

    public interface InventoryConverter {
        Inventory createInventory(InventoryHolder holder, InventoryType type);
        Inventory createInventory(InventoryHolder holder, InventoryType type, String title);
    }

}