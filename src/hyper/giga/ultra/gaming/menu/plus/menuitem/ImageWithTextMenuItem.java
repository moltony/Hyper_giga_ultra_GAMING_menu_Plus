package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import java.awt.Graphics;

public abstract class ImageWithTextMenuItem extends MenuItem
{
    protected ImageMenuItem image;
    protected TextMenuItem text;
    
    public ImageWithTextMenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolImage image, Alignment imageAlignment, int imageOffsetX, int imageOffsetY, CoolText text, Alignment textAlignment, int textOffsetX, int textOffsetY)
    {
        super(backgroundNormal, backgroundSelected);
        
        this.text = new TextMenuItem(null, null, text, textAlignment, textOffsetX, textOffsetY);
        this.image = new ImageMenuItem(null, null, image, imageAlignment, imageOffsetX, imageOffsetY);
    }
    
    @Override
    public void update()
    {
        this.text.update();
        this.image.update();
        
        height = image.getHeight();
    }
    
    @Override
    public void render(Graphics g, int y, int width, boolean selected)
    {
        super.render(g, y, width, selected);
        text.render(g, y, width, selected);
        image.render(g, y, width, selected);
    }
}
