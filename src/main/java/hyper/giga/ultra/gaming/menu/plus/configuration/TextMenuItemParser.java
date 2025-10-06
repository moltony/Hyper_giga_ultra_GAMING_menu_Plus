package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import hyper.giga.ultra.gaming.menu.plus.menuitem.Alignment;
import hyper.giga.ultra.gaming.menu.plus.menuitem.TextMenuItem;
import java.awt.Color;

public class TextMenuItemParser
{
    public static TextMenuItem parse(JsonObject itemObject, CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound)
    {
        CoolText text = CoolTextParser.parse(itemObject.get("text")).orElse(new CoolText("", CoolText.DEFAULT_FONT, Color.WHITE, 0.0));
        CoolText selectedText = CoolTextParser.parse(itemObject.get("selected_text")).orElse(null);
        Alignment alignment = AlignmentParser.parse(itemObject.get("alignment")).orElse(Alignment.Center);
        Alignment selectedAlignment = AlignmentParser.parse(itemObject.get("selected_alignment")).orElse(null);
        int[] offset = OffsetParser.parse(itemObject.get("offset"));
        Integer[] selectedOffset = OffsetParser.parseNullable(itemObject.get("selected_offset"));
        return new TextMenuItem(
                backgroundNormal,
                backgroundSelected,
                selectionSound,
                interactionSound,
                text,
                selectedText,
                alignment,
                selectedAlignment,
                offset[0],
                selectedOffset[0],
                offset[1],
                selectedOffset[1]
        );
    }
}
