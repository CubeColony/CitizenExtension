package com.cubecolony.citizen.region;

import com.cubecolony.api.annotation.AwaitingDocumentation;
import com.cubecolony.api.util.Callback;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Point;
import net.minestom.server.cubecolony.region.BoundingBox;
import net.minestom.server.cubecolony.region.Region;
import net.minestom.server.cubecolony.region.RegionType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            MinecraftServer.getDatabase()
                    .find(Region.class)
                    .findList()
                    .forEach(region -> this.regions.put(Math.toIntExact(region.getId()), region));
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
        this.regions.put(Math.toIntExact(region.getId()), region);
    }

    public void removeRegion(@NotNull Region region) {
        this.regions.remove(Math.toIntExact(region.getId()));
    }

    public void createRegion(@NotNull RegionType type, @NotNull BoundingBox boundingBox, @NotNull Callback<Region> callback) {
        Region region = new Region(type, boundingBox);
        MinecraftServer.getDatabase()
                .insert(region);
        callback.call(region);
    }
}
