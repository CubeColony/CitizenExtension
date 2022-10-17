package com.cubecolony.citizen.skin.props;

import com.cubecolony.citizen.skin.SkinElement;

/**
 * @author LBuke (Teddeh)
 */
public enum SkinBottoms implements SkinElement {

    BASIC_DARK_BLUE_JEANS("skin/bottoms/1.png"),
    BASIC_BLACK_JEANS("skin/bottoms/2.png"),
    ;

    private final String link;
    private final int weight;

    SkinBottoms(String link, int weight) {
        this.link = link;
        this.weight = weight;
    }

    SkinBottoms(String link) {
        this(link, 4);
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
