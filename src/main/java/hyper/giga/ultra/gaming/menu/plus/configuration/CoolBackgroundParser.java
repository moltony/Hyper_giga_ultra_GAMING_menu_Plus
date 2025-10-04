package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.JsonUtils;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolGradientBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImageBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImageBackgroundMode;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSolidColorBackground;
import java.awt.Color;
import java.util.Optional;

public class CoolBackgroundParser
{
    // this warning shows up when assigning the background mode
    // spoiler alert: it is used
    @SuppressWarnings("UnusedAssignment")
    public static Optional<CoolBackground> parse(JsonElement element)
    {
        if (element == null || !element.isJsonObject()) {
            return Optional.empty();
        }
        JsonObject backgroundObject = element.getAsJsonObject();
        
        JsonElement typeElement = backgroundObject.get("type");
        if (typeElement == null || typeElement.isJsonNull()) {
            throw new IllegalArgumentException("Background type must be present and not be null");
        }
        String typeString = typeElement.getAsString();

        switch (typeString) {
            case "solid" -> {
                Color color = ColorParser.parse(backgroundObject.get("color")).orElse(Color.BLACK);
                return Optional.of(new CoolSolidColorBackground(color));
            }
            case "gradient" -> {
                // step 1 parse fractions
                JsonElement fractionsElement = backgroundObject.get("fractions");
                if (fractionsElement == null || !fractionsElement.isJsonArray()) {
                    throw new IllegalArgumentException("Gradient background fractions must be an array");
                }
                JsonArray fractionsArray = fractionsElement.getAsJsonArray();
                int fractionsArraySize = fractionsArray.size();
                float[] fractions = new float[fractionsArraySize];
                for (int i = 0; i < fractionsArraySize; i++) {
                    JsonElement fractionElement = fractionsArray.get(i);
                    if (!fractionElement.isJsonPrimitive()) {
                        throw new IllegalArgumentException("Fractions array must only contain primitives");
                    }
                    fractions[i] = fractionElement.getAsFloat();
                }
                
                // step 2 parse colors
                JsonElement colorsElement = backgroundObject.get("colors");
                if (colorsElement == null || !colorsElement.isJsonArray()) {
                    throw new IllegalArgumentException("Gradient background colors must be an array");
                }
                JsonArray colorsArray = colorsElement.getAsJsonArray();
                int colorsArraySize = colorsArray.size();
                Color[] colors = new Color[colorsArraySize];
                for (int i = 0; i < colorsArraySize; i++) {
                    colors[i] = ColorParser.parse(colorsArray.get(i)).get();
                }
                
                // step 3 angle
                float angle = JsonUtils.jsonGetOrDefaultFloat("angle", backgroundObject, 0.0f);
                
                // step 4 put them together
                return Optional.of(new CoolGradientBackground(colors, fractions, angle));
            }
            case "image" -> {
                CoolImage image = ImageParser.parse(backgroundObject.get("image"));
                JsonElement modeStringObject = backgroundObject.get("mode");
                CoolImageBackgroundMode mode = CoolImageBackgroundMode.Center;
                if (!(modeStringObject == null || modeStringObject.isJsonNull() || !modeStringObject.isJsonPrimitive())) {
                    String modeString = modeStringObject.getAsString();
                    mode = switch (modeString) {
                        case "center" -> CoolImageBackgroundMode.Center;
                        case "zoom" -> CoolImageBackgroundMode.Zoom;
                        case "tile" -> CoolImageBackgroundMode.Tile;
                        case "stretch" -> CoolImageBackgroundMode.Stretch;
                        default -> throw new IllegalArgumentException(String.format("Unknown image background mode: %s", modeString));
                    };
                }
                Color backgroundColor = ColorParser.parse(backgroundObject.get("color")).orElse(Color.BLACK);
                return Optional.of(new CoolImageBackground(image, mode, backgroundColor));
            }
            default -> {
                throw new IllegalArgumentException(String.format("Unknown background type: %s", typeString));
            }
        }
    }
}
