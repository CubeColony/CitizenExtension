package com.cubecolony.citizen.region;

import com.cubecolony.api.annotation.AwaitingDocumentation;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author LBuke (Teddeh)
 */
@AwaitingDocumentation
public final class Region {
    private final int id;
    private final RegionType type;
    private final BoundingBox boundingBox;

    private IntList nodes;

    public Region(int id, @NotNull RegionType type, @NotNull BoundingBox boundingBox) {
        this.type = type;
        this.boundingBox = boundingBox;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public @NotNull RegionType getType() {
        return this.type;
    }

    public @NotNull BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    public @NotNull IntList getNodes() {
        return this.nodes;
    }

    public boolean hasNodes() {
        return Objects.nonNull(this.nodes) && !this.nodes.isEmpty();
    }

    public void addNode(int id) {
        if (Objects.isNull(this.nodes)) {
            this.nodes = new IntArrayList();
        }
        this.nodes.add(id);
    }
}
