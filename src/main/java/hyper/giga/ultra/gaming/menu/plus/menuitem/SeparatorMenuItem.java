package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import java.awt.Color;
import java.awt.Graphics;

public class SeparatorMenuItem extends MenuItem
{
    private Color color;
    private int thickness;
    private int length;
    private Alignment alignment;
    private int offsetX, offsetY;
    
    public SeparatorMenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound, Color color, int thickness, int length, Alignment alignment, int offsetX, int offsetY)
    {
        super(backgroundNormal, backgroundSelected, selectionSound, interactionSound);
        this.color = color;
        this.thickness = thickness;
        this.length = length;
        this.alignment = alignment;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    @Override
    public void render(Graphics g, int y, int width, boolean selected)
    {
        super.render(g, y, width, selected);
        
        int pixelWidth = getPixelWidth(width);
        int targetX = 0;
        int targetY = height / 2 + offsetY + y;
        switch (alignment) {
            case Center -> {
                targetX = width / 2 - pixelWidth / 2 + offsetX;
            }
            case Left -> {
                targetX = offsetX;
            }
            case Right -> {
                targetX = width - pixelWidth - offsetX;
            }
        }
        
        g.setColor(color);
        g.drawRect(targetX, targetY, pixelWidth, thickness - 1);
    }
    
    private int getPixelWidth(int screenWidth)
    {
        return (int)((double)screenWidth * ((double)length / 100));
    }
}
