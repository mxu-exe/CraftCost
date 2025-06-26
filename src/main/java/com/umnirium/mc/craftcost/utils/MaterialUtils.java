package com.umnirium.mc.craftcost.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialUtils {
    public static boolean isMaterialValid(Material material) {
        try {
            if (material.isLegacy()) return false;

            new ItemStack(material);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
