package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.AnimationLoader;
import hyper.giga.ultra.gaming.menu.plus.JsonUtils;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageParser
{ 
    public static CoolImage parse(JsonElement element)
    {
        if (element == null || element.isJsonNull() || !element.isJsonObject()) {
            throw new IllegalArgumentException("Image not present or has incorrect data type");
        }
        JsonObject imageObject = element.getAsJsonObject();
        
        String imageFile = imageObject.get("file").getAsString();
        if (imageFile == null) {
            throw new IllegalArgumentException("Image path not present");
        }
        
        float angle = JsonUtils.jsonGetOrDefaultFloat("angle", imageObject, 0.0f);

        float scaleX = 1.0f;
        float scaleY = 1.0f;
        JsonElement scaleElement = imageObject.get("scale");
        if (!(scaleElement == null || scaleElement.isJsonNull() || !scaleElement.isJsonObject())) {
            JsonObject scaleObject = scaleElement.getAsJsonObject();
            scaleX = scaleObject.get("x").getAsFloat();
            scaleY = scaleObject.get("y").getAsFloat();
        }
        
        Color tint = ColorParser.parse(imageObject.get("tint")).orElse(Color.WHITE);
        int frameDelay = JsonUtils.jsonGetOrDefaultInt("frame_delay", imageObject, 0);
        
        if (imageFile.toLowerCase().endsWith(".gif")) {
            BufferedImage[] images = AnimationLoader.loadGIF(imageFile);
            return new CoolImage(images, angle, scaleX, scaleY, tint, frameDelay);
        }
        return new CoolImage(imageFile, angle, scaleX, scaleY, tint);
    }
}
