package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.JsonUtils;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.Alignment;
import hyper.giga.ultra.gaming.menu.plus.menuitem.SeparatorMenuItem;
import java.awt.Color;

public class SeparatorMenuItemParser
{
    public static SeparatorMenuItem parse(JsonObject itemObject, CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound)
    {
        Color color = ColorParser.parse(itemObject.get("color")).orElse(Color.LIGHT_GRAY);
        int thickness = JsonUtils.jsonGetOrDefaultInt("thickness", itemObject, 1);
        int length = JsonUtils.jsonGetOrDefaultInt("length", itemObject, 80);
        Alignment alignment = AlignmentParser.parse(itemObject.get("alignment")).orElse(Alignment.Center);
        int[] offset = OffsetParser.parse(itemObject.get("offset"));
        return new SeparatorMenuItem(
                backgroundNormal,
                backgroundSelected,
                selectionSound,
                interactionSound,
                color,
                thickness,
                length,
                alignment,
                offset[0],
                offset[1]
        );
    }
}
