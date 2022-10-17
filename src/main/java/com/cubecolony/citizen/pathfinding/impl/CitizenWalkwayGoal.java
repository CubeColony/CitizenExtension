package com.cubecolony.citizen.pathfinding.impl;

import com.cubecolony.citizen.region.BoundingBox;
import com.cubecolony.citizen.region.Region;
import com.cubecolony.citizen.region.RegionManager;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.ai.GoalSelector;
import net.minestom.server.entity.pathfinding.Navigator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings("unused")
public final class CitizenWalkwayGoal extends GoalSelector {

    private static final long DELAY = 200L;

    private final RegionManager regionManager;

    private long lastTicked = System.currentTimeMillis() + 10_000L;
    private boolean pathSet;
    private Region lastRegion;
    private Region nextRegion;

    /**
     * Creates a follow target goal object.
     *
     * @param entityCreature the entity
     */
    public CitizenWalkwayGoal(@NotNull EntityCreature entityCreature, @NotNull Region spawnRegion) {
        super(entityCreature);
        this.regionManager = Manager.get(RegionManager.class);
        this.lastRegion = spawnRegion;
        this.nextRegion = this.nextRegion(spawnRegion);
    }

    private @UnknownNullability Region nextRegion(@NotNull Region current) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        final IntList nodes = current.getNodes();
        final int nextRegionId = nodes.getInt(random.nextInt(nodes.size()));
        final Region next = this.regionManager.getRegion(nextRegionId);
        if (Objects.isNull(next) || next.getId() == this.lastRegion.getId())
            return this.nextRegion(current);
        return next;
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
        if ((this.lastTicked + DELAY) >= System.currentTimeMillis())
            return;

        this.lastTicked = System.currentTimeMillis();

        final Pos pos = this.entityCreature.getPosition();
        final BoundingBox boundingBox = this.nextRegion.getBoundingBox();
        if (boundingBox.isInside(pos)) {
            final Region region = this.nextRegion(this.nextRegion);
            this.lastRegion = this.nextRegion;
            this.nextRegion = region;

            final Navigator navigator = this.entityCreature.getNavigator();
            final BoundingBox nextBox = region.getBoundingBox();
            navigator.setPathTo(new Pos(nextBox.getMidX(), nextBox.maxY(), nextBox.getMidZ()), true);
            return;
        }

        if (this.pathSet) {
            return;
        }

        this.pathSet = true;

        final Navigator navigator = this.entityCreature.getNavigator();
        navigator.setPathTo(new Pos(boundingBox.getMidX(), boundingBox.maxY(), boundingBox.getMidZ()), true);
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
