package hyper.giga.ultra.gaming.menu.plus;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItem;
import hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItemInteractionResult;
import static hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItemInteractionResultType.Exit;
import static hyper.giga.ultra.gaming.menu.plus.menuitem.MenuItemInteractionResultType.SwitchScreen;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Optional;

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
    private Optional<CoolSound> defaultSelectionSound;
    private Optional<CoolSound> defaultInteractionSound;
    
    public Screen(MenuItem[] items, int width, int height, CoolBackground bg, CoolSound defaultSelectionSound, CoolSound defaultInteractionSound)
    {
        this.items = items;
        this.width = width;
        this.height = height;
        this.bg = bg;
        this.defaultSelectionSound = Optional.ofNullable(defaultSelectionSound);
        this.defaultInteractionSound = Optional.ofNullable(defaultInteractionSound);
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
                if (selected != i) {
                    playSelectionSound(item);
                }
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
                playSelectionSound(items[selected]);
            }
            case KeyEvent.VK_DOWN -> {
                selected = (selected + 1) % items.length;
                playSelectionSound(items[selected]);
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
        playInteractionSound(items[selected]);
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
    
    private void playSelectionSound(MenuItem item)
    {
        if (item.hasSelectionSound()) {
            item.playSelectionSound();
            return;
        }
        
        defaultSelectionSound.ifPresent(sound -> sound.play());
    }
    
    private void playInteractionSound(MenuItem item)
    {
        if (item.hasInteractionSound()) {
            item.playInteractionSound();
            return;
        }
        
        defaultInteractionSound.ifPresent(sound -> sound.play());
    }
}
