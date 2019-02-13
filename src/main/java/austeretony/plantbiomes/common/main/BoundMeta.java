package austeretony.plantbiomes.common.main;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BoundMeta {

    public final int meta;

    public final ResourceLocation plantRegistryName;

    public final int plantMeta;

    public BoundMeta(int meta, ResourceLocation plantRegistryName, int plantMeta) {
        this.meta = meta;
        this.plantRegistryName = plantRegistryName;
        this.plantMeta = plantMeta;
    }
}
