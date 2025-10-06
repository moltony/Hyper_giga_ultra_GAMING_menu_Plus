package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.JsonUtils;

public class OffsetParser
{
    public static int[] parse(JsonElement offsetElement)
    {
        if (offsetElement == null || !offsetElement.isJsonObject()) {
            return new int[] {0, 0};
        }
        
        JsonObject offsetObject = offsetElement.getAsJsonObject();
        int x = JsonUtils.jsonGetOrDefaultInt("x", offsetObject, 0);
        int y = JsonUtils.jsonGetOrDefaultInt("y", offsetObject, 0);
        return new int[] {x, y};
    }
    
    public static Integer[] parseNullable(JsonElement offsetElement)
    {
        if (offsetElement == null || !offsetElement.isJsonObject()) {
            return new Integer[] {null, null};
        }
        
        JsonObject offsetObject = offsetElement.getAsJsonObject();
        Integer x = JsonUtils.jsonGetOrNullInt("x", offsetObject);
        Integer y = JsonUtils.jsonGetOrNullInt("y", offsetObject);
        return new Integer[] {x, y};
    }
}
