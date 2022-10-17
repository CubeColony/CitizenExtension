package com.cubecolony.citizen.pathfinding;

import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author LBuke (Teddeh)
 */
public final class PathBlock {

    private final @NotNull Block block;
    private final @Nullable Point point;
    private final double weight;

    public PathBlock(@NotNull Block block, @Nullable Point point, double weight) {
        this.block = block;
        this.point = point;
        this.weight = weight;
    }

    public PathBlock(@NotNull Block block, double weight) {
        this(block, null, weight);
    }

    public @NotNull Block getBlock() {
        return this.block;
    }

    public @Nullable Point getPoint() {
        return this.point;
    }

    public double getWeight() {
        return this.weight;
    }
}
