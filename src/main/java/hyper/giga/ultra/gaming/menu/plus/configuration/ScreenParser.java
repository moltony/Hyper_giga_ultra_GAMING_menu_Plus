package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.JsonUtils;
import hyper.giga.ultra.gaming.menu.plus.Screen;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSolidColorBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import java.awt.Color;

public class ScreenParser
{
    private static final CoolSolidColorBackground DEFAULT_BACKGROUND = new CoolSolidColorBackground(Color.BLACK);
    private static final int DEFAULT_WIDTH = 480;
    private static final int DEFAULT_HEIGHT = 360;
    
    public static Screen parse(JsonObject screenObject) throws IllegalArgumentException
    {
        int width = JsonUtils.jsonGetOrDefaultInt("width", screenObject, DEFAULT_WIDTH);
        int height = JsonUtils.jsonGetOrDefaultInt("height", screenObject, DEFAULT_HEIGHT);
        CoolBackground background = CoolBackgroundParser.parse(screenObject.get("background")).orElse(DEFAULT_BACKGROUND);
        
        JsonElement itemsElement = screenObject.get("items");
        MenuItem[] items;
        if (itemsElement == null || itemsElement.isJsonNull()) {
            // might as well not have any items. that's one way to customize idc
            items = new MenuItem[0];
        } else {
            if (!itemsElement.isJsonArray()) {
                throw new IllegalArgumentException("Items list must be of array type");
            }
            JsonArray itemsArray = itemsElement.getAsJsonArray();
            int itemsArraySize = itemsArray.size();
            items = new MenuItem[itemsArraySize];
            
            for (int i = 0; i < itemsArraySize; i++) {
                JsonElement item = itemsArray.get(i);
                // TODO it's unnecessary to check for isJsonNull and isJsonObject invidivually.
                //      do this where we check for null + type.
                if (item == null || !item.isJsonObject()) {
                    throw new IllegalArgumentException("Menu item must be an object");
                }
                JsonObject itemObject = item.getAsJsonObject();
                items[i] = MenuItemParser.parse(itemObject);
            }
        }
        
        CoolSound[] sounds = SoundsParser.parse(screenObject);
        
        return new Screen(
                items,
                width,
                height,
                background,
                sounds[0],
                sounds[1]
        );
    }
}
