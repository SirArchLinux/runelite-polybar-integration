package com.sirarchlinux.runelite.polybar;

import lombok.Getter;

@SuppressWarnings("SpellCheckingInspection")
public enum State {
    IDLE("Wasting XP"),
    WCING("Woodcutting");

    @Getter
    private final String message;

    State(String message) {
        this.message = message;
    }
}
