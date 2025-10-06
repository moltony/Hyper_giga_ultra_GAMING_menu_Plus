package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.Alignment;
import hyper.giga.ultra.gaming.menu.plus.menuitem.ImageMenuItem;

public class ImageMenuItemParser
{
    public static ImageMenuItem parse(JsonObject itemObject, CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound)
    {
        CoolImage image = ImageParser.parse(itemObject.get("image"));
        if (image == null) {
            throw new IllegalArgumentException("Image not present or has incorrect type");
        }
        CoolImage selectedImage = ImageParser.parse(itemObject.get("selected_image"));
        Alignment alignment = AlignmentParser.parse(itemObject.get("alignment")).orElse(Alignment.Center);
        Alignment selectedAlignment = AlignmentParser.parse(itemObject.get("selected_alignment")).orElse(null);
        int[] offset = OffsetParser.parse(itemObject.get("offset"));
        Integer[] selectedOffset = OffsetParser.parseNullable(itemObject.get("selected_offset"));
        return new ImageMenuItem(
                backgroundNormal,
                backgroundSelected,
                selectionSound,
                interactionSound,
                image,
                selectedImage,
                alignment,
                selectedAlignment,
                offset[0],
                selectedOffset[0],
                offset[1],
                selectedOffset[1]
        );
    }
}
