package com.cubecolony.citizen.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

/**
 * @author LBuke (Teddeh)
 */
@Slf4j
@UtilityClass
public class ImageUtil {

    /**
     * Combine two images into one
     *
     * @param original The original image.
     * @param overlay The image to be drawn on top of the original image.
     * @return The combined image.
     */
    public static @NotNull BufferedImage combine(@Nullable BufferedImage original, @NotNull BufferedImage overlay) {
        if (Objects.isNull(original)) {
            return overlay;
        }

        final int width = Math.max(original.getWidth(), overlay.getWidth());
        final int height = Math.max(original.getHeight(), overlay.getHeight());

        final BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics graphics = combined.getGraphics();
        graphics.drawImage(original, 0, 0, null);
        graphics.drawImage(overlay, 0, 0, null);
        graphics.dispose();

        return combined;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String uploadImage(BufferedImage image, String hash) {
        final File file = new File("/var/www/cubecolony/html/skins/%s.png".formatted(hash));
        if (file.exists()) {
            return "https://cubecolony.net/skins/%s.png".formatted(hash);
        }

        file.mkdirs();
        try {
            ImageIO.write(image, "png", file);
            return "https://cubecolony.net/skins/%s.png".formatted(hash);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Uploads an image to Imgur and returns the link to the image
     *
     * @param image The image to upload.
     * @return The link to the image.
     */
    public static String uploadImageToImgur(BufferedImage image) {
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", byteArrayOutputStream);

            final byte[] byteImage = byteArrayOutputStream.toByteArray();
            final String dataImage = new String(Base64.getEncoder().encode(byteImage));

            final OkHttpClient client = new OkHttpClient().newBuilder().build();
            final RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", dataImage)
                .build();

            final Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .method("POST", body)
                .addHeader("Authorization", "Client-ID d634473e4a70c58")
                .build();

            final Response response = client.newCall(request).execute();
            final ResponseBody bodyResponse = response.body();
            if (Objects.isNull(bodyResponse)) {
                return "";
            }

            final JsonObject jsonObject = (JsonObject) JsonParser.parseString(bodyResponse.string());
            log.info(jsonObject.toString());

            final JsonObject data = jsonObject.get("data").getAsJsonObject();
            return data.get("link").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
