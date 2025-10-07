package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.JsonUtils;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.LauncherMenuItem;
import java.util.LinkedList;

public class LauncherMenuItemParser
{
    public static LauncherMenuItem parse(JsonObject itemObject, CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound)
    {
        JsonElement argsElement = itemObject.get("arguments");
        if (argsElement == null || !argsElement.isJsonArray()) {
            throw new IllegalArgumentException("Launcher arguments not present or has incorrect type");
        }
        JsonArray argsArray = argsElement.getAsJsonArray();
        LinkedList<String> argsList = new LinkedList<>();
        for (JsonElement element : argsArray) {
            if (!element.isJsonPrimitive()) {
                throw new IllegalArgumentException("Launcher arguments must be primitives");
            }
            argsList.add(element.getAsString());
        }
        String[] args = argsList.toArray(String[]::new);
        
        String workingDirectory = JsonUtils.jsonGetOrDefaultString("working_directory", itemObject, System.getProperty("user.dir"));
        boolean closeOnLaunch = JsonUtils.jsonGetOrDefaultBoolean("close_on_launch", itemObject, true);
        
        LauncherMenuItem.Builder builder = new LauncherMenuItem.Builder();
        ImageWithTextMenuItemArgsParser.parse(builder, itemObject);
        LauncherMenuItem item = builder.build(backgroundNormal, backgroundSelected, selectionSound, interactionSound, args, workingDirectory, closeOnLaunch);
        return item;
    }
}
