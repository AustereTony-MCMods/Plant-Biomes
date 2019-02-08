package austeretony.plantbiomes.common.network.client;

import austeretony.plantbiomes.common.main.EnumPBPlantType;
import austeretony.plantbiomes.common.main.MetaPlant;
import austeretony.plantbiomes.common.main.PBManager;
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
            String regNameStr, biomeRegNameStr;
            buffer.writeByte(PBManager.getDataServer().size());
            for (PlantData plantData : PBManager.getDataServer().values()) {
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
                    buffer.writeByte(metaPlant.isDeniedGlobal() ? 1 : 0);
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
                }
            }  
        } else if (this.action == EnumAction.SYNC_LATEST.ordinal()) {
            String regNameStr, biomeRegNameStr;
            PlantData plantData = PBManager.getServer(PBManager.latestPlant.registryName);
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
                buffer.writeByte(metaPlant.isDeniedGlobal() ? 1 : 0);
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
            }
        } else if (this.action == EnumAction.REMOVE_LATEST.ordinal()) {
            String regNameStr = PBManager.latestPlant.registryName.toString();
            buffer.writeByte(regNameStr.length());
            buffer.writeString(regNameStr);
            buffer.writeByte(PBManager.latestPlant.meta);
        }
    }

    @Override
    public void read(PacketBuffer buffer) {
        if (!PBManager.isClientDataInitialized())
            PBManager.initClientData();
        this.action = buffer.readByte();
        if (this.action == EnumAction.SYNC_ALL.ordinal()) {
            PBManager.clearDataClient();
            String regNameStr, plantTypeStr, specName, biomeRegNameStr;
            String[] regNameStrSplitted, biomeRegNameStrSplitted;
            ResourceLocation registryName, biomeRegName;
            int amount, metaAmount, deniedAmount, validAmount, mainMeta, meta;
            amount = buffer.readByte();
            for (int i = 0; i < amount; i++) {
                regNameStr = buffer.readString(buffer.readByte());
                regNameStrSplitted = regNameStr.split("[:]");
                registryName = new ResourceLocation(regNameStrSplitted[0], regNameStrSplitted[1]);
                plantTypeStr = buffer.readString(buffer.readByte());
                mainMeta = buffer.readByte();
                metaAmount = buffer.readByte();
                for (int j = 0; j < metaAmount; j++) {
                    meta = buffer.readByte();
                    specName = buffer.readString(buffer.readByte());
                    PBManager.createMetaClient(
                            EnumPBPlantType.getOf(plantTypeStr), 
                            registryName, 
                            meta, 
                            specName, 
                            "");
                    PBManager.getClient(registryName).setMainMeta(mainMeta);
                    if (buffer.readByte() == 1)
                        PBManager.getClient(registryName).getMeta(meta).denyGlobal();
                    deniedAmount = buffer.readByte();
                    for (int k = 0; k < deniedAmount; k++) {
                        biomeRegNameStr = buffer.readString(buffer.readByte());
                        biomeRegNameStrSplitted = biomeRegNameStr.split("[:]");
                        biomeRegName = new ResourceLocation(biomeRegNameStrSplitted[0], biomeRegNameStrSplitted[1]); 
                        PBManager.getClient(registryName).getMeta(meta).denyBiome(biomeRegName);
                    }
                    validAmount = buffer.readByte();;
                    for (int k = 0; k < validAmount; k++) {
                        biomeRegNameStr = buffer.readString(buffer.readByte());
                        biomeRegNameStrSplitted = biomeRegNameStr.split("[:]");
                        biomeRegName = new ResourceLocation(biomeRegNameStrSplitted[0], biomeRegNameStrSplitted[1]); 
                        PBManager.getClient(registryName).getMeta(meta).addValidBiome(biomeRegName);
                    }
                }
            }
        } else if (this.action == EnumAction.SYNC_LATEST.ordinal()) {
            String regNameStr, plantTypeStr, specName, biomeRegNameStr;
            String[] regNameStrSplitted, biomeRegNameStrSplitted;
            ResourceLocation registryName, biomeRegName;
            int metaAmount, deniedAmount, validAmount, mainMeta, meta;
            regNameStr = buffer.readString(buffer.readByte());
            regNameStrSplitted = regNameStr.split("[:]");
            registryName = new ResourceLocation(regNameStrSplitted[0], regNameStrSplitted[1]);
            if (PBManager.existClient(registryName))
                PBManager.removeClient(registryName);
            plantTypeStr = buffer.readString(buffer.readByte());
            mainMeta = buffer.readByte();
            metaAmount = buffer.readByte();
            for (int j = 0; j < metaAmount; j++) {
                meta = buffer.readByte();
                specName = buffer.readString(buffer.readByte());
                PBManager.createMetaClient(
                        EnumPBPlantType.getOf(plantTypeStr), 
                        registryName, 
                        meta, 
                        specName, 
                        "");
                PBManager.getClient(registryName).setMainMeta(mainMeta);
                if (buffer.readByte() == 1)
                    PBManager.getClient(registryName).getMeta(meta).denyGlobal();
                deniedAmount = buffer.readByte();
                for (int k = 0; k < deniedAmount; k++) {
                    biomeRegNameStr = buffer.readString(buffer.readByte());
                    biomeRegNameStrSplitted = biomeRegNameStr.split("[:]");
                    biomeRegName = new ResourceLocation(biomeRegNameStrSplitted[0], biomeRegNameStrSplitted[1]); 
                    PBManager.getClient(registryName).getMeta(meta).denyBiome(biomeRegName);
                }
                validAmount = buffer.readByte();;
                for (int k = 0; k < validAmount; k++) {
                    biomeRegNameStr = buffer.readString(buffer.readByte());
                    biomeRegNameStrSplitted = biomeRegNameStr.split("[:]");
                    biomeRegName = new ResourceLocation(biomeRegNameStrSplitted[0], biomeRegNameStrSplitted[1]); 
                    PBManager.getClient(registryName).getMeta(meta).addValidBiome(biomeRegName);
                }
            }
        } else if (this.action == EnumAction.REMOVE_LATEST.ordinal()) {
            String regNameStr = buffer.readString(buffer.readByte());
            String[] regNameStrSplitted = regNameStr.split("[:]");
            ResourceLocation registryName = new ResourceLocation(regNameStrSplitted[0], regNameStrSplitted[1]);
            if (PBManager.existClient(registryName))
                PBManager.removeMetaClient(registryName, buffer.readByte());
        }
    }

    @Override
    public void process(INetHandler netHandler) {
        if (this.action == EnumAction.REMOVE_ALL.ordinal())
            PBManager.clearDataClient();
    }

    public enum EnumAction {

        SYNC_ALL,
        SYNC_LATEST,
        REMOVE_ALL,
        REMOVE_LATEST;
    }
}
