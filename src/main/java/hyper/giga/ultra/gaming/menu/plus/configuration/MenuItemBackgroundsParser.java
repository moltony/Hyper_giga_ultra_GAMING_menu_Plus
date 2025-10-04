package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;

public class MenuItemBackgroundsParser
{
    public static CoolBackground[] parse(JsonObject itemObject)
    {
        JsonElement backgroundElement = itemObject.get("background");
        if (backgroundElement == null || !backgroundElement.isJsonObject()) {
            return new CoolBackground[] {
                MenuItem.BACKGROUND_NORMAL_DEFAULT,
                MenuItem.BACKGROUND_SELECTED_DEFAULT
            };
        }
        
        JsonObject backgroundObject = backgroundElement.getAsJsonObject();
        return new CoolBackground[] {
            CoolBackgroundParser.parse(backgroundObject.get("normal")).orElse(MenuItem.BACKGROUND_NORMAL_DEFAULT),
            CoolBackgroundParser.parse(backgroundObject.get("selected")).orElse(MenuItem.BACKGROUND_SELECTED_DEFAULT)
        };
    }
}
