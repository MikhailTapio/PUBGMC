package dev.toma.pubgmc.client.animation;

import dev.toma.pubgmc.client.animation.builder.AnimationType;

public class Animations {

    public static AnimationType DEBUG;
    public static AnimationType HEALING;
    public static AnimationType AIMING;
    public static AnimationType SPRINTING;
    public static AnimationType RELOADING;
    public static AnimationType BOLT;
    public static AnimationType RECOIL;

    public static void init() {
        DEBUG = register(-1);
        HEALING = register(0);
        AIMING = register(1).blockedBy(() -> new AnimationType[] {SPRINTING, RELOADING, BOLT});
        SPRINTING = register(2).blockedBy(() -> new AnimationType[] {RELOADING, BOLT});
        RELOADING = register(3);
        BOLT = register(4);
        RECOIL = register(5);
    }

    private static AnimationType register(int id) {
        return new AnimationType(id);
    }
}
