package com.cubecolony.citizen.skin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import net.minestom.server.entity.PlayerSkin;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author LBuke (Teddeh)
 */
@UtilityClass
public class SkinUtil {

    /**
     * It takes a skin link and returns a PlayerSkin object
     *
     * @param skinLink The link to the skin you want to generate.
     * @return A PlayerSkin object.
     */
    @Blocking
    public static @Nullable PlayerSkin generateCustomSkin(@NotNull String skinLink) {
        try {
            final URL target = new URL("https://api.mineskin.org/generate/url");
            final HttpURLConnection connection = (HttpURLConnection) target.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(30000);
            try (final DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.writeBytes("%s=%s".formatted(
                    URLEncoder.encode("url", StandardCharsets.UTF_8),
                    URLEncoder.encode(skinLink, StandardCharsets.UTF_8)
                ));
                try (final InputStreamReader streamReader = new InputStreamReader(connection.getInputStream())) {
                    try (final BufferedReader bufferedReader = new BufferedReader(streamReader)) {
                        final JsonObject output = (JsonObject) JsonParser.parseReader(bufferedReader);
                        final JsonObject data = output.get("data").getAsJsonObject();
                        final JsonObject texture = data.get("texture").getAsJsonObject();

                        final String value = texture.get("value").getAsString();
                        final String signature = texture.get("signature").getAsString();

                        connection.disconnect();
                        return new PlayerSkin(value, signature);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
