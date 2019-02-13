package austeretony.plantbiomes.common.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import austeretony.plantbiomes.common.config.EnumConfigSettings;
import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class UpdateChecker implements Runnable {

    private static String availableVersion = PlantBiomesMain.VERSION_CUSTOM;

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (EnumConfigSettings.CHECK_UPDATES.isEnabled() 
                && CommonReference.isOpped(event.player))
            if (isOutdated(PlantBiomesMain.VERSION_CUSTOM, availableVersion))	
                EnumChatMessages.UPDATE_MESSAGE.sendMessage(event.player, availableVersion);
    }

    @Override
    public void run() {
        PlantBiomesMain.LOGGER.info("Update check started...");
        URL versionsURL;		
        try {			
            versionsURL = new URL(PlantBiomesMain.VERSIONS_CUSTOM_URL);
        } catch (MalformedURLException exception) {			
            exception.printStackTrace();			
            return;
        }
        JsonObject remoteData;					
        try (InputStream inputStream = versionsURL.openStream()) {			
            remoteData = (JsonObject) new JsonParser().parse(new InputStreamReader(inputStream, "UTF-8")); 
        } catch (IOException exception) {		
            PlantBiomesMain.LOGGER.error("Update check failed!");		
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
        PlantBiomesMain.LOGGER.info("Update check ended. Current/available: " + PlantBiomesMain.VERSION_CUSTOM + "/" + availableVersion);
    }

    public static boolean isOutdated(String currentVersion, String availableVersion) {        
        try {
            String[] 
                    cSplitted = currentVersion.split("[:]"),
                    aSplitted = availableVersion.split("[:]");    
            String 
            cVer = cSplitted[0],
            cType = cSplitted[1],
            cRev = cSplitted[2],
            aVer = aSplitted[0],
            aType = aSplitted[1],
            aRev = aSplitted[2];
            String[]
                    cVerSplitted = cVer.split("[.]"),
                    aVerSplitted = aVer.split("[.]");
            int verDiff, revDiff;               
            for (int i = 0; i < 3; i++) {                                                             
                verDiff = Integer.parseInt(aVerSplitted[i]) - Integer.parseInt(cVerSplitted[i]);                                                                                           
                if (verDiff > 0)
                    return true;                                
                if (verDiff < 0)
                    return false;
            }  
            if (aType.equals("release") && (cType.equals("beta") || cType.equals("alpha")))
                return true;
            if (aType.equals("beta") && cType.equals("alpha"))
                return true;
            revDiff = Integer.parseInt(aRev) - Integer.parseInt(cRev);                                                                                           
            if (revDiff > 0)
                return true;                                
            if (revDiff < 0)
                return false;
            return false;
        } catch (Exception exception) {
            PlantBiomesMain.LOGGER.error("Versions comparison failed!");               
            exception.printStackTrace();
        }
        return false;
    }
}
