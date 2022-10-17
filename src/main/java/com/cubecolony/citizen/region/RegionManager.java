package com.cubecolony.citizen.region;

import com.cubecolony.api.annotation.AwaitingDocumentation;
import com.cubecolony.api.util.Callback;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.objects.ObjectList;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.coordinate.Point;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author LBuke (Teddeh)
 */
@AwaitingDocumentation
@ApiStatus.NonExtendable
@Slf4j
public final class RegionManager {
    private final Int2ObjectMap<Region> regions = new Int2ObjectOpenHashMap<>();

    public void enable() {
        CompletableFuture.runAsync(() -> {
            BaseDatabase.run(connection -> {
                final ObjectList<Region> list = RegionDAO.getRegions(connection);
                list.forEach(region -> this.regions.put(region.getId(), region));
            });

            BaseDatabase.run(connection -> {
                final ObjectList<IntIntPair> connections = RegionDAO.getRegionConnections(connection);
                for (IntIntPair pair : connections) {
                    final Region region = this.regions.get(pair.leftInt());
                    region.addNode(pair.rightInt());
                }
            });

            log.info("Loaded {} regions.", this.regions.size());
        });
    }

    public @NotNull Int2ObjectMap<Region> getRegions() {
        return this.regions;
    }

    public @Nullable Region getRegion(int id) {
        return this.regions.get(id);
    }

    public @Nullable Region getRegion(@NotNull Point point) {
        for (Region region : this.regions.values()) {
            if (region.getBoundingBox().isInside(point)) {
                return region;
            }
        }
        return null;
    }

    public void addRegion(@NotNull Region region) {
        this.regions.put(region.getId(), region);
    }

    public void removeRegion(@NotNull Region region) {
        this.regions.remove(region.getId());
    }

    public void createRegion(@NotNull RegionType type, @NotNull BoundingBox boundingBox, @NotNull Callback<Region> callback) {
        CompletableFuture.runAsync(() -> BaseDatabase.run(connection -> {
            final Region region = RegionDAO.addRegion(connection, type, boundingBox);
            if (Objects.nonNull(region)) {
                this.regions.put(region.getId(), region);
                callback.call(region);
            }
        }));
    }
}
