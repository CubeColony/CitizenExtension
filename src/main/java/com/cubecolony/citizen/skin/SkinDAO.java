package com.cubecolony.citizen.skin;

import net.minestom.server.MinecraftServer;
import net.minestom.server.cubecolony.skin.CachedSkin;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class SkinDAO {

    /**
     * It returns the skin data for a given hashcode
     *
     * @param hashcode The hashcode of the skin.
     * @return A String array containing the value and signature of the skin.
     */
    @Blocking
    public static @Nullable CachedSkin getSkin(int hashcode) {
        return MinecraftServer.getDatabase()
                .find(CachedSkin.class)
                .where()
                .eq("hash", hashcode)
                .findOne();
    }

    /**
     * It adds a skin to the skin cache
     *
     * @param hashcode  The hashcode of the skin.
     * @param value     The skin value.
     * @param signature The signature of the skin.
     */
    @Blocking
    public static void addSkin(int hashcode, @NotNull String value, @NotNull String signature) {
        CachedSkin skin = new CachedSkin(hashcode, value, signature);
        MinecraftServer.getDatabase().save(skin);
    }
}
