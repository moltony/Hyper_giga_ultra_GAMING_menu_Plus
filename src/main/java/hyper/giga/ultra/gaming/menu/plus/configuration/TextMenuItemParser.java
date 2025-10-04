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
        Alignment alignment = AlignmentParser.parse(itemObject.get("alignment")).orElse(Alignment.Center);
        int[] offset = OffsetParser.parse(itemObject.get("offset"));
        return new TextMenuItem(
                backgroundNormal,
                backgroundSelected,
                selectionSound,
                interactionSound,
                text,
                alignment,
                offset[0],
                offset[1]
        );
    }
}
