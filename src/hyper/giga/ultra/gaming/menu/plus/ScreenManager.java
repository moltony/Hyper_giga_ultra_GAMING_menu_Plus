package hyper.giga.ultra.gaming.menu.plus;

import java.awt.Graphics;

public class ScreenManager
{
    private Screen[] screens;
    private int current;
    
    public ScreenManager(Screen[] screens)
    {
        this.screens = screens;
        current = 0;
    }
    
    public void update()
    {
        screens[current].update();
    }
    
    public void render(Graphics g)
    {
        screens[current].render(g);
    }
}
