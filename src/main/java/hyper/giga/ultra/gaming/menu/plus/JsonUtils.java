package hyper.giga.ultra.gaming.menu.plus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils
{
    public static JsonElement jsonGetOrNull(String property, JsonObject object)
    {
        JsonElement element = object.get(property);
        return (element == null || element.isJsonNull()) ? null : element;
    }
    
    public static int jsonGetOrDefaultInt(String property, JsonObject object, int defaultValue)
    {
        JsonElement element = jsonGetOrNull(property, object);
        return element == null ? defaultValue : element.getAsInt();
    }
    
    public static float jsonGetOrDefaultFloat(String property, JsonObject object, float defaultValue)
    {
        JsonElement element = jsonGetOrNull(property, object);
        return element == null ? defaultValue : element.getAsFloat();
    }
    
    public static String jsonGetOrDefaultString(String property, JsonObject object, String defaultValue)
    {
        JsonElement element = jsonGetOrNull(property, object);
        return element == null ? defaultValue : element.getAsString();
    }
    
    public static double jsonGetOrDefaultDouble(String property, JsonObject object, double defaultValue)
    {
        JsonElement element = jsonGetOrNull(property, object);
        return element == null ? defaultValue : element.getAsDouble();
    }
}
