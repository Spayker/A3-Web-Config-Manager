package com.a3wcm.domain;

/**
 *  Describes all possible config types that can be used right in games in the long run.
 **/
public enum ConfigType {

    INFANTRY("infantry"),
    VEHICLE("vehicle"),
    PRIMARY_WEAPON("primaryWeapon"),
    SECONDARY_WEAPoN("secondaryWeapon"),
    AMMO("ammo"),
    STRUCTURE("structure");

    private final String value;

    ConfigType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
