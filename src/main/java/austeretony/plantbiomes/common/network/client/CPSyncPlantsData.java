package austeretony.plantbiomes.common.network.client;

import austeretony.plantbiomes.common.main.DataManager;
import austeretony.plantbiomes.common.main.EnumPlantType;
import austeretony.plantbiomes.common.main.MetaPlant;
import austeretony.plantbiomes.common.main.PlantData;
import austeretony.plantbiomes.common.network.ProxyPacket;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class CPSyncPlantsData extends ProxyPacket {

    private byte action;

    public CPSyncPlantsData() {}

    public CPSyncPlantsData(EnumAction enumAction) {
        this.action = (byte) enumAction.ordinal();
    }

    @Override
    public void write(PacketBuffer buffer) { 
        buffer.writeByte(this.action);
        if (this.action == EnumAction.SYNC_ALL.ordinal()) {
            String regNameStr, biomeRegNameStr, boundItemRegNameStr;
            buffer.writeByte(DataManager.getDataServer().size());
            for (PlantData plantData : DataManager.getDataServer().values()) {
                regNameStr = plantData.registryName.toString();
                buffer.writeByte(regNameStr.length());
                buffer.writeString(regNameStr);
                buffer.writeByte(plantData.enumType.type.length());
                buffer.writeString(plantData.enumType.type);
                buffer.writeByte(plantData.getMainMeta());
                buffer.writeByte(plantData.getData().size());
                for (MetaPlant metaPlant : plantData.getData().values()) {
                    buffer.writeByte(metaPlant.meta);
                    buffer.writeByte(metaPlant.specialName.length());
                    buffer.writeString(metaPlant.specialName);
                    buffer.writeBoolean(metaPlant.isDeniedGlobal());
                    buffer.writeBoolean(metaPlant.canGrowOverTime());
                    buffer.writeBoolean(metaPlant.canGrowWithBonemeal());
                    buffer.writeByte(metaPlant.getDeniedBiomes().size());
                    for (ResourceLocation biomeRegName : metaPlant.getDeniedBiomes()) {
                        biomeRegNameStr = biomeRegName.toString();
                        buffer.writeByte(biomeRegNameStr.length());
                        buffer.writeString(biomeRegNameStr);
                    }
                    buffer.writeByte(metaPlant.getValidBiomes().size());
                    for (ResourceLocation biomeRegName : metaPlant.getValidBiomes()) {
                        biomeRegNameStr = biomeRegName.toString();
                        buffer.writeByte(biomeRegNameStr.length());
                        buffer.writeString(biomeRegNameStr);
                    }
                    boundItemRegNameStr = metaPlant.hasBoundItem() ? metaPlant.getBoundItemRegistryName().toString() : "";
                    buffer.writeByte(boundItemRegNameStr.length());
                    buffer.writeString(boundItemRegNameStr);
                    buffer.writeByte(metaPlant.getBoundItemMeta());
                }
            }  
        } else if (this.action == EnumAction.SYNC_LATEST.ordinal()) {
            String regNameStr, biomeRegNameStr, boundItemRegNameStr;
            PlantData plantData = DataManager.getServer(DataManager.latestPlantServer.registryName);
            regNameStr = plantData.registryName.toString();
            buffer.writeByte(regNameStr.length());
            buffer.writeString(regNameStr);
            buffer.writeByte(plantData.enumType.type.length());
            buffer.writeString(plantData.enumType.type);
            buffer.writeByte(plantData.getMainMeta());
            buffer.writeByte(plantData.getData().size());
            for (MetaPlant metaPlant : plantData.getData().values()) {
                buffer.writeByte(metaPlant.meta);
                buffer.writeByte(metaPlant.specialName.length());
                buffer.writeString(metaPlant.specialName);
                buffer.writeBoolean(metaPlant.isDeniedGlobal());
                buffer.writeBoolean(metaPlant.canGrowOverTime());
                buffer.writeBoolean(metaPlant.canGrowWithBonemeal());
                buffer.writeByte(metaPlant.getDeniedBiomes().size());
                for (ResourceLocation biomeRegName : metaPlant.getDeniedBiomes()) {
                    biomeRegNameStr = biomeRegName.toString();
                    buffer.writeByte(biomeRegNameStr.length());
                    buffer.writeString(biomeRegNameStr);
                }
                buffer.writeByte(metaPlant.getValidBiomes().size());
                for (ResourceLocation biomeRegName : metaPlant.getValidBiomes()) {
                    biomeRegNameStr = biomeRegName.toString();
                    buffer.writeByte(biomeRegNameStr.length());
                    buffer.writeString(biomeRegNameStr);
                }
                boundItemRegNameStr = metaPlant.hasBoundItem() ? metaPlant.getBoundItemRegistryName().toString() : "";
                buffer.writeByte(boundItemRegNameStr.length());
                buffer.writeString(boundItemRegNameStr);
                buffer.writeByte(metaPlant.getBoundItemMeta());
            }
        } else if (this.action == EnumAction.REMOVE_LATEST.ordinal()) {
            String regNameStr = DataManager.latestPlantServer.registryName.toString();
            buffer.writeByte(regNameStr.length());
            buffer.writeString(regNameStr);
            buffer.writeByte(DataManager.latestPlantServer.meta);
        }
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.action = buffer.readByte();
        if (this.action == EnumAction.SYNC_ALL.ordinal()) {
            DataManager.clearDataClient();
            if (DataManager.isSettingsTooltipsAllowedClient())
                DataManager.clearBoundItemsClient();
            String regNameStr, plantTypeStr, specName, biomeRegNameStr, boundItemRegNameStr;
            ResourceLocation registryName;
            int amount, metaAmount, deniedAmount, validAmount, mainMeta, meta, itemMeta;
            MetaPlant metaPlant;
            amount = buffer.readByte();
            for (int i = 0; i < amount; i++) {
                regNameStr = buffer.readString(buffer.readByte());
                registryName = new ResourceLocation(regNameStr);
                plantTypeStr = buffer.readString(buffer.readByte());
                mainMeta = buffer.readByte();
                metaAmount = buffer.readByte();
                for (int j = 0; j < metaAmount; j++) {
                    meta = buffer.readByte();
                    specName = buffer.readString(buffer.readByte());
                    DataManager.createMetaClient(
                            EnumPlantType.getOf(plantTypeStr), 
                            registryName, 
                            meta, 
                            specName, 
                            "");
                    metaPlant = DataManager.getClient(registryName).getMeta(meta);
                    metaPlant.setDeniedGlobal(buffer.readBoolean());
                    metaPlant.setCanGrowOverTime(buffer.readBoolean());
                    metaPlant.setCanGrowWithBonemeal(buffer.readBoolean());
                    deniedAmount = buffer.readByte();
                    for (int k = 0; k < deniedAmount; k++) {
                        biomeRegNameStr = buffer.readString(buffer.readByte());
                        metaPlant.denyBiome(new ResourceLocation(biomeRegNameStr));
                    }
                    validAmount = buffer.readByte();;
                    for (int k = 0; k < validAmount; k++) {
                        biomeRegNameStr = buffer.readString(buffer.readByte());
                        metaPlant.addValidBiome(new ResourceLocation(biomeRegNameStr));
                    }
                    boundItemRegNameStr = buffer.readString(buffer.readByte());
                    itemMeta = buffer.readByte();
                    if (!boundItemRegNameStr.isEmpty()) {
                        metaPlant.setBoundItem(new ResourceLocation(boundItemRegNameStr), itemMeta);
                        if (DataManager.isSettingsTooltipsAllowedClient())
                            DataManager.createBoundItemClient(registryName, meta);
                    }                  
                }
                DataManager.getClient(registryName).setMainMeta(mainMeta);
            }
        } else if (this.action == EnumAction.SYNC_LATEST.ordinal()) {
            String regNameStr, plantTypeStr, specName, biomeRegNameStr, boundItemRegNameStr;
            ResourceLocation registryName;
            int metaAmount, deniedAmount, validAmount, mainMeta, meta, itemMeta;
            MetaPlant metaPlant;
            regNameStr = buffer.readString(buffer.readByte());
            registryName = new ResourceLocation(regNameStr);
            if (DataManager.existClient(registryName))
                DataManager.removeClient(registryName);
            plantTypeStr = buffer.readString(buffer.readByte());
            mainMeta = buffer.readByte();
            metaAmount = buffer.readByte();
            for (int j = 0; j < metaAmount; j++) {
                meta = buffer.readByte();
                specName = buffer.readString(buffer.readByte());
                DataManager.createMetaClient(
                        EnumPlantType.getOf(plantTypeStr), 
                        registryName, 
                        meta, 
                        specName, 
                        "");
                metaPlant = DataManager.getClient(registryName).getMeta(meta);
                metaPlant.setDeniedGlobal(buffer.readBoolean());
                metaPlant.setCanGrowOverTime(buffer.readBoolean());
                metaPlant.setCanGrowWithBonemeal(buffer.readBoolean());
                deniedAmount = buffer.readByte();
                for (int k = 0; k < deniedAmount; k++) {
                    biomeRegNameStr = buffer.readString(buffer.readByte());
                    DataManager.getClient(registryName).getMeta(meta).denyBiome(new ResourceLocation(biomeRegNameStr));
                }
                validAmount = buffer.readByte();;
                for (int k = 0; k < validAmount; k++) {
                    biomeRegNameStr = buffer.readString(buffer.readByte());
                    DataManager.getClient(registryName).getMeta(meta).addValidBiome(new ResourceLocation(biomeRegNameStr));
                }
                boundItemRegNameStr = buffer.readString(buffer.readByte());
                itemMeta = buffer.readByte();
                if (!boundItemRegNameStr.isEmpty()) {
                    DataManager.getClient(registryName).getMeta(meta).setBoundItem(new ResourceLocation(boundItemRegNameStr), itemMeta);
                    if (DataManager.isSettingsTooltipsAllowedClient())
                        DataManager.createBoundItemClient(registryName, meta);
                }
            }
            DataManager.getClient(registryName).setMainMeta(mainMeta);
        } else if (this.action == EnumAction.REMOVE_LATEST.ordinal()) {
            String regNameStr = buffer.readString(buffer.readByte());
            String[] regNameStrSplitted = regNameStr.split("[:]");
            ResourceLocation registryName = new ResourceLocation(regNameStrSplitted[0], regNameStrSplitted[1]);
            if (DataManager.existClient(registryName))
                DataManager.removeMetaClient(registryName, buffer.readByte());
        }
    }

    @Override
    public void process(INetHandler netHandler) {
        if (this.action == EnumAction.REMOVE_ALL.ordinal()) {
            DataManager.clearDataClient();
            if (DataManager.isSettingsTooltipsAllowedClient())
                DataManager.clearBoundItemsClient();
        }
    }

    public enum EnumAction {

        SYNC_ALL,
        SYNC_LATEST,
        REMOVE_ALL,
        REMOVE_LATEST;
    }
}
