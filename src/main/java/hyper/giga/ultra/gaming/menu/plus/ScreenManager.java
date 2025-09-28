package hyper.giga.ultra.gaming.menu.plus;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ScreenManager implements MouseMotionListener, KeyListener, MouseListener
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
    
    public boolean isCloseRequested()
    {
        return screens[current].isCloseRequested();
    }
    
    public boolean isSwitchScreenRequested()
    {
        return screens[current].isSwitchScreenRequested();
    }
    
    public int getRequestedScreenID()
    {
        return screens[current].getRequestedScreenID();
    }
    
    public void resetSwitchScreenRequest()
    {
        screens[current].resetSwitchScreenRequest();
    }
    
    public void setCurrentScreen(int screen)
    {
        current = screen;
    }
    
    public int getScreenWidth()
    {
        return screens[current].getWidth();
    }
    
    public int getScreenHeight()
    {
        return screens[current].getHeight();
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

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        screens[current].onMousePress(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }
}
