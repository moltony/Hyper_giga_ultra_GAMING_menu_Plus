package hyper.giga.ultra.gaming.menu.plus.cool;

import java.awt.Color;
import java.awt.Graphics;

public class CoolSolidColorBackground implements CoolBackground
{
    private Color color;
    
    public CoolSolidColorBackground(Color color)
    {
        this.color = color;
    }
    
    @Override
    public void update()
    {
    }
    
    @Override
    public void render(Graphics g, int x, int y, int width, int height)
    {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
}
