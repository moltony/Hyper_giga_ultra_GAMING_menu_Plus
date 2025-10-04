package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.JsonUtils;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import java.awt.Color;
import java.awt.Font;
import java.util.Optional;

public class CoolTextParser
{
    public static Optional<CoolText> parse(JsonElement element)
    {
        if (element == null || !element.isJsonObject()) {
            return Optional.empty();
        }
        JsonObject textObject = element.getAsJsonObject();
        
        String text = JsonUtils.jsonGetOrDefaultString("text", textObject, "");
        Font font = FontParser.parse(textObject.get("font")).orElse(CoolText.DEFAULT_FONT);
        Color color = ColorParser.parse(textObject.get("color")).orElse(Color.WHITE);
        double angle = JsonUtils.jsonGetOrDefaultDouble("angle", textObject, 0.0);
        return Optional.of(new CoolText(text, font, color, angle));
    }
}
