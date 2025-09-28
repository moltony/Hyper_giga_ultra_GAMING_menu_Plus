package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;

public class SwitchScreenMenuItem extends ImageWithTextMenuItem
{
    private int screenID;
    
    public SwitchScreenMenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolImage image, Alignment imageAlignment, int imageOffsetX, int imageOffsetY, CoolText text, Alignment textAlignment, int textOffsetX, int textOffsetY, int screenID)
    {
        super(backgroundNormal, backgroundSelected, image, imageAlignment, imageOffsetX, imageOffsetY, text, textAlignment, textOffsetX, textOffsetY);
        this.screenID = screenID;
    }
    
    @Override
    public MenuItemInteractionResult interact()
    {
        return new MenuItemInteractionResult(screenID, MenuItemInteractionResultType.SwitchScreen);
    }
}
