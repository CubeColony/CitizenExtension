package com.cubecolony.citizen.skin;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class SkinDAO {

    /**
     * It returns the skin data for a given hashcode
     *
     * @param connection The connection to the database.
     * @param hashcode The hashcode of the skin.
     * @return A String array containing the value and signature of the skin.
     */
    @Blocking
    public static @Nullable String[] getSkin(@NotNull Connection connection, int hashcode) {
        @Language("SQL")
        final String query = """
            SELECT `value`, `signature` \
            FROM `cubecolony`.`skin_cache` \
            WHERE `hash`=?;
            """;
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hashcode);
            try (final ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    final String value = result.getString("value");
                    final String signature = result.getString("signature");
                    return new String[]{value, signature};
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * It adds a skin to the skin cache
     *
     * @param connection The connection to the database.
     * @param hashcode The hashcode of the skin.
     * @param value The skin value.
     * @param signature The signature of the skin.
     */
    @Blocking
    public static void addSkin(@NotNull Connection connection, int hashcode,
                               @NotNull String value, @NotNull String signature) {
        @Language("SQL")
        final String query = """
            INSERT INTO `cubecolony`.`skin_cache` \
            (`hash`, `value`, `signature`) \
            VALUES (?, ?, ?);
            """;
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hashcode);
            statement.setString(2, value);
            statement.setString(3, signature);
            statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
