package com.cubecolony.citizen.utils;

import org.jetbrains.annotations.NotNull;

/**
 * @author LBuke (Teddeh)
 */
@SuppressWarnings("unused")
public class O2<A, B> {

    private final @NotNull A a;
    private final @NotNull B b;

    protected O2(@NotNull A a, @NotNull B b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Get the value of the field `a` of this object
     *
     * @return The value of the field a.
     */
    public @NotNull A getA() {
        return this.a;
    }

    /**
     * Get the value of the field "b" from the object "this"
     *
     * @return The value of the field b.
     */
    public @NotNull B getB() {
        return this.b;
    }

    /**
     * It takes two arguments and returns an object of type O2
     *
     * @param a The first parameter of the function.
     * @param b The second parameter.
     * @return An O2 object.
     */
    public static <A, B> @NotNull O2<A, B> of(@NotNull A a, @NotNull B b) {
        return new O2<>(a, b);
    }
}
