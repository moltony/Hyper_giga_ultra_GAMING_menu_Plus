package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Font;
import java.util.Optional;

public class FontParser
{
    public static Optional<Font> parse(JsonElement element)
    {
        if (element == null || !element.isJsonObject()) {
            return Optional.empty();
        }
        JsonObject fontObject = element.getAsJsonObject();
        
        JsonElement nameElement = fontObject.get("name");
        if (nameElement == null || !nameElement.isJsonPrimitive()) {
            throw new IllegalArgumentException("Font name not present or has incorrect type");
        }
        String fontName = nameElement.getAsString();
        
        JsonElement sizeElement = fontObject.get("size");
        if (sizeElement == null || !nameElement.isJsonPrimitive()) {
            throw new IllegalArgumentException("Font size not present or has incorrect type");
        }
        int fontSize = sizeElement.getAsInt();
        
        JsonElement styleElement = fontObject.get("style");
        int fontStyle = Font.PLAIN;
        if (styleElement != null && styleElement.isJsonPrimitive()) {
            String styleString = styleElement.getAsString();
            switch (styleString) {
                case "bold" -> {
                    fontStyle = Font.BOLD;
                }
                case "italic" -> {
                    fontStyle = Font.ITALIC;
                }
                case "plain" -> {
                    fontStyle = Font.PLAIN;
                }
                default -> {
                    throw new IllegalArgumentException(String.format("Invalid font style: %s", styleString));
                }
            }
        }

        return Optional.of(new Font(fontName, fontStyle, fontSize));
    }
}
