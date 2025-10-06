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
        Color selectedColor = ColorParser.parse(itemObject.get("selected_color")).orElse(null);
        int thickness = JsonUtils.jsonGetOrDefaultInt("thickness", itemObject, 1);
        Integer selectedThickness = JsonUtils.jsonGetOrNullInt("selected_thickness", itemObject);
        int length = JsonUtils.jsonGetOrDefaultInt("length", itemObject, 80);
        Integer selectedLength = JsonUtils.jsonGetOrNullInt("selected_length", itemObject);
        Alignment alignment = AlignmentParser.parse(itemObject.get("alignment")).orElse(Alignment.Center);
        Alignment selectedAlignment = AlignmentParser.parse(itemObject.get("selected_alignment")).orElse(null);
        int[] offset = OffsetParser.parse(itemObject.get("offset"));
        Integer[] selectedOffset = OffsetParser.parseNullable(itemObject.get("selected_offset"));
        return new SeparatorMenuItem(
                backgroundNormal,
                backgroundSelected,
                selectionSound,
                interactionSound,
                color,
                selectedColor,
                thickness,
                selectedThickness,
                length,
                selectedLength,
                alignment,
                selectedAlignment,
                offset[0],
                selectedOffset[0],
                offset[1],
                selectedOffset[1]
        );
    }
}
