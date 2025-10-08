package hyper.giga.ultra.gaming.menu.plus.configuration;

import com.google.gson.JsonObject;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.ExitMenuItem;

public class ExitMenuItemParser
{
    public static ExitMenuItem parse(JsonObject itemObject, CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound)
    {
        ExitMenuItem.Builder builder = new ExitMenuItem.Builder();
        ImageWithTextMenuItemArgsParser.parse(builder, itemObject);
        ExitMenuItem item = builder.build(backgroundNormal, backgroundSelected, selectionSound, interactionSound);
        return item;
    }
}
