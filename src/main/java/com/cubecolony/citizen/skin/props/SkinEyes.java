package com.cubecolony.citizen.skin.props;

import com.cubecolony.citizen.skin.SkinElement;

/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings("unused")
public enum SkinEyes implements SkinElement {

    NORMAL("skin/eyes/normal.png"),
    NORMAL_AQUA("skin/eyes/normal_aqua.png"),
    NORMAL_BLUE("skin/eyes/normal_blue.png"),
    NORMAL_GREEN("skin/eyes/normal_green.png"),
    NORMAL_ORANGE("skin/eyes/normal_orange.png"),
    NORMAL_PINK("skin/eyes/normal_pink.png"),
    NORMAL_PURPLE("skin/eyes/normal_purple.png"),
    NORMAL_RED("skin/eyes/normal_red.png"),
    NORMAL_YELLOW("skin/eyes/normal_yellow.png"),
    ;

    private final String link;
    private final int weight;

    SkinEyes(String link) {
        this(link, 2);
    }

    SkinEyes(String link, int weight) {
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
