package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.SwitchScreenMenuItem;

public class SwitchScreenMenuItemParser
{
    public static SwitchScreenMenuItem parse(JsonObject itemObject, CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound)
    {
        JsonElement screenElement = itemObject.get("screen");
        if (screenElement == null || !screenElement.isJsonPrimitive()) {
            throw new IllegalArgumentException("Screen ID not present or has incorrect type");
        }
        int screenID = screenElement.getAsInt();
        
        SwitchScreenMenuItem.Builder builder = new SwitchScreenMenuItem.Builder();
        ImageWithTextMenuItemArgsParser.parse(builder, itemObject);
        SwitchScreenMenuItem item = builder.build(backgroundNormal, backgroundSelected, selectionSound, interactionSound, screenID);
        return item;
    }
}
