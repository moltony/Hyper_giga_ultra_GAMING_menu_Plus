package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import java.awt.Graphics;
import java.util.Optional;

@SuppressWarnings("LocalVariableHidesMemberVariable")
public abstract class ImageWithTextMenuItem extends MenuItem
{
    protected Optional<ImageMenuItem> image;
    protected Optional<TextMenuItem> text;
    
    public ImageWithTextMenuItem(CoolBackground backgroundNormal, CoolBackground backgroundSelected, CoolSound selectionSound, CoolSound interactionSound, CoolImage image, Alignment imageAlignment, int imageOffsetX, int imageOffsetY, CoolText text, Alignment textAlignment, int textOffsetX, int textOffsetY)
    {
        super(backgroundNormal, backgroundSelected, selectionSound, interactionSound);
        
        this.text = Optional.ofNullable(new TextMenuItem(null, null, null, null, text, textAlignment, textOffsetX, textOffsetY));
        this.image = Optional.ofNullable(new ImageMenuItem(null, null, null, null, image, imageAlignment, imageOffsetX, imageOffsetY));
    }
    
    @Override
    public void update()
    {
        this.text.ifPresent(text -> text.update());
        this.image.ifPresent(image -> image.update());
        
        image.ifPresentOrElse(
                image ->
                    height = image.getHeight(),
                () ->
                    height = MenuItem.DEFAULT_HEIGHT
        );
    }
    
    @Override
    public void render(Graphics g, int y, int width, boolean selected)
    {
        super.render(g, y, width, selected);
        text.ifPresent(text -> text.render(g, y, width, selected));
        image.ifPresent(image -> image.render(g, y, width, selected));
    }
}
