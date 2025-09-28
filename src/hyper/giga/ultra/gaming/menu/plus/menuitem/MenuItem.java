package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSolidColorBackground;
import java.awt.Color;
import java.awt.Graphics;

public abstract class MenuItem
{
    private CoolBackground backgroundNormal;
    private CoolBackground backgroundSelected;
    
    protected int height;
    
    public static final int DEFAULT_HEIGHT = 32;
    public static final CoolBackground BACKGROUND_NORMAL_DEFAULT = new CoolSolidColorBackground(new Color(61, 77, 145, 100));
    public static final CoolBackground BACKGROUND_SELECTED_DEFAULT = new CoolSolidColorBackground(new Color(61, 77, 145, 150));
    
    public MenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected)
    {
        this.backgroundNormal = backgroundNormal;
        this.backgroundSelected = backgroundSelected;
        height = DEFAULT_HEIGHT;
    }
    
    public void update()
    {
        backgroundNormal.update();
        backgroundSelected.update();
    }
    
    public void render(Graphics g, int y, int width, boolean selected)
    {
        if (selected) {
            backgroundSelected.render(g, 0, y, width, height);
        } else {
            backgroundNormal.render(g, 0, y, width, height);
        }
    }
    
    public void interact()
    {
    }
    
    public int getHeight()
    {
        return height;
    }
}
