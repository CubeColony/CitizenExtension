package com.cubecolony.citizen.utils;

import org.jetbrains.annotations.NotNull;

/**
 * @author LBuke (Teddeh)
 */
public class O3<A, B, C> extends O2<A, B> {

    private final @NotNull C c;

    protected O3(@NotNull A a, @NotNull B b, @NotNull C c) {
        super(a, b);
        this.c = c;
    }

    /**
     * Get the C field
     *
     * @return The value of the field c.
     */
    public @NotNull C getC() {
        return this.c;
    }

    /**
     * It takes three parameters and returns an O3
     *
     * @param a The first parameter of the function.
     * @param b The second parameter.
     * @param c The third parameter
     * @return An O3 instance.
     */
    public static <A, B, C> @NotNull O3<A, B, C> of(@NotNull A a, @NotNull B b, @NotNull C c) {
        return new O3<>(a, b, c);
    }
}
