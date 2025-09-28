package hyper.giga.ultra.gaming.menu.plus;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public class ScreenManager extends MouseInputAdapter
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

    //
    // events
    //

    @Override
    public void mouseMoved(MouseEvent e)
    {
        screens[current].onMouseMove(e.getX(), e.getY());
    }
}
