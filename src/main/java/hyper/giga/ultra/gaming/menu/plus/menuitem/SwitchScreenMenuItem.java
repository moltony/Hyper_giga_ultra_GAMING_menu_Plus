package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;


public class SwitchScreenMenuItem extends ImageWithTextMenuItem
{
    private int screenID;

    public class Builder extends ImageWithTextMenuItem.Builder
    {
        public SwitchScreenMenuItem build(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound, int screenID)
        {
            return new SwitchScreenMenuItem(buildCommonArgs(backgroundNormal, backgroundSelected, selectionSound, interactionSound), screenID);
        }
    }
    
    public SwitchScreenMenuItem(ImageWithTextMenuItemArgs args, int screenID)
    {
        super(args);
        this.screenID = screenID;
    }
    
    @Override
    public MenuItemInteractionResult interact()
    {
        return new MenuItemInteractionResult(screenID, MenuItemInteractionResultType.SwitchScreen);
    }
}
