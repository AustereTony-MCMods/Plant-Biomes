package austeretony.plantbiomes.common.main;

public enum EnumPlantType {

    STANDARD("various", "standard"),//any plant using Block#updateTick() method for growth mechanic, domain is never used
    AGRICRAFT_CROP("agricraft", "agricraft_crop"),
    FORESTRY_FRUIT("forestry", "forestry_fruit"),
    FORESTRY_LEAVES("forestry", "forestry_leaves"),
    FORESTRY_SAPLING("forestry", "forestry_sapling"),
    DYNAMIC_TREES_SAPLING("dynamictrees", "dynamic_sapling"),
    IC2_CROP("ic2", "ic2_crop");

    public final String domain, type;

    EnumPlantType(String domain, String type) {
        this.domain = domain;
        this.type = type;
    }

    public static EnumPlantType getOf(String type) {
        for (EnumPlantType enumType : values())
            if (type.equals(enumType.type))
                return enumType;        
        return STANDARD;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
