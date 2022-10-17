package com.cubecolony.citizen.region;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;

/**
 * @author LBuke (Teddeh)
 */
@Documented
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection"})
public final class RegionDAO {

    /**
     * Get all the regions from the database and put them into a map
     *
     * @param connection The connection to the database.
     * @return A map of region ids to regions.
     */
    @Blocking
    public static @NotNull ObjectList<Region> getRegions(@NotNull Connection connection) {
        final ObjectList<Region> regions = new ObjectArrayList<>();

        @Language("SQL")
        final String query = """
                SELECT * \
                FROM `hitandrun`.`region` \
                WHERE 1;
                """;
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            try (final ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    final int id = result.getInt("id");
                    final RegionType type = RegionType.of(result.getByte("type"));
                    final int rightX = result.getInt("right_x");
                    final int rightY = result.getInt("right_y");
                    final int rightZ = result.getInt("right_z");
                    final int leftX = result.getInt("left_x");
                    final int leftY = result.getInt("left_y");
                    final int leftZ = result.getInt("left_z");
                    final BoundingBox box = new BoundingBox(rightX, rightY, rightZ, leftX, leftY, leftZ);
                    regions.add(new Region(id, type, box));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return regions;
    }

    /**
     * Add a new region to the database
     *
     * @param connection The connection to the database.
     * @param type The type of region.
     * @param boundingBox The bounding box of the region.
     * @return A Region object.
     */
    @Blocking
    public static @Nullable Region addRegion(@NotNull Connection connection, @NotNull RegionType type, @NotNull BoundingBox boundingBox) {
        @Language("SQL")
        final String query = """
                INSERT INTO `hitandrun`.`region` \
                (`type`, `right_x`, `right_y`, `right_z`, `left_x`, `left_y`, `left_z`) \
                VALUE (?, ?, ?, ?, ?, ?, ?);
                """;
        try (final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setByte(1, type.getId());
            statement.setInt(2, boundingBox.maxX());
            statement.setInt(3, boundingBox.maxY());
            statement.setInt(4, boundingBox.maxZ());
            statement.setInt(5, boundingBox.minX());
            statement.setInt(6, boundingBox.minY());
            statement.setInt(7, boundingBox.minZ());
            statement.executeUpdate();
            try (final ResultSet result = statement.getGeneratedKeys()) {
                if (result.next()) {
                    final int id = result.getInt(1);
                    return new Region(id, type, boundingBox);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Blocking
    public static @NotNull ObjectList<IntIntPair> getRegionConnections(@NotNull Connection connection) {
        ObjectList<IntIntPair> list = new ObjectArrayList<>();

        @Language("SQL")
        final String query = """
                SELECT * \
                FROM `hitandrun`.`region_connection` \
                WHERE 1;
                """;
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            try (final ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    final int region = result.getInt("region_id");
                    final int child = result.getInt("child_id");
                    list.add(new IntIntImmutablePair(region, child));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return list;
    }

    @Blocking
    public static void setRegionConnection(@NotNull Connection connection, int region, int child) {
        @Language("SQL")
        final String query = """
                INSERT IGNORE INTO `hitandrun`.`region_connection` \
                (`region_id`, `child_id`) \
                VALUES (?, ?);
                """;
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, region);
            statement.setInt(2, child);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
