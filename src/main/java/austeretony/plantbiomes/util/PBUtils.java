package austeretony.plantbiomes.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class PBUtils {

    public static JsonElement getInternalJsonData(String path) throws IOException {	
        JsonElement rawData = null;		
        try (InputStream inputStream = PBUtils.class.getClassLoader().getResourceAsStream(path)) {				    	
            rawData = new JsonParser().parse(new InputStreamReader(inputStream, "UTF-8"));  
        }
        return rawData;
    }

    public static JsonElement getExternalJsonData(String path) throws IOException {		
        JsonElement rawData = null;		
        try (InputStream inputStream = new FileInputStream(new File(path))) {				    	
            rawData = new JsonParser().parse(new InputStreamReader(inputStream, "UTF-8"));  
        }		
        return rawData;
    }

    public static void createExternalJsonFile(String path, JsonElement data) throws IOException {		
        try (Writer writer = new FileWriter(path)) {    		    	        	
            new GsonBuilder().setPrettyPrinting().create().toJson(data, writer);
        }
    }

    public static boolean isOutdated(String currentVersion, String availableVersion) {                                                               
        String[] 
                cVer = currentVersion.split("[.]"),
                aVer = availableVersion.split("[.]");                           
        int diff;               
        for (int i = 0; i < cVer.length; i++) {                                 
            try {                               
                diff = Integer.parseInt(aVer[i]) - Integer.parseInt(cVer[i]);                                                                                           
                if (diff > 0)
                    return true;                                
                if (diff < 0)
                    return false;
            } catch (NumberFormatException exception) {                         
                exception.printStackTrace();
            }
        }               
        return false;
    }
}
