package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hyper.giga.ultra.gaming.menu.plus.ErrorHandler;
import hyper.giga.ultra.gaming.menu.plus.Screen;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class ConfigurationLoader
{
    public static Screen[] loadConfiguration(String configurationFile)
    {
        // Read the file
        FileReader fileReader;
        try {
            fileReader = new FileReader(configurationFile);
        } catch (FileNotFoundException exc) {
            ErrorHandler.handleError(exc, "Configuration file not found");
            throw new RuntimeException();
        }
        
        JsonObject root = JsonParser.parseReader(fileReader).getAsJsonObject();
        JsonArray screensArray = root.get("screens").getAsJsonArray();
        ArrayList<Screen> screens = new ArrayList<>();
        for (JsonElement screenElement : screensArray) {
            JsonObject screenObject = screenElement.getAsJsonObject();
            try {
                screens.add(ScreenParser.parse(screenObject));
            } catch (Exception exc) { // Other things that ScreenParser calls may fail with a non-IAE.
                ErrorHandler.handleError(exc, "Error parsing screen");
            }
        }
        
        return screens.toArray(Screen[]::new);
    }
}
