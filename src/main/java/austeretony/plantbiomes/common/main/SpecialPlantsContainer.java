package austeretony.plantbiomes.common.main;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class SpecialPlantsContainer {

    public final EnumPlantType enumPlant;

    public final String domain, plant;

    private final Map<String, ResourceLocation> data = new HashMap<String, ResourceLocation>();

    private boolean hasData;

    public SpecialPlantsContainer(EnumPlantType enumPlant) {
        this.enumPlant = enumPlant;
        this.domain = enumPlant.domain;
        this.plant = enumPlant.type;
    }

    public Map<String, ResourceLocation> data() {
        return this.data;
    }

    public boolean hasData() {
        return this.hasData;
    }

    public boolean contains(String specialName) {
        return this.hasData && this.data.containsKey(specialName);
    }

    public ResourceLocation get(String specialName) {
        return this.data.get(specialName);
    }

    public void put(String name, ResourceLocation registryName) {
        this.data.put(name, registryName);
        this.hasData = true;
    }

    public void remove(String name) {
        this.data.remove(name);
        this.hasData = !this.data.isEmpty();
    }

    public void clear() {
        this.data.clear();
        this.hasData = false;
    }
}
