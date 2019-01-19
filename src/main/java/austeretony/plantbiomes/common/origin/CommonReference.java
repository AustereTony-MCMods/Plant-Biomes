package austeretony.plantbiomes.common.origin;

import java.io.File;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

public class CommonReference {

    public static String getGameFolder() {
        return ((File) (FMLInjectionData.data()[6])).getAbsolutePath();
    }

    public static void registerEvent(Object eventClazz) {
        MinecraftForge.EVENT_BUS.register(eventClazz);
    }

    public static void registerCommand(FMLServerStartingEvent event, ICommand command) {
        event.registerServerCommand(command);
    }

    public static boolean isOpped(EntityPlayer player) {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().canSendCommands(player.getGameProfile());
    }
}