package com.cubecolony.citizen.pathfinding.impl;

import com.cubecolony.citizen.pathfinding.PathType;
import com.cubecolony.citizen.utils.BlockUtil;
import com.cubecolony.citizen.utils.O2;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.ai.GoalSelector;
import net.minestom.server.entity.pathfinding.Navigator;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author LBuke (Teddeh)
 */
public final class CitizenDriveCarGoal extends GoalSelector {

    private static final long DELAY = 100;
    private static final PathType pathType = PathType.CITIZEN_DRIVE;

    private long lastTicked = System.currentTimeMillis() + 10_000L;
    private @Nullable Point targetPoint;
    private @Nullable Point lastPoint;

    /**
     * Creates a follow target goal object.
     *
     * @param entityCreature the entity
     */
    public CitizenDriveCarGoal(@NotNull EntityCreature entityCreature) {
        super(entityCreature);
    }

    @Override
    public boolean shouldStart() {
        return true;
    }

    @Override
    public void start() {
    }

    @Override
    public void tick(long time) {
        if (System.currentTimeMillis() <= this.lastTicked + DELAY)
            return;

        this.lastTicked = System.currentTimeMillis();

        final Pos pos = this.entityCreature.getPosition();

        if (Objects.nonNull(this.targetPoint) && !BlockUtil.isSameBlockXZ(pos, this.targetPoint))
            return;

        final Instance instance = this.entityCreature.getInstance();
        if (Objects.isNull(instance))
            return;

        final O2<Point, Block> next = pathType.next(instance, pos, this.lastPoint);
        if (Objects.isNull(next)) {
            this.lastPoint = null;
            return;
        }

        final Point nextPoint = next.getA();

        final Navigator navigator = this.entityCreature.getNavigator();
        final Point target = nextPoint.withY(pos.y());
        final boolean result = navigator.setPathTo(target);
        if (result) {
            this.lastPoint = this.targetPoint;
            this.targetPoint = target;
        }
    }

    @Override
    public boolean shouldEnd() {
        return false;
    }

    @Override
    public void end() {
        this.entityCreature.getNavigator().setPathTo(null);
    }
}
