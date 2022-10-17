package com.cubecolony.citizen.skin.props;

import com.cubecolony.citizen.skin.SkinElement;

/**
 * @author LBuke (Teddeh)
 */
public enum SkinShirts implements SkinElement {

    STRIPE_RED("skin/shirts/1.png"),
    STRIPE_BLUE("skin/shirts/2.png"),
    ;

    private final String link;
    private final int weight;

    SkinShirts(String link, int weight) {
        this.link = link;
        this.weight = weight;
    }

    SkinShirts(String link) {
        this(link, 5);
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
