package austeretony.plantbiomes.common.main;

import java.util.HashSet;
import java.util.Set;

public class PlantData {

    public final String registryName;
    
    private String unlocalizedName;
    
    public final int meta;

    private final Set<String> deniedBiomes = new HashSet<String>();

    public PlantData(String registryName, int meta) {
        this.registryName = registryName;
        this.meta = meta;
    }
    
    public String getUnlocalizedName() {
        return this.unlocalizedName;             
    }
    
    public void setUnlocalizedName(String name) {
        this.unlocalizedName = name;
    }
    
    public boolean hasDeniedBiomes() {
        return !this.deniedBiomes.isEmpty();
    }
    
    public Set<String> getDeniedBiomesSet() {
        return this.deniedBiomes;
    }

    public void denyBiome(String biomeRegistryName) {
        this.deniedBiomes.add(biomeRegistryName);
    }
    
    public void allowBiome(String biomeRegistryName) {
        this.deniedBiomes.remove(biomeRegistryName);
    }

    public boolean isValidBiome(String biomeRegistryName) {
        return !this.deniedBiomes.contains(biomeRegistryName);
    }
}
