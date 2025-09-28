package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;

public class ExitMenuItem extends ImageWithTextMenuItem
{
    public ExitMenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound, CoolImage image, Alignment imageAlignment, int imageOffsetX, int imageOffsetY, CoolText text, Alignment textAlignment, int textOffsetX, int textOffsetY)
    {
        super(backgroundNormal, backgroundSelected, selectionSound, interactionSound, image, imageAlignment, imageOffsetX, imageOffsetY, text, textAlignment, textOffsetX, textOffsetY);
    }
    
    @Override
    public MenuItemInteractionResult interact()
    {
        return MenuItemInteractionResult.EXIT;
    }
}
