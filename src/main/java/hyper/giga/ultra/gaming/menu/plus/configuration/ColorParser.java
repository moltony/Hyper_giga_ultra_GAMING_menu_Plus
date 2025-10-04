package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import java.util.Optional;

public class ColorParser
{
    private static int parseValueDefault(JsonElement element, int defaultValue)
    {
        if (element == null || element.isJsonNull() || !element.isJsonPrimitive()) {
            return defaultValue;
        }
        
        int value = element.getAsInt();
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Color value must not be negative or higher than 255");
        }

        return value;
    }
    
    private static int parseValue(JsonElement element)
    {
        int value = parseValueDefault(element, -1);
        if (value == -1) {
            throw new IllegalArgumentException("Color value not present or has incorrect type");
        }
        return value;
    }

    public static Optional<Color> parse(JsonElement element)
    {
        if (element == null || element.isJsonNull()) {
            return Optional.empty();
        }
        if (!element.isJsonObject()) {
            throw new IllegalArgumentException("Color JSON element must be of object type");
        }
        JsonObject colorObject = element.getAsJsonObject();
        
        int r = parseValue(colorObject.get("r"));
        int g = parseValue(colorObject.get("g"));
        int b = parseValue(colorObject.get("b"));
        int a = parseValueDefault(colorObject.get("a"), 255);
        
        return Optional.of(new Color(r, g, b, a));
    }
}
