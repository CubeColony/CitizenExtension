package com.cubecolony.citizen.skin;

import com.cubecolony.citizen.skin.props.*;
import com.cubecolony.citizen.utils.ImageUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.entity.PlayerSkin;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Slf4j
public class SkinBuilder {

    private static final Int2ObjectMap<PlayerSkin> cachedSkins = new Int2ObjectOpenHashMap<>();
    private final TreeMap<Integer, SkinElement> elements = Maps.newTreeMap();
    private BufferedImage image = null;
    private int lastBuiltSkin = -1;

    private SkinTones skinTone = SkinTones.LIGHTEST;
    private SkinEyes skinEyes = null;
    private SkinShoes skinShoes = null;
    private SkinBottoms skinBottoms = null;
    private SkinShirts skinShirt = null;
    private SkinHair skinHair = null;

    private int index = 0;

    public SkinBuilder next() {
        int i = 0;
        for (SkinTones tone : SkinTones.values()) {
            for (SkinEyes eyes : SkinEyes.values()) {
                for (SkinShoes shoes : SkinShoes.values()) {
                    for (SkinBottoms bottoms : SkinBottoms.values()) {
                        for (SkinShirts shirts : SkinShirts.values()) {
                            for (SkinHair hair : SkinHair.values()) {
                                if (this.index == i) {
                                    this.setSkinTone(tone);
                                    this.setSkinEyes(eyes);
                                    this.setSkinShoes(shoes);
                                    this.setSkinBottoms(bottoms);
                                    this.setSkinShirt(shirts);
                                    this.setSkinHair(hair);
                                    this.index++;
                                    return this;
                                }
                                i++;
                            }
                            if (this.index == i) {
                                this.setSkinTone(tone);
                                this.setSkinEyes(eyes);
                                this.setSkinShoes(shoes);
                                this.setSkinBottoms(bottoms);
                                this.setSkinShirt(shirts);
                                this.index++;
                                return this;
                            }
                            i++;
                        }
                        if (this.index == i) {
                            this.setSkinTone(tone);
                            this.setSkinEyes(eyes);
                            this.setSkinShoes(shoes);
                            this.setSkinBottoms(bottoms);
                            this.index++;
                            return this;
                        }
                        i++;
                    }
                    if (this.index == i) {
                        this.setSkinTone(tone);
                        this.setSkinEyes(eyes);
                        this.setSkinShoes(shoes);
                        this.index++;
                        return this;
                    }
                    i++;

                }
                if (this.index == i) {
                    this.setSkinTone(tone);
                    this.setSkinEyes(eyes);
                    this.index++;
                    return this;
                }
                i++;
            }
            if (this.index == i) {
                this.setSkinTone(tone);
                this.index++;
                return this;
            }
            i++;
        }

        return this;
    }

    /**
     * Returns the image that is being displayed
     *
     * @return The image that was created.
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * It sets the image of the image panel to the image passed in.
     *
     * @param image The image to be displayed.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * This function returns the skin tone of the character
     *
     * @return The skin tone of the person.
     */
    public SkinTones getSkinTone() {
        return this.skinTone;
    }

    /**
     * This function sets the skin tone of the character
     *
     * @param skinTone The skin tone to set.
     * @return Instance of this.
     */
    public SkinBuilder setSkinTone(SkinTones skinTone) {
        this.skinTone = skinTone;
        this.elements.put(skinTone.getWeight(), skinTone);
        return this;
    }

    /**
     * Returns the skinEyes attribute of the object
     *
     * @return The SkinEyes object.
     */
    public SkinEyes getSkinEyes() {
        return this.skinEyes;
    }

    /**
     * This function sets the skinEyes property of the SkinBuilder object to the skinEyes parameter
     *
     * @param skinEyes The skinEyes object that will be used to generate the skin.
     * @return Instance of this.
     */
    public SkinBuilder setSkinEyes(SkinEyes skinEyes) {
        this.skinEyes = skinEyes;
        this.elements.put(skinEyes.getWeight(), skinEyes);
        return this;
    }

    /**
     * Returns the skin shoes of the player
     *
     * @return The skinShoes object.
     */
    public SkinShoes getSkinShoes() {
        return this.skinShoes;
    }

    /**
     * This function sets the skin shoes of the skin builder to the skin shoes passed in
     *
     * @param skinShoes The skin shoes to set.
     * @return Instance of this.
     */
    public SkinBuilder setSkinShoes(SkinShoes skinShoes) {
        this.skinShoes = skinShoes;
        this.elements.put(skinShoes.getWeight(), skinShoes);
        return this;
    }

    /**
     * Returns the skin bottoms of the player
     *
     * @return The SkinBottoms object.
     */
    public SkinBottoms getSkinBottoms() {
        return this.skinBottoms;
    }

    /**
     * This function sets the skin bottoms for the skin builder
     *
     * @param skinBottoms The skin bottoms to use.
     * @return Instance of this.
     */
    public SkinBuilder setSkinBottoms(SkinBottoms skinBottoms) {
        this.skinBottoms = skinBottoms;
        this.elements.put(skinBottoms.getWeight(), skinBottoms);
        return this;
    }

    /**
     * Returns the skin shirt of the character
     *
     * @return The skinShirt field.
     */
    public SkinShirts getSkinShirt() {
        return this.skinShirt;
    }

    /**
     * This function sets the skinShirt variable to the skinShirt parameter
     *
     * @param skinShirt The skin shirt to set.
     * @return Instance of this.
     */
    public SkinBuilder setSkinShirt(SkinShirts skinShirt) {
        this.skinShirt = skinShirt;
        this.elements.put(skinShirt.getWeight(), skinShirt);
        return this;
    }

    /**
     * Get the skin hair of the character
     *
     * @return The skinHair field.
     */
    public SkinHair getSkinHair() {
        return this.skinHair;
    }

    /**
     * This function sets the skinHair variable to the skinHair parameter
     *
     * @param skinHair The skin hair to set.
     * @return Instance of this.
     */
    public SkinBuilder setSkinHair(SkinHair skinHair) {
        this.skinHair = skinHair;
        this.elements.put(skinHair.getWeight(), skinHair);
        return this;
    }

    /**
     * This function builds a skin from the given elements and uploads it to the Mojang servers
     *
     * @return A CompletableFuture<PlayerSkin>
     */
    public @Nullable CompletableFuture<PlayerSkin> build() {
        final StringBuilder combinedElements = new StringBuilder();
        for (Map.Entry<Integer, SkinElement> entry : this.elements.entrySet()) {
            combinedElements.append(entry.getValue().getLink());
        }

        final String hash = DigestUtils.sha1Hex(combinedElements.toString());
        final int hashcode = hash.hashCode();

        if (this.lastBuiltSkin == hashcode) {
            return null;
        }

        this.lastBuiltSkin = hashcode;

        log.info("Cached skins: " + cachedSkins.size());

        final PlayerSkin cachedPlayerSkin = cachedSkins.get(hashcode);
        if (Objects.nonNull(cachedPlayerSkin)) {
            log.info("Used cached skin.");

            return CompletableFuture.completedFuture(cachedPlayerSkin);
        }

        return CompletableFuture.supplyAsync(() -> {
            try (final Connection connection = BaseDatabase.connection()) {
                Preconditions.checkNotNull(connection);

                final String[] result = SkinDAO.getSkin(connection, hashcode);
                if (Objects.nonNull(result)) {
                    final PlayerSkin playerSkin = new PlayerSkin(result[0], result[1]);
                    cachedSkins.put(hashcode, playerSkin);
                    return playerSkin;
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            for (Map.Entry<Integer, SkinElement> entry : this.elements.entrySet()) {
                final SkinElement element = entry.getValue();
                try (final InputStream inputStream = SkinBuilder.class.getClassLoader().getResourceAsStream(element.getLink())) {
                    if (Objects.isNull(inputStream)) {
                        continue;
                    }

                    final BufferedImage original = this.getImage();
                    final BufferedImage image = ImageIO.read(inputStream);

                    final BufferedImage combined = ImageUtil.combine(original, image);
                    this.setImage(combined);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

            final String skinLink = ImageUtil.uploadImage(this.getImage(), hash);
            if (skinLink.isEmpty()) {
                return null;
            }

            final PlayerSkin skin = SkinUtil.generateCustomSkin(skinLink);
            if (Objects.isNull(skin)) {
                return null;
            }

            BaseDatabase.run(connection -> SkinDAO.addSkin(connection, hashcode, skin.textures(), skin.signature()));
            return skin;
        }).whenComplete((playerSkin, throwable) -> cachedSkins.put(hashcode, playerSkin));
    }
}
