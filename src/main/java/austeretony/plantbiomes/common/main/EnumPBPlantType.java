package austeretony.plantbiomes.common.main;

public enum EnumPBPlantType {

    STANDARD("minecraft", "standard"),
    AGRICRAFT_CROP("agricraft", "agricraft_crop"),
    FORESTRY_SAPLING("forestry", "forestry_sapling"),
    DYNAMIC_TREES_SAPLING("dynamictrees", "dynamic_sapling"),
    IC2_CROP("ic2", "ic2_crop");
    
    public final String domain, type;

    EnumPBPlantType(String domain, String type) {
        this.domain = domain;
        this.type = type;
    }
    
    public static EnumPBPlantType getOf(String type) {
        for (EnumPBPlantType enumType : values())
            if (type.equals(enumType.type))
                return enumType;
        return STANDARD;
    }
    
    @Override
    public String toString() {
        return this.type;
    }
}
