package austeretony.plantbiomes.common.main;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BoundItemStack {

    public final ResourceLocation registryName;

    private final Map<Integer, BoundMeta> data = new HashMap<Integer, BoundMeta>();

    public BoundItemStack(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    public boolean hasData() {
        return !this.data.isEmpty();
    }

    public Map<Integer, BoundMeta> getData() {
        return this.data;
    }

    public boolean hasMetaData(int meta) {
        return this.hasData() && this.data.containsKey(meta);
    }

    public BoundMeta getMeta(int meta) {
        return this.data.get(meta);
    }

    public void createMeta(int meta, ResourceLocation plantRegistryName, int plantMeta) {
        this.data.put(meta, new BoundMeta(meta, plantRegistryName, plantMeta));
    }
}