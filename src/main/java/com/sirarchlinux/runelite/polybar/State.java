package com.sirarchlinux.runelite.polybar;

import lombok.Getter;

@SuppressWarnings("SpellCheckingInspection")
public enum State {
    IDLE("Wasting XP"),
    ANY("Grinding"),
    WCING("Woodcutting"),
    MINING("Mining"),
    ALCH("Alching"),
    CLIMB("Climbing"),
    STUN("Stunned Still"),
    SMITH("Smithing"),
    SMELT("Smelting"),
    PRAY("Praying"),
    FLETCH("Fletching"),
    COOK("Cooking"),
    GEM("Cutting Gems"),
    FISH("Fishing"),
    TELE("Teleporting Home"),
    FIRE("Lighting Fires"),
    STEAL("Stealing Stuff"),
    MTELE("Teleporting"),
    FARM("Farming"),
    HERB("Refining Herbs"),
    CHUNT("Checking Traps"),
    LHUNT("Setting up Traps"),
    SAND("Collecting Sand"),
    TABMK("Making Tablets"),
    CONSTR("Building"),
    CRAFT("Crafting"),
    CRFTBS("Making BattleStaves"),
    CHORB("Charging Orbs"),
    BCRPS("Blessing Crops"),
    PLANK("Transmuting Wood"),
    STRNG("Stringing Jewelry"),
    ENCHJWL("Enchanting Jewelry");

    @Getter
    private final String message;

    State(String message) {
        this.message = message;
    }
}
