package com.cubecolony.citizen.skin.props;

import com.cubecolony.citizen.skin.SkinElement;

/**
 * @author LBuke (Teddeh)
 */
public enum SkinShoes implements SkinElement {

    BLACK_SHOES("skin/shoes/1.png"),
    WHITE_SHOES("skin/shoes/2.png"),
    ;

    private final String link;
    private final int weight;

    SkinShoes(String link, int weight) {
        this.link = link;
        this.weight = weight;
    }

    SkinShoes(String link) {
        this(link, 3);
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
