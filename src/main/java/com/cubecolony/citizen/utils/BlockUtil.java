package com.cubecolony.citizen.utils;

import lombok.experimental.UtilityClass;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings("ALL")
@UtilityClass
public class BlockUtil {

    /**
     * If the two points are in the same block, return true
     *
     * @param p1 The first point.
     * @param p2 The point to compare to p1.
     * @return A boolean value.
     */
    public static boolean isSameBlockXZ(@Nullable Point p1, @Nullable Point p2) {
        if (Objects.isNull(p1) || Objects.isNull(p2))
            return false;
        return p1.blockX() == p2.blockX() && p1.blockZ() == p2.blockZ();
    }

    /**
     * Given a block, return an item stack with the same material
     *
     * @param block The block to convert to an item stack.
     * @return The ItemStack of the block.
     */
    public static ItemStack toItemStack(@NotNull Block block) {
        Material material = Material.fromNamespaceId(block.namespace());
        if (Objects.isNull(material))
            material = Material.AIR;
        return ItemStack.of(material);
    }
}
