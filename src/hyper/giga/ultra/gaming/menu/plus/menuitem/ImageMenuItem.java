package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import java.awt.Graphics;

public class ImageMenuItem extends MenuItem
{
    private CoolImage image;
    private Alignment alignment;
    private int offsetX, offsetY;
    
    public ImageMenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolImage image, Alignment alignment, int offsetX, int offsetY)
    {
        super(backgroundNormal, backgroundSelected);
        this.image = image;
        this.alignment = alignment;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
    
    @Override
    public void update()
    {
        height = (int)Math.max(MenuItem.DEFAULT_HEIGHT, image.getScaledHeight() + 5);
        image.update();
    }
    
    @Override
    public void render(Graphics g, int y, int width, boolean selected)
    {
        super.render(g, y, width, selected);
        
        int targetX = 0;
        int targetY = offsetY + y;
        switch (alignment) {
            case Center -> {
                targetX = (int)(double)((width / 2 - (int)(image.getScaledWidth() / 2.0) + offsetX));
            }
            case Left -> {
                targetX = offsetX;
            }
            case Right -> {
                targetX = (int)(double)((width - (int)image.getScaledWidth() - offsetX));
            }
        }
        
        image.render(g, targetX, targetY);
    }
}
