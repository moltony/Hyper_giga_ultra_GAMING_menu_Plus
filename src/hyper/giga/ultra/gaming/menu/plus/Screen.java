package hyper.giga.ultra.gaming.menu.plus;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import java.awt.Graphics;
import java.util.ArrayList;

public class Screen
{
    private MenuItem[] items;
    private int width;
    private int height;
    private CoolBackground bg;
    
    public Screen(MenuItem[] items, int width, int height, CoolBackground bg)
    {
        this.items = items;
        this.width = width;
        this.height = height;
        this.bg = bg;
    }
    
    public void update()
    {
        for (MenuItem item : items) {
            item.update();
        }
    }
    
    public void render(Graphics g, int mouseX, int mouseY)
    {
        bg.render(g, 0, 0, width, height);
        
        int y = 0;
        
        for (MenuItem item : items) {
            item.render(g, y, width, mouseY > y && mouseY < y + item.getHeight());
            y += item.getHeight();
        }
    }
}
