package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import hyper.giga.ultra.gaming.menu.plus.menuitem.Alignment;
import hyper.giga.ultra.gaming.menu.plus.menuitem.ImageWithTextMenuItem;

public class ImageWithTextMenuItemArgsParser
{
    public static void parse(ImageWithTextMenuItem.Builder builder, JsonObject itemObject)
    {
        JsonElement textElement = itemObject.get("text");
        if (textElement != null && textElement.isJsonObject()) {
            JsonObject textObject = textElement.getAsJsonObject();
            CoolText text = CoolTextParser.parse(textObject.get("text")).orElse(null);
            if (text != null) {
                int[] offset = OffsetParser.parse(textObject.get("offset"));
                Alignment alignment = AlignmentParser.parse(textObject.get("alignment")).orElse(Alignment.Center);
                builder.addText(text, offset[0], offset[1], alignment);
            }
        }
        
        JsonElement selectedTextElement = itemObject.get("selected_text");
        if (selectedTextElement != null && selectedTextElement.isJsonObject()) {
            JsonObject textObject = selectedTextElement.getAsJsonObject();
            CoolText text = CoolTextParser.parse(textObject.get("text")).orElse(null);
            if (text != null) {
                Integer[] offset = OffsetParser.parseNullable(textObject.get("offset"));
                Alignment alignment = AlignmentParser.parse(textObject.get("alignment")).orElse(null);
                builder.addSelectedText(text, offset[0], offset[1], alignment);
            }
        }
        
        JsonElement imageElement = itemObject.get("image");
        if (imageElement != null && imageElement.isJsonObject()) {
            JsonObject imageObject = imageElement.getAsJsonObject();
            CoolImage image = ImageParser.parse(imageObject.get("image"));
            if (image != null) {
                int[] offset = OffsetParser.parse(imageObject.get("offset"));
                Alignment alignment = AlignmentParser.parse(imageObject.get("alignment")).orElse(Alignment.Center);
                builder.addImage(image, offset[0], offset[1], alignment);
            }
        }
        
        JsonElement selectedImageElement = itemObject.get("selected_image");
        if (selectedImageElement != null && selectedImageElement.isJsonObject()) {
            JsonObject imageObject = selectedImageElement.getAsJsonObject();
            CoolImage image = ImageParser.parse(imageObject.get("image"));
            if (image != null) {
                Integer[] offset = OffsetParser.parseNullable(imageObject.get("offset"));
                Alignment alignment = AlignmentParser.parse(imageObject.get("alignment")).orElse(null);
                builder.addSelectedImage(image, offset[0], offset[1], alignment);
            }
        }
    }
}
