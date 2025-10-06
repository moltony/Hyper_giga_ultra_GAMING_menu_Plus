package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolText;
import java.awt.Graphics;
import java.util.Optional;

public class TextMenuItem extends MenuItem
{
    private CoolText text;
    private Optional<CoolText> selectedText;
    private Alignment alignment;
    private Optional<Alignment> selectedAlignment;
    private int offsetX, offsetY;
    private Optional<Integer> selectedOffsetX, selectedOffsetY;
    
    public TextMenuItem(
            CoolBackground backgroundNormal,
            CoolBackground backgroundSelected,
            CoolSound selectionSound,
            CoolSound interactionSound,
            CoolText text,
            CoolText selectedText,
            Alignment alignment,
            Alignment selectedAlignment,
            int offsetX,
            Integer selectedOffsetX,
            int offsetY,
            Integer selectedOffsetY)
    {
        super(backgroundNormal, backgroundSelected, selectionSound, interactionSound);
        this.text = text;
        this.selectedText = Optional.ofNullable(selectedText);
        this.alignment = alignment;
        this.selectedAlignment = Optional.ofNullable(selectedAlignment);
        this.offsetX = offsetX;
        this.selectedOffsetX = Optional.ofNullable(selectedOffsetX);
        this.offsetY = offsetY;
        this.selectedOffsetY = Optional.ofNullable(selectedOffsetY);
    }
    
    @Override
    public void render(Graphics g, int y, int width, boolean selected)
    {
        CoolText activeText = resolveState(selected, text, selectedText);
        int activeOffsetX = resolveState(selected, offsetX, selectedOffsetX);
        int activeOffsetY = resolveState(selected, offsetY, selectedOffsetY);
        Alignment activeAlignment = resolveState(selected, alignment, selectedAlignment);
        
        super.render(g, y, width, selected);

        int pixelWidth = activeText.getPixelWidth(g);
        int targetX = 0;
        int targetY = height / 2 + activeOffsetY + y;
        switch (activeAlignment) {
            case Center -> {
                targetX = width / 2 - pixelWidth / 2 + activeOffsetX;
            }
            case Left -> {
                targetX = activeOffsetX;
            }
            case Right -> {
                targetX = width - pixelWidth - activeOffsetX;
            }
        }
        
        activeText.render(g, targetX, targetY);
    }
}
