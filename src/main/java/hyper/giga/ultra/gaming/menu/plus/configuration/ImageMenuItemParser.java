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
        Alignment alignment = AlignmentParser.parse(itemObject.get("alignment")).orElse(Alignment.Center);
        int[] offset = OffsetParser.parse(itemObject.get("offset"));
        return new ImageMenuItem(
                backgroundNormal,
                backgroundSelected,
                selectionSound,
                interactionSound,
                image,
                alignment,
                offset[0],
                offset[1]
        );
    }
}
