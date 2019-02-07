package austeretony.plantbiomes.common.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import austeretony.plantbiomes.common.reference.CommonReference;
import austeretony.plantbiomes.common.util.PBUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class UpdateChecker implements Runnable {

    private static String availableVersion = PlantBiomesMain.VERSION;

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (PBManager.isUpdateMessagesEnabled() && CommonReference.isOpped(event.player))
            if (PBUtils.isOutdated(PlantBiomesMain.VERSION, availableVersion))	
                EnumChatMessages.UPDATE_MESSAGE.sendMessage(event.player, availableVersion);
    }

    @Override
    public void run() {
        PlantBiomesMain.LOGGER.info("Update check started...");
        URL versionsURL;		
        try {			
            versionsURL = new URL(PlantBiomesMain.VERSIONS_URL);
        } catch (MalformedURLException exception) {			
            exception.printStackTrace();			
            return;
        }
        JsonObject remoteData;					
        try (InputStream inputStream = versionsURL.openStream()) {			
            remoteData = (JsonObject) new JsonParser().parse(new InputStreamReader(inputStream, "UTF-8")); 
        } catch (UnknownHostException exception) {		
            PlantBiomesMain.LOGGER.error("Update check failed, no internet connection.");		
            return;
        } catch (FileNotFoundException exception) {			
            PlantBiomesMain.LOGGER.error("Update check failed, remote file is absent.");			
            return;
        } catch (IOException exception) {						
            exception.printStackTrace();			
            return;
        }				        
        JsonObject data;          
        try {        	
            data = remoteData.get(PlantBiomesMain.GAME_VERSION).getAsJsonObject();      
        } catch (NullPointerException exception) {        	
            PlantBiomesMain.LOGGER.error("Update check failed, data is undefined for " + PlantBiomesMain.GAME_VERSION + " version.");        	
            return;
        }        
        availableVersion = data.get("available").getAsString();
        PlantBiomesMain.LOGGER.info("Update check ended. Current/available: " + PlantBiomesMain.VERSION + "/" + availableVersion);
    }
}
