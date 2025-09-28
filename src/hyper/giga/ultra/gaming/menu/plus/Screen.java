package hyper.giga.ultra.gaming.menu.plus;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItemInteractionResult;
import static hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItemInteractionResultType.Exit;
import static hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItemInteractionResultType.SwitchScreen;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Screen
{
    private MenuItem[] items;
    private int width;
    private int height;
    private CoolBackground bg;
    private int selected;
    private boolean closeRequested;
    private boolean switchScreenRequested;
    private int requestedScreenID;
    
    public Screen(MenuItem[] items, int width, int height, CoolBackground bg)
    {
        this.items = items;
        this.width = width;
        this.height = height;
        this.bg = bg;
        selected = 0;
        closeRequested = false;
        switchScreenRequested = false;
        requestedScreenID = 0;
    }
    
    public void update()
    {
        for (MenuItem item : items) {
            item.update();
        }
        bg.update();
    }
    
    public void render(Graphics g)
    {
        bg.render(g, 0, 0, width, height);
        
        int y = 0;
        
        for (int i = 0; i < items.length; i++) {
            MenuItem item = items[i];
            item.render(g, y, width, selected == i);
            y += item.getHeight();
        }
    }
    
    public boolean isCloseRequested()
    {
        return closeRequested;
    }
    
    //
    // events
    //
    
    public void onMouseMove(int mouseX, int mouseY)
    {
        selected = -1;
        int y = 0;
        for (int i = 0; i < items.length; i++) {
            MenuItem item = items[i];
            if (mouseY > y && mouseY < y + item.getHeight()) {
                selected = i;
            }                
            y += item.getHeight();
        }
    }
    
    public void onKeyPress(int keyCode)
    {
        System.out.println(keyCode);
        
        switch (keyCode) {
            case KeyEvent.VK_UP -> {
                selected--;
                if (selected < 0) {
                    selected = items.length - 1;
                }
            }
            case KeyEvent.VK_DOWN -> {
                selected = (selected + 1) % items.length;
            }
            case KeyEvent.VK_ENTER -> {
                handleInteraction();
            }
        }
    }
    
    public void onMousePress(int button)
    {
        handleInteraction();
    }

    public boolean isSwitchScreenRequested()
    {
        return switchScreenRequested;
    }
    
    public void resetSwitchScreenRequest()
    {
        switchScreenRequested = false;
    }

    public int getRequestedScreenID()
    {
        return requestedScreenID;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
    
    private void handleInteraction()
    {
        if (selected < 0 || selected >= items.length) {
            return;
        }
        
        MenuItemInteractionResult interactionResult = items[selected].interact();
        switch (interactionResult.getType()) {
            case Exit -> {
                closeRequested = true;
            }
            case SwitchScreen -> {
                switchScreenRequested = true;
                requestedScreenID = interactionResult.getScreenID();
                selected = 0;
            }
        }
    }
}
