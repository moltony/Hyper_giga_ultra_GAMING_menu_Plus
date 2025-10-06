package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolImage;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import java.awt.Graphics;
import java.util.Optional;

public class ImageMenuItem extends MenuItem
{
    private CoolImage image;
    private Optional<CoolImage> selectedImage;
    private Alignment alignment;
    private Optional<Alignment> selectedAlignment;
    private int offsetX, offsetY;
    private Optional<Integer> selectedOffsetX, selectedOffsetY;
    
    public ImageMenuItem(
            CoolBackground backgroundNormal,
            CoolBackground backgroundSelected,
            CoolSound selectionSound,
            CoolSound interactionSound,
            CoolImage image,
            CoolImage selectedImage,
            Alignment alignment,
            Alignment selectedAlignment,
            int offsetX,
            Integer selectedOffsetX,
            int offsetY,
            Integer selectedOffsetY)
    {
        super(backgroundNormal, backgroundSelected, selectionSound, interactionSound);
        this.image = image;
        this.selectedImage = Optional.ofNullable(selectedImage);
        this.alignment = alignment;
        this.selectedAlignment = Optional.ofNullable(selectedAlignment);
        this.offsetX = offsetX;
        this.selectedOffsetX = Optional.ofNullable(selectedOffsetX);
        this.offsetY = offsetY;
        this.selectedOffsetY = Optional.ofNullable(selectedOffsetY);
    }
    
    @Override
    public void update()
    {
        height = (int)Math.max(MenuItem.DEFAULT_HEIGHT, image.getScaledHeight() + 5);
        image.update();
        selectedImage.ifPresent(i -> i.update());
    }
    
    @Override
    public void render(Graphics g, int y, int width, boolean selected)
    {
        CoolImage activeImage = resolveState(selected, image, selectedImage);
        int activeOffsetX = resolveState(selected, offsetX, selectedOffsetX);
        int activeOffsetY = resolveState(selected, offsetY, selectedOffsetY);
        Alignment activeAlignment = resolveState(selected, alignment, selectedAlignment);
        
        super.render(g, y, width, selected);
        
        int targetX = 0;
        int targetY = activeOffsetY + y;
        switch (activeAlignment) {
            case Center -> {
                targetX = (int)(double)((width / 2 - (int)(activeImage.getScaledWidth() / 2.0) + activeOffsetX));
            }
            case Left -> {
                targetX = activeOffsetX;
            }
            case Right -> {
                targetX = (int)(double)((width - (int)activeImage.getScaledWidth() - activeOffsetX));
            }
        }
        
        activeImage.render(g, targetX, targetY);
    }
}
