package hyper.giga.ultra.gaming.menu.plus;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ScreenManager implements MouseMotionListener, KeyListener
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

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        screens[current].onKeyPress(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
