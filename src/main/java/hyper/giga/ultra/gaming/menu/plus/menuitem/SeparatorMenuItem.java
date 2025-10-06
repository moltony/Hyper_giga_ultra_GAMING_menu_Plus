package hyper.giga.ultra.gaming.menu.plus.menuitem;

import hyper.giga.ultra.gaming.menu.plus.cool.CoolBackground;
import hyper.giga.ultra.gaming.menu.plus.cool.CoolSound;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Optional;

public class SeparatorMenuItem extends MenuItem
{
    private Color color;
    private Optional<Color> selectedColor; 
    private int thickness;
    private Optional<Integer> selectedThickness;
    private int length;
    private Optional<Integer> selectedLength;
    private Alignment alignment;
    private Optional<Alignment> selectedAlignment;
    private int offsetX, offsetY;
    private Optional<Integer> selectedOffsetX, selectedOffsetY;
    
    public SeparatorMenuItem(
            CoolBackground backgroundNormal,
            CoolBackground backgroundSelected,
            CoolSound selectionSound,
            CoolSound interactionSound,
            Color color,
            Color selectedColor,
            int thickness,
            Integer selectedThickness,
            int length,
            Integer selectedLength,
            Alignment alignment,
            Alignment selectedAlignment,
            int offsetX,
            Integer selectedOffsetX,
            int offsetY,
            Integer selectedOffsetY
    )
    {
        super(backgroundNormal, backgroundSelected, selectionSound, interactionSound);
        this.color = color;
        this.selectedColor = Optional.ofNullable(selectedColor);
        this.thickness = thickness;
        this.selectedThickness = Optional.ofNullable(selectedThickness);
        this.length = length;
        this.selectedLength = Optional.ofNullable(selectedLength);
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
        int activeOffsetX = resolveState(selected, offsetX, selectedOffsetX);
        int activeOffsetY = resolveState(selected, offsetY, selectedOffsetY);
        Color activeColor = resolveState(selected, color, selectedColor);
        int activeThickness = resolveState(selected, thickness, selectedThickness);
        Alignment activeAlignment = resolveState(selected, alignment, selectedAlignment);
        
        super.render(g, y, width, selected);
        
        int pixelWidth = getPixelWidth(width, selected);
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
        
        g.setColor(activeColor);
        g.drawRect(targetX, targetY, pixelWidth, activeThickness - 1);
    }
    
    private int getPixelWidth(int screenWidth, boolean selected)
    {
        return (int)((double)screenWidth * ((double)resolveState(selected, length, selectedLength) / 100));
    }
}
