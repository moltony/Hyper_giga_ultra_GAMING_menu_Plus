package hyper.giga.ultra.gaming.menu.plus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSolidColorBackground;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ConfigurationLoader
{
    //
    // Default configuration values
    //
    
    private static final CoolSolidColorBackground DEFAULT_SCREEN_BACKGROUND = new CoolSolidColorBackground(Color.BLACK);
    private static final int DEFAULT_SCREEN_WIDTH = 480;
    private static final int DEFAULT_SCREEN_HEIGHT = 360;

    //
    // various json-related utilities
    //
    
    private static JsonElement jsonGetOrNull(String property, JsonObject object)
    {
        JsonElement element = object.get(property);
        return (element == null || element.isJsonNull()) ? null : element;
    }
    
    private static int jsonGetOrDefaultInt(String property, JsonObject object, int defaultValue)
    {
        JsonElement element = jsonGetOrNull(property, object);
        return element == null ? defaultValue : element.getAsInt();
    }
    
    //
    // various thing parsers
    //
    
    private static int parseColorValue(JsonElement element)
    {
        if (element == null || element.isJsonNull() || !element.isJsonPrimitive()) {
            throw new IllegalArgumentException("Color value not present or has incorrect type");
        }
        
        int value = element.getAsInt();
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Color value must not be negative or higher than 255");
        }

        return value;
    }
    
    private static Color parseColor(JsonElement element, Color defaultValue)
    {
        if (element == null || element.isJsonNull()) {
            return defaultValue;
        }
        if (!element.isJsonObject()) {
            throw new IllegalArgumentException("Color JSON element must be of object type");
        }
        JsonObject colorObject = element.getAsJsonObject();
        
        int r = parseColorValue(colorObject.get("r"));
        int g = parseColorValue(colorObject.get("g"));
        int b = parseColorValue(colorObject.get("b"));
        return new Color(r, g, b);
    }
    
    private static CoolBackground parseBackground(JsonElement element, CoolBackground defaultValue)
    {
        if (element == null || element.isJsonNull()) {
            return defaultValue;
        }
        if (!element.isJsonObject()) {
            throw new IllegalArgumentException("Background JSON element must be of object type");
        }
        JsonObject backgroundObject = element.getAsJsonObject();
        
        JsonElement typeElement = backgroundObject.get("type");
        if (typeElement == null || typeElement.isJsonNull()) {
            throw new IllegalArgumentException("Background type must be present and not be null");
        }
        String typeString = typeElement.getAsString();

        switch (typeString) {
            case "solid" -> {
                Color color = parseColor(backgroundObject.get("color"), Color.BLACK);
                return new CoolSolidColorBackground(color);
            }
            case "gradient" -> {
            }
            case "image" -> {
                
            }
            default -> {
                throw new IllegalArgumentException(String.format("Unknown background type: %s", typeString));
            }
        }
        
        return null;
    }
    
    private static Screen parseScreen(JsonObject screenElement) throws IllegalArgumentException
    {
        int width = jsonGetOrDefaultInt("width", screenElement, DEFAULT_SCREEN_WIDTH);
        int height = jsonGetOrDefaultInt("height", screenElement, DEFAULT_SCREEN_HEIGHT);
        CoolBackground background = parseBackground(screenElement.get("background"), DEFAULT_SCREEN_BACKGROUND);
        
        return new Screen(
                new MenuItem[] {},
                width,
                height,
                background,
                null, null
        );
    }
    
    public static Screen[] loadConfiguration(String configurationFile)
    {
        // Read the file
        String configFileContent;
        try (BufferedReader reader = new BufferedReader(new FileReader(configurationFile))) {
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
                line = reader.readLine();
            }
            configFileContent = builder.toString();
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
            return null;
        }
        
        JsonObject root = JsonParser.parseString(configFileContent).getAsJsonObject();
        JsonArray screensArray = root.get("screens").getAsJsonArray();
        ArrayList screens = new ArrayList<>();
        for (JsonElement screenElement : screensArray) {
            JsonObject screenObject = screenElement.getAsJsonObject();
            screens.add(parseScreen(screenObject));
        }
        
        return (Screen[])screens.toArray(new Screen[0]);
    }
}
