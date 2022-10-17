package com.cubecolony.citizen.skin.props;

import com.cubecolony.citizen.skin.SkinElement;

/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings("unused")
public enum SkinTones implements SkinElement {

    LIGHTEST("skin/tones/1.png"),
    LIGHT("skin/tones/2.png"),
    TANNED("skin/tones/3.png"),
    DARK("skin/tones/4.png"),
    DARKEST("skin/tones/5.png"),
    ;

    private final String link;

    SkinTones(String link) {
        this.link = link;
    }

    @Override
    public String getLink() {
        return this.link;
    }

    @Override
    public int getWeight() {
        return 1;
    }
}
