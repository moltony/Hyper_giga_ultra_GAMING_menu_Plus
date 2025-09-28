package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSolidColorBackground;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Optional;

public abstract class MenuItem
{
    private Optional<CoolBackground> backgroundNormal;
    private Optional<CoolBackground> backgroundSelected;
    
    protected int height;
    
    public static final int DEFAULT_HEIGHT = 32;
    public static final CoolBackground BACKGROUND_NORMAL_DEFAULT = new CoolSolidColorBackground(new Color(61, 77, 145, 100));
    public static final CoolBackground BACKGROUND_SELECTED_DEFAULT = new CoolSolidColorBackground(new Color(61, 77, 145, 150));
    
    public MenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected)
    {
        this.backgroundNormal = Optional.ofNullable(backgroundNormal);
        this.backgroundSelected = Optional.ofNullable(backgroundSelected);
        height = DEFAULT_HEIGHT;
    }
    
    public void update()
    {
        backgroundNormal.ifPresent(bg -> bg.update());
        backgroundSelected.ifPresent(bg -> bg.update());
    }
    
    public void render(Graphics g, int y, int width, boolean selected)
    {
        if (selected) {
            backgroundSelected.ifPresentOrElse(
                    bg -> bg.render(g, 0, y, width, height),
                    () -> backgroundNormal.ifPresent(bg -> bg.render(g, 0, y, width, height))
            );
        } else {
            backgroundNormal.ifPresent(bg -> bg.render(g, 0, y, width, height));
        }
    }
    
    public MenuItemInteractionResult interact()
    {
        return new MenuItemInteractionResult(0, MenuItemInteractionResultType.None);
    }
    
    public int getHeight()
    {
        return height;
    }
}
