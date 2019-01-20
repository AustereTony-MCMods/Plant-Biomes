package austeretony.plantbiomes.common.main;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

public class PlantData {

    public final ResourceLocation registryName;

    private final Map<Integer, MetaPlant> data = new LinkedHashMap<Integer, MetaPlant>();

    public PlantData(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    public boolean hasData() {
        return !this.data.isEmpty();
    }

    public Map<Integer, MetaPlant> getData() {
        return this.data;
    }

    public boolean hasData(int meta) {
        return this.data.containsKey(meta);
    }

    public MetaPlant get(int meta) {
        return this.data.get(meta);
    }
    
    public void add(int meta, MetaPlant metaPlant) {
        this.data.put(meta, metaPlant);
    }

    public void create(int meta, String unlocalizedName) {
        this.data.put(meta, new MetaPlant(meta, unlocalizedName));
    }

    public void remove(int meta) {
        this.data.remove(meta);
    }
}
