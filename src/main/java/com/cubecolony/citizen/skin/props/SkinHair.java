package com.cubecolony.citizen.skin.props;

import com.cubecolony.citizen.skin.SkinElement;

/**
 * @author LBuke (Teddeh)
 */
public enum SkinHair implements SkinElement {

    MEDIUM_BLACK_HAIR("skin/hair/medium_black_hair.png"),
    LONG_BLACK_HAIR("skin/hair/long_black_hair.png"),
    ;

    private final String link;
    private final int weight;

    SkinHair(String link) {
        this(link, 6);
    }

    SkinHair(String link, int weight) {
        this.link = link;
        this.weight = weight;
    }

    @Override
    public String getLink() {
        return this.link;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }
}
