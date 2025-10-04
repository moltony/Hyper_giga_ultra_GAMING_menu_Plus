package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;

public class MenuItemParser
{
    public static MenuItem parse(JsonObject itemObject)
    {
        JsonElement typeElement = itemObject.get("type");
        if (typeElement == null || typeElement.isJsonNull() || !typeElement.isJsonPrimitive()) {
            throw new IllegalArgumentException("Menu item type not present or invalid");
        }
        
        CoolBackground[] backgrounds = MenuItemBackgroundsParser.parse(itemObject);
        CoolSound[] sounds = SoundsParser.parse(itemObject);
        
        String typeString = typeElement.getAsString();
        switch (typeString) {
            case "separator" -> {
                return SeparatorMenuItemParser.parse(itemObject, backgrounds[0], backgrounds[1], sounds[0], sounds[1]);
            }
        }
        throw new IllegalArgumentException(String.format("Invalid menu item type: %s", typeString));
    }
}
