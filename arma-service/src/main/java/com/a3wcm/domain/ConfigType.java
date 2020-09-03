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
    STRUCTURE("structure"),
    UNKNOWN("unknown");

    private final String value;

    ConfigType(String value) {
        this.value = value;
    }

    public static ConfigType getTypeByValue(String value){
        ConfigType[] values = ConfigType.values();
        for(int i = 0; i != values.length; i++){
            if(values[i].getValue().equals(value)){
                return values[i];
            }
        }
        return UNKNOWN;
    }

    public String getValue() {
        return value;
    }
}
