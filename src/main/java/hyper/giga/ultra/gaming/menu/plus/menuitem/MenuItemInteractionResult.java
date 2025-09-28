package hyper.giga.ultra.gaming.menu.plus.menuitem;

public class MenuItemInteractionResult
{
    private int screenID;
    private MenuItemInteractionResultType type;
    
    public static final MenuItemInteractionResult NONE = new MenuItemInteractionResult(0, MenuItemInteractionResultType.None);
    public static final MenuItemInteractionResult EXIT = new MenuItemInteractionResult(0, MenuItemInteractionResultType.Exit);
    
    public MenuItemInteractionResult(int screenID, MenuItemInteractionResultType type)
    {
        this.screenID = screenID;
        this.type = type;
    }

    public int getScreenID()
    {
        return screenID;
    }

    public MenuItemInteractionResultType getType()
    {
        return type;
    }
}
