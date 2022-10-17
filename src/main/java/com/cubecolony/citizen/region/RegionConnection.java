package com.cubecolony.citizen.region;

import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.UnknownNullability;

/**
 * @author LBuke (Teddeh)
 */
public enum RegionConnection {
    PARENT((byte) 1),
    CHILD((byte) 2),
    ;

    private final byte id;

    RegionConnection(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public static @UnknownNullability RegionConnection of(@Range(from = 1, to = 2) byte id) {
        for (RegionConnection connection : values()) {
            if (connection.id == id) {
                return connection;
            }
        }

        return null;
    }
}
