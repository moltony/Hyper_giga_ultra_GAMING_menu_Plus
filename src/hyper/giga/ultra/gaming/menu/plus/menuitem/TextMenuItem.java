package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import java.awt.Graphics;

public class TextMenuItem extends MenuItem
{
    private CoolText text;
    private Alignment alignment;
    private int offsetX, offsetY;
    
    public TextMenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolText text, Alignment alignment, int offsetX, int offsetY)
    {
        super(backgroundNormal, backgroundSelected);
        this.text = text;
        this.alignment = alignment;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    @Override
    public void render(Graphics g, int y, int width, boolean selected)
    {
        super.render(g, y, width, selected);

        int pixelWidth = text.getPixelWidth(g);
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
        
        text.render(g, targetX, targetY);
    }
}
