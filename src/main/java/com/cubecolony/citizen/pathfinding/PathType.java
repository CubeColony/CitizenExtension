package com.cubecolony.citizen.pathfinding;

import com.cubecolony.citizen.utils.BlockUtil;
import com.cubecolony.citizen.utils.O2;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AtomicDouble;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings("unused")
public enum PathType {

    CITIZEN_WALK_1(
        -64,
        new PathBlock(Block.IRON_BLOCK, 75d), // Walkway 75%
        new PathBlock(Block.GOLD_BLOCK, 20d), // Road Crossing 20%
        new PathBlock(Block.DIAMOND_BLOCK, 5d) // Building 5%
    ),
    CITIZEN_WALK_2(
        -63,
        new PathBlock(Block.IRON_BLOCK, 75d), // Walkway 75%
        new PathBlock(Block.GOLD_BLOCK, 20d), // Road Crossing 20%
        new PathBlock(Block.DIAMOND_BLOCK, 5d) // Building 5%
    ),
    CITIZEN_WALK_3(
        -62,
        new PathBlock(Block.IRON_BLOCK, 75d), // Walkway 75%
        new PathBlock(Block.GOLD_BLOCK, 20d), // Road Crossing 20%
        new PathBlock(Block.DIAMOND_BLOCK, 5d) // Building 5%
    ),
    CITIZEN_DRIVE(
        -63,
        new PathBlock(Block.IRON_BLOCK, 100d),
        new PathBlock(Block.GOLD_BLOCK, 100d)
    ),
    ;

    private static final BlockFace[] faces = {BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};

    private final Map<PathBlock, Double> map = Maps.newHashMap();
    private final int y;

    PathType(int y, PathBlock... blocks) {
        this.y = y;
        for (PathBlock block : blocks) {
            this.map.put(block, block.getWeight());
        }
    }

    public int getY() {
        return this.y;
    }

    public @Nullable O2<Point, Block> next(@NotNull Instance instance, @NotNull Point currentPoint, @Nullable Point previousPoint) {
        final Map<PathBlock, Double> nextBlockMap = Maps.newHashMap();
        final Point currentPosWithY = currentPoint.withY(this.y);
        for (BlockFace face : faces) {
            final Point point = currentPosWithY.relative(face);

            // If found point is equal to previous point, skip.
            if (Objects.nonNull(previousPoint) && BlockUtil.isSameBlockXZ(point, previousPoint)) {
                continue;
            }

            final Block block = instance.getBlock(point);
            final PathBlock pathBlock = this.getFromBlock(block);
            if (Objects.isNull(pathBlock)) {
                continue;
            }

            final double weight = pathBlock.getWeight();

            nextBlockMap.put(new PathBlock(block, point, weight), weight);
        }

        final PathBlock pathBlock = this.getRandomPathBlock(nextBlockMap);
        if (Objects.isNull(pathBlock) || Objects.isNull(pathBlock.getPoint())) {
            return null;
        }

        return O2.of(pathBlock.getPoint(), pathBlock.getBlock());
    }

    private @Nullable PathBlock getFromBlock(@NotNull Block block) {
        return this.map.keySet()
            .stream().filter(path -> path.getBlock().id() == block.id()).findFirst().orElse(null);
    }

    private PathBlock getRandomPathBlock(Map<PathBlock, Double> chances) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final double chance = random.nextDouble() * chances.values().stream().reduce(0D, Double::sum);
        final AtomicDouble needle = new AtomicDouble();
        return chances.entrySet()
            .stream()
            .filter((ent) -> needle.addAndGet(ent.getValue()) >= chance).findFirst()
            .map(Map.Entry::getKey).orElse(null);
    }
}
