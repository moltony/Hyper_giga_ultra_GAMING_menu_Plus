package hyper.giga.ultra.gaming.menu.plus;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolGradientBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImageBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImageBackgroundMode;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSolidColorBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.Alignment;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.SeparatorMenuItem;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ConfigurationLoader
{
    //
    // Default configuration values
    //
    
    private static final CoolSolidColorBackground DEFAULT_SCREEN_BACKGROUND = new CoolSolidColorBackground(Color.BLACK);
    private static final int DEFAULT_SCREEN_WIDTH = 480;
    private static final int DEFAULT_SCREEN_HEIGHT = 360;

    //
    // various json-related utilities
    //
    
    private static JsonElement jsonGetOrNull(String property, JsonObject object)
    {
        JsonElement element = object.get(property);
        return (element == null || element.isJsonNull()) ? null : element;
    }
    
    private static int jsonGetOrDefaultInt(String property, JsonObject object, int defaultValue)
    {
        JsonElement element = jsonGetOrNull(property, object);
        return element == null ? defaultValue : element.getAsInt();
    }
    
    private static float jsonGetOrDefaultFloat(String property, JsonObject object, float defaultValue)
    {
        JsonElement element = jsonGetOrNull(property, object);
        return element == null ? defaultValue : element.getAsFloat();
    }
    
    private static String jsonGetOrDefaultString(String property, JsonObject object, String defaultValue)
    {
        JsonElement element = jsonGetOrNull(property, object);
        return element == null ? defaultValue : element.getAsString();
    }
    
    //
    // various thing parsers
    //

    private static int parseColorValueDefault(JsonElement element, int defaultValue)
    {
        if (element == null || element.isJsonNull() || !element.isJsonPrimitive()) {
            return defaultValue;
        }
        
        int value = element.getAsInt();
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Color value must not be negative or higher than 255");
        }

        return value;
    }
    
    private static int parseColorValue(JsonElement element)
    {
        int value = parseColorValueDefault(element, -1);
        if (value == -1) {
            throw new IllegalArgumentException("Color value not present or has incorrect type");
        }
        return value;
    }

    private static Color parseColor(JsonElement element, Color defaultValue)
    {
        if (element == null || element.isJsonNull()) {
            if (defaultValue == null) {
                throw new IllegalArgumentException("Color not present");
            }
            return defaultValue;
        }
        if (!element.isJsonObject()) {
            throw new IllegalArgumentException("Color JSON element must be of object type");
        }
        JsonObject colorObject = element.getAsJsonObject();
        
        int r = parseColorValue(colorObject.get("r"));
        int g = parseColorValue(colorObject.get("g"));
        int b = parseColorValue(colorObject.get("b"));
        
        return new Color(r, g, b);
    }
    
    private static CoolImage parseImage(JsonElement element)
    {
        if (element == null || element.isJsonNull() || !element.isJsonObject()) {
            throw new IllegalArgumentException("Image not present or has incorrect data type");
        }
        JsonObject imageObject = element.getAsJsonObject();
        
        String imageFile = imageObject.get("file").getAsString();
        if (imageFile == null) {
            throw new IllegalArgumentException("Image path not present");
        }
        
        float angle = jsonGetOrDefaultFloat("angle", imageObject, 0.0f);

        float scaleX = 1.0f;
        float scaleY = 1.0f;
        JsonElement scaleElement = imageObject.get("scale");
        if (!(scaleElement == null || scaleElement.isJsonNull() || !scaleElement.isJsonObject())) {
            JsonObject scaleObject = scaleElement.getAsJsonObject();
            scaleX = scaleObject.get("x").getAsFloat();
            scaleY = scaleObject.get("y").getAsFloat();
        }
        
        Color tint = parseColor(imageObject.get("tint"), Color.WHITE);
        int frameDelay = jsonGetOrDefaultInt("frame_delay", imageObject, 0);
        
        if (imageFile.toLowerCase().endsWith(".gif")) {
            BufferedImage[] images = AnimationLoader.loadGIF(imageFile);
            return new CoolImage(images, angle, scaleX, scaleY, tint, frameDelay);
        }
        return new CoolImage(imageFile, angle, scaleX, scaleY, tint);
    }
    
    // this warning shows up when assigning the background mode
    // spoiler alert: it is used
    @SuppressWarnings("UnusedAssignment")
    private static CoolBackground parseBackground(JsonElement element, CoolBackground defaultValue)
    {
        if (element == null || element.isJsonNull()) {
            return defaultValue;
        }
        if (!element.isJsonObject()) {
            throw new IllegalArgumentException("Background JSON element must be of object type");
        }
        JsonObject backgroundObject = element.getAsJsonObject();
        
        JsonElement typeElement = backgroundObject.get("type");
        if (typeElement == null || typeElement.isJsonNull()) {
            throw new IllegalArgumentException("Background type must be present and not be null");
        }
        String typeString = typeElement.getAsString();

        switch (typeString) {
            case "solid" -> {
                Color color = parseColor(backgroundObject.get("color"), Color.BLACK);
                return new CoolSolidColorBackground(color);
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
                    colors[i] = parseColor(colorsArray.get(i), null);
                }
                
                // step 3 angle
                float angle = jsonGetOrDefaultFloat("angle", backgroundObject, 0.0f);
                
                // step 4 put them together
                return new CoolGradientBackground(colors, fractions, angle);
            }
            case "image" -> {
                CoolImage image = parseImage(backgroundObject.get("image"));
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
                Color backgroundColor = parseColor(backgroundObject.get("color"), Color.BLACK);
                return new CoolImageBackground(image, mode, backgroundColor);
            }
            default -> {
                throw new IllegalArgumentException(String.format("Unknown background type: %s", typeString));
            }
        }
    }
    
    private static CoolBackground[] parseMenuItemBackgrounds(JsonObject itemObject)
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
            parseBackground(backgroundObject.get("normal"), MenuItem.BACKGROUND_NORMAL_DEFAULT),
            parseBackground(backgroundObject.get("selected"), MenuItem.BACKGROUND_SELECTED_DEFAULT)
        };
    }
    
    private static Alignment parseAlignment(JsonElement alignmentElement)
    {
        if (alignmentElement == null || !alignmentElement.isJsonPrimitive()) {
            return Alignment.Center;
        }
        
        String alignmentString = alignmentElement.getAsString();
        
        // this is what they call modern java i think
        return switch (alignmentString) {
            case "center" -> Alignment.Center;
            case "left" -> Alignment.Left;
            case "right" -> Alignment.Right;
            default -> throw new IllegalArgumentException(String.format("Invalid alignment: %s", alignmentString));
        };
    }
    
    private static int[] parseOffset(JsonElement offsetElement)
    {
        if (offsetElement == null || !offsetElement.isJsonObject()) {
            return new int[] {0, 0};
        }
        
        JsonObject offsetObject = offsetElement.getAsJsonObject();
        int x = jsonGetOrDefaultInt("x", offsetObject, 0);
        int y = jsonGetOrDefaultInt("y", offsetObject, 0);
        return new int[] {x, y};
    }
    
    private static CoolSound[] parseSounds(JsonObject container)
    {
        JsonElement soundsElement = container.get("sounds");
        if (soundsElement == null || !soundsElement.isJsonObject()) {
            return new CoolSound[] {null, null};
        }
        JsonObject soundsObject = soundsElement.getAsJsonObject();
        
        String selectSoundPath = jsonGetOrDefaultString("select", soundsObject, null);
        String interactSoundPath = jsonGetOrDefaultString("interact", soundsObject, null);
        return new CoolSound[] {
            selectSoundPath == null ? null : new CoolSound(selectSoundPath),
            interactSoundPath == null ? null : new CoolSound(interactSoundPath)
        };
    }
    
    private static SeparatorMenuItem parseSeparator(JsonObject itemObject)
    {
        Color color = parseColor(itemObject.get("color"), Color.LIGHT_GRAY);
        int thickness = jsonGetOrDefaultInt("thickness", itemObject, 1);
        int length = jsonGetOrDefaultInt("length", itemObject, 80);
        Alignment alignment = parseAlignment(itemObject.get("alignment"));
        int[] offset = parseOffset(itemObject.get("offset"));
        CoolBackground[] backgrounds = parseMenuItemBackgrounds(itemObject);
        CoolSound[] sounds = parseSounds(itemObject);
        return new SeparatorMenuItem(
                backgrounds[0],
                backgrounds[1],
                sounds[0],
                sounds[1],
                color,
                thickness,
                length,
                alignment,
                offset[0],
                offset[1]
        );
    }
    
    private static MenuItem parseMenuItem(JsonObject itemObject)
    {
        JsonElement typeElement = itemObject.get("type");
        if (typeElement == null || typeElement.isJsonNull() || !typeElement.isJsonPrimitive()) {
            throw new IllegalArgumentException("Menu item type not present or invalid");
        }
        
        String typeString = typeElement.getAsString();
        switch (typeString) {
            case "separator" -> {
                return parseSeparator(itemObject);
            }
        }
        throw new IllegalArgumentException(String.format("Invalid menu item type: %s", typeString));
    }
    
    private static Screen parseScreen(JsonObject screenObject) throws IllegalArgumentException
    {
        int width = jsonGetOrDefaultInt("width", screenObject, DEFAULT_SCREEN_WIDTH);
        int height = jsonGetOrDefaultInt("height", screenObject, DEFAULT_SCREEN_HEIGHT);
        CoolBackground background = parseBackground(screenObject.get("background"), DEFAULT_SCREEN_BACKGROUND);
        
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
                items[i] = parseMenuItem(itemObject);
            }
        }
        
        CoolSound[] sounds = parseSounds(screenObject);
        
        return new Screen(
                items,
                width,
                height,
                background,
                sounds[0],
                sounds[1]
        );
    }
    
    public static Screen[] loadConfiguration(String configurationFile)
    {
        // Read the file
        FileReader fileReader;
        try {
            fileReader = new FileReader(configurationFile);
        } catch (FileNotFoundException exc) {
            ErrorHandler.handleError(exc, "Configuration file not found");
            throw new RuntimeException();
        }
        
        JsonObject root = JsonParser.parseReader(fileReader).getAsJsonObject();
        JsonArray screensArray = root.get("screens").getAsJsonArray();
        ArrayList<Screen> screens = new ArrayList<>();
        for (JsonElement screenElement : screensArray) {
            JsonObject screenObject = screenElement.getAsJsonObject();
            try {
                screens.add(parseScreen(screenObject));
            } catch (IllegalArgumentException exc) {
                ErrorHandler.handleError(exc, "Error parsing screen");
                return null;
            }
        }
        
        return screens.toArray(Screen[]::new);
    }
}
