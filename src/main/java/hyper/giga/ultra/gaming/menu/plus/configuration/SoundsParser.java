package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.JsonUtils;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;

public class SoundsParser
{
    public static CoolSound[] parse(JsonObject container)
    {
        JsonElement soundsElement = container.get("sounds");
        if (soundsElement == null || !soundsElement.isJsonObject()) {
            return new CoolSound[] {null, null};
        }
        JsonObject soundsObject = soundsElement.getAsJsonObject();
        
        String selectSoundPath = JsonUtils.jsonGetOrDefaultString("select", soundsObject, null);
        String interactSoundPath = JsonUtils.jsonGetOrDefaultString("interact", soundsObject, null);
        return new CoolSound[] {
            selectSoundPath == null ? null : new CoolSound(selectSoundPath),
            interactSoundPath == null ? null : new CoolSound(interactSoundPath)
        };
    }
}
