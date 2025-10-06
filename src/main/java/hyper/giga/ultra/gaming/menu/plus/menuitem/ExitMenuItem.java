package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;

public class ExitMenuItem extends ImageWithTextMenuItem
{
    public class Builder extends ImageWithTextMenuItem.Builder
    {
        public ImageWithTextMenuItem build(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound)
        {
            return new ExitMenuItem(buildCommonArgs(backgroundNormal, backgroundSelected, selectionSound, interactionSound));
        }
        
    }
    
    public ExitMenuItem(ImageWithTextMenuItemArgs args)
    {
        super(args);
    }
    
    @Override
    public MenuItemInteractionResult interact()
    {
        return MenuItemInteractionResult.EXIT;
    }
}
